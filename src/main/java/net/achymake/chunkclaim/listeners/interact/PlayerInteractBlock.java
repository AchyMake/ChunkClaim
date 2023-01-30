package net.achymake.chunkclaim.listeners.interact;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.MessageFormat;

public class PlayerInteractBlock implements Listener {
    public PlayerInteractBlock(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntity (PlayerInteractEvent event){
        if (event.getClickedBlock() == null)return;
        Chunk chunk = event.getClickedBlock().getChunk();
        if (!Settings.isClaimed(chunk))return;
        Player player = event.getPlayer();
        if (Settings.hasAccess(player,chunk))return;
        if (Tag.CARPETS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.STAIRS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.SLABS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.SIGNS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.WALL_SIGNS.isTagged(event.getClickedBlock().getType()))return;
        if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST))return;
        if (event.getClickedBlock().getType().equals(Material.CRAFTING_TABLE))return;
        if (event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE))return;
        event.setCancelled(true);
        cancelPlayer(player,chunk);
    }
    private void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),Settings.getOwner(chunk)))));
    }
}