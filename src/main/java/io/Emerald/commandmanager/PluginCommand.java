package io.Emerald.commandmanager;

import io.Emerald.annotations.CommandMeta;
import io.Emerald.exceptions.CommandMetaException;
import io.Emerald.internal.api.ChannelType;
import io.Emerald.internal.api.CommandSender;
import io.Emerald.internal.api.SenderType;
import io.Emerald.internal.plugin.EmeraldPlugin;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Plugin command object
 */
public abstract class PluginCommand implements Command {

    private CommandMeta meta;
    private EmeraldPlugin plugin;


    public PluginCommand() {
        meta = getClass().getAnnotation(CommandMeta.class);

        if (meta == null) {
            throw new CommandMetaException(getClass().getName() + " does not have a CommandMeta annotation!");
        }
    }

    @Override
    public String getCommand() {
        return meta.command();
    }

    @Override
    public String getUsage() {
        return meta.usage();
    }

    @Override
    public String getDescription() {
        return meta.description();
    }

    @Override
    public boolean requiresPermissions() {
        return meta.usePermissions();
    }

    @Override
    public int getMinimumArgs() {
        return meta.minimumArgs();
    }

    @Override
    public SenderType[] getValidSenders() {
        return meta.validSenders();
    }

    @Override
    public ChannelType[] getValidChannels() {
        return meta.validChannels();
    }

    @Override
    public abstract void execute(CommandSender sender, IMessage message, String[] args);

    public EmeraldPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(EmeraldPlugin plugin) {
        this.plugin = plugin;
    }
}
