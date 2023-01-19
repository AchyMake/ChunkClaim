package net.achymake.chunkclaim.settings;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.MessageConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ChunkSettings {
    public static PersistentDataContainer getData(Chunk chunk){
        return chunk.getPersistentDataContainer();
    }
    public static void claimChunk(Player player){
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("owner"), PersistentDataType.STRING,player.getUniqueId().toString());
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("date-claimed"),PersistentDataType.STRING, SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,"");
    }
    public static boolean isClaimed(Chunk chunk){
        return getData(chunk).has(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
    }
    public static boolean isOwner(UUID uuid, Chunk chunk){
        return getData(chunk).get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING).equals(uuid.toString());
    }
    public static void setOwner(Player player,UUID uuid, Chunk chunk){
        getData(chunk).set(NamespacedKey.minecraft("owner"), PersistentDataType.STRING,uuid.toString());
        getData(chunk).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,"");
        getData(chunk).set(NamespacedKey.minecraft("date-claimed"),PersistentDataType.STRING, SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
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
    public static void addMember(Chunk chunk, UUID uuid){
        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(uuid);
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeInt(uuidList.size());
            for (UUID listedUUID : uuidList){
                os.writeObject(listedUUID);
            }
            os.flush();
            byte[] rawData = io.toByteArray();
            String encodedData = Base64.getEncoder().encodeToString(rawData);
            getData(chunk).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,encodedData);
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public static List<UUID> getMembers(Chunk chunk){
        ArrayList<UUID> uuidArrayList = new ArrayList<>();
        String encodedUUID = getData(chunk).get(NamespacedKey.minecraft("members"),PersistentDataType.STRING);
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
    public static void removeMember(Chunk chunk, UUID uuid){
        List<UUID> uuidList = getMembers(chunk);
        uuidList.remove(uuid);
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeInt(uuidList.size());
            for (UUID listedUUID : uuidList){
                os.writeObject(listedUUID);
            }
            os.flush();
            byte[] rawData = io.toByteArray();
            String encodedData = Base64.getEncoder().encodeToString(rawData);
            getData(chunk).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,encodedData);
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public static void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),getOwner(chunk)))));
    }
    public static void playerVisitClaimedChunk(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("player-visit-claimed-chunk"),getOwner(chunk)))));
    }
    public static void playerExitClaimedChunk(Player player) {
        String w = player.getPersistentDataContainer().get(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("player-exit-claimed-chunk"),w))));
    }
    public static void delete(Chunk chunk){
        if (getOwner(chunk) != null){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(getOwner(chunk));
            ChunkClaim.econ.depositPlayer(offlinePlayer, Config.get().getDouble("economy.refund"));
        }
        ChunkSettings.getData(chunk).remove(NamespacedKey.minecraft("owner"));
        ChunkSettings.getData(chunk).remove(NamespacedKey.minecraft("date-claimed"));
        ChunkSettings.getData(chunk).remove(NamespacedKey.minecraft("members"));
    }
}