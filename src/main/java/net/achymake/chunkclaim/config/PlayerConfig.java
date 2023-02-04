package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerConfig {
    public static boolean exist(OfflinePlayer offlinePlayer){
        return new File(ChunkClaim.instance.getDataFolder(), "userdata/"+offlinePlayer.getUniqueId()+".yml").exists();
    }
    public static FileConfiguration get(OfflinePlayer offlinePlayer){
        File file = new File(ChunkClaim.instance.getDataFolder(), "userdata/"+offlinePlayer.getUniqueId()+".yml");
        return YamlConfiguration.loadConfiguration(file);
    }
    public static void create(OfflinePlayer offlinePlayer){
        File folder = new File(ChunkClaim.instance.getDataFolder(), "userdata");
        File file = new File(ChunkClaim.instance.getDataFolder(), "userdata/"+offlinePlayer.getUniqueId()+".yml");
        if (!folder.exists()){
            folder.mkdirs();
        }
        if (!file.exists()){
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set("name",offlinePlayer.getName());
            configuration.createSection("members");
            try {
                configuration.save(file);
            } catch (IOException e) {
                ChunkClaim.instance.sendMessage(e.getMessage());
            }
        }else{
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            if (!configuration.getString("name").equals(offlinePlayer.getName())){
                configuration.set("name",offlinePlayer.getName());
                try {
                    configuration.save(file);
                } catch (IOException e) {
                    ChunkClaim.instance.sendMessage(e.getMessage());
                }
            }
        }
    }
    public static void reload(){
        for (OfflinePlayer offlinePlayer : Bukkit.getServer().getOfflinePlayers()){
            File file = new File(ChunkClaim.instance.getDataFolder(), "userdata/"+offlinePlayer.getUniqueId()+".yml");
            if (file.exists()){
                FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                try {
                    configuration.load(file);
                } catch (IOException | InvalidConfigurationException e) {
                    ChunkClaim.instance.sendMessage(e.getMessage());
                }
            }
        }
    }
    public static void setStringList(OfflinePlayer offlinePlayer,String type, List<String> value){
        File file = new File(ChunkClaim.instance.getDataFolder(), "userdata/"+offlinePlayer.getUniqueId()+".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set(type,value);
        try {
            configuration.save(file);
        } catch (IOException e) {
            ChunkClaim.instance.sendMessage(e.getMessage());
        }
    }
}