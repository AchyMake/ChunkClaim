package net.achymake.chunkclaim.listeners.block;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.config.Message;
import net.achymake.chunkclaim.settings.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    public BlockBreak(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBlockBreak (BlockBreakEvent event){
        Chunk chunk = event.getBlock().getChunk();
        if (!Settings.isClaimed(chunk))return;
        Player player = event.getPlayer();
        if (Settings.hasAccess(player,chunk))return;
        event.setCancelled(true);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Message.eventCancel(Settings.getOwner(chunk))));
    }
}