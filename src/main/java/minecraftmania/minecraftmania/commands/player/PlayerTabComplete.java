package minecraftmania.minecraftmania.commands.player;

import minecraftmania.minecraftmania.games.GameMode;
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

public class PlayerTabComplete implements TabCompleter
{
    private static final List<String> ACTIONS = Arrays.asList("move", "reset", "info", "setPremissionLevel");
    private static final List<String> RESET_ACTIONS = Arrays.asList("coins");


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
        {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers())
            {
                playerNames.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<>());
        }
        else if (args.length == 2)
        {
            return StringUtil.copyPartialMatches(args[1], ACTIONS, new ArrayList<>());
        }
        else if (args.length == 3)
        {
            if(args[1].equalsIgnoreCase("reset"))
            {
                return StringUtil.copyPartialMatches(args[2], RESET_ACTIONS, new ArrayList<>());
            }
            else if(args[1].equalsIgnoreCase("move"))
            {
                List<String> gameModes = Arrays.stream(GameMode.values())
                        .map(Enum::name)
                        .collect(Collectors.toList());
                return StringUtil.copyPartialMatches(args[2], gameModes, new ArrayList<>());
            }
        }
        return Collections.emptyList();
    }
}
