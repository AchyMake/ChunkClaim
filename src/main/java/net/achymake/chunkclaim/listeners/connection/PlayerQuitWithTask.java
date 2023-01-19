package net.achymake.chunkclaim.listeners.connection;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerQuitWithTask implements Listener {
    public PlayerQuitWithTask(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin (PlayerJoinEvent event){
        if (!event.getPlayer().getPersistentDataContainer().has(NamespacedKey.minecraft("change-owner"), PersistentDataType.STRING))return;
        int task = event.getPlayer().getPersistentDataContainer().get(NamespacedKey.minecraft("change-owner-task"),PersistentDataType.INTEGER);
        Bukkit.getScheduler().cancelTask(task);
        event.getPlayer().getPersistentDataContainer().remove(NamespacedKey.minecraft("change-owner"));
        event.getPlayer().getPersistentDataContainer().remove(NamespacedKey.minecraft("change-owner-task"));
    }
}