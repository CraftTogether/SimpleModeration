package com.github.crafttoegether.simplemoderation;

import com.github.crafttoegether.simplemoderation.commands.Ban;
import com.github.crafttoegether.simplemoderation.commands.Mod;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Plugin extends JavaPlugin {

    public static Plugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        try {
            getConfig().load(Files.newBufferedReader(Path.of(getDataFolder() + "/config.yml")));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        getCommand("mod").setExecutor(new Mod());
        getCommand("mod-ban").setExecutor(new Ban());
    }

    public void saveConfig() {
        try {
            this.getConfig().save(new File(getDataFolder() + "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
