package io.EmeraldDevelopment.Emerald.internal;

import io.EmeraldDevelopment.Emerald.commandmanager.Command;
import io.EmeraldDevelopment.Emerald.internal.plugin.CommandSender;
import sx.blah.discord.api.IShard;
import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IPresence;

public class EUser extends User implements CommandSender {

    public EUser(IShard shard, String name, long id, String discriminator, String avatar, IPresence presence, boolean isBot) {
        super(shard, name, id, discriminator, avatar, presence, isBot);
    }

    @Override
    public void sendMessage(String message) {
        getClient().getOrCreatePMChannel(this).sendMessage(message);
    }

    @Override
    public boolean hasPermission(Command command) {
        return false;
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.USER;
    }
}
