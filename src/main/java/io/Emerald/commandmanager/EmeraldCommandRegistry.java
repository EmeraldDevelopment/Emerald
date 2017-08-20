package io.Emerald.commandmanager;

import io.Emerald.Emerald;
import io.Emerald.commandmanager.internalcommands.HelpCommand;
import io.Emerald.commandmanager.internalcommands.StatsCommand;
import io.Emerald.internal.api.ChannelType;
import io.Emerald.internal.api.CommandSender;
import io.Emerald.internal.api.EmeraldUser;
import io.Emerald.internal.plugin.EmeraldPlugin;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The registry for every command Emerald currently has stored.
 */
public class EmeraldCommandRegistry implements CommandRegistry {

    private static EmeraldCommandRegistry registry;
    private List<Command> commands = new ArrayList<>();

    /**
     * Gets the instance of the command registry.
     *
     * @return The command registry instance.
     */
    public static EmeraldCommandRegistry getRegistry() {

        if (registry == null) {
            registry = new EmeraldCommandRegistry();
            Emerald.getClient().getDispatcher().registerListener(registry);
            // Register internal commands
            registry.registerInternals(
                    new HelpCommand(),
                    new StatsCommand()
            );
        }
        return registry;
    }

    /**
     * Gets the global list of commands.
     *
     * @return The global command list.
     */
    @Override @SuppressWarnings("unchecked")
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Gets the list of internal commands for Emerald.
     *
     * @return The list of internal commands.
     */
    @Override @SuppressWarnings("unchecked")
    public List<InternalCommand> getInternalCommands() {
        List<InternalCommand> internalCommands = new ArrayList<>();
        for (Command command : commands) {
            if (command instanceof InternalCommand) {
                internalCommands.add((InternalCommand) command);
            }
        }
        return internalCommands;
    }

    /**
     * Gets the list of commands added by plugins.
     *
     * @return The plugin commands list.
     */
    @Override @SuppressWarnings("unchecked")
    public List<PluginCommand> getPluginCommands() {
        List<PluginCommand> pluginCommands = new ArrayList<>();
        for (Command command : commands) {
            if (command instanceof PluginCommand) {
                pluginCommands.add((PluginCommand) command);
            }
        }
        return pluginCommands;
    }

    /**
     * Gets the commands for the requested plugin.
     *
     * @param plugin The plugin to check.
     * @return The command list for the requested plugin.
     */
    @SuppressWarnings("unchecked")
    public List<PluginCommand> getCommandsForPlugin(EmeraldPlugin plugin) {
        return getPluginCommands().stream().filter(command -> command.getPlugin().equals(plugin)).collect(Collectors.toList());
    }

    /**
     * Registers a set of commands to the command registry.
     *
     * @param plugin The plugin to register commands for.
     * @param pluginCommands The commands to add.
     */
    @Override
    public void registerCommands(EmeraldPlugin plugin, PluginCommand... pluginCommands) {
        List<String> names = getPluginCommands().stream().map(Command::getCommand).collect(Collectors.toList());

        for (PluginCommand command : pluginCommands) {
            // Skip current command if the command already exists in the plugin list
            if (names.contains(command.getCommand())) {
                continue;
            }
            // If the current command matches names with an internal command, disable the internal command
            for (InternalCommand ic : getInternalCommands()) {
                if (ic.getCommand().equals(command.getCommand())) {
                    ic.setEnabled(false);
                    break;
                }
            }
            // Add command to registry and update names list
            command.setPlugin(plugin);
            commands.add(command);
            names.add(command.getCommand());
        }
    }

    /**
     * Registers a set of default commands.
     *
     * @param command The commands to register.
     */
    private void registerInternals(InternalCommand... command) {
        commands.addAll(Arrays.asList(command));
    }

    @EventSubscriber // The user command handler
    public void onCommandSent(MessageReceivedEvent event) {
        String[] commandData = event.getMessage().getContent().split(" ");
        // Make sure the command beings with the prefix
        if (!commandData[0].startsWith("!")) {
            return;
        }
        // Process the command
        processCommand(new EmeraldUser(event.getAuthor()), event.getChannel(), commandData);
    }

    // Processes a command without a channel
    private void processCommand(CommandSender sender, String[] commandData) {
        processCommand(sender, null, commandData);
    }

    // Processes the command TODO: Clean this up a bit later
    private void processCommand(CommandSender sender, IChannel channel, String[] commandData) {
        // Ignore the event if client isn't ready or logged in
        if (!Emerald.getClient().isReady() || !Emerald.getClient().isLoggedIn()) {
            return;
        }

        String commandName = commandData[0].replaceFirst("!", "");
        String[] args = commandData.length > 1 ? Arrays.copyOfRange(commandData, 1, commandData.length) : new String[0];

        for (Command command : getCommands()) {
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
