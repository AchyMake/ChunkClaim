package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.MessageConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help extends ChunkSubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "checks chunk help";
    }

    @Override
    public String getSyntax() {
        return "/chunk help";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1){
            if (player.hasPermission("chunkclaim.default")){
                for (String helpMessage : MessageConfig.get().getStringList("command-help-default")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',helpMessage));
                }
            }
            if (player.hasPermission("chunkclaim.admin")){
                for (String helpMessage : MessageConfig.get().getStringList("command-help-admin")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',helpMessage));
                }
            }
        }
    }
}