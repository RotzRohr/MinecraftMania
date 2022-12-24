package minecraftmania.minecraftmania.commands.event;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EventCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            // Not enough arguments were provided
            return false;
        }
        String gameName = args[0];
        String action = args[1];
        if (action.equalsIgnoreCase("start")) {
            // Start the event
            Bukkit.broadcastMessage("Starting event " + gameName);
        } else if (action.equalsIgnoreCase("stop")) {
            // Stop the event
            Bukkit.broadcastMessage("Stopping the event");
        } else {
            // Invalid action provided
            return false;
        }

        return true;
    }
}
