package io.Emerald.datamanager;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Emerald's internal data management code.
 */
class DataUtil {

    private static DumperOptions options = new DumperOptions();

    // Dumps data to a file
    static void dumpToFile(File file, HashMap<String, Object> data) {
        FileWriter writer = null;
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        try {
            writer = new FileWriter(file);
            new Yaml(options).dump(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(writer);
        }
    }

    // Grabs data from a file
    @SuppressWarnings("unchecked")
    static Map<String, Object> getData(File file) {
        InputStream stream;
        Map<String, Object> data = null;
        try {
            stream = new FileInputStream(file);
            data =  (Map<String, Object>) new Yaml(options).load(stream);
            close(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Closes a stream
    private static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
