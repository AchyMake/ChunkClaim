package net.achymake.chunkclaim.listeners.bucket;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.text.MessageFormat;
import java.util.UUID;

public class BucketEmpty implements Listener {
    public BucketEmpty(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBucketEmpty (PlayerBucketEmptyEvent event){
        Chunk chunk = event.getBlockClicked().getChunk();
        UUID uuid = event.getPlayer().getUniqueId();
        if (!Settings.isClaimed(chunk))return;
        if (Settings.isOwner(chunk,uuid))return;
        if (Settings.getMembers(chunk).contains(event.getPlayer().getUniqueId()))return;
        if (Settings.hasChunkEdit(event.getPlayer()))return;
        event.setCancelled(true);
        cancelPlayer(event.getPlayer(),chunk);
    }
    private void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),Settings.getOwner(chunk)))));
    }
}