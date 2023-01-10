package net.achymake.chunkclaim.settings;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkSettings {
    public static PersistentDataContainer getData(Chunk chunk){
        return chunk.getPersistentDataContainer();
    }
    public static void claimChunk(Player player){
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("owner"), PersistentDataType.STRING,player.getUniqueId().toString());
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("date-claimed"),PersistentDataType.STRING, SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
    }
    public static boolean isClaimed(Chunk chunk){
        return getData(chunk).has(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
    }
    public static boolean isOwner(UUID uuid, Chunk chunk){
        return getData(chunk).get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING).equals(uuid.toString());
    }
    public static String getOwner(Chunk chunk){
        String uuidString = getData(chunk).get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
        UUID uuid = UUID.fromString(uuidString);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        String name = offlinePlayer.getName();
        return name;
    }
    public static String getDateClaimed(Chunk chunk){
        return getData(chunk).get(NamespacedKey.minecraft("date-claimed"),PersistentDataType.STRING);
    }
    public static boolean isMember(UUID uuid, Chunk chunk){
        return getData(chunk).get(NamespacedKey.minecraft("members"),PersistentDataType.STRING).contains(uuid.toString());
    }
    public static void addMember(Chunk chunk, UUID targetUUID){
        if (getData(chunk).has(NamespacedKey.minecraft("members"),PersistentDataType.STRING)){
            String membersUUID = getData(chunk).get(NamespacedKey.minecraft("members"),PersistentDataType.STRING);
            getData(chunk).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,membersUUID+targetUUID+"_");
        }else{
            getData(chunk).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,targetUUID+"_");
        }
    }
    public static void removeMember(Chunk chunk, UUID targetUUID){
        String membersUUID = getData(chunk).get(NamespacedKey.minecraft("members"),PersistentDataType.STRING).replace(targetUUID+"_","");
        getData(chunk).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,membersUUID);
    }
    public static List<String> getMembers(Chunk chunk){
        List<String> names = new ArrayList<>();
        if (getData(chunk).has(NamespacedKey.minecraft("members"),PersistentDataType.STRING)){
            String members = getData(chunk).get(NamespacedKey.minecraft("members"),PersistentDataType.STRING);
            String[] buildUUID = new String[]{members.replace("_", " ")};
            for (String uuidString : buildUUID){
                UUID uuid = UUID.fromString(uuidString);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                String playerNames = offlinePlayer.getName();
                names.add(playerNames);
            }
        }
        return names;
    }
    public static void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&',"&cChunk is owned by &f" + getOwner(chunk))));
    }
    public static void delete(Chunk chunk){
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(ChunkSettings.getOwner(chunk));
        ChunkClaim.econ.depositPlayer(offlinePlayer, Config.get().getDouble("economy.refund"));
        ChunkSettings.getData(chunk).remove(NamespacedKey.minecraft("owner"));
        ChunkSettings.getData(chunk).remove(NamespacedKey.minecraft("date-claimed"));
    }
}
