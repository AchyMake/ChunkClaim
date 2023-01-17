package net.achymake.chunkclaim.listeners.block;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.ChunkSettings;
import net.achymake.chunkclaim.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    public BlockPlace(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerBlockPlace (BlockPlaceEvent event){
        Chunk chunk = event.getBlockPlaced().getChunk();
        if (!ChunkSettings.isClaimed(chunk))return;
        if (ChunkSettings.isOwner(event.getPlayer().getUniqueId(),chunk))return;
        if (ChunkSettings.getMembers(chunk).contains(event.getPlayer().getUniqueId()))return;
        if (PlayerSettings.hasEdit(event.getPlayer()))return;
        event.setCancelled(true);
        ChunkSettings.cancelPlayer(event.getPlayer(),chunk);
    }
}