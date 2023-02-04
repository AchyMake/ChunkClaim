package net.achymake.chunkclaim.listeners.entity;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.Message;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
        if (!Settings.isClaimed(chunk))return;
        if (!event.getDamager().getType().equals(EntityType.SNOWBALL))return;
        Snowball snowball = (Snowball) event.getDamager();
        if (snowball.getShooter() instanceof Player){
            Player player = (Player) snowball.getShooter();
            if (Config.config.getStringList("hostiles").contains(event.getEntity().getType().toString()))return;
            if (Settings.hasAccess(player,chunk))return;
            event.setCancelled(true);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Message.eventCancel(Settings.getOwner(chunk))));
        }
    }
}