package minecraftmania.minecraftmania.commands;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.team.TeamColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeamCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String whatToDo = args[0];
        switch (whatToDo)
        {
            case "list":
                listTeams(sender);
                break;
            case "add":
                setupTeams(sender, args);
                break;
            case "set":
                setupTeams(sender, args);
                break;
            case "remove":
                removePlayerFromTeam(sender, args);
                break;

        }
        return true;
    }

    private void removePlayerFromTeam(CommandSender sender, String[] args)
    {
        EventPlayer ep = MinecraftMania.getInstance().getEventPlayer(MinecraftMania.getInstance().getServer().getPlayer(args[1]));
        TeamHandler.getInstance().removePlayerFromTeam(ep);
    }

    private void setupTeams(CommandSender sender, String[] args)
    {
        TeamColor targetTeam;
        switch (args[1].toLowerCase()) {
            case "red":
                targetTeam = TeamColor.Red;
                break;
            case "blue":
                targetTeam = TeamColor.Blue;
                break;
            case "yellow":
                targetTeam = TeamColor.Yellow;
                break;
            case "green":
                targetTeam = TeamColor.Green;
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown team.");
                return;
        }
        for (int i = 2; i < args.length; i++)
        {
            EventPlayer ep = MinecraftMania.getInstance().getEventPlayer(MinecraftMania.getInstance().getServer().getPlayer(args[i]));
            addPlayerToTeam(targetTeam, ep);
        }
    }

    private void listTeams(CommandSender sender) {
        sender.sendMessage(TeamHandler.getInstance().getAllTeamsToString());
    }

    private void addPlayerToTeam(TeamColor teamColor, EventPlayer player) {
        if(!TeamHandler.getInstance().isInTeam(player))
            TeamHandler.getInstance().addPlayerToTeam(teamColor, player);
        else {

            TeamHandler.getInstance().removePlayerFromTeam(player);
            TeamHandler.getInstance().addPlayerToTeam(teamColor, player);
        }
    }
}
