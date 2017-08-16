package io.EmeraldDevelopment.Emerald.commandmanager;

import io.EmeraldDevelopment.Emerald.Emerald;
import io.EmeraldDevelopment.Emerald.internal.plugin.EmeraldPlugin;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The registry for every command Emerald currently has stored.
 */
public class CommandRegistry {

    private static CommandRegistry registry;
    private HashMap<EmeraldPlugin, List<Command>> commands = new HashMap<>();

    /**
     * Gets the instance of the command registry.
     *
     * @return The command registry instance.
     */
    public static CommandRegistry getRegistry() {

        if (registry == null) {
            registry = new CommandRegistry();
        }

        Emerald.getClient().getDispatcher().registerListener(registry);
        return registry;
    }

    /**
     * Gets the list of commands currently registered in Emerald.
     *
     * @return The list of registered commands.
     */
    public HashMap<EmeraldPlugin, List<Command>> getRegisteredCommands() {
        return commands;
    }

    /**
     * Gets a flattened list of commands registered in Emerald.
     *
     * @return The flattened command list.
     */
    public List<Command> getFlattenedCommandList() {
        List<Command> allCommands = new ArrayList<>();

        for (List<Command> cmd : commands.values()) {
            allCommands.addAll(cmd);
        }

        return allCommands;
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
        // Add the commands to the registry.
        pluginCommands.addAll(Arrays.asList(cmd));
        commands.put(plugin, pluginCommands);
    }

    @EventSubscriber
    public void onCommandSent(MessageReceivedEvent event) {

        IDiscordClient client = event.getClient();
        IMessage message = event.getMessage();
        IUser user = event.getAuthor();

        // Ignore the event if client isn't ready or logged in
        if (!client.isReady() || !client.isLoggedIn()) {
            return;
        }

        String[] commandData = message.getContent().split(" ");

        if (commandData.length < 1) {
            return;
        }

        String commandName = commandData[0].replaceFirst("!", "");
        String[] args = commandData.length > 1 ? Arrays.copyOfRange(commandData, 1, commandData.length) : new String[0];

        for (Command command : getFlattenedCommandList()) {

            // Skip the current command if it doesn't match the sent command
            if (!command.getCommand().equalsIgnoreCase(commandName)) {
                continue;
            }

            if (command.requiresPermissions()) {
                // TODO: Add permission system.
            }

            // Make sure the command has the minimum required argument count
            if (args.length < command.getMinimumArgs()) {
                return;
            }

            // TODO: Add sender type detection.
            command.execute(args); // TODO Implement command definition properly
            break;
        }
    }
}
