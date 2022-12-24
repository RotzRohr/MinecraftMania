package minecraftmania.minecraftmania;

import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.commands.HelpCommand;
import minecraftmania.minecraftmania.commands.event.EventCommand;
import minecraftmania.minecraftmania.commands.event.EventTabCompleter;
import minecraftmania.minecraftmania.commands.player.PlayerCommand;
import minecraftmania.minecraftmania.commands.player.PlayerTabComplete;
import minecraftmania.minecraftmania.commands.team.TeamCommand;
import minecraftmania.minecraftmania.commands.team.TeamTabCompleter;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.games.*;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.listener.general.OnChat;
import minecraftmania.minecraftmania.listener.general.OnJoin;
import minecraftmania.minecraftmania.listener.general.OnQuit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MinecraftMania extends JavaPlugin implements Game
{
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final Map<UUID, EventPlayer> playerlist = new HashMap<>();
    GameMode gameMode;
    private TeamHandler teamHandler;
    private static MinecraftMania instance;
    @Override
    public void onEnable() {
        instance = this;
        teamHandler = new TeamHandler();
        initializeCommands();
        initializeListeners();
        setGameMode(GameMode.Hub);
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : this.boards.values()) {
                switch (gameMode)
                {
                    case Hub:
                        MinecraftMania.getInstance().updateBoard(board);
                        break;
                    case Spleef:
                        Spleef.getInstance().updateBoard(board);
                        break;
                    case Parkour:
                        Parkour.getInstance().updateBoard(board);
                        break;
                    case Dropper:
                        Dropper.getInstance().updateBoard(board);
                        break;
                    case Knockout:
                        Knockout.getInstance().updateBoard(board);
                        break;
                    default:
                        MinecraftMania.getInstance().updateBoard(board);
                        break;

                }
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() {
        disableListeners();
    }

    @Override
    public void updateBoard(FastBoard board)
    {
        EventPlayer player = getEventPlayer(board.getPlayer().getUniqueId());
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
                ChatColor.WHITE + "Your Coins: " + ChatColor.RESET + "" + ChatColor.YELLOW + player.getPoints(),
                "     ",
                ChatColor.WHITE + "Team: " + TeamHandler.getInstance().getTeamColor(player),
                ChatColor.WHITE + "Sponsored by " + ChatColor.RESET + "" + ChatColor.AQUA + "Kinetic Hosting"
        );
    }
    public void initializeCommands() {
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("team").setTabCompleter(new TeamTabCompleter());
        getCommand("event").setExecutor(new EventCommand());
        getCommand("event").setTabCompleter(new EventTabCompleter());
        getCommand("player").setExecutor(new PlayerCommand());
        getCommand("player").setTabCompleter(new PlayerTabComplete());
        getCommand("h").setExecutor(new HelpCommand());
        getCommand("h").setTabCompleter(new HelpCommand());

    }

    public void initializeListeners() {
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnQuit(), this);
        getServer().getPluginManager().registerEvents(new OnChat(), this);
    }

    public void disableListeners() {
        HandlerList.unregisterAll(new OnJoin());
        HandlerList.unregisterAll(new OnQuit());
        HandlerList.unregisterAll(new OnChat());
    }
    public static MinecraftMania getInstance()
    {
        return instance;
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
    public boolean wasOnServer(UUID uuid)
    {
        return playerlist.containsKey(uuid);
    }
    public void updatePlayer(Player p)
    {
        playerlist.get(p.getUniqueId()).setPlayer(p);
    }
    public void newPlayer(Player p)
    {
        playerlist.put(p.getUniqueId(), new EventPlayer(p));
    }
    public EventPlayer getEventPlayer(UUID uuid)
    {
        return playerlist.get(uuid);
    }
}