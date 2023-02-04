package net.achymake.chunkclaim.listeners.movement;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Message;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class PlayerMove implements Listener {
    public PlayerMove(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntity (PlayerMoveEvent event){
        Player player = event.getPlayer();
        Chunk chunk = event.getPlayer().getLocation().getChunk();
        if (Settings.isClaimed(chunk)){
            if (player.getPersistentDataContainer().has(NamespacedKey.minecraft("chunk-visitor"), PersistentDataType.STRING)){
                if (!player.getPersistentDataContainer().get(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING).equals(getOwner(player,chunk).getName())){
                    player.getPersistentDataContainer().remove(NamespacedKey.minecraft("chunk-visitor"));
                }
            }else{
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Message.eventVisit(getOwner(player,chunk))));
                player.getPersistentDataContainer().set(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING,getOwner(player,chunk).getName());
            }
        }else{
            if (player.getPersistentDataContainer().has(NamespacedKey.minecraft("chunk-visitor"), PersistentDataType.STRING)){
                String chunkOwner = player.getPersistentDataContainer().get(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Message.eventExit(chunkOwner)));
                player.getPersistentDataContainer().remove(NamespacedKey.minecraft("chunk-visitor"));
            }
        }
    }
    private OfflinePlayer getOwner(Player player, Chunk chunk) {
        return player.getServer().getOfflinePlayer(UUID.fromString(chunk.getPersistentDataContainer().get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING)));
    }
}