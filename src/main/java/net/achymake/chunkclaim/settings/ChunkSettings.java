package net.achymake.chunkclaim.settings;

import net.achymake.chunkclaim.config.ChunkData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkSettings {
    private static FileConfiguration getData(){
        return ChunkData.get();
    }
    public static boolean isClaimed(Chunk chunk){
        return getData().getKeys(true).contains(chunk.getWorld().getName() + "." + chunk);
    }
    public static boolean isOwner(UUID uuid, Chunk chunk){
        return getData().getString(chunk.getWorld().getName() + "." + chunk + ".owner").equals(uuid.toString());
    }
    public static String getOwner(Chunk chunk){
        String uuid = getData().getString(chunk.getWorld().getName() + "." + chunk + ".owner");
        return Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
    }
    public static String getDateClaimed(Chunk chunk){
        return getData().getString(chunk.getWorld().getName() + "." + chunk + ".date-claimed");
    }
    public static boolean isMember(UUID uuid, Chunk chunk){
        return getData().getStringList(chunk.getWorld().getName() + "." + chunk + ".members").contains(uuid.toString());
    }
    public static List<String> getMembers(Chunk chunk){
        List<String> names = new ArrayList<>();
        for (String uuidStrings : getData().getStringList(chunk.getWorld().getName()+"."+chunk+".members")){
            names.add(Bukkit.getOfflinePlayer(UUID.fromString(uuidStrings)).getName());
        }
        return names;
    }
    public static void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&',"&cChunk is owned by &f" + getOwner(chunk))));
    }
}
