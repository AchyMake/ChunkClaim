package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.Message;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.SimpleDateFormat;

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
            if (Settings.isClaimed(chunk)) {
                if (Settings.isOwner(player,chunk)) {
                    player.sendMessage(Message.commandClaimAlreadyOwner());
                } else {
                    player.sendMessage(Message.commandClaimAlreadyClaimed(Settings.getOwner(chunk)));
                }
            } else {
                if (ChunkClaim.econ.getBalance(player) >= Config.config.getDouble("economy.cost")) {
                    ChunkClaim.econ.withdrawPlayer(player, Config.config.getDouble("economy.cost"));
                    claimChunk(player);
                    player.sendMessage(Message.commandClaimSuccess(Config.config.getDouble("economy.cost")));
                } else {
                    player.sendMessage(Message.commandClaimNotEnoughCurrency(Config.config.getDouble("economy.cost")));
                }
            }
        }
    }
    private PersistentDataContainer getData(Chunk chunk){
        return chunk.getPersistentDataContainer();
    }
    private void claimChunk(Player player){
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("owner"), PersistentDataType.STRING,player.getUniqueId().toString());
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("date-claimed"),PersistentDataType.STRING, SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,"");
        chunkParticle(player);
    }
    private void chunkParticle(Player player){
        int a = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(15,0,8).getX(),player.getLocation().getChunk().getBlock(15,0,8).getZ()).getLocation().getBlockY();
        int b = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(8,0,15).getX(),player.getLocation().getChunk().getBlock(8,0,15).getZ()).getLocation().getBlockY();
        int c = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(8,0,0).getX(),player.getLocation().getChunk().getBlock(8,0,0).getZ()).getLocation().getBlockY();
        int d = player.getWorld().getHighestBlockAt(player.getLocation().getChunk().getBlock(0,0,8).getX(),player.getLocation().getChunk().getBlock(0,0,8).getZ()).getLocation().getBlockY();
        Location locationSouth = new Location(player.getWorld(),player.getLocation().getChunk().getBlock(15, 0, 8).getX(), a, player.getLocation().getChunk().getBlock(15, 0, 8).getZ());
        Location locationEast = new Location(player.getWorld(), player.getLocation().getChunk().getBlock(8, 0, 15).getX(), b, player.getLocation().getChunk().getBlock(8, 0, 15).getZ());
        player.playSound(player.getLocation(), Sound.valueOf(Config.config.getString("settings.claim.sound.type")), Float.parseFloat(Config.config.getString("settings.claim.sound.volume")), Config.config.getInt("settings.claim.sound.pitch"));
        player.spawnParticle(Particle.valueOf(Config.config.getString("settings.claim.particle.type")), player.getLocation().getChunk().getBlock(8, 0, 0).getX(), c, player.getLocation().getChunk().getBlock(8, 0, 0).getZ(), 250, 4, 12, 0, 0);
        player.spawnParticle(Particle.valueOf(Config.config.getString("settings.claim.particle.type")), player.getLocation().getChunk().getBlock(0, 0, 8).getX(), d, player.getLocation().getChunk().getBlock(0, 0, 8).getZ(), 250, 0, 12, 4, 0);
        player.spawnParticle(Particle.valueOf(Config.config.getString("settings.claim.particle.type")),locationSouth.add(1,0,0), 250, 0, 12, 4, 0);
        player.spawnParticle(Particle.valueOf(Config.config.getString("settings.claim.particle.type")),locationEast.add(0,0,1), 250, 4, 12, 0, 0);
    }
}