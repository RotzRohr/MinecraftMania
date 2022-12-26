package minecraftmania.minecraftmania.commands.player;

import minecraftmania.minecraftmania.MinecraftMania;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            return false;
        }
        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            return false;
        }
        String action = args[1];
        switch (action)
        {
            case "move":
                Bukkit.broadcastMessage("move player " + playerName);
                return true;
            case "reset":
                Bukkit.broadcastMessage("reset player " + playerName);
                return true;
            case "info":
                Bukkit.broadcastMessage("info player " + playerName);
                return true;
            case "setPremissionLevel":
                Bukkit.broadcastMessage("givePremission player " + playerName);
                MinecraftMania.getInstance().getEventPlayer(player.getUniqueId()).setPremissionLevel(Integer.parseInt(args[2]));
                return true;
            default:
                return false;
        }
    }
}
