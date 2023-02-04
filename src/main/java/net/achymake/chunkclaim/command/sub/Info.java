package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Message;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

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
            Chunk chunk = player.getLocation().getChunk();
            if (Settings.isClaimed(chunk)){
                player.sendMessage(Message.commandInfoOwner(Settings.getOwner(chunk)));
                player.sendMessage(Message.commandInfoDateClaimed(chunk));
                if (Settings.getMembers(player.getLocation().getChunk()).isEmpty()){
                    player.sendMessage(Message.commandInfoMembersEmpty());
                }else{
                    player.sendMessage(Message.commandInfoMembersTitle());
                    for (UUID uuid : Settings.getMembersUUID(player.getLocation().getChunk())){
                        player.sendMessage(Message.commandInfoMembersList(Bukkit.getOfflinePlayer(uuid)));
                    }
                }
            }
        }
    }
}