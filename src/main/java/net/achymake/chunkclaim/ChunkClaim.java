package net.achymake.chunkclaim;

import net.achymake.chunkclaim.command.ChunkCommand;
import net.achymake.chunkclaim.config.Files;
import net.achymake.chunkclaim.listeners.Events;
import net.achymake.chunkclaim.version.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChunkClaim extends JavaPlugin {
    public static Economy econ;
    public static ChunkClaim instance;
    @Override
    public void onEnable() {
        setupEconomy(this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        instance = this;
        Files.start();
        Events.start(this);
        UpdateChecker.getUpdate(this);
        getCommand("chunk").setExecutor(new ChunkCommand());
        sendMessage("&aEnabled&r "+this.getName()+ " " +this.getDescription().getVersion());
    }
    @Override
    public void onDisable() {
        sendMessage("&cDisabled&r "+this.getName()+ " " +this.getDescription().getVersion());
    }
    public void sendMessage(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&l[&e"+this.getName()+"&6&l]&r "+message));
    }
    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        ChunkClaim.econ = rsp.getProvider();
        return ChunkClaim.econ != null;
    }
    public void setupEconomy(ChunkClaim plugin){
        if (!setupEconomy()){
            plugin.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", plugin.getDescription().getName()));
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}