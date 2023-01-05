package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.ChunkConfig;
import net.achymake.chunkclaim.config.ChunkData;
import net.achymake.chunkclaim.settings.ChunkSettings;
import net.achymake.essential.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class ChunkClaim extends ChunkSubCommand {
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
        if (ChunkConfig.get().getStringList("worlds").contains(player.getWorld().getName())){
            Chunk chunk = player.getLocation().getChunk();
            if (ChunkSettings.isClaimed(chunk)) {
                if (ChunkSettings.isOwner(player.getUniqueId(),chunk)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou already own this chunk"));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cChunk is owned by &f"+ ChunkSettings.getOwner(chunk)));
                }
            } else {
                if (Economy.getEconomy(player.getUniqueId()) >= ChunkConfig.get().getDouble("claim-cost")) {
                    Economy.removeEconomy(player.getUniqueId(), ChunkConfig.get().getDouble("claim-cost"));
                    ChunkData.get().set(player.getWorld().getName() + "." + player.getLocation().getChunk() + ".owner", player.getUniqueId().toString());
                    ChunkData.get().set(player.getWorld().getName()+"."+player.getLocation().getChunk()+".date-claimed",SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
                    ChunkData.save();
                    player.spawnParticle(Particle.valueOf(ChunkConfig.get().getString("claim.particle.type")), player.getLocation().getChunk().getBlock(8, 64, 8).getX(), player.getLocation().add(0,3,0).getBlockY(), player.getLocation().getChunk().getBlock(8, 64, 8).getZ(), ChunkConfig.get().getInt("claim.particle.count"), ChunkConfig.get().getDouble("claim.particle.offsetX"), ChunkConfig.get().getDouble("claim.particle.offsetY"), ChunkConfig.get().getDouble("claim.particle.offsetZ"), ChunkConfig.get().getDouble("claim.particle.extra"));
                    player.playSound(player.getLocation(), Sound.valueOf(ChunkConfig.get().getString("claim.sound.type")), Float.parseFloat(ChunkConfig.get().getString("claim.sound.volume")), ChunkConfig.get().getInt("claim.sound.pitch"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6Chunk is claimed for &c$"+Economy.getFormat(ChunkConfig.get().getDouble("claim-cost"))));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou dont have &a$"+Economy.getFormat(ChunkConfig.get().getDouble("claim-cost"))+"&c to claim this chunk"));
                }
            }
        }
    }
}