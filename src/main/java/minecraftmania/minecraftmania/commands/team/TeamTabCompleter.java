package minecraftmania.minecraftmania.commands.team;

import minecraftmania.minecraftmania.team.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TeamTabCompleter implements TabCompleter {
    private static final List<String> ACTIONS = Arrays.asList("add", "remove", "setup", "list", "reset");
    private static final List<String> RESET_OPTIONS = Arrays.asList("points", "players");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
        {
            // Autocomplete action
            return StringUtil.copyPartialMatches(args[0], ACTIONS, new ArrayList<>());
        }
        else if (args.length == 2)
        {
            // Autocomplete team color
            List<String> teamColors = Arrays.stream(TeamColor.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            teamColors.add("all");
            return StringUtil.copyPartialMatches(args[1], teamColors, new ArrayList<>());
        }
        else if (args.length == 3)
        {
            // Autocomplete player name
            if (args[0].equalsIgnoreCase("reset"))
            {
                return StringUtil.copyPartialMatches(args[2], RESET_OPTIONS, new ArrayList<>());
            }
            else
            {
                List<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    playerNames.add(player.getName());
                }
                return StringUtil.copyPartialMatches(args[2], playerNames, new ArrayList<>());
            }
        }
        if(args[0].equalsIgnoreCase("setup"))
        {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers())
            {
                playerNames.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[2], playerNames, new ArrayList<>());
        }
        return Collections.emptyList();
    }
}