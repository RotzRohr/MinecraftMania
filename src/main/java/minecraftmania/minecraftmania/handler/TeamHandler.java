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
        teams.add(new Team(TeamColor.Red, ChatColor.RED, "Red"));
        teams.add(new Team(TeamColor.Blue, ChatColor.BLUE, "Blue"));
        teams.add(new Team(TeamColor.Yellow, ChatColor.YELLOW, "Yellow"));
        teams.add(new Team(TeamColor.Green, ChatColor.GREEN, "Green"));
    }

    public static TeamHandler getInstance()
    {
        return instance;
    }

    public ArrayList<Team> getTeams()
    {
        return teams;
    }

    public Team getTeam(int index)
    {
        return teams.get(index);
    }

    public void addPlayerToTeam(TeamColor teamColor, EventPlayer player)
    {
        if(isInTeam(player))
        {
            removePlayerFromTeam(player);
        }
            for (Team team : teams) {
                if (team.getTeamColor() == teamColor) {
                    team.addPlayer(player);
                    return;
                }
            }
    }

    public void removePlayerFromTeam(EventPlayer player)
    {
        TeamColor teamColor = getTeamColor(player);
        if(teamColor!=TeamColor.NoTeam)
        {
            for (Team team : teams) {
                if (team.getTeamColor() == teamColor) {
                    team.removePlayer(player);
                    return;
                }
            }
        }
    }

    public TeamColor getTeamColor(EventPlayer player)
    {
        for(Team team : teams)
        {
            if(team.hasPlayer(player))
            {
                return team.getTeamColor();
            }
        }
        return TeamColor.NoTeam;
    }

    public ChatColor getChatColor(EventPlayer player)
    {
        for(Team team : teams)
        {
            if(team.hasPlayer(player))
            {
                return team.getChatColor();
            }
        }
        return ChatColor.WHITE;
    }

    public int getTeamCoins(TeamColor teamColor)
    {
        for(Team team : teams)
        {
            if(team.getTeamColor() == teamColor)
            {
                return team.getCoins();
            }
        }
        return 0;
    }

    public boolean isInTeam(EventPlayer player)
    {
        return getTeamColor(player)!=TeamColor.NoTeam;
    }

    public void sortTeams()
    {
        Collections.sort(teams);
    }

    public String getAllTeamsToString()
    {
        String allTeams = "";
        for (Team team : teams) {
            allTeams += team.getFormattedPlayerNames() + "\n";
        }
        return allTeams;
    }


}
