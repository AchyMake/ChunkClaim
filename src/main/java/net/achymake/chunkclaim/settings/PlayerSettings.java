package net.achymake.chunkclaim.settings;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerSettings {
    private static PersistentDataContainer getData(Player player){
        return player.getPersistentDataContainer();
    }
    public static boolean hasEdit(Player player){
        return getData(player).has(NamespacedKey.minecraft("chunk-edit"), PersistentDataType.STRING);
    }
    public static void toggleChunkEdit(Player player){
        if (hasEdit(player)){
            getData(player).remove(NamespacedKey.minecraft("chunk-edit"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6You exited chunk edit"));
        }else{
            getData(player).set(NamespacedKey.minecraft("chunk-edit"), PersistentDataType.STRING,"true");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6You entered chunk edit"));
        }
    }
}
