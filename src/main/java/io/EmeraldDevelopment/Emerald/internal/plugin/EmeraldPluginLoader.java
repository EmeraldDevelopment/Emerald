package io.EmeraldDevelopment.Emerald.internal.plugin;

import com.sun.istack.internal.NotNull;
import io.EmeraldDevelopment.Emerald.Emerald;
import org.xeustechnologies.jcl.JarClassLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public final class EmeraldPluginLoader implements PluginLoader {

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
            Class c = loader.loadClass(mainClassURL);
            // If the class doesn't extend EmeraldPlugin then unload it
            if (!c.isAssignableFrom(EmeraldPlugin.class)) {
                loader.unloadClass(c.getName());
                System.out.println("Class unloaded.");
            }

            stream.close();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
            return null;
        }
    }
}
