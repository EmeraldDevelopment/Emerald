package io.Emerald.internal.plugin;

import io.Emerald.annotations.PluginMeta;
import io.Emerald.commandmanager.EmeraldCommandRegistry;
import io.Emerald.commandmanager.PluginCommand;
import io.Emerald.exceptions.PluginMetaException;

import java.util.HashSet;
import java.util.List;

@SuppressWarnings("ALL")
public class EmeraldPlugin implements Plugin {

    PluginMeta meta;
    HashSet<Class<?>> classMap = new HashSet<>();

    public EmeraldPlugin() {
        meta = getClass().getAnnotation(PluginMeta.class);
        if (meta == null) {
            throw new PluginMetaException(getClass().getName() + " doesn't have a plugin meta annotation!");
        }
    }

    /**
     * Gets the plugin's name.
     *
     * @return The plugin's name.
     */
    @Override
    public String getPluginName() {
        return meta.pluginName();
    }

    /**
     * Gets the plugin's authors.
     *
     * @return the plugin's authors.
     */
    @Override
    public String[] getAuthors() {
        return meta.authors();
    }

    /**
     * Gets the plugin's version.
     *
     * @return The plugin's version.
     */
    @Override
    public String getVersion() {
        return meta.version();
    }

    /**
     * Gets the plugin's ID.
     *
     * @return The plugin's ID.
     */
    @Override
    public String getPluginID() {
        return meta.pluginID();
    }

    /**
     * Gets this plugin's base permission identifier.
     *
     * @return The plugin's permission identifier.
     */
    @Override
    public String getPermissionIdentifier() {
        return meta.permissionIdentifier();
    }

    /**
     * Gets if the plugin requires a database.
     *
     * @return True if database is required.
     */
    @Override
    public boolean requireDatabase() {
        return meta.requireDatabase();
    }

    /**
     * Gets this plugin's command list.
     *
     * @return The plugin's command list.
     */
    @Override
    public List<PluginCommand> getCommands() {
        return EmeraldCommandRegistry.getRegistry().getCommandsForPlugin(this);
    }

    /**
     * Gets the classes associated with this plugin.
     *
     * @return The plugin's class map.
     */
    @Override
    public HashSet<Class<?>> getClassMap() {
        return classMap;
    }

    /**
     * Sets the classes associated with this plugin.
     *
     * @param classMap The classes to assign to this plugin.
     */
    @Override
    public void setClassMap(HashSet<Class<?>> classMap) {
        this.classMap = classMap;
    }

    // Method to run on plugin startup
    public void onEnable() {}

    // Method to run on plugin shutdown
    public void onDisable() {}
}
