package io.Emerald.internal.api.commandsender;

import io.Emerald.commandmanager.Command;
import sx.blah.discord.api.IShard;
import sx.blah.discord.handle.obj.IPresence;

/**
 * Emerald's implementation of the user object.
 */
public class EmeraldUser extends sx.blah.discord.handle.impl.obj.User implements IUser {

    public EmeraldUser(IShard shard, String name, long id, String discriminator, String avatar, IPresence presence, boolean isBot) {
        super(shard, name, id, discriminator, avatar, presence, isBot);
    }

    @Override
    public void sendMessage(String message) {
        getOrCreatePMChannel().sendMessage(message);
    }

    @Override // TODO: Implement permission system
    public boolean hasPermission(Command command) {
        return false;
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.USER;
    }
}
