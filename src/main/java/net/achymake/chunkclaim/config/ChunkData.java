package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ChunkData {
    public static File configFile = new File(ChunkClaim.instance.getDataFolder(), "chunk.db");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    public static void setup(ChunkClaim plugin){
        if (!configFile.exists()){
            plugin.saveResource("chunk.db",false);
            reload();
        }
    }
    public static FileConfiguration get(){
        return config;
    }
    public static void save(){
        try {
            config.save(configFile);
        }catch (IOException e){
            System.out.println("Couldn't save 'chunk.db'");
        }
    }
    public static void reload(){
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}
