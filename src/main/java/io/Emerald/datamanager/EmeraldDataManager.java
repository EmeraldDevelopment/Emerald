package io.Emerald.datamanager;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Emerald's global data manager implementation.
 */
public class EmeraldDataManager implements DataManager {

    private static EmeraldDataManager manager;

    /**
     * Gets the manager instance.
     *
     * @return The static manager instance.
     */
    public static EmeraldDataManager getManager() {

        if (manager == null) {
            manager = new EmeraldDataManager();
        }

        return manager;
    }

    /**
     * Creates a file for a given user.
     *
     * @param user The user to create for.
     */
    @Override
    public void createFile(IUser user) {
        createFile(new File("users/" + user.getLongID() + ".yml"));
    }

    /**
     * Creates a file for a given guild.
     *
     * @param guild The guild to create for.
     */
    @Override
    public void createFile(IGuild guild) {
        createFile(new File("guilds/" + guild.getLongID() + ".yml"));
    }

    /**
     * Creates a config file.
     *
     * @param configName The config file's name.
     */
    @Override
    public void createConfig(String configName) {
        createFile(new File(configName.toLowerCase() + ".yml"));
    }

    /**
     * Creates or loads data from a file.
     *
     * @param user The user to get data for.
     * @return The file's data map.
     */
    @Override
    public Map<String, Object> loadFile(IUser user) {
        return loadFile(new File("users/" + user.getLongID() + ".yml"));
    }

    /**
     * Creates or loads data from a file.
     *
     * @param guild The guild to get data for.
     * @return The file's data map.
     */
    @Override
    public Map<String, Object> loadFile(IGuild guild) {
        return loadFile(new File("guilds/" + guild.getLongID() + ".yml"));
    }

    /**
     * Loads the config file.
     *
     * @param configName The config file's name.
     * @return The config's data.
     */
    @Override
    public Map<String, Object> loadConfig(String configName) {
        return loadFile(new File(configName.toLowerCase() + ".yml"));
    }

    /**
     * Saves data to a file.
     *
     * @param user The user to save data for.
     * @param data The data to save.
     */
    @Override
    public void saveFile(IUser user, LinkedHashMap<String, Object> data) {
        DataUtil.dumpToFile(new File("users/" + user.getLongID() + ".yml"), data);
    }

    /**
     * Saves data to a file.
     *
     * @param guild The guild to save data for.
     * @param data The data to save.
     */
    @Override
    public void saveFile(IGuild guild, LinkedHashMap<String, Object> data) {
        DataUtil.dumpToFile(new File("guilds/" + guild.getLongID() + ".yml"), data);
    }

    /**
     * Saves the config file.
     *
     * @param configName The config file's name.
     * @param data The data to save.
     */
    @Override
    public void saveConfig(String configName, LinkedHashMap<String, Object> data) {
        DataUtil.dumpToFile(new File(configName.toLowerCase() + ".yml"), data);
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
