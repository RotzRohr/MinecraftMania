package minecraftmania.minecraftmania.games;

import minecraftmania.minecraftmania.GameManager;
import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.listener.spleef.*;
import minecraftmania.minecraftmania.team.Team;
import org.bukkit.*;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class Spleef implements Game
{
    private static Spleef instance;
    private ArrayList<EventPlayer> alivePlayers;
    private ArrayList<EventPlayer> deadPlayers;
    private int taskId1;
    private int taskId2;
    private boolean breakBlocksAllowed;
    private Listener l1 = new SpleefOnDeath();
    private Listener l2 = new SpleefOnHit();
    private Listener l3 = new SpleefOnBreak();
    private Listener l4 = new SpleefOnProjectileHit();
    private Listener l5 = new SpleefOnCraft();
    private Listener l6 = new SpleefOnCollison();

    private int spleefGameNumber;

    public Spleef()
    {
        instance = this;
    }
    @Override
    public void updateBoard(FastBoard board)
    {
        EventPlayer player = MinecraftMania.getInstance().getEventPlayer(board.getPlayer().getUniqueId());
        TeamHandler.getInstance().sortTeams();
        board.updateLines(
                " ",
                "Game   " + ChatColor.RED + GameManager.getInstance().getTotalGames() + ChatColor.WHITE + "/" + ChatColor.YELLOW + "4",
                "Round " + ChatColor.RED + spleefGameNumber + ChatColor.WHITE + "/" + ChatColor.YELLOW + "3",
                "  ",
                "Players Alive: " + ChatColor.AQUA + alivePlayers.size() + "/" + (alivePlayers.size() + deadPlayers.size()),
                "  ",
                "Team Placements",
                ChatColor.WHITE + "1: " + TeamHandler.getInstance().getTeam(0).toString(),
                ChatColor.WHITE + "2: " + TeamHandler.getInstance().getTeam(1).toString(),
                ChatColor.WHITE + "3: " + TeamHandler.getInstance().getTeam(2).toString(),
                ChatColor.WHITE + "4: " + TeamHandler.getInstance().getTeam(3).toString(),
                "  ",
                ChatColor.WHITE + "Your Points: " + ChatColor.RESET + "" + ChatColor.YELLOW + player.getPoints(),
                ChatColor.WHITE + "Sponsored by " + ChatColor.RESET + "" + ChatColor.AQUA + "Kinetic Hosting"
        );
    }

    public boolean isPlayerAlive(EventPlayer player)
    {
        return alivePlayers.contains(player);
    }

    public void setBreakBlocksAllowed(boolean breakBlocksAllowed)
    {
        this.breakBlocksAllowed = breakBlocksAllowed;
    }

    public boolean isBreakBlocksAllowed()
    {
        return breakBlocksAllowed;
    }

    public void playerDied(EventPlayer p)
    {
        alivePlayers.remove(p);
        deadPlayers.add(p);
        p.getPlayer().setGameMode(org.bukkit.GameMode.SPECTATOR);

        for(EventPlayer alivePlayer : alivePlayers)
        {
            alivePlayer.addPoints(2);
        }
        if(alivePlayers.size() == 1)
        {
            alivePlayers.get(0).addPoints(100);

            String team = TeamHandler.getInstance().getTeamColor(alivePlayers.get(0)).toString();
            ChatColor color = TeamHandler.getInstance().getTeamChatColor(alivePlayers.get(0));
            Bukkit.broadcastMessage(color + "Team " + team + " has won Round "+spleefGameNumber);

            GameManager.getInstance().shutdown();
            GameManager.getInstance().setCurrentGameMode(minecraftmania.minecraftmania.games.GameMode.Hub);
        }
    }

    @Override
    public void onEnable() {
        alivePlayers = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        spleefGameNumber = GameManager.getInstance().getNextGame();
        initializeListeners();
        alivePlayers.clear();
        deadPlayers.clear();
        World world = Bukkit.getWorld("Spleef");
        for(Team t : TeamHandler.getInstance().getTeams())
        {
            for(EventPlayer eventPlayer : t.getPlayers())
            {
                eventPlayer.getPlayer().teleport(Bukkit.getWorld("Spleef").getSpawnLocation());
                alivePlayers.add(eventPlayer);
                eventPlayer.getPlayer().getInventory().clear();
            }
        }
        BukkitScheduler scheduler = Bukkit.getScheduler();
        taskId1 = scheduler.scheduleSyncRepeatingTask(MinecraftMania.getInstance(), new Runnable() {
            int time =  28;
            int phase = 1;
            @Override
            public void run() {
                time--;
                if(phase == 1) {
                    switch (time) {
                        case 27:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Welcome to Spleef");
                            break;
                        case 22:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Spleef is a team strategy game all about staying alive and breaking blocks beneath your enemies!");
                            break;
                        case 17:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "You must be the last team standing in order to win a round, and there are three rounds in Spleef.");
                            break;
                        case 12:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "You can earn 2 coins by surviving longer than another player, and you can earn 100 coins for winning a round of Spleef.");
                            break;
                        case 7:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "You will be teleported shortly to the arena where you will spread out.");
                            break;
                        case 2:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Good luck!");
                            phase = 2;
                            time = 6;
                            break;
                    }
                }
                else if(phase == 2)
                {
                    if (time <= 0)
                    {
                        scheduler.cancelTask(taskId1);
                        taskId1 = -1;
                        for(Team t : TeamHandler.getInstance().getTeams())
                        {
                            for(EventPlayer eventPlayer : t.getPlayers())
                            {
                                setBreakBlocksAllowed(true);
                                eventPlayer.getPlayer().sendTitle(ChatColor.RED + "Start", "", 0, 20, 0);
                                eventPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999, 0, false, false));
                            }
                        }
                    }
                    else if(time >=1)
                    {
                        if(time == 5)
                        {
                            for(Team t : TeamHandler.getInstance().getTeams())
                            {
                                for (EventPlayer eventPlayer : t.getPlayers())
                                {
                                    Location location = new Location(Bukkit.getWorld("Spleef"), 25, 21, 25);
                                    eventPlayer.getPlayer().teleport(location);
                                    PlayerInventory inventory = eventPlayer.getPlayer().getInventory();
                                    ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL, 1);
                                    shovel.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                                    shovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
                                    inventory.setItem(0, shovel);
                                }
                            }
                        }
                        for(Team t : TeamHandler.getInstance().getTeams())
                        {
                            for (EventPlayer eventPlayer : t.getPlayers())
                            {
                                eventPlayer.getPlayer().sendTitle(ChatColor.RED + "" + time, "", 0, 20, 0);
                            }
                        }
                    }
                }
            }
        }, 0L, 20L);
        taskId2 = scheduler.scheduleSyncRepeatingTask(MinecraftMania.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(alivePlayers != null) {
                    for(int i = 0; i < alivePlayers.size(); i++)
                    {
                        if(alivePlayers.get(i).getPlayer().getLocation().getY() <= 10)
                        {
                            alivePlayers.get(i).getPlayer().setHealth(0);
                        }
                    }
                }
            }
        }, 0L, 20L);
    }

    public void initializeListeners() {
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l1, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l2, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l3, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l4, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l5, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l6, MinecraftMania.getInstance());
    }

    public void disableListeners() {

        HandlerList.unregisterAll(l1);
        HandlerList.unregisterAll(l2);
        HandlerList.unregisterAll(l3);
        HandlerList.unregisterAll(l4);
        HandlerList.unregisterAll(l5);
        HandlerList.unregisterAll(l6);
    }

    public void onDisable() {
        SpleefOnBreak.getInstance().replaceBrokenSnowBlocks();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        if(taskId1 != -1)
        {
            scheduler.cancelTask(taskId1);
        }
        scheduler.cancelTask(taskId2);
        for(EventPlayer eventPlayer : alivePlayers)
        {
            eventPlayer.getPlayer().teleport(Bukkit.getWorld("Spleef").getSpawnLocation());
            eventPlayer.getPlayer().getInventory().clear();
            eventPlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
        for(EventPlayer eventPlayer : deadPlayers)
        {
            eventPlayer.getPlayer().teleport(Bukkit.getWorld("Spleef").getSpawnLocation());
            eventPlayer.getPlayer().getInventory().clear();
            eventPlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
        disableListeners();
        instance = null;
    }

    public static Spleef getInstance()
    {
        return instance;
    }
}
