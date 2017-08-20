package io.Emerald.internal.plugin;

import io.Emerald.commandmanager.PluginCommand;

import java.util.HashSet;
import java.util.List;

/**
 * Class implementation of plugin object.
 */
public interface Plugin {

    /**
     * Gets the plugin's name.
     *
     * @return The plugin's name.
     */
    String getPluginName();

    /**
     * Gets the plugin's authors.
     *
     * @return the plugin's authors.
     */
    String[] getAuthors();

    /**
     * Gets the plugin's version.
     *
     * @return The plugin's version.
     */
    String getVersion();

    /**
     * Gets the plugin's ID.
     *
     * @return The plugin's ID.
     */
    String getPluginID();

    /**
     * Gets this plugin's base permission identifier.
     *
     * @return The plugin's permission identifier.
     */
    String getPermissionIdentifier();

    /**
     * Gets if the plugin requires a database.
     *
     * @return True if database is required.
     */
    boolean requireDatabase();

    /**
     * Gets this plugin's command list.
     *
     * @return The plugin's command list.
     */
    List<PluginCommand> getCommands();

    /**
     * Gets the classes associated with this plugin.
     *
     * @return The plugin's class map.
     */
    HashSet<Class<?>> getClassMap();

    /**
     * Sets the classes associated with this plugin.
     *
     * @param classMap The classes to assign to this plugin.
     */
    void setClassMap(HashSet<Class<?>> classMap);
}
