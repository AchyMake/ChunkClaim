package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Files;
import net.achymake.chunkclaim.config.Message;
import org.bukkit.entity.Player;

public class Reload extends ChunkSubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reload config";
    }

    @Override
    public String getSyntax() {
        return "/chunk reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("chunkclaim.reload")){
            Files.reload();
            player.sendMessage(Message.commandReload());
        }
    }
}