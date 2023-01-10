package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class MembersCommand extends ChunkSubCommand {
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
                    if (ChunkSettings.getMembers(chunk).isEmpty()){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6There are no members for this chunk"));
                    }else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Members:"));
                        for (String names : ChunkSettings.getMembers(chunk)){
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6-&f " + names));
                        }
                    }
                } else if (args.length == 3) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                    List<String> uuid = ChunkSettings.getMembers(chunk);
                    if (args[1].equalsIgnoreCase("add")) {
                        if (ChunkSettings.getMembers(chunk).contains(target.getName())) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getName() + "&c is already a member of this chunk"));
                        } else {
                            ChunkSettings.addMember(chunk,target.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getName() + "&6 is added to this chunk"));
                        }
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        if (ChunkSettings.getMembers(chunk).contains(target.getName())) {
                            ChunkSettings.removeMember(chunk,target.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getName() + "&6 is removed from this chunk"));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getName() + "&c is not a member of this chunk"));
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cChunk is owned by &f"+ ChunkSettings.getOwner(chunk)));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis chunk is unclaimed"));
        }
    }
}