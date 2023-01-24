package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class SetOwner extends ChunkSubCommand {
    @Override
    public String getName() {
        return "setowner";
    }

    @Override
    public String getDescription() {
        return "sets chunk owner";
    }

    @Override
    public String getSyntax() {
        return "/chunk setowner target";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 2){
            Chunk chunk = player.getLocation().getChunk();
            UUID uuid = player.getUniqueId();
            if (Settings.isClaimed(chunk)) {
                if (Settings.isOwner(chunk,uuid)) {
                    if (player.getPersistentDataContainer().has(NamespacedKey.minecraft("change-owner"), PersistentDataType.STRING)){
                        String nextOwner = player.getPersistentDataContainer().get(NamespacedKey.minecraft("change-owner"), PersistentDataType.STRING);
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nextOwner);
                        setOwner(player,offlinePlayer.getUniqueId());
                        int task = player.getPersistentDataContainer().get(NamespacedKey.minecraft("change-owner-task"),PersistentDataType.INTEGER);
                        Bukkit.getScheduler().cancelTask(task);
                        player.getPersistentDataContainer().remove(NamespacedKey.minecraft("change-owner"));
                        player.getPersistentDataContainer().remove(NamespacedKey.minecraft("change-owner-task"));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-setowner-changed"),offlinePlayer.getName())));
                    }else{
                        final int taskID = Bukkit.getScheduler().runTaskLater(ChunkClaim.instance, new Runnable() {
                            @Override
                            public void run() {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("error-chunk-changes-expired")));
                                player.getPersistentDataContainer().remove(NamespacedKey.minecraft("change-owner"));
                                player.getPersistentDataContainer().remove(NamespacedKey.minecraft("change-owner-task"));
                            }
                        }, 300).getTaskId();
                        for (String messages : MessageConfig.get().getStringList("command-setowner")){
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageFormat.format(messages,args[1])));
                        }
                        player.getPersistentDataContainer().set(NamespacedKey.minecraft("change-owner"),PersistentDataType.STRING,args[1]);
                        player.getPersistentDataContainer().set(NamespacedKey.minecraft("change-owner-task"),PersistentDataType.INTEGER,taskID);
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),Settings.getOwner(chunk))));
                }
            }
        }
    }
    private static PersistentDataContainer getData(Chunk chunk){
        return chunk.getPersistentDataContainer();
    }
    private static void setOwner(Player player, UUID uuid){
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("owner"), PersistentDataType.STRING,uuid.toString());
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,"");
        getData(player.getLocation().getChunk()).set(NamespacedKey.minecraft("date-claimed"),PersistentDataType.STRING, SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
    }
}