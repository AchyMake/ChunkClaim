package net.achymake.chunkclaim.api;

import net.achymake.chunkclaim.ChunkClaim;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class PlaceholderSetup {
    private static boolean isPlaceholderAPIEnabled() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
    public static void setup(ChunkClaim plugin){
        if (isPlaceholderAPIEnabled()){
            new PlaceholderProvider().register();
        }else{
            plugin.sendMessage("You have to install 'PlaceholderAPI'");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}