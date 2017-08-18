package io.Emerald.datamanager;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Interface for defining data manager objects.
 */
public interface DataManager {

    /**
     * Creates a file for a given user.
     *
     * @param user The user to create for.
     */
    void createFile(IUser user);

    /**
     * Creates a file for a given guild.
     *
     * @param guild The guild to create for.
     */
    void createFile(IGuild guild);

    /**
     * Creates or loads data from a file.
     *
     * @param user The user to get data for.
     * @return The file's data map.
     */
    Map<String, Object> loadFile(IUser user);


    /**
     * Creates or loads data from a file.
     *
     * @param guild The guild to get data for.
     * @return The file's data map.
     */
    Map<String, Object> loadFile(IGuild guild);

    /**
     * Saves data to a file.
     *
     * @param user The user to save data for.
     * @param data The data to save.
     */
    void saveFile(IUser user, LinkedHashMap<String, Object> data);

    /**
     * Saves data to a file.
     *
     * @param guild The guild to save data for.
     * @param data The data to save.
     */
    void saveFile(IGuild guild, LinkedHashMap<String, Object> data);
}
