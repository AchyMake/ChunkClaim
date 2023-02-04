package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    private static final File configFile = new File(ChunkClaim.instance.getDataFolder(), "config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    public static void reload(){
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}