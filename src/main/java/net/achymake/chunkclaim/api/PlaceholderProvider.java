package net.achymake.chunkclaim.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class PlaceholderProvider extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "chunkclaim";
    }
    @Override
    public String getAuthor() {
        return "AchyMake";
    }
    @Override
    public String getVersion() {
        return ChunkClaim.instance.getDescription().getVersion();
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public boolean register() {
        return super.register();
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null){
            return "";
        }
        if (params.equals("is_claimed")) {
            if (player.getLocation().getChunk().getPersistentDataContainer().has(NamespacedKey.minecraft("owner"), PersistentDataType.STRING)){
                return ChatColor.translateAlternateColorCodes('&',"&atrue");
            }else{
                return ChatColor.translateAlternateColorCodes('&',"&cfalse");
            }
        }
        if (params.equals("owner")) {
            if (player.getLocation().getChunk().getPersistentDataContainer().has(NamespacedKey.minecraft("owner"), PersistentDataType.STRING)){
                UUID uuid = UUID.fromString(player.getLocation().getChunk().getPersistentDataContainer().get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING));
                String name = player.getServer().getOfflinePlayer(uuid).getName();
                return name;
            }else{
                return "none";
            }
        }
        return super.onRequest(player, params);
    }
}