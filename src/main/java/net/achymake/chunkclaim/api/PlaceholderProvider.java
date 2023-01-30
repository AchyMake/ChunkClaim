package net.achymake.chunkclaim.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
            if (Settings.isClaimed(player.getLocation().getChunk())){
                return ChatColor.translateAlternateColorCodes('&',"&aTrue");
            }else{
                return ChatColor.translateAlternateColorCodes('&',"&cFalse");
            }
        }
        if (params.equals("owner")) {
            if (Settings.isClaimed(player.getLocation().getChunk())){
                return Settings.getOwner(player.getLocation().getChunk());
            }else{
                return "unclaimed";
            }
        }
        if (params.equals("has_access")) {
            if (Settings.isClaimed(player.getLocation().getChunk())){
                if (Settings.hasAccess(player,player.getLocation().getChunk())){
                    return ChatColor.translateAlternateColorCodes('&',"&aTrue");
                }else{
                    return ChatColor.translateAlternateColorCodes('&',"&cFalse");
                }
            }else{
                return "unclaimed";
            }
        }
        return super.onRequest(player, params);
    }
}