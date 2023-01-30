package net.achymake.chunkclaim.listeners.block;

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
import org.bukkit.event.block.BlockPlaceEvent;

import java.text.MessageFormat;
import java.util.UUID;

public class BlockPlace implements Listener {
    public BlockPlace(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBlockPlace (BlockPlaceEvent event){
        Chunk chunk = event.getBlockPlaced().getChunk();
        if (!Settings.isClaimed(chunk))return;
        Player player = event.getPlayer();
        if (Settings.hasAccess(player,chunk))return;
        event.setCancelled(true);
        cancelPlayer(player,chunk);
    }
    private void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),Settings.getOwner(chunk)))));
    }
}