package io.EmeraldDevelopment.Emerald.commandmanager;

import io.EmeraldDevelopment.Emerald.annotations.CommandMeta;
import io.EmeraldDevelopment.Emerald.exceptions.CommandMetaException;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Command object
 */
public abstract class Command {

    private String command;
    private String usage;
    private String description;
    private boolean usePermissions;

    public Command() {
        CommandMeta meta = getClass().getAnnotation(CommandMeta.class);

        if (meta == null) {
            throw new CommandMetaException(getClass().getName() + " does not have a CommandMeta annotation!");
        }

        command = meta.command();
        usage = meta.usage();
        description = meta.description();
        usePermissions = meta.usePermissions();
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

    public boolean isUsePermissions() {
        return usePermissions;
    }

    public abstract void execute(IMessage message, IUser user, String[] args);
}
