package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.ChunkData;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class UnProtectCommand extends ChunkSubCommand {
    @Override
    public String getName() {
        return "unprotect";
    }

    @Override
    public String getDescription() {
        return "unprotect the chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk unprotect";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("chunkclaim.unprotect")){
            if (Config.get().getStringList("worlds").contains(player.getWorld().getName())){
                ChunkData.get().set(player.getWorld().getName() + "." + player.getLocation().getChunk(),null);
                ChunkData.save();
                player.spawnParticle(Particle.valueOf(Config.get().getString("unclaim.particle.type")),player.getLocation().getChunk().getBlock(8,64,8).getX(),player.getLocation().add(0,3,0).getBlockY(),player.getLocation().getChunk().getBlock(8,64,8).getZ(), Config.get().getInt("unclaim.particle.count"), Config.get().getDouble("unclaim.particle.offsetX"), Config.get().getDouble("unclaim.particle.offsetY"), Config.get().getDouble("unclaim.particle.offsetZ"), Config.get().getDouble("unclaim.particle.extra"));
                player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("unclaim.sound.type")),Float.parseFloat(Config.get().getString("unclaim.sound.volume")), Config.get().getInt("unclaim.sound.pitch"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Chunk is now unprotected"));
            }
        }
    }
}