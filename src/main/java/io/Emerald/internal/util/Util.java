package io.Emerald.internal.util;

import io.Emerald.internal.api.User;
import io.Emerald.internal.api.EmeraldUser;
import sx.blah.discord.handle.obj.IUser;

public class Util {

    public static EmeraldUser getEmeraldUser(IUser user) {
        return new User(user.getShard(), user.getName(), user.getLongID(), user.getDiscriminator(), user.getAvatar(), user.getPresence(), user.isBot());
    }
}
