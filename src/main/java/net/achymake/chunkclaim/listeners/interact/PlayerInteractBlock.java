package net.achymake.chunkclaim.listeners.interact;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.ChunkSettings;
import net.achymake.chunkclaim.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractBlock implements Listener {
    public PlayerInteractBlock(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChatEvent (PlayerInteractEvent event){
        if (event.getClickedBlock() == null)return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        Chunk chunk = event.getClickedBlock().getChunk();
        if (!ChunkSettings.isClaimed(chunk))return;
        if (Tag.STAIRS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.SLABS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.SIGNS.isTagged(event.getClickedBlock().getType()))return;
        if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST))return;
        if (event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE))return;
        if (ChunkSettings.isOwner(event.getPlayer().getUniqueId(),chunk))return;
        if (ChunkSettings.isMember(event.getPlayer().getUniqueId(),chunk))return;
        if (PlayerSettings.hasEdit(event.getPlayer()))return;
        event.setCancelled(true);
        ChunkSettings.cancelPlayer(event.getPlayer(),chunk);
    }
}