package io.Emerald.commandmanager;

import io.Emerald.annotations.CommandMeta;
import io.Emerald.exceptions.CommandMetaException;
import io.Emerald.internal.api.ChannelType;
import io.Emerald.internal.api.CommandSender;
import io.Emerald.internal.api.SenderType;
import sx.blah.discord.handle.obj.IMessage;

public abstract class InternalCommand implements Command {

    private CommandMeta meta;
    private boolean isEnabled;

    public InternalCommand() {
        meta = getClass().getAnnotation(CommandMeta.class);
        isEnabled = true;

        if (meta == null) {
            throw new CommandMetaException(getClass().getName() + " does not have a CommandMeta annotation!");
        }
    }

    /**
     * Get the label for this command.
     *
     * @return The command label.
     */
    @Override
    public String getCommand() {
        return meta.command();
    }

    /**
     * Gets the usage for this command.
     *
     * @return The command usage.
     */
    @Override
    public String getUsage() {
        return meta.usage();
    }

    /**
     * Gets the description for this command.
     *
     * @return The command description.
     */
    @Override
    public String getDescription() {
        return meta.description();
    }

    /**
     * Checks if this command requires permission checking.
     *
     * @return True if the command requires permissions.
     */
    @Override
    public boolean requiresPermissions() {
        return meta.usePermissions();
    }

    /**
     * Gets the minimum number of arguments required for this command.
     *
     * @return The command's minimum argument count.
     */
    @Override
    public int getMinimumArgs() {
        return meta.minimumArgs();
    }

    /**
     * Gets the valid sender types for this command.
     *
     * @return The command's valid sender types.
     */
    @Override
    public SenderType[] getValidSenders() {
        return new SenderType[]{SenderType.USER, SenderType.CONSOLE};
    }

    /**
     * Gets the valid channel types this command can be sent from.
     *
     * @return The command's valid channel types.
     */
    @Override
    public ChannelType[] getValidChannels() {
        return new ChannelType[]{ChannelType.PRIVATE, ChannelType.PUBLIC};
    }

    /**
     * Executes the given command.
     *
     * @param sender The command source.
     * @param args   The arguments given.
     */
    @Override
    public abstract void execute(CommandSender sender, IMessage message, String[] args);

    /**
     * Checks if the internal command is enabled.
     *
     * @return True if the command is enabled.
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Sets if this command is enabled.
     *
     * @param isEnabled Sets if this command is enabled.
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
