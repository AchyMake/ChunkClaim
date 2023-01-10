package net.achymake.chunkclaim.command;

import net.achymake.chunkclaim.command.sub.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChunkCommand implements CommandExecutor, TabCompleter {
    private final ArrayList<ChunkSubCommand> chunkSubCommands = new ArrayList<>();

    public ChunkCommand(){
        chunkSubCommands.add(new ClaimCommand());
        chunkSubCommands.add(new EditCommand());
        chunkSubCommands.add(new InfoCommand());
        chunkSubCommands.add(new MembersCommand());
        chunkSubCommands.add(new ReloadCommand());
        chunkSubCommands.add(new UnclaimCommand());
        chunkSubCommands.add(new DeleteCommand());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cusage: &f/chunk help"));
        }else{
            for (ChunkSubCommand commands : getSubCommands()){
                if (args[0].equals(commands.getName())){
                    commands.perform(player,args);
                }
            }
        }
        return true;
    }
    public ArrayList<ChunkSubCommand> getSubCommands(){
        return chunkSubCommands;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1){
            if (sender.hasPermission("chunkclaim.edit")){
                commands.add("edit");
                commands.add("info");
            }
            if (sender.hasPermission("chunkclaim.delete")){
                commands.add("delete");
            }
            if (sender.hasPermission("chunkclaim.reload")){
                commands.add("reload");
            }
            commands.add("claim");
            commands.add("members");
            commands.add("unclaim");
            return commands;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("members")){
                commands.add("add");
                commands.add("remove");
                return commands;
            }
        } else if (args.length == 3) {
            for (Player players : Bukkit.getOnlinePlayers()){
                commands.add(players.getName());
            }
        }
        return commands;
    }
}