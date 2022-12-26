package minecraftmania.minecraftmania.games;

import minecraftmania.minecraftmania.GameManager;
import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.listener.spleef.*;
import minecraftmania.minecraftmania.team.Team;
import minecraftmania.minecraftmania.team.TeamColor;
import org.bukkit.*;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class Spleef implements Game
{
    private static Spleef instance;
    private ArrayList<EventPlayer> alivePlayers;
    private ArrayList<EventPlayer> deadPlayers;
    private ArrayList<EventPlayer> players;
    private int taskId1;
    private int taskId2;
    private int taskId3;
    private boolean breakBlocksAllowed;
    private boolean chatAllowed;
    private Listener l1 = new SpleefOnDeath();
    private Listener l2 = new SpleefOnHit();
    private Listener l3 = new SpleefOnBreak();
    private Listener l4 = new SpleefOnProjectileHit();
    private Listener l5 = new SpleefOnCraft();
    private Listener l6 = new SpleefOnCollison();
    private Listener l7 = new SpleefOnChat();
    private Listener l8 = new SpleefOnDrop();
    private Listener l9 = new SpleefOnUnequip();

    private int spleefGameNumber;

    public Spleef()
    {
        instance = this;
    }


    public boolean isPlayerAlive(EventPlayer player)
    {
        return alivePlayers.contains(player);
    }

    public boolean isBreakBlocksAllowed()
    {
        return breakBlocksAllowed;
    }

    public boolean isChatAllowed()
    {
        return chatAllowed;
    }

    //is run when a player dies
    public void playerDied(EventPlayer p)
    {
        alivePlayers.remove(p);
        deadPlayers.add(p);
        p.getPlayer().setGameMode(org.bukkit.GameMode.SPECTATOR);

        for(EventPlayer alivePlayer : alivePlayers)
        {
            alivePlayer.addPoints(2);
            alivePlayer.getPlayer().sendMessage(ChatColor.GOLD + "You got 2 points for living longer than " + p.getPlayer().getName());
        }
        if(alivePlayers.size() == 1)
        {
            alivePlayers.get(0).addPoints(100);

            String team = TeamHandler.getInstance().getTeamColor(alivePlayers.get(0)).toString();
            ChatColor color = TeamHandler.getInstance().getTeamChatColor(alivePlayers.get(0));
            Broadcast(color + "Team " + team + " has won Round "+spleefGameNumber);
            GameManager.getInstance().shutdown();
            GameManager.getInstance().setCurrentGameMode(minecraftmania.minecraftmania.games.GameMode.Hub);
        }
        //check if only one team is left
        int teamsleft = 0;
        ChatColor teamColor = null;
        for(Team team : TeamHandler.getInstance().getTeams())
        {
            boolean wasoneleft = false;
            for(EventPlayer ep : team.getPlayers())
            {
                if(alivePlayers.contains(ep))
                {
                    wasoneleft = true;
                    teamColor = team.getColor();
                    break;
                }
            }
            if(wasoneleft)
            {
                teamsleft++;
            }
        }
        if(teamsleft==1)
        {
            int pointsToGive = 100;
            for(EventPlayer ep : alivePlayers)
            {
                ep.addPoints((int) pointsToGive/alivePlayers.size());
                ep.getPlayer().sendMessage(ChatColor.GOLD + "You got " +(int) pointsToGive/alivePlayers.size() + " points for winning the game");
            }
            Broadcast(teamColor + "Team " + teamColor + " has won Round "+spleefGameNumber);
            GameManager.getInstance().shutdown();
        }
    }
    //sends a Broadcastmessage to all players
    private void Broadcast(String message)
    {
        for(EventPlayer p : players)
        {
            p.getPlayer().sendMessage(message);
        }
    }
    //explains the game if it is the first round
    private void rules()
    {
        taskId1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftMania.getInstance(), new Runnable() {
            int time =  28;
            @Override
            public void run()
            {
                time--;
                if(time == 27)
                {
                    Broadcast(ChatColor.YELLOW + "Welcome to Spleef");
                }
                else if (time == 22)
                {
                    Broadcast(ChatColor.YELLOW + "Spleef is a team strategy game all about staying alive and breaking blocks beneath your enemies!");
                }
                else if (time == 17)
                {
                    Broadcast(ChatColor.YELLOW + "You must be the last team standing in order to win a round, and there are three rounds in Spleef.");
                }
                else if (time == 12)
                {
                    Broadcast(ChatColor.YELLOW + "You can earn 2 coins by surviving longer than another player, and you can earn 100 coins for winning a round of Spleef.");
                }
                else if (time == 7)
                {
                    Broadcast(ChatColor.YELLOW + "You will be teleported shortly to the arena where you will spread out.");
                }
                else if (time == 2)
                {
                    Broadcast(ChatColor.YELLOW + "Good luck!");
                }
                else if(time == 0)
                {
                    Broadcast("§6Round "+spleefGameNumber+"§c is starting!");
                    fight();
                    Bukkit.getScheduler().cancelTask(taskId1);
                }
            }
        }, 0, 20L);
    }
    //gives each player a shovel and starts the event
    private void fight()
    {
        giveShovel();
        teleportPlayersTo(new Location(Bukkit.getWorld("Spleef"), 25, 21, 25));
        givePlayersTheirHead();
        chatAllowed = true;
        taskId3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftMania.getInstance(), new Runnable() {
            int time = 6;
            @Override
            public void run()
            {
                time--;
                if(time>0)
                {
                    sendTitle(ChatColor.RED + "" + time);
                }
                else
                {
                    sendTitle(ChatColor.RED + "Start");
                    breakBlocksAllowed = true;
                    Bukkit.getScheduler().cancelTask(taskId3);
                    taskId3 = -1;
                }

            }
        }, 0L, 20L);
    }
    //checks if a play get under Y-Level -10 if so he dies
    private void checkForDeath()
    {
        taskId2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftMania.getInstance(), new Runnable() {
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
    //is run when the event is started
    @Override
    public void onEnable()
    {
        breakBlocksAllowed = false;
        chatAllowed = false;
        initializeListeners();
        alivePlayers = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        players = new ArrayList<>();
        for(Team t : TeamHandler.getInstance().getTeams())
        {
            for(EventPlayer p : t.getPlayers())
            {
                players.add(p);
                alivePlayers.add(p);
            }
        }
        giveSaturation();
        clearInventory();
        setGamemode(GameMode.SURVIVAL);
        teleportPlayersTo(new Location(Bukkit.getWorld("Spleef"),28.5,41,25.5,90,0));
        spleefGameNumber = GameManager.getInstance().getNextGame();
        if(spleefGameNumber==1)
        {
            rules();
        }
        else
        {
            Broadcast("§6Round "+spleefGameNumber+"§c is starting!");
            fight();
        }
        checkForDeath();
    }
    //is run when the event is started so the listeners are initialized
    public void initializeListeners() {
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l1, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l2, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l3, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l4, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l5, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l6, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l7, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l8, MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(l9, MinecraftMania.getInstance());
    }
    //is run when the event is stopped so that the listeners are not listening anymore
    public void disableListeners() {

        HandlerList.unregisterAll(l1);
        HandlerList.unregisterAll(l2);
        HandlerList.unregisterAll(l3);
        HandlerList.unregisterAll(l4);
        HandlerList.unregisterAll(l5);
        HandlerList.unregisterAll(l6);
        HandlerList.unregisterAll(l7);
        HandlerList.unregisterAll(l8);
        HandlerList.unregisterAll(l9);
    }
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
    //teleports all players to the given location
    private void teleportPlayersTo(Location loc)
    {
        for(EventPlayer p : players)
        {
            p.getPlayer().teleport(loc);
        }
    }
    //sets the players gamemode to a given gamemode
    private void setGamemode(GameMode mode)
    {
        for(EventPlayer p : players)
        {
            p.getPlayer().setGameMode(mode);
        }
    }
    //clears the inventory of all players
    private void clearInventory()
    {
        for(EventPlayer p : players)
        {
            p.getPlayer().getInventory().clear();
        }
    }
    //sends a title to all players
    private void sendTitle(String messasge)
    {
    for(EventPlayer p : players)
        {
            p.getPlayer().sendTitle(ChatColor.RED + "" + messasge, "", 0, 20, 0);
        }
    }
    //gives each player a shovel
    private void giveShovel()
    {
        for(EventPlayer p : players)
        {
            ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
            shovel.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            shovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
            p.getPlayer().getInventory().addItem(shovel);
        }
    }
    //gives saturation to all players
    private void giveSaturation()
    {
        for(EventPlayer p : players)
        {
            p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999, 0, false, false));
        }
    }
    //is run when the event is ended
    @Override
    public void onDisable() {
        SpleefOnBreak.getInstance().replaceBrokenSnowBlocks();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        if(taskId1 != -1)
        {
            scheduler.cancelTask(taskId1);
        }
        if(taskId3 != -1)
        {
            scheduler.cancelTask(taskId3);
        }
        scheduler.cancelTask(taskId2);
        clearInventory();
        setGamemode(GameMode.SURVIVAL);
        teleportPlayersTo(new Location(Bukkit.getWorld("Spleef"),28.5,41,25.5,90,0));
        disableListeners();
        instance = null;
    }

    private void givePlayersTheirHead()
    {
        for(Team t : TeamHandler.getInstance().getTeams())
        {
            for(EventPlayer p : t.getPlayers())
            {
                ItemStack wool;
                switch (t.getName())
                {
                    case "Red":
                        wool = new ItemStack(Material.RED_WOOL);
                        break;
                    case "Blue":
                        wool = new ItemStack(Material.BLUE_WOOL);
                        break;
                    case "Green":
                        wool = new ItemStack(Material.GREEN_WOOL);
                        break;
                    case "Yellow":
                        wool = new ItemStack(Material.YELLOW_WOOL);
                        break;
                    default:
                        wool = new ItemStack(Material.WHITE_WOOL);
                        break;
                }
                p.getPlayer().getInventory().setHelmet(wool);
            }
        }
    }

    public static Spleef getInstance()
    {
        return instance;
    }
}
