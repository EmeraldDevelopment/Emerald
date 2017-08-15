package io.EmeraldDevelopment.Emerald.internal.plugin;

import java.io.IOException;
import java.util.jar.JarFile;

public interface PluginLoader {

    Plugin loadPlugin(JarFile file) throws IOException;
}
