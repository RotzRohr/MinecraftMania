package minecraftmania.minecraftmania.commands;

import minecraftmania.minecraftmania.games.Spleef;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SpleefCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0])
        {
            case "start":
                Spleef.getInstance().startGame();
                break;
            case "stop":
                Spleef.getInstance().endGame();
                break;
        }
        return false;
    }
}
