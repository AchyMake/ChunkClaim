package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.text.MessageFormat;

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
            if (ChunkSettings.isClaimed(chunk)) {
                if (player.getPersistentDataContainer().has(NamespacedKey.minecraft("change-owner"), PersistentDataType.STRING)){
                    String nextOwner = player.getPersistentDataContainer().get(NamespacedKey.minecraft("change-owner"), PersistentDataType.STRING);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nextOwner);
                    if (ChunkSettings.isOwner(player.getUniqueId(),chunk)) {
                        ChunkSettings.setOwner(player,offlinePlayer.getUniqueId(),player.getLocation().getChunk());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-setowner-changed"),offlinePlayer.getName())));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),ChunkSettings.getOwner(chunk))));
                    }
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
            }
        }
    }
}