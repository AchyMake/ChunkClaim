package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.settings.ChunkSettings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class InfoCommand extends ChunkSubCommand {
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
        if (player.hasPermission("chunkclaim.edit")){
            if (ChunkSettings.isClaimed(player.getLocation().getChunk())){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6Chunk owner:&f "+ChunkSettings.getOwner(player.getLocation().getChunk())));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6Date claimed:&f "+ChunkSettings.getDateClaimed(player.getLocation().getChunk())));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6Members:"));
                if (ChunkSettings.getMembers(player.getLocation().getChunk()) != null){
                    for (String members : ChunkSettings.getMembers(player.getLocation().getChunk())){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6-&f "+members));
                    }
                }
            }
        }
    }
}