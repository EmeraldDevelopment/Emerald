package io.Emerald.datamanager;

import io.Emerald.internal.plugin.EmeraldPlugin;
import io.Emerald.internal.plugin.Plugin;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Emerald's plugin data manager implementation.
 */
public class PluginDataManager implements DataManager {

    private Plugin plugin;

    /**
     * Creates a new data manager instance.
     *
     * @param plugin The implementing plugin.
     */
    public PluginDataManager(EmeraldPlugin plugin) {
        this.plugin = plugin.getPlugin();
    }

    /**
     * Creates a file for a given user.
     *
     * @param user The user to create for.
     */
    @Override
    public void createFile(IUser user) {
        createFile(new File("plugins/" + plugin.getPluginName()  + "/users/" + user.getLongID() + ".yml"));
    }

    /**
     * Creates a file for a given guild.
     *
     * @param guild The guild to create for.
     */
    @Override
    public void createFile(IGuild guild) {
        createFile(new File("plugins/" + plugin.getPluginName() + "/users/" + guild.getLongID() + ".yml"));
    }

    /**
     * Creates or loads data from a file.
     *
     * @param user The user to get data for.
     * @return The file's data map.
     */
    @Override
    public Map<String, Object> loadFile(IUser user) {
        return loadFile(new File("plugins/" + plugin.getPluginName() + "/users/" + user.getLongID() + ".yml"));
    }

    /**
     * Creates or loads data from a file.
     *
     * @param guild The guild to get data for.
     * @return The file's data map.
     */
    @Override
    public Map<String, Object> loadFile(IGuild guild) {
        return loadFile(new File("plugins/" + plugin.getPluginName() + "/guilds/" + guild.getLongID() + ".yml"));
    }

    /**
     * Loads the config file.
     *
     * @return The config's data.
     */
    @Override
    public Map<String, Object> loadConfig() {
        return loadFile(new File("plugins/" + plugin.getPluginName() + "/config.yml"));
    }

    /**
     * Saves data to a file.
     *
     * @param user The user to save data for.
     */
    @Override
    public void saveFile(IUser user, LinkedHashMap<String, Object> data) {
        DataUtil.dumpToFile(new File("plugins/" + plugin.getPluginName() + "/users/" + user.getLongID() + ".yml"), data);
    }

    /**
     * Saves data to a file.
     *
     * @param guild The guild to save data for.
     */
    @Override
    public void saveFile(IGuild guild, LinkedHashMap<String, Object> data) {
        DataUtil.dumpToFile(new File("plugins/" + plugin.getPluginName() + "/guilds/" + guild.getLongID() + ".yml"), data);
    }

    /**
     * Saves the config file.
     *
     * @param data The data to save.
     */
    @Override
    public void saveConfig(LinkedHashMap<String, Object> data) {
        DataUtil.dumpToFile(new File("plugins/" + plugin.getPluginName() + "/config.yml"), data);
    }

    // Creates a file
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(File file) {
        file.mkdirs();
        DataUtil.dumpToFile(file, new LinkedHashMap<>());
    }

    // Loads data from a file
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Map<String, Object> loadFile(File file) {
        // Return null if the file doesn't exist
        if (!file.exists()) {
            return null;
        }
        return DataUtil.getData(file);
    }
}
