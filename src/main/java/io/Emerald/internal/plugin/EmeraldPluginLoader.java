package io.Emerald.internal.plugin;

import io.Emerald.Emerald;
import org.xeustechnologies.jcl.JarClassLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class EmeraldPluginLoader implements PluginLoader {
    // TODO : More improvements to come
    private static EmeraldPluginLoader instance;
    private JarClassLoader loader = Emerald.getClassLoader();

    /**
     * Gets the instance of the plugin loader.
     *
     * @return The instance of the plugin loader.
     */
    public static EmeraldPluginLoader getInstance() {

        if (instance == null) {
            instance = new EmeraldPluginLoader();
        }

        return instance;
    }

    /**
     * Loads a jar file as a plugin.
     *
     * @param file The jar file to load
     *
     * @throws IOException If the jar couldn't be loaded for some reason
     *
     * @return The finished plugin.
     */
    @Override @SuppressWarnings("unchecked")
    public Plugin loadPlugin(JarFile file) throws IOException {
        InputStream stream = file.getInputStream(file.getEntry("plugin.yml"));
        HashSet<Class<?>> classMap = new HashSet<>();

        if (stream == null) {
            System.out.println("Couldn't find plugin.yml");
            return null;
        }

        Map<String, String> pluginConfig = (Map<String, String>) new Yaml().load(stream);

        if (pluginConfig == null || !pluginConfig.containsKey("main")) {
            System.out.println("plugin.yml does not exist or is improperly written.");
            return null;
        }

        String mainClassURL = pluginConfig.get("main");

        if (mainClassURL == null) {
            System.out.println("Main class not provided.");
            return null;
        }

        for (JarEntry entry : Collections.list(file.entries())) {
            if (entry.getName().endsWith(".class")) {
                try {
                    Class<?> loadedClass = Class.forName(entry.getName().replace("/", ".").replace(".class", ""), true, loader);
                    classMap.add(loadedClass);
                } catch (ClassNotFoundException e) {
                    System.out.println("Failed to load class " + entry.getName() + " (Possibly corrupted?)");
                }
            }
        }

        Class<?> mainClass = null;
        for (Class<?> clazz : classMap) {
            if (clazz.getName().equals(mainClassURL)) {
                mainClass = clazz;
            }
        }

        if (mainClass == null) {
            System.out.println(mainClassURL + " does not point to a class.");
            stream.close();
            return null;
        }

        Class<? extends EmeraldPlugin> pluginClass;
        try {
            pluginClass = mainClass.asSubclass(EmeraldPlugin.class);
        } catch (ClassCastException e) {
            System.out.println(file.getName() + " is not a valid plugin and cannot be loaded! Contact the developers for help.");
            for (Class<?> clazz : classMap) {
                loader.unloadClass(clazz.getName());
            }
            return null;
        }

        EmeraldPlugin plugin;
        try {
            plugin = pluginClass.newInstance();
            plugin.setClassMap(classMap);
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("Error occurred in plugin loading.");
            return null;
        }

        plugin.onEnable();
        stream.close();
        return plugin;
    }

    @Override
    public void prepareUnload() {
        // Execute onDisable() code before shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (EmeraldPlugin plugin : PluginRepository.getInstance().getPlugins()) {
                unloadPlugin(plugin);
            }
        }, "Shutdown"));
    }

    @Override
    public void unloadPlugin(EmeraldPlugin plugin) {
        plugin.onDisable();
        for (Class clazz : plugin.getClassMap()) {
            loader.unloadClass(clazz.getName());
        }
    }
}
