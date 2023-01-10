package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class DeleteCommand extends ChunkSubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "deletes the chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk delete";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("chunkclaim.delete")){
            Chunk chunk = player.getLocation().getChunk();
            if (ChunkSettings.isClaimed(chunk)){
                ChunkSettings.delete(chunk);
                player.spawnParticle(Particle.valueOf(Config.get().getString("unclaim.particle.type")),player.getLocation().getChunk().getBlock(8,64,8).getX(),player.getLocation().add(0,3,0).getBlockY(),player.getLocation().getChunk().getBlock(8,64,8).getZ(), Config.get().getInt("unclaim.particle.count"), Config.get().getDouble("unclaim.particle.offsetX"), Config.get().getDouble("unclaim.particle.offsetY"), Config.get().getDouble("unclaim.particle.offsetZ"), Config.get().getDouble("unclaim.particle.extra"));
                player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("unclaim.sound.type")),Float.parseFloat(Config.get().getString("unclaim.sound.volume")), Config.get().getInt("unclaim.sound.pitch"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Chunk is now unclaimed"));
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cChunk is already unclaimed"));
            }
        }
    }
}