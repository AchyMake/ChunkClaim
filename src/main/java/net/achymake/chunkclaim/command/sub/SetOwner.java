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
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                if (ChunkSettings.isOwner(player.getUniqueId(),chunk)) {
                    ChunkSettings.setOwner(player,offlinePlayer.getUniqueId(),player.getLocation().getChunk());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-setowner"),offlinePlayer.getName())));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),ChunkSettings.getOwner(chunk))));
                }
            }
        }
    }
}