package minecraftmania.minecraftmania.team;

import minecraftmania.minecraftmania.event.EventPlayer;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Team implements Comparable
{
    private final ChatColor color;
    private final String name;
    private final ArrayList<EventPlayer> players;
    private final TeamColor teamColor;

    public Team(TeamColor teamColor, ChatColor color, String name)
    {
        this.teamColor = teamColor;
        this.color = color;
        this.name = name;
        players = new ArrayList<>();
    }

    public TeamColor getTeamColor()
    {
        return teamColor;
    }
    public ChatColor getChatColor()
    {
        return color;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<EventPlayer> getPlayers()
    {
        return players;
    }

    public void addPlayer(EventPlayer player)
    {
        this.players.add(player);
    }

    public void removePlayer(EventPlayer player)
    {
        this.players.remove(player);
    }

    public boolean hasPlayer(EventPlayer player)
    {
        return this.players.contains(player);
    }

    public int getCoins()
    {
        int coins = 0;
        for(EventPlayer player : players)
        {
            coins += player.getCoins();
        }
        return coins;
    }

    public String getFormattedPlayerNames()
    {
        StringBuilder playerNameBuilder = new StringBuilder();

        if(this.players.size() == 0) return color + "Team:\n" + "    No players.";

        for(EventPlayer player : this.players) {
            playerNameBuilder.append("    " + player.getPlayer().getName() + "\n");
        }

        return color + "Team:\n" + playerNameBuilder.toString();
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return this.getCoins() - ((Team) o).getCoins();
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
        return color + temp + ChatColor.YELLOW + getCoins() + ChatColor.WHITE+ " Coins";
    }
}
