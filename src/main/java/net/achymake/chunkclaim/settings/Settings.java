package net.achymake.chunkclaim.settings;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.ChatColor;

public class Settings {
    public static void checkEssential(ChunkClaim plugin){
        if (plugin.getServer().getPluginManager().getPlugin("Essential") != null){
            if (plugin.getServer().getPluginManager().getPlugin("Essential").getDescription().getAuthors().contains("AchyMake")){
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+plugin.getName()+"&6&l]&r Hooked to '&aEssential&r'"));
            }else{
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+plugin.getName()+"&6&l]&r '&cEssential&r' does not support this plugin"));
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+plugin.getName()+"&6&l]&r &cDisabled &f"+plugin.getName()+ " " +plugin.getDescription().getVersion()));
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }else{
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+plugin.getName()+"&6&l]&r Unable to find '&cEssential.jar&r'"));
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+plugin.getName()+"&6&l]&r &cDisabled &f"+plugin.getName()+ " " +plugin.getDescription().getVersion()));
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}
