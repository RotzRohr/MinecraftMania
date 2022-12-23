package minecraftmania.minecraftmania.event;

import org.bukkit.entity.Player;

public class EventPlayer
{
    private Player player;
    private int coins;
    private boolean online;

    public EventPlayer(Player player)
    {
        this.player = player;
        coins = 0;
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
