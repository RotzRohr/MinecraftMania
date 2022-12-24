package minecraftmania.minecraftmania.team;

import minecraftmania.minecraftmania.event.EventPlayer;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class Team implements Comparable
{
    private TeamColor name;
    private ArrayList<EventPlayer> players;

    public Team(TeamColor name)
    {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public String getName()
    {
        return name.toString();
    }

    public ChatColor getColor()
    {
        return ChatColor.valueOf(getName().toUpperCase());
    }

    public void addEventPlayer(EventPlayer eventPlayer)
    {
        players.add(eventPlayer);
    }

    public void removeEventPlayer(EventPlayer eventPlayer)
    {
        players.remove(eventPlayer);
    }

    public void resetTeam()
    {
        players.clear();
    }

    public void resetTeamPoints()
    {
        for (EventPlayer eventPlayer : players)
        {
            eventPlayer.resetPoints();
        }
    }

    public EventPlayer getEventPlayer(UUID uuid)
    {
        for (EventPlayer eventPlayer : players)
        {
            if (eventPlayer.getPlayer().getUniqueId().equals(uuid))
            {
                return eventPlayer;
            }
        }
        return null;
    }

    public boolean isInTeam(UUID uuid)
    {
        return getEventPlayer(uuid) != null;
    }

    public int getPoints()
    {
        int points = 0;
        for (EventPlayer eventPlayer : players)
        {
            points += eventPlayer.getPoints();
        }
        return points;
    }

    public String getFormattedPlayerNames()
    {
        String playerNames = "";
        for (EventPlayer eventPlayer : players)
        {
            playerNames += "    " + eventPlayer.getPlayer().getName() + "\n";
        }
        if(playerNames.equals(""))
            playerNames = "    No players in this team ";
        return playerNames.substring(0, playerNames.length() - 1);
    }

    public String toString()
    {
        String temp = name.toString();
        switch(temp)
        {
            case "Red":
                temp+="     ";
                break;
            case "Blue":
                temp+="    ";
                break;
            case "Yellow":
                temp+="  ";
                break;
            case "Green":
                temp+="  ";
                break;
        }
        return getColor() + temp + ChatColor.YELLOW + getPoints() + ChatColor.WHITE+ " Points";
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Team team = (Team) o;
        return team.getPoints() - getPoints();
    }
}
