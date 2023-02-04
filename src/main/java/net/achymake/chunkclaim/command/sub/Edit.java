package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Message;
import org.bukkit.entity.Player;

public class Edit extends ChunkSubCommand {
    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "allow to edit chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk edit";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("chunkclaim.edit")){
            if (args.length == 1){
                if (ChunkClaim.edit.contains(player)){
                    ChunkClaim.edit.remove(player);
                    player.sendMessage(Message.commandEditDisabled());
                }else{
                    ChunkClaim.edit.add(player);
                    player.sendMessage(Message.commandEditEnabled());
                }
            }
        }
    }
}