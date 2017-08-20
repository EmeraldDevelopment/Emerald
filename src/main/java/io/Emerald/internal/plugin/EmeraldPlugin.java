package io.Emerald.internal.plugin;

@SuppressWarnings("ALL")
public abstract class EmeraldPlugin {
    // TODO: Simplify this and Plugin into a single object and make plugin an interface.
    public abstract void onEnable();
    public abstract void onDisable();

    public Plugin getPlugin() {
        return PluginRepository.getInstance().getPlugin(this);
    }
}
