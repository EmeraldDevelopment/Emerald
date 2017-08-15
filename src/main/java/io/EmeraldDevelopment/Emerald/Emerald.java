package io.EmeraldDevelopment.Emerald;

import io.EmeraldDevelopment.Emerald.internal.plugin.EmeraldPluginLoader;
import io.EmeraldDevelopment.Emerald.internal.plugin.PluginLoader;
import org.xeustechnologies.jcl.JarClassLoader;
import sx.blah.discord.api.ClientBuilder;
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

    // Emerald Initialization
    public static void main(String[] args) {
        classLoader.add(getPluginDirectory().getName());

        // Create directories
        createDirectories();
        // Load plugins
        loadPlugins();
        // Login to the service
        login(args[0]);
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
        PluginLoader loader = new EmeraldPluginLoader();
        try {
            // TODO: Replace this with a dynamic system
            loader.loadPlugin(new JarFile("plugins/test.jar"));
        } catch (IOException e) {
            System.out.println("Failed to load plugin!");
        }
    }

    // Log into the service with the given token
    private static void login(String token) {

        try {
            new ClientBuilder().withToken(token).login();
        } catch (DiscordException e) {
            // TODO: Throw a text error here later.
        }
    }

    /**
     * Gets the plugin directory for Emerald.
     *
     * @return Emerald's plugin directory.
     */
    public static File getPluginDirectory() {
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
}
