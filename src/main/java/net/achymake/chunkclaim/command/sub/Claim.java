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

public class Claim extends ChunkSubCommand {
    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "claims the chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk claim";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1){
            Chunk chunk = player.getLocation().getChunk();
            if (ChunkSettings.isClaimed(chunk)) {
                if (ChunkSettings.isOwner(player.getUniqueId(),chunk)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("error-chunk-already-owned")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),ChunkSettings.getOwner(chunk))));
                }
            } else {
                if (ChunkClaim.econ.getBalance(player) >= Config.get().getDouble("economy.cost")) {
                    ChunkClaim.econ.withdrawPlayer(player, Config.get().getDouble("economy.cost"));
                    ChunkSettings.claimChunk(player);
                    player.spawnParticle(Particle.valueOf(Config.get().getString("settings.claim.particle.type")), player.getLocation().getChunk().getBlock(8, 64, 8).getX(), player.getLocation().add(0,3,0).getBlockY(), player.getLocation().getChunk().getBlock(8, 64, 8).getZ(), Config.get().getInt("settings.claim.particle.count"), Config.get().getDouble("settings.claim.particle.offsetX"), Config.get().getDouble("settings.claim.particle.offsetY"), Config.get().getDouble("settings.claim.particle.offsetZ"), 0);
                    player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("settings.claim.sound.type")), Float.parseFloat(Config.get().getString("settings.claim.sound.volume")), Config.get().getInt("settings.claim.sound.pitch"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageFormat.format(MessageConfig.get().getString("command-claim"),ChunkClaim.econ.format(Config.get().getDouble("economy.cost")))));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageFormat.format(MessageConfig.get().getString("error-not-enough-currency"),ChunkClaim.econ.format(Config.get().getDouble("economy.cost")))));
                }
            }
        }
    }
}