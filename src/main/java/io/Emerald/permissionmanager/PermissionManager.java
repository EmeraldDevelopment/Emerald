package io.Emerald.permissionmanager;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.List;

/**
 * The manager for the permissions system.
 */
public interface PermissionManager {

    /**
     * Gets the permissions for the given user.
     *
     * @param user The user to get permissions for.
     * @return The user's permissions.
     */
    List<String> getPermissionsForUser(IUser user);

    /**
     * Gets the permissions for the given role.
     *
     * @param role The role to get permissions for.
     * @return The role's permissions.
     */
    List<String> getPermissionsForRole(IGuild guild, IRole role);

    /**
     * Gets the permissions file for the given guild.
     *
     * @param guild The guild to get permissions for.
     * @return The guild's permissions.
     */
    HashMap<String, Object> getPermissionsForGuild(IGuild guild);

    /**
     * Gets the global permissions file.
     *
     * @return The global permissions file.
     */
    HashMap<String, Object> getPermissionsFile();
}
