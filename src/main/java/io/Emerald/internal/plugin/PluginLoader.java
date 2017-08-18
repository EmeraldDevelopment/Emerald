package io.Emerald.internal.plugin;

import java.io.IOException;
import java.util.jar.JarFile;

/**
 * Emerald's plugin loader interface.
 */
public interface PluginLoader {

    /**
     * Loads a jar file as a plugin.
     *
     * @param file The jar file to load
     *
     * @throws IOException If the jar couldn't be loaded for some reason
     * @throws IllegalAccessException If part of the jar couldn't be properly loaded.
     * @throws InstantiationException If the jar's main class couldn't be instantiated.
     */
    void loadPlugin(JarFile file) throws IOException, IllegalAccessException, InstantiationException;

    /**
     * Prepares the application to unload plugins on shutdown.
     */
    void prepareUnload();

    /**
     * Unloads a specific plugin.
     *
     * @param plugin The plugin to unload.
     */
    void unloadPlugin(Plugin plugin);
}
