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
import minecraftmania.minecraftmania.listener.general.OnBreak;
import minecraftmania.minecraftmania.listener.general.OnChat;
import minecraftmania.minecraftmania.listener.spleef.SpleefOnChat;
import minecraftmania.minecraftmania.listener.general.OnJoin;
import minecraftmania.minecraftmania.listener.general.OnQuit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MinecraftMania extends JavaPlugin implements Game
{
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final Map<UUID, EventPlayer> playerlist = new HashMap<>();
    private ArrayList<data> datalist = new ArrayList<>();
    private TeamHandler teamHandler;
    private GameManager gameManager;
    private static MinecraftMania instance;
    private Listener l1 = new OnChat();
    private Listener l2 = new OnBreak();

    public ArrayList<data> getDatalist() {
        return datalist;
    }
    @Override
    public void onEnable() {
        instance = this;
        teamHandler = new TeamHandler();
        gameManager = new GameManager();
        initializeCommands();
        initializeListeners();
        load();
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : this.boards.values()) {
                switch (gameManager.getCurrentGameMode())
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

    public void onDisable() {
        disableListeners();
        GameManager.getInstance().shutdown();
        save();
    }
    public void save()
    {
        String writeString = "";
        for(EventPlayer player : playerlist.values()) {
            writeString += player.getPlayer().getUniqueId() + "," + TeamHandler.getInstance().getTeamColor(player) + "," + player.getPoints() + "," + player.getPremissionLevel() + "\n";
        }

        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write(writeString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean containsPlayer(UUID uuid)
    {
        for(data d : datalist)
        {
            if(d.uuid.equals(uuid))
            {
                return true;
            }
        }
        return false;
    }

    public void load()
    {
        try
        {
            BufferedReader objReader = new BufferedReader(new FileReader("filename.txt"));
            String strCurrentLine;
            while ((strCurrentLine = objReader.readLine()) != null)
            {
                String[] data = strCurrentLine.split(",");
                datalist.add(new data(UUID.fromString(data[0]), data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

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
                ChatColor.YELLOW + GameManager.getInstance().getCurrentGameMode().toString(),
                "   ",
                ChatColor.WHITE + "1: " + TeamHandler.getInstance().getTeam(0).toString(),
                ChatColor.WHITE + "2: " + TeamHandler.getInstance().getTeam(1).toString(),
                ChatColor.WHITE + "3: " + TeamHandler.getInstance().getTeam(2).toString(),
                ChatColor.WHITE + "4: " + TeamHandler.getInstance().getTeam(3).toString(),
                "    ",
                ChatColor.WHITE + "Your Points: " + ChatColor.RESET + "" + ChatColor.YELLOW + player.getPoints(),
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
        enableTempListeners();
    }

    public void initializeListeners() {
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnQuit(), this);
    }

    public void enableTempListeners() {
        getServer().getPluginManager().registerEvents(l1, this);
        getServer().getPluginManager().registerEvents(l2, this);
    }

    public void disableTempListeners() {
        HandlerList.unregisterAll(l1);
        HandlerList.unregisterAll(l2);
    }

    public void disableListeners() {
        HandlerList.unregisterAll(new OnJoin());
        HandlerList.unregisterAll(new OnQuit());
        HandlerList.unregisterAll(new SpleefOnChat());
    }
    public static MinecraftMania getInstance()
    {
        return instance;
    }
    public Map<UUID, FastBoard> getBoards()
    {
        return boards;
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

    public class data
    {
        UUID uuid;
        String team;
        int points;
        int permissionLevel;

        public data(UUID uuid, String team, int points, int permissionLevel)
        {
            this.uuid = uuid;
            this.team = team;
            this.points = points;
            this.permissionLevel = permissionLevel;
        }

        public UUID getUUID()
        {
            return uuid;
        }

        public String getTeam()
        {
            return team;
        }

        public int getPoints()
        {
            return points;
        }

        public int getPermissionLevel()
        {
            return permissionLevel;
        }
    }
}