package io.Emerald.commandmanager;

import io.Emerald.Emerald;
import io.Emerald.internal.api.ChannelType;
import io.Emerald.internal.api.CommandSender;
import io.Emerald.internal.api.EmeraldUser;
import io.Emerald.internal.plugin.EmeraldPlugin;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

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
    private IDiscordClient client = Emerald.getClient();

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
    public List<Command> getCommandList() {
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

    @EventSubscriber // The user command handler
    public void onCommandSent(MessageReceivedEvent event) {

        String[] commandData = event.getMessage().getContent().split(" ");
        // Make sure the command beings with the prefix
        if (!commandData[0].startsWith("!")) {
            return;
        }

        processCommand(new EmeraldUser(event.getAuthor()), event.getChannel(), commandData);
    }

    // Processes a command without a channel
    private void processCommand(CommandSender sender, String[] commandData) {
        processCommand(sender, null, commandData);
    }

    // Processes the command with a given channel
    private void processCommand(CommandSender sender, IChannel channel, String[] commandData) {
        // Ignore the event if client isn't ready or logged in
        if (!client.isReady() || !client.isLoggedIn()) {
            return;
        }

        String commandName = commandData[0].replaceFirst("!", "");
        String[] args = commandData.length > 1 ? Arrays.copyOfRange(commandData, 1, commandData.length) : new String[0];

        for (Command command : getCommandList()) {
            // Skip the current command if it doesn't match the sent command
            if (!command.getCommand().equalsIgnoreCase(commandName)) {
                continue;
            }
            // Check if the user has the proper permissions
            if (command.requiresPermissions()) {
                if (!sender.hasPermission(command)) {
                    return;
                }
            }
            // Make sure the command has the minimum required argument count
            if (args.length < command.getMinimumArgs()) {
                sender.sendMessage(command.getUsage());
                return;
            }
            // Make sure sender is a valid command sender
            if (!Arrays.asList(command.getValidSenders()).contains(sender.getSenderType())) {
                return;
            }

            if (channel != null && !Arrays.asList(command.getValidChannels()).contains(ChannelType.getChannelType(channel))) {
                return;
            }

            // Execute the command
            command.execute(sender, args);
            break;
        }
    }
}
