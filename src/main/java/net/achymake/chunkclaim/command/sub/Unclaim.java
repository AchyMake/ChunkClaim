package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.*;
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
                chunkParticle(player);
                player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("settings.un-claim.sound.type")),Float.parseFloat(Config.get().getString("settings.un-claim.sound.volume")), Config.get().getInt("settings.un-claim.sound.pitch"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageFormat.format(MessageConfig.get().getString("command-unclaim"),ChunkClaim.econ.format(Config.get().getDouble("economy.refund")))));
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),ChunkSettings.getOwner(chunk))));
            }
        }else{
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("error-chunk-already-unclaimed")));
        }
    }
    private void chunkParticle(Player player){
        int a = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(15,0,8).getX(),player.getLocation().getChunk().getBlock(15,0,8).getZ()).getLocation().getBlockY();
        Location locationSouth = new Location(player.getWorld(),player.getLocation().getChunk().getBlock(15, 0, 8).getX(), a, player.getLocation().getChunk().getBlock(15, 0, 8).getZ());
        int b = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(8,0,15).getX(),player.getLocation().getChunk().getBlock(8,0,15).getZ()).getLocation().getBlockY();
        Location locationEast = new Location(player.getWorld(), player.getLocation().getChunk().getBlock(8, 0, 15).getX(), b, player.getLocation().getChunk().getBlock(8, 0, 15).getZ());
        int c = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(8,0,0).getX(),player.getLocation().getChunk().getBlock(8,0,0).getZ()).getLocation().getBlockY();
        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")), player.getLocation().getChunk().getBlock(8, 0, 0).getX(), c, player.getLocation().getChunk().getBlock(8, 0, 0).getZ(), 250, 4, 12, 0, 0);
        int d = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(0,0,8).getX(),player.getLocation().getChunk().getBlock(0,0,8).getZ()).getLocation().getBlockY();
        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")), player.getLocation().getChunk().getBlock(0, 0, 8).getX(), d, player.getLocation().getChunk().getBlock(0, 0, 8).getZ(), 250, 0, 12, 4, 0);

        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")),locationSouth.add(1,0,0), 250, 0, 12, 4, 0);
        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")),locationEast.add(0,0,1), 250, 4, 12, 0, 0);
    }
}