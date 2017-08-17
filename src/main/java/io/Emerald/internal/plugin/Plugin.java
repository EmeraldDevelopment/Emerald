package io.EmeraldDevelopment.Emerald.internal.plugin;

import io.EmeraldDevelopment.Emerald.commandmanager.Command;
import io.EmeraldDevelopment.Emerald.commandmanager.CommandRegistry;

import java.util.HashSet;
import java.util.List;

/**
 * Class implementation of plugin object.
 */
public final class Plugin {

    private EmeraldPlugin plugin;
    private String pluginName;
    private String[] authors;
    private String version;
    private String permissionIdentifier;
    private boolean requireDatabase;
    private HashSet<Class<?>> classMap = new HashSet<>();

    public Plugin(EmeraldPlugin plugin, String pluginName, String[] authors, String version, String permissionIdentifier, boolean requireDatabase) {
        this.plugin = plugin;
        this.pluginName = pluginName;
        this.authors = authors;
        this.version = version;
        this.permissionIdentifier = permissionIdentifier;
        this.requireDatabase = requireDatabase;
    }

    // Gets the EmeraldPlugin object for the plugin.
    public EmeraldPlugin getPlugin() {
        return plugin;
    }

    // The name of the plugin.
    public String getPluginName() {
        return pluginName;
    }

    // The authors of the plugin.
    public String[] getAuthors() {
        return authors;
    }

    // The version of the plugin.
    public String getVersion() {
        return version;
    }

    // The plugin's ID. (this should be handled automatically somehow)
    public String getPluginID() {
        return null;
    }

    // The base level permission identifier this plugin will use. (ie. for the permission "test.hello.world" the identifier is "test")
    public String getPermissionIdentifier() {
        return permissionIdentifier;
    }

    // Determines if this plugin requires a connected database to function.
    public boolean requireDatabase() {
        return requireDatabase;
    }

    // List of all commands this plugin contains.
    public List<Command> getCommands() {
        return CommandRegistry.getRegistry().getRegisteredCommandsForPlugin(plugin);
    }

    public HashSet<Class<?>> getClassMap() {
        return classMap;
    }

    public void setClassMap(HashSet<Class<?>> classMap) {
        this.classMap = classMap;
    }
}
