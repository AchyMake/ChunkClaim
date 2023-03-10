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
        chunkSubCommands.add(new Claim());
        chunkSubCommands.add(new Delete());
        chunkSubCommands.add(new Edit());
        chunkSubCommands.add(new Help());
        chunkSubCommands.add(new Info());
        chunkSubCommands.add(new Members());
        chunkSubCommands.add(new Reload());
        chunkSubCommands.add(new Unclaim());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cUsage: &f/chunk help"));
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
            if (sender.hasPermission("chunkclaim.claim")){
                commands.add("claim");
            }
            if (sender.hasPermission("chunkclaim.delete")){
                commands.add("delete");
            }
            if (sender.hasPermission("chunkclaim.edit")){
                commands.add("edit");
            }
            if (sender.hasPermission("chunkclaim.help")){
                commands.add("help");
            }
            if (sender.hasPermission("chunkclaim.info")){
                commands.add("info");
            }
            if (sender.hasPermission("chunkclaim.members")){
                commands.add("members");
            }
            if (sender.hasPermission("chunkclaim.reload")){
                commands.add("reload");
            }
            if (sender.hasPermission("chunkclaim.unclaim")){
                commands.add("unclaim");
            }
            return commands;
        } else if (args.length == 2) {
            if (sender.hasPermission("chunkclaim.members")){
                if (args[0].equalsIgnoreCase("members")){
                    commands.add("add");
                    commands.add("remove");
                    return commands;
                }
            }
        } else if (args.length == 3) {
            if (sender.hasPermission("chunkclaim.members")){
                if (args[0].equalsIgnoreCase("members")){
                    for (Player players : Bukkit.getOnlinePlayers()){
                        commands.add(players.getName());
                    }
                    return commands;
                }
            }
        } else if (args.length == 4) {
            if (sender.hasPermission("chunkclaim.members")){
                if (args[0].equalsIgnoreCase("members")){
                    commands.add("all");
                    return commands;
                }
            }
        }
        return commands;
    }
}