package net.achymake.chunkclaim;

import net.achymake.chunkclaim.command.ChunkCommand;
import net.achymake.chunkclaim.config.Files;
import net.achymake.chunkclaim.listeners.Events;
import net.achymake.chunkclaim.settings.Settings;
import net.achymake.chunkclaim.version.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChunkClaim extends JavaPlugin {
    public static Economy econ;
    public static ChunkClaim instance;
    @Override
    public void onEnable() {
        Settings.setupEconomy(this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        instance = this;
        Files.start();
        Events.start(this);
        UpdateChecker.getUpdate(this);
        getCommand("chunk").setExecutor(new ChunkCommand());
        Settings.sendMessage("&aEnabled&r "+this.getName()+ " " +this.getDescription().getVersion());
    }
    @Override
    public void onDisable() {
        Settings.sendMessage("&cDisabled&r "+this.getName()+ " " +this.getDescription().getVersion());
    }
}