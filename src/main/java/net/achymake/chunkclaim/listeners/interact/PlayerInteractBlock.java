package net.achymake.chunkclaim.listeners.interact;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Message;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Message.eventCancel(Settings.getOwner(chunk))));
    }
}