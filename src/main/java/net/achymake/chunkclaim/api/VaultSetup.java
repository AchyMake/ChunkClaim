package net.achymake.chunkclaim.api;

import net.achymake.chunkclaim.ChunkClaim;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultSetup {
    private static boolean isVaultEnable(ChunkClaim plugin) {
        return (plugin.getServer().getPluginManager().getPlugin("Vault") != null);
    }
    private static boolean isEconomyEnabled() {
        return ChunkClaim.econ != null;
    }
    public static void setupEconomy(ChunkClaim plugin){
        if (isVaultEnable(plugin)){
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            ChunkClaim.econ = rsp.getProvider();
            if (isEconomyEnabled()){
                plugin.sendMessage("Economy hooked to Vault");
            }else{
                plugin.sendMessage("You need 'Economy' to enable chunk claim");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }else{
            plugin.sendMessage("You have to install 'Vault'");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}
