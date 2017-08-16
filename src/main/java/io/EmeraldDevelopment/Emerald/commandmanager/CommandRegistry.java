package io.EmeraldDevelopment.Emerald.commandmanager;

import io.EmeraldDevelopment.Emerald.internal.plugin.EmeraldPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The registry for every command Emerald currently has stored.
 */
public class CommandRegistry {

    private static CommandRegistry registry;
    private LinkedHashMap<EmeraldPlugin, List<Command>> commands;

    /**
     * Gets the instance of the command registry.
     *
     * @return The command registry instance.
     */
    public static CommandRegistry getRegistry() {

        if (registry == null) {
            registry = new CommandRegistry();
        }

        return registry;
    }

    /**
     * Gets the list of commands currently registered in Emerald.
     *
     * @return The list of registered commands.
     */
    public LinkedHashMap<EmeraldPlugin, List<Command>> getRegisteredCommands() {
        return commands;
    }

    /**
     * Gets the commands for the requested plugin.
     *
     * @param plugin The plugin to check.
     * @return The command list for the requested plugin.
     */
    public List<Command> getRegisteredCommandsForPlugin(EmeraldPlugin plugin) {
        return commands.get(plugin);
    }

    /**
     * Registers a set of commands to the command registry.
     *
     * @param plugin The plugin to register commands for.
     * @param cmd The commands to add.
     */
    public void registerCommands(EmeraldPlugin plugin, Command... cmd) {
        // Gets the plugin's command list if it already exists, otherwise makes a new one
        List<Command> pluginCommands = commands.containsKey(plugin) ? commands.get(plugin) : new ArrayList<>();
        // Adds the commands to the
        pluginCommands.addAll(Arrays.asList(cmd));
        commands.put(plugin, pluginCommands);
    }
}
