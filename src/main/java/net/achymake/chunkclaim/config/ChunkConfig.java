package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ChunkConfig {
    public static File configFile = new File(ChunkClaim.instance.getDataFolder(), "config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    public static void setup(ChunkClaim plugin){
        if (!configFile.exists()){
            plugin.saveResource("config.yml",false);
            reload();
        }
    }
    public static FileConfiguration get(){
        return config;
    }
    public static void reload(){
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}
