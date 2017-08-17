package io.Emerald.internal.util;

import io.Emerald.internal.api.EmeraldUser;
import io.Emerald.internal.api.User;
import sx.blah.discord.handle.obj.IUser;

public class Util {

    public static User getEmeraldUser(IUser user) {
        return new EmeraldUser(user.getShard(), user.getName(), user.getLongID(), user.getDiscriminator(), user.getAvatar(), user.getPresence(), user.isBot());
    }
}
