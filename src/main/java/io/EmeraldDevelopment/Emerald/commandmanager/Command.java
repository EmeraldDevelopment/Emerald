package io.EmeraldDevelopment.Emerald.commandmanager;

import io.EmeraldDevelopment.Emerald.annotations.CommandMeta;
import io.EmeraldDevelopment.Emerald.exceptions.CommandMetaException;
import io.EmeraldDevelopment.Emerald.internal.SenderType;

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

    public abstract void execute(String[] args);
}
