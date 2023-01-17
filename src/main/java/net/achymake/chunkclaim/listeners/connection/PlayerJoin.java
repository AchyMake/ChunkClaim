package net.achymake.chunkclaim.listeners.connection;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.version.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    public PlayerJoin(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin (PlayerJoinEvent event){
        if (!event.getPlayer().hasPermission("chunkclaim.reload"))return;
        UpdateChecker.sendMessage(event.getPlayer());
    }
}