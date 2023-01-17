package net.achymake.chunkclaim.version;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final ChunkClaim plugin;
    private final int resourceId;
    public UpdateChecker(ChunkClaim plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }
    public void getVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId)).openStream();
                Scanner scanner = new Scanner(inputStream);
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                    scanner.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Settings.sendMessage("Unable to check for updates: " + e.getMessage());
            }
        });
    }
    public static void getUpdate(ChunkClaim plugin){
        if (plugin.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(plugin, 107444)).getVersion((latest) -> {
                if (plugin.getDescription().getVersion().equalsIgnoreCase(latest)) {
                    Settings.sendMessage("You are using the latest version");
                } else {
                    Settings.sendMessage("&cNew update:&r " + latest);
                    Settings.sendMessage("&cCurrent version:&r " + plugin.getDescription().getVersion());
                }
            });
        }
    }
    public static void sendMessage(Player player){
        if (ChunkClaim.instance.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(ChunkClaim.instance, 107444)).getVersion((latest) -> {
                if (!ChunkClaim.instance.getDescription().getVersion().equalsIgnoreCase(latest)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6"+ChunkClaim.instance.getName()+" Update:"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6new release: &f" + latest));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6current: &f" + ChunkClaim.instance.getDescription().getVersion()));
                }
            });
        }
    }
}