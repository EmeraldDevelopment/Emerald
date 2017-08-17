package io.Emerald.internal.plugin;

@SuppressWarnings("ALL")
public abstract class EmeraldPlugin {

    public abstract void onEnable();
    public abstract void onDisable();

    public Plugin getPlugin() {
        return PluginRepository.getInstance().getPlugin(this);
    }
}
