package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.UUID;

public class Info extends ChunkSubCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "checks chunk info";
    }

    @Override
    public String getSyntax() {
        return "/chunk info";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("chunkclaim.info")){
            if (ChunkSettings.isClaimed(player.getLocation().getChunk())){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-info-owner"),ChunkSettings.getOwner(player.getLocation().getChunk()))));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-info-date-claimed"),ChunkSettings.getDateClaimed(player.getLocation().getChunk()))));
                if (ChunkSettings.getMembers(player.getLocation().getChunk()).isEmpty()){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("command-members-no-members")));
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("command-members")));
                    for (UUID uuid : ChunkSettings.getMembers(player.getLocation().getChunk())){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7-&f "+Bukkit.getOfflinePlayer(uuid).getName()));
                    }
                }
            }
        }
    }
}