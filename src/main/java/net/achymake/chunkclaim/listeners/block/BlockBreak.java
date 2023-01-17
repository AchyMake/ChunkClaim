package net.achymake.chunkclaim.listeners.block;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.ChunkSettings;
import net.achymake.chunkclaim.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreak implements Listener {
    public BlockBreak(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerBlockBreak (BlockBreakEvent event){
        Chunk chunk = event.getBlock().getChunk();
        UUID uuid = event.getPlayer().getUniqueId();
        if (!ChunkSettings.isClaimed(chunk))return;
        if (ChunkSettings.isOwner(uuid,chunk))return;
        if (ChunkSettings.getMembers(chunk).contains(event.getPlayer().getUniqueId()))return;
        if (PlayerSettings.hasEdit(event.getPlayer()))return;
        event.setCancelled(true);
        ChunkSettings.cancelPlayer(event.getPlayer(),chunk);
    }
}