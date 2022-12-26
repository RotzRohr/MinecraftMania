package minecraftmania.minecraftmania.event;

import org.bukkit.entity.Player;

public class EventPlayer
{
    private Player player;
    private int points;
    private int premissionLevel;
    private boolean online;

    public EventPlayer(Player player)
    {
        this.player = player;
        points = 0;
        premissionLevel = 0;
        online = true;
    }

    public Player getPlayer()
    {
        return player;
    }
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public int getPoints()
    {
        return points;
    }

    public void addPoints(int points)
    {
        this.points += points;
    }

    public void resetPoints()
    {
        points = 0;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public boolean isOnline()
    {
        return online;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
    }

    public int getPremissionLevel()
    {
        return premissionLevel;
    }

    public void setPremissionLevel(int premissionLevel)
    {
        this.premissionLevel = premissionLevel;
    }
}
