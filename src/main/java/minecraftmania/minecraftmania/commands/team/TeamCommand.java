package minecraftmania.minecraftmania.commands.team;

import minecraftmania.minecraftmania.team.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length < 1 ) {
            return false;
        }
        String action = args[0];
        if (action.equalsIgnoreCase("add"))
        {
            if( args.length > 2 ) {
                return false;
            }
            TeamColor teamColor = TeamColor.valueOf(args[1]);
            String playerName = args[2];
            // Add the player to the team
            Bukkit.broadcastMessage("Adding player " + playerName + " to team " + teamColor);
        }
        else if (action.equalsIgnoreCase("remove"))
        {
            if( args.length > 2 ) {
                return false;
            }
            TeamColor teamColor = TeamColor.valueOf(args[1]);
            String playerName = args[2];
            // Remove the player from the team
            Bukkit.broadcastMessage("Removing player " + playerName + " from team " + teamColor);
        }
        else if (action.equalsIgnoreCase("setup"))
        {
            TeamColor teamColor = TeamColor.valueOf(args[1]);
            // Set up the team
            Bukkit.broadcastMessage("Setting up team " + teamColor);
        } else if (action.equalsIgnoreCase("list")) {
            TeamColor teamColor = TeamColor.valueOf(args[1]);
            if(teamColor == null)
            {
                //list all teams
            }
            else
            {
                //list all players in team teamColor
            }
            Bukkit.broadcastMessage("Listing players on team " + teamColor);
        } else if (action.equalsIgnoreCase("reset")) {
            // Reset the specified aspect of the specified team
            TeamColor teamColor = TeamColor.valueOf(args[1]);
            String aspect = args[2];
            // Reset the aspect of the team
            Bukkit.broadcastMessage("Resetting " + aspect + " of team " + teamColor);
        } else {
            // Invalid action provided
            return false;
        }
        return true;
    }
}