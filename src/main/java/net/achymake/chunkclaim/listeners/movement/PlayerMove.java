package net.achymake.chunkclaim.listeners.movement;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerMove implements Listener {
    public PlayerMove(ChunkClaim plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntity (PlayerMoveEvent event){
        Chunk chunk = event.getPlayer().getLocation().getChunk();
        if (ChunkSettings.isClaimed(chunk)){
            if (!event.getPlayer().getPersistentDataContainer().has(NamespacedKey.minecraft("chunk-visitor"), PersistentDataType.STRING)){
                ChunkSettings.playerVisitClaimedChunk(event.getPlayer(),chunk);
                event.getPlayer().getPersistentDataContainer().set(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING,ChunkSettings.getOwner(chunk));
            }else{
                if (!event.getPlayer().getPersistentDataContainer().get(NamespacedKey.minecraft("chunk-visitor"),PersistentDataType.STRING).equals(ChunkSettings.getOwner(chunk))){
                    event.getPlayer().getPersistentDataContainer().remove(NamespacedKey.minecraft("chunk-visitor"));
                }
            }
        }else{
            if (event.getPlayer().getPersistentDataContainer().has(NamespacedKey.minecraft("chunk-visitor"), PersistentDataType.STRING)){
                ChunkSettings.playerExitClaimedChunk(event.getPlayer());
                event.getPlayer().getPersistentDataContainer().remove(NamespacedKey.minecraft("chunk-visitor"));
            }
        }
    }
}