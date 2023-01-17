package net.achymake.chunkclaim.settings;

import net.achymake.chunkclaim.ChunkClaim;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Settings {
    public static void sendMessage(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&l[&eChunkClaim&6&l]&r "+message));
    }
    private static boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        ChunkClaim.econ = rsp.getProvider();
        return ChunkClaim.econ != null;
    }
    public static void setupEconomy(ChunkClaim plugin){
        if (!setupEconomy()){
            plugin.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", plugin.getDescription().getName()));
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}