package minecraftmania.minecraftmania.commands.team;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.team.Team;
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
            if( args.length > 3 ) {
                return false;
            }
            addPlayerToTeam(args[2], args[1]);
        }
        else if (action.equalsIgnoreCase("remove"))
        {
            if( args.length < 1 ) {
                return false;
            }
            String playerName = args[1];
            EventPlayer eventPlayer = MinecraftMania.getInstance().getEventPlayer(Bukkit.getPlayer(playerName).getUniqueId());
            TeamColor teamColor = TeamColor.valueOf(TeamHandler.getInstance().getTeamColor(eventPlayer));

            TeamHandler.getInstance().removePlayerFromTeam(eventPlayer, teamColor);
        }
        else if (action.equalsIgnoreCase("setup"))
        {
            for(int i = 2; i < args.length; i++)
            {
                addPlayerToTeam(args[i], args[1]);
            }
        }
        else if (action.equalsIgnoreCase("list"))
        {

            if(args.length > 1)
            {
                sender.sendMessage(TeamHandler.getInstance().ListTeam(TeamColor.valueOf(args[1])));
            }
            else
            {
                sender.sendMessage(TeamHandler.getInstance().ListAllTeams());
            }
        } else if (action.equalsIgnoreCase("reset")) {
            // Reset the specified aspect of the specified team
            TeamColor teamColor = TeamColor.valueOf(args[1]);
            String aspect = args[2];

            if(args[1] == "all")
            {
                switch (aspect)
                {
                    case "players" :
                        TeamHandler.getInstance().clearTeams();
                        break;
                    case "points" :
                        TeamHandler.getInstance().clearPoints();
                        break;
                }
            }
            else
            {
                switch (aspect)
                {
                    case "players" :
                        TeamHandler.getInstance().clearTeam(teamColor);
                        break;
                    case "points" :
                        TeamHandler.getInstance().clearPoints(teamColor);
                        break;
                }
            }
        } else {
            // Invalid action provided
            return false;
        }
        return true;
    }

    public void addPlayerToTeam(String name, String color)
    {
        TeamColor teamColor = TeamColor.valueOf(color);
        String playerName = name;
        EventPlayer eventPlayer = MinecraftMania.getInstance().getEventPlayer(Bukkit.getPlayer(playerName).getUniqueId());
        TeamHandler.getInstance().addPlayerToTeam(eventPlayer, teamColor);
    }
}