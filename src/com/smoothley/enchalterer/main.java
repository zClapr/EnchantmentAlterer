package com.smoothley.enchalterer;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    private static main instance;
    public static main plugin() { return instance; }

    @Override
    public void onEnable(){
        saveDefaultConfig();
        instance = this;
        getServer().getPluginManager().registerEvents(new listener(), this);
        getCommand("enchantalter").setExecutor(new commands());
        getCommand("ea").setExecutor(new commands());
    }
}
