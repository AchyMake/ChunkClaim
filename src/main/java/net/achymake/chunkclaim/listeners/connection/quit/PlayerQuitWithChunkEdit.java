package net.achymake.chunkclaim.listeners.connection.quit;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerQuitWithChunkEdit implements Listener {
    public PlayerQuitWithChunkEdit(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin (PlayerJoinEvent event){
        if (!ChunkClaim.edit.contains(event.getPlayer()))return;
        ChunkClaim.edit.remove(event.getPlayer());
    }
}