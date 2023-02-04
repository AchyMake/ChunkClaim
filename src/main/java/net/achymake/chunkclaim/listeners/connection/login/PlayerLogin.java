package net.achymake.chunkclaim.listeners.connection.login;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {
    public PlayerLogin(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onLogin (PlayerLoginEvent event){
        Player player = event.getPlayer();
        PlayerConfig.create(player);
    }
}