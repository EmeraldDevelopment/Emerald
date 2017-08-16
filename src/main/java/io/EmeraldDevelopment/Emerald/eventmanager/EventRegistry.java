package io.EmeraldDevelopment.Emerald.eventmanager;

import io.EmeraldDevelopment.Emerald.internal.plugin.EmeraldPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The event registry for Emerald.
 */
public class EventRegistry {

    private static EventRegistry instance;
    private HashMap<EmeraldPlugin, List<Listener>> listeners = new HashMap<>();

    /**
     * Gets the instance of the listener registry.
     *
     * @return The listener registry instance.
     */
    public static EventRegistry getRegistry() {

        if (instance == null) {
            instance = new EventRegistry();
        }

        return instance;
    }

    /**
     * Gets the list of listeners currently registered in Emerald.
     *
     * @return The list of registered listeners.
     */
    public HashMap<EmeraldPlugin, List<Listener>> getRegisteredCommands() {
        return listeners;
    }

    /**
     * Gets the listeners for the requested plugin.
     *
     * @param plugin The plugin to check.
     * @return The listener list for the requested plugin.
     */
    public List<Listener> getRegisteredCommandsForPlugin(EmeraldPlugin plugin) {
        return listeners.get(plugin);
    }

    /**
     * Registers a set of listeners to the listener registry.
     *
     * @param plugin The plugin to register commands for.
     * @param lis The listeners to add.
     */
    public void registerEvents(EmeraldPlugin plugin, Listener... lis) {
        // Gets the plugin's listener list if it already exists, otherwise makes a new one
        List<Listener> pluginCommands = listeners.containsKey(plugin) ? listeners.get(plugin) : new ArrayList<>();
        // Add the listeners to the registry.
        pluginCommands.addAll(Arrays.asList(lis));
        listeners.put(plugin, pluginCommands);
    }
}
