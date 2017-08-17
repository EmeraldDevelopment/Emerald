package io.Emerald;

import io.Emerald.internal.plugin.EmeraldPluginLoader;
import io.Emerald.internal.plugin.PluginLoader;
import org.xeustechnologies.jcl.JarClassLoader;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

/**
 * Emerald is a Discord bot built with the same idea as Bukkit. Most Discord bots are generally designed
 * as a single unchanging instance that cannot be changed or modified in at best simple ways. Emerald is
 * designed to change that by allowing developers to simply build plugins to expand the capabilities of
 * their bots.
 *
 * @author Atrius
 * @author mikethemaker
 */
public class Emerald {

    private static File plugins = new File("plugins/");
    private static File users = new File("users/");
    private static File guilds = new File("guilds/");
    private static JarClassLoader classLoader = new JarClassLoader();
    private static IDiscordClient client;

    // Emerald Initialization
    public static void main(String[] args) {
        classLoader.add(getPluginDirectory().getName());

        // Login to the service
        login(args[0]);
        // Create directories
        createDirectories();
        // Load plugins
        loadPlugins();
    }

    // Create directories for users and guilds
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createDirectories() {
        if (!plugins.exists()) plugins.mkdir();
        if (!users.exists()) users.mkdir();
        if (!guilds.exists()) guilds.mkdir();
    }

    // Load the plugins in the plugins directory
    private static void loadPlugins() {
        PluginLoader loader = EmeraldPluginLoader.getInstance();
        try {
            File[] files = getPluginDirectory().listFiles();
            // Check if the directory is empty
            if (files == null) {
                return;
            }

            for (File file : files) {
                // Load the file as a jar file
                if (file.getName().endsWith(".jar")) {
                    System.out.println("Loading plugin " + file.getName() + "...");
                    loader.loadPlugin(new JarFile(file));
                }
            }
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            System.out.println("Failed to load plugin! (Possibly corrupted?)");
        }
    }

    // Log into the service with the given token
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void login(String token) {
        try {
            client = new ClientBuilder().withToken(token).login();
        } catch (DiscordException e) {
            System.out.println("Failed to login! (Token possibly incorrect?)");
        }
        // Deletes Discord4J's module directory
        new File("modules/").delete();
    }

    /**
     * Gets the plugin directory for Emerald.
     *
     * @return Emerald's plugin directory.
     */
    private static File getPluginDirectory() {
        return plugins;
    }

    /**
     * Gets the class loader to be used for plugin registration.
     *
     * @return The plugin class loader.
     */
    public static JarClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Gets the Discord client for Emerald.
     *
     * @return Emerald's discord client.
     */
    public static IDiscordClient getClient() {
        return client;
    }
}
