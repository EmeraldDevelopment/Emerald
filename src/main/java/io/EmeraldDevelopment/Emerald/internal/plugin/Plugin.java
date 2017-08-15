package io.EmeraldDevelopment.Emerald.internal.plugin;

import io.EmeraldDevelopment.Emerald.commandmanager.Command;

import java.util.List;

/**
 * An interface object for our plugins
 */
public interface Plugin {

    // The name of the plugin.
    String gePluginName();
    // The authors of the plugin.
    String[] getAuthors();
    // The version of the plugin.
    String getVersion();
    // The plugin's ID. (this should be handled automatically somehow)
    String getPluginID();
    // The base level permission identifier this plugin will use. (ie. for the permission "test.hello.world" the identifier is "test")
    String getPermissionIdentifier();
    // Determines if this plugin requires a connected database to function.
    boolean requireDatabase();
    // List of all commands this plugin contains.
    List<Command> commands();
}
