package minecraftmania.minecraftmania.event;

import org.bukkit.entity.Player;

public class EventPlayer
{
    private final Player player;
    private int coins;

    public EventPlayer(Player player)
    {
        this.player = player;
        coins = 0;
    }

    public Player getPlayer()
    {
        return player;
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
}
