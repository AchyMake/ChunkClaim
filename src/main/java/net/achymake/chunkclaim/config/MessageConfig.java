package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageConfig {
    public static File configFile = new File(ChunkClaim.instance.getDataFolder(), "message.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    public static void setup(){
        List<String> helpDefault = new ArrayList<>();
        helpDefault.add("&6ChunkClaim Help:");
        helpDefault.add("&6- &f/chunk claim &7- Claims current Chunk");
        helpDefault.add("&6- &f/chunk members &7- Checks current Chunk Members");
        helpDefault.add("&6- &f/chunk members add player &7- Add target to Members");
        helpDefault.add("&6- &f/chunk members remove player &7- Remove target from Members");
        helpDefault.add("&6- &f/chunk unclaim &7- Unclaims current Chunk");
        List<String> helpAdmin = new ArrayList<>();
        helpAdmin.add("&6- &f/chunk info &7- Checks current Chunk Info");
        helpAdmin.add("&6- &f/chunk edit &7- Toggle edit Chunks");
        helpAdmin.add("&6- &f/chunk delete &7- Safely unclaims current Chunk");
        helpAdmin.add("&6- &f/chunk reload &7- Reload Config");
        List<String> setowner = new ArrayList<>();
        setowner.add("&6Type &a/chunk setowner {0}&6 to change chunk owner");
        setowner.add("&6You will not receive refund");
        get().addDefault("command-claim","&6Chunk is claimed for &c{0}");
        get().addDefault("command-unclaim","&6You unclaimed a chunk refunded &a{0}");
        get().addDefault("command-delete","&6Chunk is now safely deleted");
        get().addDefault("command-edit-enabled","&6You entered chunk edit");
        get().addDefault("command-edit-disabled","&6You exited chunk edit");
        get().addDefault("command-help-default",helpDefault);
        get().addDefault("command-help-admin",helpAdmin);
        get().addDefault("command-info-owner","&6Chunk owner:&f {0}");
        get().addDefault("command-info-date-claimed","&6Date claimed:&f {0}");
        get().addDefault("command-members","&6Members:");
        get().addDefault("command-members-add","&f{0}&6 is added to this chunk");
        get().addDefault("command-members-already-member","&f{0}&c is already a member of this chunk");
        get().addDefault("command-members-remove","&f{0}&6 is removed from this chunk");
        get().addDefault("command-members-already-non-member","&f{0}&c is not a member of this chunk");
        get().addDefault("command-members-no-members","&6Chunk has no members");
        get().addDefault("command-reload","&6{0} reloaded");
        get().addDefault("command-setowner",setowner);
        get().addDefault("command-setowner-changed","&6Chunk owner changed to &f{0}");
        get().addDefault("error-chunk-already-owned","&cYou already own this chunk");
        get().addDefault("error-chunk-already-claimed","&cChunk is owned by &f{0}");
        get().addDefault("error-chunk-already-unclaimed","&cChunk is already unclaimed");
        get().addDefault("error-chunk-changes-expired","&cChunk changes expired");
        get().addDefault("error-not-enough-currency","&cYou dont have &a{0}&c to claim this chunk");
        get().addDefault("player-visit-claimed-chunk","&6Visiting &f{0}&6 chunk");
        get().addDefault("player-exit-claimed-chunk","&6Exiting &f{0}&6 chunk");
        get().options().copyDefaults(true);
        save();
    }
    public static FileConfiguration get(){
        return config;
    }
    public static void save(){
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void reload(){
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}
