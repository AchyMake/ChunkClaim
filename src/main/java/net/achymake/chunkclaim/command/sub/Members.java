package net.achymake.chunkclaim.command.sub;

import net.achymake.chunkclaim.command.ChunkSubCommand;
import net.achymake.chunkclaim.config.Message;
import net.achymake.chunkclaim.config.PlayerConfig;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
                        player.sendMessage(Message.commandMembersEmpty());
                    }else{
                        player.sendMessage(Message.commandMembersTitle());
                        for (UUID uuidListed : Settings.getMembersUUID(chunk)){
                            player.sendMessage(Message.commandMembersList(Bukkit.getOfflinePlayer(uuidListed)));
                        }
                    }
                    if (!PlayerConfig.get(player).getStringList("members").isEmpty()){
                        player.sendMessage(Message.commandAllMembersTitle());
                        for (String uuidListed : PlayerConfig.get(player).getStringList("members")){
                            player.sendMessage(Message.commandAllMembersList(Bukkit.getOfflinePlayer(UUID.fromString(uuidListed))));

                        }
                    }
                } else if (args.length == 3) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                    if (args[1].equalsIgnoreCase("add")) {
                        if (Settings.getMembersUUID(chunk).contains(target.getUniqueId())) {
                            player.sendMessage(Message.commandMembersAddAlreadyMember(target));
                        } else {
                            addMember(chunk,target.getUniqueId());
                            player.sendMessage(Message.commandMembersAddSuccess(target));
                        }
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        if (Settings.getMembersUUID(chunk).contains(target.getUniqueId())) {
                            removeMember(chunk,target.getUniqueId());
                            player.sendMessage(Message.commandMembersRemoveSuccess(target));
                        } else {
                            player.sendMessage(Message.commandMembersRemoveAlreadyNonMember(target));
                        }
                    }
                } else if (args.length == 4) {
                    if (args[1].equalsIgnoreCase("add")){
                        if (args[3].equalsIgnoreCase("all")){
                            Player target = player.getServer().getPlayerExact(args[2]);
                            if (target != null){
                                if (PlayerConfig.get(player).getStringList("members").contains(target.getUniqueId().toString())){
                                    player.sendMessage(Message.commandMembersAddAllAlreadyMember(target));
                                }else{
                                    List<String> members = PlayerConfig.get(player).getStringList("members");
                                    members.add(target.getUniqueId().toString());
                                    PlayerConfig.setStringList(player,"members",members);
                                    player.sendMessage(Message.commandMembersAddAllSuccess(target));
                                }
                            }else{
                                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
                                if (PlayerConfig.get(player).getStringList("members").contains(offlinePlayer.getUniqueId().toString())){
                                    player.sendMessage(Message.commandMembersAddAllAlreadyMember(offlinePlayer));
                                }else{
                                    List<String> members = PlayerConfig.get(player).getStringList("members");
                                    members.add(offlinePlayer.getUniqueId().toString());
                                    PlayerConfig.setStringList(player,"members",members);
                                    player.sendMessage(Message.commandMembersAddAllSuccess(offlinePlayer));
                                }

                            }
                        }
                    }else if (args[1].equalsIgnoreCase("remove")){
                        if (args[3].equalsIgnoreCase("all")){
                            Player target = player.getServer().getPlayerExact(args[2]);
                            if (target != null){
                                if (PlayerConfig.get(player).getStringList("members").contains(target.getUniqueId().toString())){
                                    List<String> members = PlayerConfig.get(player).getStringList("members");
                                    members.remove(target.getUniqueId().toString());
                                    PlayerConfig.setStringList(player,"members",members);
                                    player.sendMessage(Message.commandMembersRemoveAllSuccess(target));
                                }else{
                                    player.sendMessage(Message.commandMembersRemoveAllNonMember(target));
                                }
                            }else{
                                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
                                if (PlayerConfig.get(player).getStringList("members").contains(offlinePlayer.getUniqueId().toString())){
                                    List<String> members = PlayerConfig.get(player).getStringList("members");
                                    members.remove(offlinePlayer.getUniqueId().toString());
                                    PlayerConfig.setStringList(player,"members",members);
                                    player.sendMessage(Message.commandMembersRemoveAllSuccess(offlinePlayer));
                                }else{
                                    player.sendMessage(Message.commandMembersRemoveAllNonMember(offlinePlayer));
                                }
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(Message.commandMembersAlreadyClaimed(Settings.getOwner(chunk)));
            }
        }
    }
    private static void addMember(Chunk chunk, UUID uuid){
        List<UUID> uuidList = Settings.getMembersUUID(chunk);
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
            chunk.getPersistentDataContainer().set(NamespacedKey.minecraft("members"), PersistentDataType.STRING,encodedData);
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
            chunk.getPersistentDataContainer().set(NamespacedKey.minecraft("members"),PersistentDataType.STRING,encodedData);
        }catch (IOException e){
            System.out.println(e);
        }
    }
}