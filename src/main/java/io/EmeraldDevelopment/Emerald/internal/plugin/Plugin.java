package io.EmeraldDevelopment.Emerald.internal.plugin;

/**
 * An interface object for our plugins
 */
public interface Plugin {

    // The name of the plugin.
    String pluginName();
    // The authors of the plugin.
    String[] authors();
    // The version of the plugin.
    String version();
    // The plugin's ID. (this should be handled automatically somehow)
    String pluginID();
    // The base level permission identifier this plugin will use. (ie. for the permission "test.hello.world" the identifier is "test")
    String permissionIdentifier();
    // Determines if this plugin requires a connected database to function.
    boolean requireDatabase();
}
