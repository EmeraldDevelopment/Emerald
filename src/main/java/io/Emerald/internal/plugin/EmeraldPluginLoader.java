package io.Emerald.internal.plugin;

import io.Emerald.Emerald;
import io.Emerald.annotations.PluginMeta;
import org.xeustechnologies.jcl.JarClassLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

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
     * Loads a plugin from a given jar file.
     *
     * @param file The jar file to check.
     * @throws IOException If the plugin doesn't have a plugin.yml
     */
    @SuppressWarnings("unchecked")
    public void loadPlugin(JarFile file) throws IOException {
        InputStream stream = file.getInputStream(file.getEntry("plugin.yml"));
        Enumeration<JarEntry> entries = file.entries();
        HashSet<Class<?>> classMap = new HashSet<>();
        // Make sure the stream isn't null
        if (stream == null) {
            Logger.getLogger("Test").warning("Couldn't find plugin.yml");
            return;
        }
        // Try to load the plugin.yml into the bot
        Map<String, String> pluginConfig = (Map<String, String>) new Yaml().load(stream);
        // Return an error if the plugin config doesn't exist or doesn't contain a main key
        if (pluginConfig == null || !pluginConfig.containsKey("main")) {
            System.out.println("plugin.yml does not exist or is corrupted.");
            return;
        }
        // Get the value of the main key
        String mainClassURL = pluginConfig.get("main");
        // If the main key doesn't exist throw an error
        if (mainClassURL == null) {
            System.out.println("Main class not provided.");
            return;
        }
        // Loop over jar entries
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.getName().endsWith(".class")) {
                String name = entry.getName().replace("/", ".").replace(".class", "");

                try {
                    Class<?> loadedClass = Class.forName(name, true, loader);
                    classMap.add(loadedClass);
                } catch (ClassNotFoundException e) {
                    System.out.println("Failed to load class " + entry.getName() + " (Possibly corrupted?)");
                }
            }
        }
        // Resolve the main class from the URL
        Class<?> mainClass = null;
        for (Class<?> clazz : classMap) {
            if (clazz.getName().equals(mainClassURL)) {
                mainClass = clazz;
            }
        }
        // Check to make sure the main class isn't null
        if (mainClass == null) {
            System.out.println(mainClassURL + " does not point to a class.");
            stream.close();
            return;
        }
        // If the class doesn't extend EmeraldPlugin then unload it
        Class<? extends EmeraldPlugin> pluginClass;
        try {
            pluginClass = mainClass.asSubclass(EmeraldPlugin.class);
        } catch (ClassCastException e) {
            System.out.println(file.getName() + " is not a valid plugin and cannot be loaded! Contact the developers for help.");
            for (Class<?> clazz : classMap) {
                loader.unloadClass(clazz.getName());
            }
            return;
        }
        // Build the plugin and assign the class map to it.
        Plugin plugin;
        try {
            plugin = buildPlugin(pluginClass);
            plugin.setClassMap(classMap);
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("Error occurred in plugin loading.");
            return;
        }

        plugin.getPlugin().onEnable();
        stream.close();
    }

    /**
     * Partially builds the plugin without any commands or listeners.
     *
     * @param clazz The main plugin class (should extend EmeraldPlugin).
     * @return The partially built plugin.
     */
    private Plugin buildPlugin(Class<? extends EmeraldPlugin> clazz) throws IllegalAccessException, InstantiationException {
        EmeraldPlugin plugin = clazz.newInstance();
        PluginMeta meta = clazz.getAnnotation(PluginMeta.class);
        new File("plugins/" + meta.pluginName() + "/config.yml").mkdirs();

        // If the @PluginMeta annotation doesn't exist fill in default info
        if (meta == null) {
            return new Plugin(plugin, "", new String[0], "", "", false);
        }
        // Fill in plugin data from the @PluginMeta annotation
        return new Plugin(plugin, meta.pluginName(), meta.authors(), meta.version(), meta.permissionIdentifier(), meta.requireDatabase());
    }

    public void prepareUnload() {
        // Execute onDisable() code before shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Plugin plugin : PluginRepository.getInstance().getPlugins()) {
                plugin.getPlugin().onDisable();
            }
        }, "Shutdown"));
    }
}
