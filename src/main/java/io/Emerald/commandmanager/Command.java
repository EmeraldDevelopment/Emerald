package io.Emerald.commandmanager;

import io.Emerald.internal.api.ChannelType;
import io.Emerald.internal.api.CommandSender;
import io.Emerald.internal.api.SenderType;

public interface Command {

    /**
     * Get the label for this command.
     *
     * @return The command label.
     */
    String getCommand();

    /**
     * Gets the usage for this command.
     *
     * @return The command usage.
     */
    String getUsage();

    /**
     * Gets the description for this command.
     *
     * @return The command description.
     */
    String getDescription();

    /**
     * Checks if this command requires permission checking.
     *
     * @return True if the command requires permissions.
     */
    boolean requiresPermissions();

    /**
     * Gets the minimum number of arguments required for this command.
     *
     * @return The command's minimum argument count.
     */
    int getMinimumArgs();

    /**
     * Gets the valid sender types for this command.
     *
     * @return The command's valid sender types.
     */
    SenderType[] getValidSenders();

    /**
     * Gets the valid channel types this command can be sent from.
     *
     * @return The command's valid channel types.
     */
    ChannelType[] getValidChannels();

    /**
     * Executes the given command.
     *
     * @param sender The command source.
     * @param args The arguments given.
     */
    void execute(CommandSender sender, String[] args);
}
