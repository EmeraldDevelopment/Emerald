package io.Emerald.internal.api;

import sx.blah.discord.handle.obj.IChannel;

public enum ChannelType {
    PRIVATE, PUBLIC;

    /**
     * Gets the channel type for a given channel.
     *
     * @param channel The channel to check.
     * @return The channel type.
     */
    public static ChannelType getChannelType(IChannel channel) {
        return channel.isPrivate() ? ChannelType.PRIVATE : ChannelType.PRIVATE;
    }
}
