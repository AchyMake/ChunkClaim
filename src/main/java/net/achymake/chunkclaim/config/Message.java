package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class Message {
    private static final File file = new File(ChunkClaim.instance.getDataFolder(), "message.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public static void setup(){
        config.addDefault("command.chunk.reload","&6ChunkClaim reloaded");
        config.addDefault("command.chunk.claim.already-owner","&6You already own this chunk");
        config.addDefault("command.chunk.claim.already-claimed","&cChunk is already claimed by&f {0}");
        config.addDefault("command.chunk.claim.success","&6You claimed a chunk for&a {0}");
        config.addDefault("command.chunk.claim.not-enough-currency","&cYou dont have&a {0}&c to claim a chunk");
        config.addDefault("command.chunk.delete.unclaimed","&6Chunk is already unclaimed");
        config.addDefault("command.chunk.delete.success","&6You safely unclaimed&f {0}&6 chunk");
        config.addDefault("command.chunk.edit.disabled","&6You exited Chunk edit");
        config.addDefault("command.chunk.edit.enabled","&6You entered Chunk edit");
        config.addDefault("command.chunk.help.title","&6ChunkClaim Help:");
        config.addDefault("command.chunk.help.claim","/chunk claim &7- claims a chunk");
        config.addDefault("command.chunk.help.delete","/chunk delete &7- safely unclaims a chunk");
        config.addDefault("command.chunk.help.edit","/chunk edit &7- toggle chunk edit");
        config.addDefault("command.chunk.help.help","/chunk help &7- show this list");
        config.addDefault("command.chunk.help.info","/chunk info &7- show chunk info");
        config.addDefault("command.chunk.help.members","/chunk members &7- show members");
        config.addDefault("command.chunk.help.reload","/chunk reload &7- reload config");
        config.addDefault("command.chunk.help.unclaim","/chunk unclaim &7- unclaims a chunk");
        config.addDefault("command.chunk.info.owner","&6Chunk owner:&f {0}");
        config.addDefault("command.chunk.info.date-claimed","&6Date claimed:&f {0}");
        config.addDefault("command.chunk.info.members.empty","&6Chunk has no members");
        config.addDefault("command.chunk.info.members.title","&6Members:");
        config.addDefault("command.chunk.info.members.list","- {0}");
        config.addDefault("command.chunk.members.empty","&6Chunk has no members");
        config.addDefault("command.chunk.members.title","&6Members:");
        config.addDefault("command.chunk.members.list","- {0}");
        config.addDefault("command.chunk.members.all.title","&6All Members:");
        config.addDefault("command.chunk.members.all.list","- {0}");
        config.addDefault("command.chunk.members.add.already-member","{0}&c is already a member");
        config.addDefault("command.chunk.members.add.success","&6Chunk added&f {0}&6 as member");
        config.addDefault("command.chunk.members.remove.already-non-member","{0}&c is not a member of the chunk");
        config.addDefault("command.chunk.members.remove.success","&6Chunk removed&f {0}&6 from member");
        config.addDefault("command.chunk.members.add.all.already-member","{0}&c is already a member");
        config.addDefault("command.chunk.members.add.all.success","&6You added&f {0}&6 to all chunks");
        config.addDefault("command.chunk.members.remove.all.already-non-member","{0}&c is not a member");
        config.addDefault("command.chunk.members.remove.all.success","&6You removed&f {0}&6 from all chunks");
        config.addDefault("command.chunk.members.already-claimed","&cChunk is already owned by&f {0}");
        config.addDefault("command.chunk.unclaim.success","&6You unclaimed a chunk refunded&a {0}");
        config.addDefault("command.chunk.unclaim.already-claimed","&6Chunk is already owned by&f {0}");
        config.addDefault("command.chunk.unclaim.already-unclaimed","&6Chunk is already unclaimed");
        config.addDefault("event.chunk.visit","&6Visiting {0} chunk");
        config.addDefault("event.chunk.exit","&6Exiting {0} chunk");
        config.addDefault("event.chunk.cancel","&cChunk is owned by&f {0}");
        config.addDefault("placeholderapi.chunk.owner.none","&fNone");
        config.addDefault("placeholderapi.chunk.access.true","&aTrue");
        config.addDefault("placeholderapi.chunk.access.false","&aFalse");
        config.addDefault("placeholderapi.chunk.access.unclaimed","&aFalse");
        config.options().copyDefaults(true);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String commandReload(){
        return color(config.getString("command.chunk.reload"));
    }
    public static String commandClaimAlreadyOwner(){
        return color(config.getString("command.chunk.claim.already-owner"));
    }
    public static String commandClaimAlreadyClaimed(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.claim.already-claimed")), offlinePlayer.getName());
    }
    public static String commandClaimSuccess(double amount){
        return MessageFormat.format(color(config.getString("command.chunk.claim.success")), ChunkClaim.econ.format(amount));
    }
    public static String commandClaimNotEnoughCurrency(double amount){
        return MessageFormat.format(color(config.getString("command.chunk.claim.not-enough-currency")), ChunkClaim.econ.format(amount));
    }
    public static String commandDeleteUnclaimed(){
        return color(config.getString("command.chunk.delete.unclaimed"));
    }
    public static String commandDeleteSuccess(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.delete.success")), offlinePlayer.getName());
    }
    public static String commandEditDisabled(){
        return color(config.getString("command.chunk.edit.disabled"));
    }
    public static String commandEditEnabled(){
        return color(config.getString("command.chunk.edit.enabled"));
    }
    public static String commandHelpTitle(){
        return color(config.getString("command.chunk.help.title"));
    }
    public static String commandHelpClaim(){
        return color(config.getString("command.chunk.help.claim"));
    }
    public static String commandHelpDelete(){
        return color(config.getString("command.chunk.help.delete"));
    }
    public static String commandHelpEdit(){
        return color(config.getString("command.chunk.help.edit"));
    }
    public static String commandHelpHelp(){
        return color(config.getString("command.chunk.help.help"));
    }
    public static String commandHelpInfo(){
        return color(config.getString("command.chunk.help.info"));
    }
    public static String commandHelpMembers(){
        return color(config.getString("command.chunk.help.members"));
    }
    public static String commandHelpReload(){
        return color(config.getString("command.chunk.help.reload"));
    }
    public static String commandHelpUnclaim(){
        return color(config.getString("command.chunk.help.unclaim"));
    }
    public static String commandInfoOwner(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.info.owner")),offlinePlayer.getName());
    }
    public static String commandInfoDateClaimed(Chunk chunk){
        return MessageFormat.format(color(config.getString("command.chunk.info.date-claimed")), Settings.getDateClaimed(chunk));
    }
    public static String commandInfoMembersEmpty(){
        return color(config.getString("command.chunk.info.members.empty"));
    }
    public static String commandInfoMembersTitle(){
        return color(config.getString("command.chunk.info.members.title"));
    }
    public static String commandInfoMembersList(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.info.members.list")),offlinePlayer.getName());
    }
    public static String commandMembersEmpty(){
        return color(config.getString("command.chunk.members.empty"));
    }
    public static String commandMembersTitle(){
        return color(config.getString("command.chunk.members.title"));
    }
    public static String commandMembersList(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.list")),offlinePlayer.getName());
    }
    public static String commandAllMembersTitle(){
        return color(config.getString("command.chunk.members.all.title"));
    }
    public static String commandAllMembersList(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.all.list")),offlinePlayer.getName());
    }
    public static String commandMembersAddAlreadyMember(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.add.already-member")),offlinePlayer.getName());
    }
    public static String commandMembersAddSuccess(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.add.success")),offlinePlayer.getName());
    }
    public static String commandMembersRemoveAlreadyNonMember(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.remove.already-non-member")),offlinePlayer.getName());
    }
    public static String commandMembersRemoveSuccess(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.remove.success")),offlinePlayer.getName());
    }
    public static String commandMembersAddAllAlreadyMember(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.add.all.already-member")),offlinePlayer.getName());
    }
    public static String commandMembersAddAllSuccess(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.add.all.success")),offlinePlayer.getName());
    }
    public static String commandMembersRemoveAllSuccess(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.remove.all.success")),offlinePlayer.getName());
    }
    public static String commandMembersRemoveAllNonMember(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.remove.all.non-member")),offlinePlayer.getName());
    }
    public static String commandMembersAlreadyClaimed(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.members.already-claimed")),offlinePlayer.getName());
    }
    public static String commandUnclaimSuccess(double amount){
        return MessageFormat.format(color(config.getString("command.chunk.unclaim.success")),ChunkClaim.econ.format(amount));
    }
    public static String commandUnclaimAlreadyClaimed(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("command.chunk.unclaim.already-claimed")),offlinePlayer.getName());
    }
    public static String commandUnclaimAlreadyUnclaimed(){
        return color(config.getString("command.chunk.unclaim.already-unclaimed"));
    }
    public static String eventCancel(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("event.chunk.cancel")),offlinePlayer.getName());
    }
    public static String eventVisit(OfflinePlayer offlinePlayer){
        return MessageFormat.format(color(config.getString("event.chunk.visit")),offlinePlayer.getName());
    }
    public static String eventExit(String owner){
        return MessageFormat.format(color(config.getString("event.chunk.exit")),owner);
    }
    public static String color(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }
    public static void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }
}