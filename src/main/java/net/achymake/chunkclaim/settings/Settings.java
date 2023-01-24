package net.achymake.chunkclaim.settings;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class Settings {
    private static PersistentDataContainer getChunkData(Chunk chunk){
        return chunk.getPersistentDataContainer();
    }
    private static PersistentDataContainer getPlayerData(Player player){
        return player.getPersistentDataContainer();
    }
    public static boolean hasChunkEdit(Player player){
        return getPlayerData(player).has(NamespacedKey.minecraft("chunk-edit"), PersistentDataType.STRING);
    }
    public static boolean isClaimed(Chunk chunk){
        return getChunkData(chunk).has(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
    }
    public static boolean isOwner(Chunk chunk,UUID uuid){
        return getChunkData(chunk).get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING).equals(uuid.toString());
    }
    public static String getOwner(Chunk chunk){
        String uuidString = getChunkData(chunk).get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
        UUID uuid = UUID.fromString(uuidString);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        String name = offlinePlayer.getName();
        return name;
    }
    public static String getDateClaimed(Chunk chunk){
        return getChunkData(chunk).get(NamespacedKey.minecraft("date-claimed"),PersistentDataType.STRING);
    }
    public static boolean isMember(Chunk chunk,UUID uuid){
        return getMembers(chunk).contains(uuid);
    }
    public static List<UUID> getMembers(Chunk chunk){
        ArrayList<UUID> uuidArrayList = new ArrayList<>();
        String encodedUUID = getChunkData(chunk).get(NamespacedKey.minecraft("members"),PersistentDataType.STRING);
        if (!encodedUUID.isEmpty()){
            byte[] rawData = Base64.getDecoder().decode(encodedUUID);
            try {
                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);
                int uuidCount = in.readInt();
                for (int i = 0; i < uuidCount; i++){
                    uuidArrayList.add((UUID) in.readObject());
                }
            }catch (IOException | ClassNotFoundException ex){
                System.out.println(ex);
            }
        }
        return uuidArrayList;
    }
}