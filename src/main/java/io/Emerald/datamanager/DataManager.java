package io.Emerald.datamanager;

import io.Emerald.internal.plugin.EmeraldPlugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataManager {

    private static DataManager instance;
    private static DumperOptions options = new DumperOptions();
    private static Yaml yaml;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            yaml = new Yaml(options);
        }
        return instance;
    }

    // Creates global data for a user
    void createFile(IUser user) {
        createFile(new File("users/" + user.getLongID() + ".yml"));
    }

    /**
     * Creates a file for a given user.
     *
     * @param plugin The plugin to create for.
     * @param user The user to create for.
     */
    public void createFile(EmeraldPlugin plugin, IUser user) {
        createFile(new File(plugin.getPlugin().getPluginID() + "/users/" + user.getLongID() + ".yml"));
    }

    // Creates global data for a guild
    void createFile(IGuild guild) {
        createFile(new File("users/" + guild.getLongID() + ".yml"));
    }

    /**
     * Creates a file for a given guild.
     *
     * @param plugin The plugin to create for.
     * @param guild The guild to create for.
     */
    public void createFile(EmeraldPlugin plugin, IGuild guild) {
        createFile(new File(plugin.getPlugin().getPluginID() + "/users/" + guild.getLongID() + ".yml"));
    }

    // Creates a file
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(File file) {
        file.mkdirs();
        dumpToFile(file, new LinkedHashMap<>());
    }

    // Loads global data for a user
    Map<String, Object> loadFile(IUser user) {
        return loadFile(new File("users/" + user.getLongID() + ".yml"));
    }

    /**
     * Creates or loads data from a file.
     *
     * @param plugin The plugin to get data for.
     * @param user The user to get data for.
     * @return The file's data map.
     */
    public Map<String, Object> loadFile(EmeraldPlugin plugin, IUser user) {
        return loadFile(new File(plugin.getPlugin().getPluginID() + "/users/" + user.getLongID() + ".yml"));
    }

    // Loads global data for a guild
    Map<String, Object> loadFile(IGuild guild) {
        return loadFile(new File("guilds/" + guild.getLongID() + ".yml"));
    }

    /**
     * Creates or loads data from a file.
     *
     * @param plugin The plugin to get data for.
     * @param guild The guild to get data for.
     * @return The file's data map.
     */
    public Map<String, Object> loadFile(EmeraldPlugin plugin, IGuild guild) {
        return loadFile(new File(plugin.getPlugin().getPluginID() + "/guilds/" + guild.getLongID() + ".yml"));
    }

    // Loads data from a file
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Map<String, Object> loadFile(File file) {
        // Return null if the file doesn't exist
        if (!file.exists()) {
            return null;
        }
        return getData(file);
    }

    // Dumps data to a file
    private static void dumpToFile(File file, HashMap<String, Object> data) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            yaml.dump(data, writer);
            close(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Grabs data from a file
    @SuppressWarnings("unchecked")
    private static Map<String, Object> getData(File file) {
        InputStream stream;
        Map<String, Object> data = null;
        try {
            stream = new FileInputStream(file);
            data =  (Map<String, Object>) yaml.load(stream);
            close(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Closes a stream
    private static void close(Closeable stream) throws IOException {
        if (stream != null) {
            stream.close();
        }
    }
}
