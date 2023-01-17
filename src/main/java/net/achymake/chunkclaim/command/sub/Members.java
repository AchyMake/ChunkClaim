package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class Members extends ChunkSubCommand {
    @Override
    public String getName() {
        return "members";
    }

    @Override
    public String getDescription() {
        return "add or removes members to the chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk members add/remove target";
    }

    @Override
    public void perform(Player player, String[] args) {
        Chunk chunk = player.getLocation().getChunk();
        if (ChunkSettings.isClaimed(chunk)) {
            if (ChunkSettings.isOwner(player.getUniqueId(),chunk)) {
                if (args.length == 1) {
                    if (ChunkSettings.getMembers(player.getLocation().getChunk()).isEmpty()){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("command-members-no-members")));
                    }else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("command-members")));
                        for (UUID uuid : ChunkSettings.getMembers(player.getLocation().getChunk())){
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7-&f "+Bukkit.getOfflinePlayer(uuid).getName()));
                        }
                    }
                } else if (args.length == 3) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                    if (args[1].equalsIgnoreCase("add")) {
                        if (ChunkSettings.getMembers(chunk).contains(target.getUniqueId())) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-already-member"),target.getName())));
                        } else {
                            ChunkSettings.addMember(chunk,target.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-add"),target.getName())));
                        }
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        if (ChunkSettings.getMembers(chunk).contains(target.getUniqueId())) {
                            ChunkSettings.removeMember(chunk,target.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-remove"),target.getName())));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-already-non-member"),target.getName())));
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),ChunkSettings.getOwner(chunk))));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("error-chunk-already-unclaimed")));
        }
    }
}