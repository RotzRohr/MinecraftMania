package minecraftmania.minecraftmania.handler;

import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.team.Team;
import minecraftmania.minecraftmania.team.TeamColor;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;

public class TeamHandler
{
    private static TeamHandler instance;
    private ArrayList<Team> teams;

    public TeamHandler()
    {
        instance = this;
        teams = new ArrayList<>();
        teams.add(new Team(TeamColor.Red));
        teams.add(new Team(TeamColor.Blue));
        teams.add(new Team(TeamColor.Yellow));
        teams.add(new Team(TeamColor.Green));
    }

    public static TeamHandler getInstance()
    {
        return instance;
    }

    public Team getTeam(TeamColor teamColor)
    {
        for (Team team : teams)
        {
            if (team.getName().equals(teamColor.toString()))
            {
                return team;
            }
        }
        return null;
    }
    public Team getTeam(int index)
    {
        return teams.get(index);
    }

    public String getTeamColor(EventPlayer p)
    {
        //p.getPlayer().getUniqueId()
        for (Team team : teams)
        {
            if(team.isInTeam(p.getPlayer().getUniqueId()))
            {
                return team.getName();
            }
        }
        return TeamColor.NoTeam.toString();
    }

    public void addPlayerToTeam(EventPlayer eventPlayer, TeamColor teamColor)
    {
        getTeam(teamColor).addEventPlayer(eventPlayer);
        eventPlayer.getPlayer().sendMessage("You have been added to team " + teamColor.name());
    }

    public void removePlayerFromTeam(EventPlayer eventPlayer, TeamColor teamColor)
    {
        if (getTeam(teamColor).isInTeam(eventPlayer.getPlayer().getUniqueId()))
        {
            getTeam(teamColor).removeEventPlayer(eventPlayer);
            eventPlayer.getPlayer().sendMessage(ChatColor.RED + "You have been removed from the " + teamColor.toString() + " team!");
        }
    }

    public void clearTeams()
    {
        for (Team team : teams)
        {
            team.resetTeam();
        }
    }

    public void clearTeam(TeamColor teamColor)
    {
        getTeam(teamColor).resetTeam();
    }

    public void clearPoints()
    {
        for (Team team : teams)
        {
            team.resetTeamPoints();
        }
    }

    public void clearPoints(TeamColor teamColor)
    {
        getTeam(teamColor).resetTeamPoints();
    }

    public String ListTeam(TeamColor teamColor)
    {
        Team team = getTeam(teamColor);
        String output = team.getColor() + team.getName() + ChatColor.WHITE + "\n";
        output += team.getFormattedPlayerNames();
        return output;
    }

    public String ListAllTeams()
    {
        String output = "";
        for(Team t : teams)
        {
            output += ListTeam(TeamColor.valueOf(t.getName()))+"\n";
        }
        output = output.substring(0, output.length()-1);
        return output;
    }

    public void sortTeams()
    {
        Collections.sort(teams);
    }

}
