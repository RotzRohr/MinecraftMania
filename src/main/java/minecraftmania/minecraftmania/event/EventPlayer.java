package minecraftmania.minecraftmania.event;

import org.bukkit.entity.Player;

public class EventPlayer
{
    private Player player;
    private int coins;
    private int premissionLevel;
    private boolean online;

    public EventPlayer(Player player)
    {
        this.player = player;
        coins = 0;
        online = true;
        premissionLevel = 0;
    }

    public int getPremissionLevel()
    {
        return premissionLevel;
    }

    public void setPremissionLevel(int premissionLevel)
    {
        this.premissionLevel = premissionLevel;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public int getCoins()
    {
        return coins;
    }

    public void setCoins(int coins)
    {
        this.coins = coins;
    }

    public void addCoins(int coins)
    {
        this.coins += coins;
    }

    public void resetCoins()
    {
        coins = 0;
    }

    public boolean isOnline()
    {
        return online;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
    }
}
