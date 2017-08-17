package io.Emerald.internal.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Emerald's plugin repository.
 */
public class PluginRepository {

    private static PluginRepository instance;
    private List<Plugin> plugins = new ArrayList<>();

    /**
     * Creates an instance of the plugin repository.
     *
     * @return The instance of the plugin repository.
     */
    public static PluginRepository getInstance() {

        if (instance == null) {
            instance = new PluginRepository();
        }

        return instance;
    }

    /**
     * Registers a plugin or set of plugins to the plugin repository.
     *
     * @param plugin The plugin(s) to register.
     */
    public void registerPlugins(Plugin... plugin) {
        plugins.addAll(Arrays.asList(plugin));
    }

    /**
     * Returns a list of every currently loaded plugin in the repository.
     *
     * @return The plugin list for the repository.
     */
    public List<Plugin> getPlugins() {
        return plugins;
    }

    /**
     * Gets the internal plugin object from the plugin repository.
     *
     * @param plugin The Emerald Plugin object to check for.
     * @return The plugin object for the given plugin parameter.
     */
    public Plugin getPlugin(EmeraldPlugin plugin) {

        for (Plugin p : plugins) {
            if (p.getClassMap().contains(plugin.getClass())) {
                return p;
            }
        }

        return null;
    }
}
