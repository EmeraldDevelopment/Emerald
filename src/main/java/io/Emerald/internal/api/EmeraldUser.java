package io.Emerald.internal.api;

import io.Emerald.commandmanager.Command;
import io.Emerald.commandmanager.PluginCommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.cache.LongMap;

import java.util.EnumSet;
import java.util.List;

/**
 * Emerald's user implementation.
 */
public class EmeraldUser implements User {

    private IUser user;

    public EmeraldUser(IUser user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public String getAvatar() {
        return user.getAvatar();
    }

    @Override
    public String getAvatarURL() {
        return user.getAvatarURL();
    }

    @Override
    public IPresence getPresence() {
        return user.getPresence();
    }

    @Override
    public Status getStatus() {
        return user.getStatus();
    }

    @Override
    public String getDisplayName(IGuild iGuild) {
        return user.getDisplayName(iGuild);
    }

    @Override
    public String mention() {
        return user.mention();
    }

    @Override
    public String mention(boolean b) {
        return user.mention(b);
    }

    @Override
    public String getDiscriminator() {
        return user.getDiscriminator();
    }

    @Override
    public List<IRole> getRolesForGuild(IGuild iGuild) {
        return user.getRolesForGuild(iGuild);
    }

    @Override
    public EnumSet<Permissions> getPermissionsForGuild(IGuild iGuild) {
        return user.getPermissionsForGuild(iGuild);
    }

    @Override
    public String getNicknameForGuild(IGuild iGuild) {
        return user.getNicknameForGuild(iGuild);
    }

    @Override
    public IVoiceState getVoiceStateForGuild(IGuild iGuild) {
        return user.getVoiceStateForGuild(iGuild);
    }

    @Override
    public LongMap<IVoiceState> getVoiceStatesLong() {
        return user.getVoiceStatesLong();
    }

    @Override
    public void moveToVoiceChannel(IVoiceChannel iVoiceChannel) {
        user.moveToVoiceChannel(iVoiceChannel);
    }

    @Override
    public boolean isBot() {
        return user.isBot();
    }

    @Override
    public IPrivateChannel getOrCreatePMChannel() {
        return user.getOrCreatePMChannel();
    }

    @Override
    public void addRole(IRole iRole) {
        user.addRole(iRole);
    }

    @Override
    public void removeRole(IRole iRole) {
        user.removeRole(iRole);
    }

    @Override
    public IDiscordClient getClient() {
        return user.getClient();
    }

    @Override
    public IShard getShard() {
        return user.getShard();
    }

    @Override
    public IUser copy() {
        return user.copy();
    }

    @Override
    public long getLongID() {
        return user.getLongID();
    }

    @Override
    public void sendMessage(String message) {
        getOrCreatePMChannel().sendMessage(message);
    }
    // TODO: Implement permission checking
    @Override
    public boolean hasPermission(Command command) {
        return true;
    }

    @Override // TODO: Implement modifier checking
    public boolean hasModifier(PluginCommand command, String modifier) {
        return true;
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.USER;
    }

    @Override
    public void sendMessage(EmbedObject object) {
        getOrCreatePMChannel().sendMessage(object);
    }

    @Override
    public void sendMessage(String message, EmbedObject object) {
        getOrCreatePMChannel().sendMessage(message, object);
    }
}
