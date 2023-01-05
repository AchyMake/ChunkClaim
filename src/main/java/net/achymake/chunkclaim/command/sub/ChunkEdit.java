package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.ChunkConfig;
import net.achymake.chunkclaim.settings.PlayerSettings;
import org.bukkit.entity.Player;

public class ChunkEdit extends ChunkSubCommand {
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
            if (ChunkConfig.get().getStringList("worlds").contains(player.getWorld().getName())){
                PlayerSettings.toggleChunkEdit(player);
            }
        }
    }
}