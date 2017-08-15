package io.EmeraldDevelopment.Emerald.internal.plugin;

import java.io.File;

public interface PluginLoader {

    Plugin loadPlugin(File file);

    void enablePlugin(Plugin plugin);

    void disablePlugin(Plugin plugin);
}
