package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Config;
import net.achymake.chunkclaim.config.ChunkData;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

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
        if (Config.get().getStringList("worlds").contains(player.getWorld().getName())){
            Chunk chunk = player.getLocation().getChunk();
            if (ChunkSettings.isClaimed(chunk)) {
                if (ChunkSettings.isOwner(player.getUniqueId(),chunk)) {
                    if (args.length == 1) {
                        if (ChunkSettings.getMembers(chunk).isEmpty()){
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6There are no members for this chunk"));
                        }else{
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Members:"));
                            for (String uuid : ChunkData.get().getStringList(player.getWorld().getName() + "." + player.getLocation().getChunk() + ".members")){
                                String name = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6-&f " + name));
                            }
                        }
                    } else if (args.length == 3) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                        List<String> uuid = ChunkData.get().getStringList(player.getWorld().getName() + "." + player.getLocation().getChunk() + ".members");
                        if (args[1].equalsIgnoreCase("add")) {
                            if (uuid.contains(target.getUniqueId().toString())) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getName() + "&c is already a member of this chunk"));
                            } else {
                                uuid.add(target.getUniqueId().toString());
                                ChunkData.get().set(player.getWorld().getName() + "." + player.getLocation().getChunk() + ".members", uuid);
                                ChunkData.save();
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getName() + "&6 is added to this chunk"));
                            }
                        } else if (args[1].equalsIgnoreCase("remove")) {
                            if (uuid.contains(target.getUniqueId().toString())) {
                                uuid.remove(target.getUniqueId().toString());
                                ChunkData.get().set(player.getWorld().getName() + "." + player.getLocation().getChunk() + ".members", uuid);
                                ChunkData.save();
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
}