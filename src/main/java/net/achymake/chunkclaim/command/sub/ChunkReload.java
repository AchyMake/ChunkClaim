package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.ChunkFiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChunkReload extends ChunkSubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "allow to edit chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("chunkclaim.reload")){
            ChunkFiles.reload();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6ChunkClaim reloaded"));
        }
    }
}