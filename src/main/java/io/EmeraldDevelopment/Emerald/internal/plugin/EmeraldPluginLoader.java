package io.EmeraldDevelopment.Emerald.internal.plugin;

import io.EmeraldDevelopment.Emerald.Emerald;
import io.EmeraldDevelopment.Emerald.annotations.PluginMeta;
import org.xeustechnologies.jcl.JarClassLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public final class EmeraldPluginLoader implements PluginLoader {

    private static EmeraldPluginLoader instance;

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
     * @return The plugin object.
     * @throws IOException If the plugin doesn't have a plugin.yml
     */
    @SuppressWarnings("unchecked")
    public Plugin loadPlugin(JarFile file) throws IOException {
        InputStream stream = file.getInputStream(file.getEntry("plugin.yml"));
        JarClassLoader loader = Emerald.getClassLoader();
        // Make sure the stream isn't null
        if (stream == null) {
            Logger.getLogger("Test").warning("Failed to find plugin.yml");
            return null;
        }
        // Try to load the plugin.yml into the bot
        Map<String, String> pluginConfig = (Map<String, String>) new Yaml().load(stream);
        // Return an error if the plugin config doesn't exist or doesn't contain a main key
        if (pluginConfig == null || !pluginConfig.containsKey("main")) {
            System.out.println("plugin.yml does not exist or is corrupted.");
            return null;
        }
        // Get the value of the main key
        String mainClassURL = pluginConfig.get("main");
        // If the main key doesn't exist throw an error
        if (mainClassURL == null) {
            System.out.println("Main class not provided.");
            return null;
        }
        // Resolve the main class from the URL
        Class<?> mainClass;
        try {
              mainClass = Class.forName(mainClassURL, true, loader);
        } catch (ClassNotFoundException e) {
            System.out.println(mainClassURL + " does not point to a class.");
            stream.close();
            return null;
        }
        // If the class doesn't extend EmeraldPlugin then unload it
        Class<? extends EmeraldPlugin> pluginClass;
        try {
            pluginClass = mainClass.asSubclass(EmeraldPlugin.class);
        } catch (ClassCastException e) {
            System.out.println(file.getName() + " is not a valid plugin and cannot be loaded! Contact the developers for help.");
            loader.unloadClass(mainClass.getName());
            return null;
        }
        // Build and run the enable for the plugin
        Plugin plugin;
        try {
            plugin = buildPlugin(pluginClass);
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("Error occurred in plugin loading.");
            return null;
        }
        // Run the plugin enable code
        plugin.getPlugin().onEnable();
        stream.close();
        return plugin;
    }

    /**
     * Partially builds the plugin without any commands or listeners.
     *
     * @param clazz The main plugin class (should extend EmeraldPlugin).
     * @return The partially built plugin.
     */
    private Plugin buildPlugin(Class<? extends EmeraldPlugin> clazz) throws IllegalAccessException, InstantiationException {
        PluginMeta meta = null;
        EmeraldPlugin plugin;
        plugin = clazz.newInstance();
        // Loop over class annotations
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation instanceof PluginMeta) {
                meta = (PluginMeta) annotation;
                break;
            }
        }
        // If the @PluginMeta annotation doesn't exist fill in default info
        if (meta == null) {
            return new Plugin(plugin, "", new String[0], "", "", false);
        }
        // Fill in plugin data from the @PluginMeta annotation
        return new Plugin(plugin, meta.pluginName(), meta.authors(), meta.version(), meta.permissionIdentifier(), meta.requireDatabase());
    }
}
