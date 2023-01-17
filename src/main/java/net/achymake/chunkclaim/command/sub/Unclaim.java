package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class Unclaim extends ChunkSubCommand {
    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getDescription() {
        return "un-claims current chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk unclaim";
    }

    @Override
    public void perform(Player player, String[] args) {
        Chunk chunk = player.getLocation().getChunk();
        if (ChunkSettings.isClaimed(chunk)){
            if (ChunkSettings.isOwner(player.getUniqueId(),chunk)){
                ChunkSettings.delete(chunk);
                player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")),player.getLocation().getChunk().getBlock(8,64,8).getX(),player.getLocation().add(0,3,0).getBlockY(),player.getLocation().getChunk().getBlock(8,64,8).getZ(), Config.get().getInt("settings.un-claim.particle.count"), Config.get().getDouble("settings.un-claim.particle.offsetX"), Config.get().getDouble("settings.un-claim.particle.offsetY"), Config.get().getDouble("settings.un-claim.particle.offsetZ"), 0);
                player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("settings.un-claim.sound.type")),Float.parseFloat(Config.get().getString("settings.un-claim.sound.volume")), Config.get().getInt("settings.un-claim.sound.pitch"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageFormat.format(MessageConfig.get().getString("command-unclaim"),ChunkClaim.econ.format(Config.get().getDouble("economy.refund")))));
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),ChunkSettings.getOwner(chunk))));
            }
        }else{
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("error-chunk-already-unclaimed")));
        }
    }
}