package minecraftmania.minecraftmania.commands.event;

import minecraftmania.minecraftmania.games.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EventTabCompleter implements TabCompleter
{
    private static final List<String> ACTIONS = Arrays.asList("start", "stop");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
        {
            // Autocomplete GAMES
            List<String> gameModes = Arrays.stream(GameMode.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            return StringUtil.copyPartialMatches(args[0], gameModes, new ArrayList<>());
        }
        else if (args.length == 2)
        {
            // Autocomplete team ACTIONS
            return StringUtil.copyPartialMatches(args[1], ACTIONS, new ArrayList<>());
        }
        //nothing to autocomplete
        return Collections.emptyList();
    }
}
