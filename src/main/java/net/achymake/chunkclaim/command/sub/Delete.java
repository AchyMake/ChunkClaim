package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

public class Delete extends ChunkSubCommand {
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
            if (args.length == 1){
                Chunk chunk = player.getLocation().getChunk();
                if (Settings.isClaimed(chunk)){
                    delete(chunk);
                    chunkParticle(player);
                    player.playSound(player.getLocation(), Sound.valueOf(Config.get().getString("settings.un-claim.sound.type")),Float.parseFloat(Config.get().getString("settings.un-claim.sound.volume")), Config.get().getInt("settings.un-claim.sound.pitch"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("command-delete")));
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("error-chunk-already-unclaimed")));
                }
            }
        }
    }
    private void chunkParticle(Player player){
        int a = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(15,0,8).getX(),player.getLocation().getChunk().getBlock(15,0,8).getZ()).getLocation().getBlockY();
        int b = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(8,0,15).getX(),player.getLocation().getChunk().getBlock(8,0,15).getZ()).getLocation().getBlockY();
        int c = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(8,0,0).getX(),player.getLocation().getChunk().getBlock(8,0,0).getZ()).getLocation().getBlockY();
        int d = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(0,0,8).getX(),player.getLocation().getChunk().getBlock(0,0,8).getZ()).getLocation().getBlockY();
        Location locationSouth = new Location(player.getWorld(),player.getLocation().getChunk().getBlock(15, 0, 8).getX(), a, player.getLocation().getChunk().getBlock(15, 0, 8).getZ());
        Location locationEast = new Location(player.getWorld(), player.getLocation().getChunk().getBlock(8, 0, 15).getX(), b, player.getLocation().getChunk().getBlock(8, 0, 15).getZ());
        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")), player.getLocation().getChunk().getBlock(8, 0, 0).getX(), c, player.getLocation().getChunk().getBlock(8, 0, 0).getZ(), 250, 4, 12, 0, 0);
        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")), player.getLocation().getChunk().getBlock(0, 0, 8).getX(), d, player.getLocation().getChunk().getBlock(0, 0, 8).getZ(), 250, 0, 12, 4, 0);
        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")),locationSouth.add(1,0,0), 250, 0, 12, 4, 0);
        player.spawnParticle(Particle.valueOf(Config.get().getString("settings.un-claim.particle.type")),locationEast.add(0,0,1), 250, 4, 12, 0, 0);
    }
    private static PersistentDataContainer getData(Chunk chunk){
        return chunk.getPersistentDataContainer();
    }
    private void delete(Chunk chunk){
        if (Settings.getOwner(chunk) != null){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(Settings.getOwner(chunk));
            ChunkClaim.econ.depositPlayer(offlinePlayer, Config.get().getDouble("economy.refund"));
        }
        getData(chunk).remove(NamespacedKey.minecraft("owner"));
        getData(chunk).remove(NamespacedKey.minecraft("date-claimed"));
        getData(chunk).remove(NamespacedKey.minecraft("members"));
    }
}