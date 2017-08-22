package io.Emerald.permissionmanager;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class EmeraldPermissionManager implements PermissionManager {

    private static EmeraldPermissionManager manager;
    private static DumperOptions options = new DumperOptions();
    private static InputStream permissions;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static EmeraldPermissionManager getManager() {

        new File("permissions.yml").mkdirs();
        if (manager == null) {
            manager = new EmeraldPermissionManager();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            try {
                permissions = new FileInputStream("permissions.yml");
            } catch (FileNotFoundException e) {
                System.out.println("Permissions file doesn't exist!");
            }
        }
        return manager;
    }

    /**
     * Gets the permissions for the given user.
     *
     * @param user The user to get permissions for.
     * @return The user's permissions.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getPermissionsForUser(IUser user) {
        HashMap<String, Object> permissions = (HashMap<String, Object>) getPermissionsFile().get("users");
        if (!permissions.containsKey(user.getStringID())) {
            return null;
        }
        return (List<String>) permissions.get(user.getStringID());
    }

    /**
     * Gets the permissions for the given role.
     *
     * @param guild The guild to get the role for.
     * @param role  The role to get permissions for.
     * @return The role's permissions.
     */
    @Override @SuppressWarnings("unchecked")
    public List<String> getPermissionsForRole(IGuild guild, IRole role) {
        HashMap<String, Object> permissions = getPermissionsForGuild(guild);
        if (!permissions.containsKey(role.getStringID())) {
            return null;
        }

        return (List<String>) permissions.get(role.getStringID());
    }

    /**
     * Gets the permissions file for the given guild.
     *
     * @param guild The guild to get permissions for.
     * @return The guild's permissions.
     */
    @Override @SuppressWarnings("unchecked")
    public HashMap<String, Object> getPermissionsForGuild(IGuild guild) {
        HashMap<String, Object> permissions = (HashMap<String, Object>) getPermissionsFile().get("guilds");
        if (!permissions.containsKey(guild.getStringID())) {
            return null;
        }
        return (HashMap<String, Object>) permissions.get(guild.getStringID());
    }

    /**
     * Gets the global permissions file.
     *
     * @return The global permissions file.
     */
    @Override @SuppressWarnings("unchecked")
    public HashMap<String, Object> getPermissionsFile() {
        return (HashMap<String, Object>) new Yaml(options).load(permissions);
    }
}
