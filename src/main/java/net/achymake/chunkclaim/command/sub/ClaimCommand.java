package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.ChunkData;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class ClaimCommand extends ChunkSubCommand {
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
        if (Config.get().getStringList("worlds").contains(player.getWorld().getName())){
            Chunk chunk = player.getLocation().getChunk();
            if (ChunkSettings.isClaimed(chunk)) {
                if (ChunkSettings.isOwner(player.getUniqueId(),chunk)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou already own this chunk"));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cChunk is owned by &f"+ ChunkSettings.getOwner(chunk)));
                }
            } else {
                if (ChunkClaim.econ.getBalance(player) >= Config.get().getDouble("economy.claim-cost")) {
                    ChunkClaim.econ.withdrawPlayer(player, Config.get().getDouble("economy.claim-cost"));
                    ChunkData.get().set(player.getWorld().getName() + "." + player.getLocation().getChunk() + ".owner", player.getUniqueId().toString());
                    ChunkData.get().set(player.getWorld().getName()+"."+player.getLocation().getChunk()+".date-claimed",SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
                    ChunkData.save();
                    player.spawnParticle(Particle.valueOf(Config.get().getString("settings.claim.particle.type")), player.getLocation().getChunk().getBlock(8, 64, 8).getX(), player.getLocation().add(0,3,0).getBlockY(), player.getLocation().getChunk().getBlock(8, 64, 8).getZ(), Config.get().getInt("settings.claim.particle.count"), Config.get().getDouble("settings.claim.particle.offsetX"), Config.get().getDouble("settings.claim.particle.offsetY"), Config.get().getDouble("settings.claim.particle.offsetZ"), 0);
                    player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("settings.claim.sound.type")), Float.parseFloat(Config.get().getString("settings.claim.sound.volume")), Config.get().getInt("settings.claim.sound.pitch"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6Chunk is claimed for &c"+ChunkClaim.econ.format(Config.get().getDouble("economy.claim-cost"))));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou dont have &a"+ChunkClaim.econ.format(Config.get().getDouble("economy.claim-cost"))+"&c to claim this chunk"));
                }
            }
        }
    }
}