package io.Emerald.internal.plugin;

import java.io.IOException;
import java.util.jar.JarFile;

public interface PluginLoader {
    void loadPlugin(JarFile file) throws IOException, IllegalAccessException, InstantiationException;

    void prepareUnload();
}
