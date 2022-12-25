package minecraftmania.minecraftmania;

import minecraftmania.minecraftmania.games.GameMode;
import minecraftmania.minecraftmania.games.Spleef;

public class GameManager
{
    private static GameManager instance;
    private GameMode currentGameMode;
    private int totalGames;
    private int spleedGameCount;

    public GameManager()
    {
        instance = this;
        currentGameMode = GameMode.Hub;
        spleedGameCount = 0;
        totalGames = 0;
    }
    public int getTotalGames()
    {
        return totalGames;
    }

    public void incrementTotalGames()
    {
        totalGames++;
    }
    public void shutdown()
    {
        switch (currentGameMode)
        {
            case Spleef:
                if(Spleef.getInstance()!= null)
                {
                    Spleef.getInstance().onDisable();
                }
                break;
            case Parkour:
                break;
            case Dropper:
                break;
            case Knockout:
                break;
            case Hub:
                break;
        }
    }
    public int getNextGame()
    {
        switch (currentGameMode)
        {
            case Spleef:
                return spleedGameCount;
            case Parkour:
                return 2;
            case Dropper:
                return 3;
            case Knockout:
                return 4;
            case Hub:
                return 0;
            default:
                return 0;
        }
    }
    public void updateGame()
    {
        switch (currentGameMode)
        {
            case Spleef:
                new Spleef();
                spleedGameCount++;
                Spleef.getInstance().onEnable();
                break;
            case Parkour:
                break;
            case Dropper:
                break;
            case Knockout:
                break;
            case Hub:
                break;
        }
    }

    public static GameManager getInstance()
    {
        return instance;
    }

    public GameMode getCurrentGameMode()
    {
        return currentGameMode;
    }

    public void setCurrentGameMode(GameMode gameMode)
    {
        currentGameMode = gameMode;
    }
}
