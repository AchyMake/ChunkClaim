package net.achymake.chunkclaim;

import net.achymake.chunkclaim.api.PlaceholderSetup;
import net.achymake.chunkclaim.api.VaultSetup;
import net.achymake.chunkclaim.command.ChunkCommand;
import net.achymake.chunkclaim.config.Files;
import net.achymake.chunkclaim.listeners.Events;
import net.achymake.chunkclaim.version.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ChunkClaim extends JavaPlugin {
    public static List<Player> edit = new ArrayList<>();
    public static Economy econ;
    public static ChunkClaim instance;
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        instance = this;
        VaultSetup.setupEconomy(this);
        PlaceholderSetup.setup(this);
        Files.start();
        Events.start(this);
        UpdateChecker.getUpdate(this);
        getCommand("chunk").setExecutor(new ChunkCommand());
        sendMessage("&aEnabled&r "+this.getName()+ " " +this.getDescription().getVersion());
    }
    @Override
    public void onDisable() {
        edit.clear();
        sendMessage("&cDisabled&r "+this.getName()+ " " +this.getDescription().getVersion());
    }
    public void sendMessage(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&l[&e"+this.getName()+"&6&l]&r "+message));
    }
}