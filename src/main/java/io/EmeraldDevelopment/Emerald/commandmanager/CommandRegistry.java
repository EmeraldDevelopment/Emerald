package io.EmeraldDevelopment.Emerald.commandmanager;

import java.util.List;

/**
 * The registry for every command Emerald currently has stored.
 */
public class CommandRegistry {

    private static List<Command> commands;

    /**
     * Gets the list of commands currently registered in Emerald.
     *
     * @return The list of registered commands.
     */
    public static List<Command> getRegisteredCommands() {
        return commands;
    }

    /**
     * Registers a set of commands to the command registry.
     *
     * @param cmd The commands to add.
     */
    public void registerCommands (Command... cmd) {

        for (Command command : cmd) {

            if (commands.contains(command)) {
                // TODO: Send warning message here later.
                continue;
            }

            commands.add(command);
        }
    }
}
