package net.achymake.chunkclaim.listeners.interact;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.MessageFormat;
import java.util.UUID;

public class Anvil implements Listener {
    public Anvil(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractBlock (PlayerInteractEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (event.getClickedBlock() == null)return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        Chunk chunk = event.getClickedBlock().getChunk();
        if (!Settings.isClaimed(chunk))return;
        if (!Tag.ANVIL.isTagged(event.getClickedBlock().getType()))return;
        if (Settings.isOwner(chunk,uuid))return;
        if (Settings.isMember(chunk,uuid))return;
        if (Settings.hasChunkEdit(player))return;
        event.setCancelled(true);
        cancelPlayer(player,chunk);
    }
    private void cancelPlayer(Player player, Chunk chunk) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),Settings.getOwner(chunk)))));
    }
}