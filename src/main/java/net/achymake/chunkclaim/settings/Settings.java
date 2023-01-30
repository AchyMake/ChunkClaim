package net.achymake.chunkclaim.settings;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class Settings {
    public static boolean hasAccess(Player player, Chunk chunk){
        if (isOwner(player,chunk)){
            return true;
        }else if (isMember(player,chunk)){
            return true;
        }else return ChunkClaim.edit.contains(player);
    }
    public static boolean hasChunkEdit(Player player) {
        return ChunkClaim.edit.contains(player);
    }
    public static boolean isClaimed(Chunk chunk) {
        return chunk.getPersistentDataContainer().has(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
    }
    public static boolean isOwner(Player player, Chunk chunk) {
        return chunk.getPersistentDataContainer().get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING).equals(player.getUniqueId().toString());
    }
    public static String getOwner(Chunk chunk) {
        String uuidString = chunk.getPersistentDataContainer().get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
        return Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuidString)).getName();
    }
    public static UUID getOwnerUUID(Chunk chunk) {
        String uuidString = chunk.getPersistentDataContainer().get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
        return UUID.fromString(uuidString);
    }
    public static String getDateClaimed(Chunk chunk) {
        return chunk.getPersistentDataContainer().get(NamespacedKey.minecraft("date-claimed"), PersistentDataType.STRING);
    }
    public static boolean isMember(Player player, Chunk chunk) {
        return getMembersUUID(chunk).contains(player.getUniqueId());
    }
    public static List<String> getMembers(Chunk chunk) {
        ArrayList<String> names = new ArrayList();
        String encodedUUID = chunk.getPersistentDataContainer().get(NamespacedKey.minecraft("members"), PersistentDataType.STRING);
        if (!encodedUUID.isEmpty()) {
            byte[] rawData = Base64.getDecoder().decode(encodedUUID);
            try {
                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);
                int uuidCount = in.readInt();
                for(int i = 0; i < uuidCount; ++i) {
                    names.add(Bukkit.getServer().getOfflinePlayer((UUID)in.readObject()).getName());
                }
            } catch (ClassNotFoundException | IOException e) {
                ChunkClaim.instance.sendMessage(e.getMessage());
            }
        }
        return names;
    }
    public static List<UUID> getMembersUUID(Chunk chunk) {
        ArrayList<UUID> uuidList = new ArrayList<>();
        String encodedUUID = chunk.getPersistentDataContainer().get(NamespacedKey.minecraft("members"), PersistentDataType.STRING);
        if (!encodedUUID.isEmpty()) {
            byte[] rawData = Base64.getDecoder().decode(encodedUUID);
            try {
                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);
                int uuidCount = in.readInt();
                for(int i = 0; i < uuidCount; ++i) {
                    uuidList.add((UUID)in.readObject());
                }
            } catch (ClassNotFoundException | IOException e) {
                ChunkClaim.instance.sendMessage(e.getMessage());
            }
        }
        return uuidList;
    }
}