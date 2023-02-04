package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Message;
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
            player.sendMessage(Message.commandHelpTitle());
            if (player.hasPermission("chunkclaim.claim")){
                player.sendMessage(Message.commandHelpClaim());
            }
            if (player.hasPermission("chunkclaim.delete")){
                player.sendMessage(Message.commandHelpDelete());
            }
            if (player.hasPermission("chunkclaim.edit")){
                player.sendMessage(Message.commandHelpEdit());
            }
            if (player.hasPermission("chunkclaim.help")){
                player.sendMessage(Message.commandHelpHelp());
            }
            if (player.hasPermission("chunkclaim.info")){
                player.sendMessage(Message.commandHelpInfo());
            }
            if (player.hasPermission("chunkclaim.members")){
                player.sendMessage(Message.commandHelpMembers());
            }
            if (player.hasPermission("chunkclaim.reload")){
                player.sendMessage(Message.commandHelpReload());
            }
            if (player.hasPermission("chunkclaim.unclaim")){
                player.sendMessage(Message.commandHelpUnclaim());
            }
        }
    }
}