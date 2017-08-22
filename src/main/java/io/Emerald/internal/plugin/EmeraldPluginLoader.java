package io.Emerald.internal.plugin;

import io.Emerald.Emerald;
import org.xeustechnologies.jcl.JarClassLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public final class EmeraldPluginLoader implements PluginLoader {

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
        // Try to load the plugin config
        Map<String, String> pluginConfig = (Map<String, String>) new Yaml().load(stream);
        if (pluginConfig == null || !pluginConfig.containsKey("main")) {
            System.out.println("plugin.yml does not exist or is improperly written.");
            return null;
        }
        // Get the main class name from the config
        String mainClassName = pluginConfig.get("main");
        if (mainClassName == null) {
            System.out.println("Main class not provided.");
            return null;
        }
        // Load all classes in the jar.
        for (JarEntry entry : Collections.list(file.entries())) {
            if (!entry.getName().endsWith(".class")) {
                continue;
            }
            // Attempt to load the current entry as a class
            try {
                Class<?> loadedClass = Class.forName(entry.getName().replace("/", ".").replace(".class", ""), true, loader);
                classMap.add(loadedClass);
            } catch (ClassNotFoundException e) {
                System.out.println("Failed to load class " + entry.getName() + " (Not Found)");
            }
        }
        // Attempt to find the main class using the provided class name
        List<Class<?>> main = classMap.stream().filter(clazz -> clazz.getName().equals(mainClassName)).collect(Collectors.toList());
        if (main.isEmpty()) {
            System.out.println(mainClassName + " does not point to a class.");
            stream.close();
            return null;
        }
        // Attempt to build the plugin
        Class<? extends EmeraldPlugin> pluginClass;
        EmeraldPlugin plugin;
        try {
            pluginClass = main.get(0).asSubclass(EmeraldPlugin.class);
            plugin = pluginClass.newInstance();
            plugin.setClassMap(classMap);
        } catch (ClassCastException | IllegalAccessException | InstantiationException e) {
            System.out.println(file.getName() + " cannot be loaded! (Plugin either isn't valid or corrupted; contact the developers for help.)");
            // Unload the class map
            for (Class<?> clazz : classMap) {
                loader.unloadClass(clazz.getName());
            }
            return null;
        }
        // Run the plugin's onEnable
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

    /**
     * Unloads a specific plugin.
     *
     * @param plugin The plugin to unload.
     */
    @Override
    public void unloadPlugin(EmeraldPlugin plugin) {
        // Run plugin onDisable
        plugin.onDisable();
        // Unload the class map
        for (Class clazz : plugin.getClassMap()) {
            loader.unloadClass(clazz.getName());
        }
    }
}
