package net.achymake.chunkclaim;

import net.achymake.chunkclaim.command.ChunkCommand;
import net.achymake.chunkclaim.config.ChunkFiles;
import net.achymake.chunkclaim.listeners.ChunkEvents;
import net.achymake.chunkclaim.settings.Settings;
import net.achymake.chunkclaim.version.ChunkUpdate;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChunkClaim extends JavaPlugin {
    public static ChunkClaim instance;
    @Override
    public void onEnable() {
        Settings.checkEssential(this);
        instance = this;
        ChunkFiles.start(this);
        ChunkEvents.start(this);
        ChunkUpdate.getUpdate(this);
        getCommand("chunk").setExecutor(new ChunkCommand());
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+this.getName()+"&6&l]&r &aEnabled &f"+this.getName()+ " " +this.getDescription().getVersion()));
    }
    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&e"+this.getName()+"&6&l]&r &cDisabled &f"+this.getName()+ " " +this.getDescription().getVersion()));
    }
}
