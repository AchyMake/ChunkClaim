package net.achymake.chunkclaim.listeners.entity;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.text.MessageFormat;

public class PlayerDamageEntityByArrow implements Listener {
    public PlayerDamageEntityByArrow(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDamageEntityByArrow (EntityDamageByEntityEvent event){
        Chunk chunk = event.getEntity().getLocation().getChunk();
        if (!Settings.isClaimed(chunk))return;
        if (!event.getDamager().getType().equals(EntityType.ARROW))return;
        Arrow arrow = (Arrow) event.getDamager();
        if (arrow.getShooter() instanceof Player){
            Player player = (Player) arrow.getShooter();
            if (Config.get().getStringList("hostiles").contains(event.getEntity().getType().toString()))return;
            if (Settings.hasAccess(player,chunk))return;
            event.setCancelled(true);
            cancelPlayer(player,chunk);
        }
    }
    private void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),Settings.getOwner(chunk)))));
    }
}