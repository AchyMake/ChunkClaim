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

public class ChunkUnclaim extends ChunkSubCommand {
    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getDescription() {
        return "unclaims current chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk unclaim";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (ChunkConfig.get().getStringList("worlds").contains(player.getWorld().getName())){
            Chunk chunk = player.getLocation().getChunk();
            if (ChunkSettings.isClaimed(chunk)){
                if (ChunkSettings.isOwner(player.getUniqueId(),chunk)){
                    Economy.addEconomy(player.getUniqueId(), ChunkConfig.get().getDouble("claim-refund"));
                    ChunkData.get().set(player.getWorld().getName() + "." + player.getLocation().getChunk(),null);
                    ChunkData.save();
                    player.spawnParticle(Particle.valueOf(ChunkConfig.get().getString("unclaim.particle.type")),player.getLocation().getChunk().getBlock(8,64,8).getX(),player.getLocation().add(0,3,0).getBlockY(),player.getLocation().getChunk().getBlock(8,64,8).getZ(), ChunkConfig.get().getInt("unclaim.particle.count"), ChunkConfig.get().getDouble("unclaim.particle.offsetX"), ChunkConfig.get().getDouble("unclaim.particle.offsetY"), ChunkConfig.get().getDouble("unclaim.particle.offsetZ"), ChunkConfig.get().getDouble("unclaim.particle.extra"));
                    player.playSound(player.getLocation(), Sound.valueOf(ChunkConfig.get().getString("unclaim.sound.type")),Float.parseFloat(ChunkConfig.get().getString("unclaim.sound.volume")), ChunkConfig.get().getInt("unclaim.sound.pitch"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6You unclaimed a chunk refunded &a$"+Economy.getFormat(ChunkConfig.get().getDouble("claim-refund"))));
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cChunk is owned by &f"+ ChunkSettings.getOwner(chunk)));
                }
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThis chunk is already unclaimed"));
            }
        }
    }
}