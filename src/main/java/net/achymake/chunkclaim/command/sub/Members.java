package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.MessageConfig;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class Members extends ChunkSubCommand {
    @Override
    public String getName() {
        return "members";
    }

    @Override
    public String getDescription() {
        return "add or removes members to the chunk";
    }

    @Override
    public String getSyntax() {
        return "/chunk members add/remove target";
    }

    @Override
    public void perform(Player player, String[] args) {
        Chunk chunk = player.getLocation().getChunk();
        if (Settings.isClaimed(chunk)) {
            if (Settings.isOwner(player,chunk)) {
                if (args.length == 1) {
                    if (Settings.getMembers(chunk).isEmpty()){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("command-members-no-members")));
                    }else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',MessageConfig.get().getString("command-members")));
                        for (UUID uuidListed : Settings.getMembersUUID(chunk)){
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7-&f "+Bukkit.getServer().getOfflinePlayer(uuidListed).getName()));
                        }
                    }
                } else if (args.length == 3) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                    if (args[1].equalsIgnoreCase("add")) {
                        if (Settings.getMembersUUID(chunk).contains(target.getUniqueId())) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-already-member"),target.getName())));
                        } else {
                            addMember(chunk,target.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-add"),target.getName())));
                        }
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        if (Settings.getMembersUUID(chunk).contains(target.getUniqueId())) {
                            removeMember(chunk,target.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-remove"),target.getName())));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("command-members-already-non-member"),target.getName())));
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(MessageConfig.get().getString("error-chunk-already-claimed"),Settings.getOwner(chunk))));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("error-chunk-already-unclaimed")));
        }
    }
    private static PersistentDataContainer getData(Chunk chunk){
        return chunk.getPersistentDataContainer();
    }
    private static void addMember(Chunk chunk, UUID uuid){
        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(uuid);
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeInt(uuidList.size());
            for (UUID listedUUID : uuidList){
                os.writeObject(listedUUID);
            }
            os.flush();
            byte[] rawData = io.toByteArray();
            String encodedData = Base64.getEncoder().encodeToString(rawData);
            getData(chunk).set(NamespacedKey.minecraft("members"), PersistentDataType.STRING,encodedData);
        }catch (IOException e){
            System.out.println(e);
        }
    }
    private static void removeMember(Chunk chunk, UUID uuid){
        List<UUID> uuidList = Settings.getMembersUUID(chunk);
        uuidList.remove(uuid);
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeInt(uuidList.size());
            for (UUID listedUUID : uuidList){
                os.writeObject(listedUUID);
            }
            os.flush();
            byte[] rawData = io.toByteArray();
            String encodedData = Base64.getEncoder().encodeToString(rawData);
            getData(chunk).set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,encodedData);
        }catch (IOException e){
            System.out.println(e);
        }
    }
}