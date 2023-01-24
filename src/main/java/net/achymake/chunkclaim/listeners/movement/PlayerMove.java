package net.achymake.chunkclaim.listeners.movement;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;

import java.text.MessageFormat;

public class PlayerMove implements Listener {
    public PlayerMove(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntity (PlayerMoveEvent event){
        Chunk chunk = event.getPlayer().getLocation().getChunk();
        if (Settings.isClaimed(chunk)){
            if (!event.getPlayer().getPersistentDataContainer().has(NamespacedKey.minecraft("chunk-visitor"), PersistentDataType.STRING)){
                playerVisitClaimedChunk(event.getPlayer(),chunk);
                event.getPlayer().getPersistentDataContainer().set(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING,Settings.getOwner(chunk));
            }else{
                if (!event.getPlayer().getPersistentDataContainer().get(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING).equals(Settings.getOwner(chunk))){
                    event.getPlayer().getPersistentDataContainer().remove(NamespacedKey.minecraft("chunk-visitor"));
                }
            }
        }else{
            if (event.getPlayer().getPersistentDataContainer().has(NamespacedKey.minecraft("chunk-visitor"), PersistentDataType.STRING)){
                playerExitClaimedChunk(event.getPlayer());
                event.getPlayer().getPersistentDataContainer().remove(NamespacedKey.minecraft("chunk-visitor"));
            }
        }
    }
    private void playerVisitClaimedChunk(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("player-visit-claimed-chunk"),Settings.getOwner(chunk)))));
    }
    private void playerExitClaimedChunk(Player player) {
        String chunkOwner = player.getPersistentDataContainer().get(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("player-exit-claimed-chunk"),chunkOwner))));
    }
}