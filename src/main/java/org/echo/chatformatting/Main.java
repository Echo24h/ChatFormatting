package org.echo.chatformatting;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    private ColorsConfig colorsConfig;
    private ColorsFormat colorsFormat;

    @Override
    public void onEnable() {
        instance = this;
        colorsConfig = new ColorsConfig();
        colorsFormat = new ColorsFormat();

        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        getCommand("chat").setExecutor(new Commands(this));
    }

    public void reloadPlugin() {
        colorsConfig = new ColorsConfig();
        colorsFormat = new ColorsFormat();
    }

    public static Main getInstance() {
        return instance;
    }

    public ColorsConfig getColorsConfig() {
        return colorsConfig;
    }

    public ColorsFormat getColorsFormat() {
        return colorsFormat;
    }
}
