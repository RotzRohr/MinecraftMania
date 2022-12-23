package minecraftmania.minecraftmania;

import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.commands.SpleefCommand;
import minecraftmania.minecraftmania.commands.TeamCommand;
import minecraftmania.minecraftmania.commands.TestCommand;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.games.Game;
import minecraftmania.minecraftmania.games.GameMode;
import minecraftmania.minecraftmania.games.Spleef;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.listener.*;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MinecraftMania extends JavaPlugin implements Game
{
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final Map<Player, EventPlayer> playerlist = new HashMap<>();
    GameMode gameMode;
    private static MinecraftMania instance;
    private Spleef spleef;
    private TeamHandler teamHandler;
    private int numberOfGames;
    @Override
    public void onEnable() {
        instance = this;
        spleef = new Spleef();
        teamHandler = new TeamHandler();
        initializeCommands();
        initializeListeners();
        numberOfGames = 0;
        setGameMode(GameMode.Hub);
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : this.boards.values()) {
                if(GameMode.Hub == gameMode||GameMode.Waiting == gameMode)
                {
                    updateBoard(board);
                }
                else if(GameMode.Spleef == gameMode)
                {
                    Spleef.getInstance().updateBoard(board);
                }
            }
        }, 0, 20);
    }

    public int getNumberOfGames()
    {
        return numberOfGames;
    }

    public void incrementNumberOfGames()
    {
        numberOfGames++;
    }

    public void addPlayer(Player player)
    {
        playerlist.put(player, new EventPlayer(player));
    }

    public void removePlayer(Player player)
    {
        playerlist.remove(player);
    }

    public ArrayList<EventPlayer> getPlayerList()
    {
        return new ArrayList<>(playerlist.values());
    }

    public EventPlayer getEventPlayer(Player player)
    {
        return playerlist.get(player);
    }

    public Map<UUID, FastBoard> getBoards()
    {
        return boards;
    }

    public GameMode getGameMode()
    {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    private void initializeCommands() {
        @Nullable PluginCommand team = getCommand("team");
        @Nullable PluginCommand spleef = getCommand("spleef");
        @Nullable PluginCommand g = getCommand("rcg");
        spleef.setExecutor(new SpleefCommand());
        team.setExecutor(new TeamCommand());
        g.setExecutor(new TestCommand());
    }

    private void initializeListeners() {
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnQuit(), this);
        getServer().getPluginManager().registerEvents(new OnChat(), this);
        getServer().getPluginManager().registerEvents(new OnDeath(), this);
        getServer().getPluginManager().registerEvents(new OnBreak(), this);
        getServer().getPluginManager().registerEvents(new OnProjectileHit(), this);
        getServer().getPluginManager().registerEvents(new OnHit(), this);
        getServer().getPluginManager().registerEvents(new OnCraft(), this);
    }

    @Override
    public void updateBoard(FastBoard board)
    {
        EventPlayer player = getEventPlayer(board.getPlayer());
        TeamHandler.getInstance().sortTeams();
        LocalTime currentTime = LocalTime.now();
        String timeString = String.format("%02d:%02d:%02d", currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        board.updateLines(
                "",
                ChatColor.WHITE + "Current time: " + ChatColor.YELLOW + timeString,
                "  ",
                ChatColor.YELLOW + gameMode.toString(),
                "   ",
                ChatColor.WHITE + "1: " + TeamHandler.getInstance().getTeam(0).toString(),
                ChatColor.WHITE + "2: " + TeamHandler.getInstance().getTeam(1).toString(),
                ChatColor.WHITE + "3: " + TeamHandler.getInstance().getTeam(2).toString(),
                ChatColor.WHITE + "4: " + TeamHandler.getInstance().getTeam(3).toString(),
                "    ",
                ChatColor.WHITE + "Your Coins: " + ChatColor.RESET + "" + ChatColor.YELLOW + player.getCoins(),
                "     ",
                ChatColor.WHITE + "Team: " + TeamHandler.getInstance().getTeamColor(player),
                ChatColor.WHITE + "Sponsored by " + ChatColor.RESET + "" + ChatColor.AQUA + "Kinetic Hosting"
        );
    }

    public static MinecraftMania getInstance()
    {
        return instance;
    }
}
