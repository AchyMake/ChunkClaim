package net.achymake.chunkclaim.listeners.interact;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.settings.ChunkSettings;
import net.achymake.chunkclaim.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {
    public PlayerInteractEntity(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChatEvent (PlayerInteractEntityEvent event){
        Chunk chunk = event.getRightClicked().getLocation().getChunk();
        if (!ChunkSettings.isClaimed(chunk))return;
        if (ChunkSettings.isOwner(event.getPlayer().getUniqueId(),chunk))return;
        if (ChunkSettings.isMember(event.getPlayer().getUniqueId(),chunk))return;
        if (Config.get().getStringList("hostiles").contains(event.getRightClicked().getType().toString()))return;
        if (PlayerSettings.hasEdit(event.getPlayer()))return;
        event.setCancelled(true);
        ChunkSettings.cancelPlayer(event.getPlayer(),chunk);
    }
}