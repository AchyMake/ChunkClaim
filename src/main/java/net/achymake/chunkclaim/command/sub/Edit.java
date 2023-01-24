package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
                if (Settings.hasChunkEdit(player)){
                    getData(player).remove(NamespacedKey.minecraft("chunk-edit"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("command-edit-disabled")));
                }else{
                    getData(player).set(NamespacedKey.minecraft("chunk-edit"), PersistentDataType.STRING,"true");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("command-edit-enabled")));
                }
            }
        }
    }
    private PersistentDataContainer getData(Player player){
        return player.getPersistentDataContainer();
    }
}