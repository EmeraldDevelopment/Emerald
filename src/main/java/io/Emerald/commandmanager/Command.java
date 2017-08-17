package io.Emerald.commandmanager;

import io.Emerald.annotations.CommandMeta;
import io.Emerald.exceptions.CommandMetaException;
import io.Emerald.internal.api.ChannelType;
import io.Emerald.internal.api.SenderType;
import io.Emerald.internal.api.CommandSender;

/**
 * Command object
 */
public abstract class Command {

    private String command;
    private String usage;
    private String description;
    private boolean usePermissions;
    private int minimumArgs;
    private SenderType[] validSenders;
    private ChannelType[] validChannels;

    public Command() {
        CommandMeta meta = getClass().getAnnotation(CommandMeta.class);

        if (meta == null) {
            throw new CommandMetaException(getClass().getName() + " does not have a CommandMeta annotation!");
        }

        command = meta.command();
        usage = meta.usage();
        description = meta.description();
        usePermissions = meta.usePermissions();
        minimumArgs = meta.minimumArgs();
        validSenders = meta.validSenders();
        validChannels = meta.validChannels();
    }

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public boolean requiresPermissions() {
        return usePermissions;
    }

    public int getMinimumArgs() {
        return minimumArgs;
    }

    public SenderType[] getValidSenders() {
        return validSenders;
    }

    public ChannelType[] getValidChannels() {
        return validChannels;
    }

    /**
     * Executes the given command.
     *
     * @param sender The command source.
     * @param args The arguments given.
     */
    public abstract void execute(CommandSender sender, String[] args);
}
