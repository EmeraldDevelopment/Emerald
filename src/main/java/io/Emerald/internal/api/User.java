package io.Emerald.internal.api;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IUser;

/**
 * Emeralds user interface.
 */
public interface User extends CommandSender, IUser {

    void sendMessage(EmbedObject object);

    void sendMessage(String message, EmbedObject object);
}
