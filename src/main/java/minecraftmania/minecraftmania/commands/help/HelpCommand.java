package minecraftmania.minecraftmania.commands;

import minecraftmania.minecraftmania.games.GameMode;
import minecraftmania.minecraftmania.team.TeamColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand implements CommandExecutor, TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList("event", "player", "team");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 1) {
            return false; // Invalid number of arguments
        }
        String commandName = (args.length == 1) ? args[0] : "";
        if (commandName.equalsIgnoreCase("event"))
        {
            List<String> gameModes = Arrays.stream(GameMode.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            sender.sendMessage(ChatColor.GOLD + "Usage: /event <" + String.join(" | ", gameModes) + "> <start | stop>");
        } else if (commandName.equalsIgnoreCase("player"))
        {
            List<String> ACTIONS = Arrays.asList("move", "reset", "info", "givePremission");
            sender.sendMessage(ChatColor.GOLD + "Usage: /player <playerName> <" + String.join(" | ", ACTIONS) + ">");
        }
        else if (commandName.equalsIgnoreCase("team"))
        {
            List<String> ACTIONS = Arrays.asList("add", "remove", "setup", "list", "reset");
            List<String> teamColors = Arrays.stream(TeamColor.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            sender.sendMessage(ChatColor.GOLD + "Usage: /team <" + String.join(" | ", ACTIONS) + ">" + " <" + String.join(" | ", teamColors) + ">" + " <playerName>");
        }
        sender.sendMessage(ChatColor.AQUA + "But most of the time you can just tab complete the commands");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            // Autocomplete commands
            return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());
        }
        // Nothing to autocomplete
        return Collections.emptyList();
    }
}