package net.achymake.chunkclaim;

import net.achymake.chunkclaim.command.ChunkCommand;
import net.achymake.chunkclaim.config.Files;
import net.achymake.chunkclaim.listeners.Events;
import net.achymake.chunkclaim.version.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChunkClaim extends JavaPlugin {
    public static Economy econ;
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static ChunkClaim instance;
    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        Files.start(this);
        Events.start(this);
        UpdateChecker.getUpdate(this);
        getCommand("chunk").setExecutor(new ChunkCommand());
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+this.getName()+"&6&l]&r &aEnabled &f"+this.getName()+ " " +this.getDescription().getVersion()));
    }
    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+this.getName()+"&6&l]&r &cDisabled &f"+this.getName()+ " " +this.getDescription().getVersion()));
    }
}
