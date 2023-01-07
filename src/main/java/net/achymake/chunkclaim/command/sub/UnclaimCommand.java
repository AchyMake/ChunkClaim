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

public class UnclaimCommand extends ChunkSubCommand {
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
        if (Config.get().getStringList("worlds").contains(player.getWorld().getName())){
            Chunk chunk = player.getLocation().getChunk();
            if (ChunkSettings.isClaimed(chunk)){
                if (ChunkSettings.isOwner(player.getUniqueId(),chunk)){
                    ChunkClaim.econ.depositPlayer(player, Config.get().getDouble("economy.claim-refund"));
                    ChunkData.get().set(player.getWorld().getName() + "." + player.getLocation().getChunk(),null);
                    ChunkData.save();
                    player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")),player.getLocation().getChunk().getBlock(8,64,8).getX(),player.getLocation().add(0,3,0).getBlockY(),player.getLocation().getChunk().getBlock(8,64,8).getZ(), Config.get().getInt("settings.un-claim.particle.count"), Config.get().getDouble("settings.un-claim.particle.offsetX"), Config.get().getDouble("settings.un-claim.particle.offsetY"), Config.get().getDouble("settings.un-claim.particle.offsetZ"), 0);
                    player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("settings.un-claim.sound.type")),Float.parseFloat(Config.get().getString("settings.un-claim.sound.volume")), Config.get().getInt("settings.un-claim.sound.pitch"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6You unclaimed a chunk refunded &a$"+ChunkClaim.econ.format(Config.get().getDouble("economy.claim-refund"))));
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cChunk is owned by &f"+ ChunkSettings.getOwner(chunk)));
                }
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThis chunk is already unclaimed"));
            }
        }
    }
}