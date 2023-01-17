package net.achymake.chunkclaim.listeners.entity;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.settings.ChunkSettings;
import net.achymake.chunkclaim.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageEntityBySnowball implements Listener {
    public PlayerDamageEntityBySnowball(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDamageEntityBySnowBall (EntityDamageByEntityEvent event){
        Chunk chunk = event.getEntity().getLocation().getChunk();
        if (!ChunkSettings.isClaimed(chunk))return;
        if (!event.getDamager().getType().equals(EntityType.SNOWBALL))return;
        Snowball snowball = (Snowball) event.getDamager();
        if (snowball.getShooter() instanceof Player){
            Player player = (Player) snowball.getShooter();
            if (Config.get().getStringList("hostiles").contains(event.getEntity().getType().toString()))return;
            if (ChunkSettings.isOwner(player.getUniqueId(),chunk))return;
            if (ChunkSettings.getMembers(chunk).contains(event.getDamager().getUniqueId()))return;
            if (PlayerSettings.hasEdit(player))return;
            event.setCancelled(true);
            ChunkSettings.cancelPlayer(player,chunk);
        }
    }
}