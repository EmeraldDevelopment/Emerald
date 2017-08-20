package io.Emerald.commandmanager;

import io.Emerald.internal.plugin.EmeraldPlugin;

import java.util.List;

public interface CommandRegistry {

    /**
     * Gets the global list of commands.
     *
     * @return The global command list.
     */
    List<? extends Command> getCommands();

    /**
     * Gets the list of default Emerald commands.
     *
     * @return The internal commands list.
     */
    List<InternalCommand> getInternalCommands();

    /**
     * Gets the list of commands added by plugins.
     *
     * @return The plugin commands list.
     */
    List<PluginCommand> getPluginCommands();

    /**
     * Registers a set of commands for a given plugin.
     *
     * @param plugin The plugin to register to.
     * @param pluginCommands The commands to register.
     */
    void registerCommands(EmeraldPlugin plugin, PluginCommand... pluginCommands);
}
