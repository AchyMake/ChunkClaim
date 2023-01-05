package net.achymake.chunkclaim.listeners.bucket;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.ChunkSettings;
import net.achymake.chunkclaim.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class ChunkBucketFill implements Listener {
    public ChunkBucketFill(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChatEvent (PlayerBucketFillEvent event){
        Chunk chunk = event.getBlockClicked().getChunk();
        if (!ChunkSettings.isClaimed(chunk))return;
        if (ChunkSettings.isOwner(event.getPlayer().getUniqueId(),chunk))return;
        if (ChunkSettings.isMember(event.getPlayer().getUniqueId(),chunk))return;
        if (PlayerSettings.hasEdit(event.getPlayer()))return;
        event.setCancelled(true);
        ChunkSettings.cancelPlayer(event.getPlayer(),chunk);
    }
}