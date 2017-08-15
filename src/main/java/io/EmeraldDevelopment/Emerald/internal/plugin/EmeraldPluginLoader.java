package io.EmeraldDevelopment.Emerald.internal.plugin;

import com.sun.istack.internal.NotNull;
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

    /**
     * Loads a plugin from a given jar file.
     *
     * @param file The jar file to check.
     * @return The plugin object.
     * @throws IOException If the plugin doesn't have a plugin.yml
     */
    @SuppressWarnings("unchecked")
    public Plugin loadPlugin(@NotNull JarFile file) throws IOException {
        InputStream stream = file.getInputStream(file.getEntry("plugin.yml"));
        JarClassLoader loader = Emerald.getClassLoader();

        // Make sure the stream isn't null
        if (stream == null) {
            Logger.getLogger("Test").warning("Failed to find plugin.yml");
            return null;
        }

        try {
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

            // Load the main class
            Class mainClass = loader.loadClass(mainClassURL);
            // If the class doesn't extend EmeraldPlugin then unload it
            if (!mainClass.isAssignableFrom(EmeraldPlugin.class)) {
                loader.unloadClass(mainClass.getName());
                System.out.println(file + " is not a valid plugin! Contact the developers for help.");
                return null;
            }
            // Build an unfinished plugin
            Plugin plugin = buildPluginMeta(mainClass);

            stream.close();
            return plugin;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
            return null;
        }
    }

    /**
     * Partially builds the plugin without any commands or listeners.
     *
     * @param clazz The main plugin class (should extend EmeraldPlugin)
     * @return The partially built plugin
     */
    private Plugin buildPluginMeta(Class clazz) {
        PluginMeta meta = null;
        // Loop over class annotations
        for (Annotation annotation : clazz.getClass().getAnnotations()) {
            // Break if the class contains a @PluginMeta annotation
            if (annotation instanceof PluginMeta) {
                meta = (PluginMeta) annotation;
                break;
            }
        }
        // If the @PluginMeta annotation doesn't exist fill in default info
        if (meta == null) {
            return new Plugin("", new String[0], "", "", false);
        }
        // Fill in plugin data from the @PluginMeta annotation
        return new Plugin(meta.pluginName(), meta.authors(), meta.version(), meta.permissionIdentifier(), meta.requireDatabase());
    }
}
