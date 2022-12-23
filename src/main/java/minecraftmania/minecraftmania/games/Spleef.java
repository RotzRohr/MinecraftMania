package minecraftmania.minecraftmania.games;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.team.Team;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class Spleef implements Game
{
    private ArrayList<EventPlayer> alivePlayers = new ArrayList<>();
    private ArrayList<EventPlayer> deadPlayers = new ArrayList<>();
    private static Spleef instance;
    private int spleefGameNumber;
    private int taskId1;
    private int taskId2;

    public Spleef()
    {
        instance = this;
        spleefGameNumber = 0;
    }

    @Override
    public void updateBoard(FastBoard board)
    {
        EventPlayer player = MinecraftMania.getInstance().getEventPlayer(board.getPlayer());
        TeamHandler.getInstance().sortTeams();
        board.updateLines(
                " ",
                "Game   " + ChatColor.RED + MinecraftMania.getInstance().getNumberOfGames() + ChatColor.WHITE + "/" + ChatColor.YELLOW + "4",
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
                ChatColor.WHITE + "Your Coins: " + ChatColor.RESET + "" + ChatColor.YELLOW + player.getCoins(),
                ChatColor.WHITE + "Sponsored by " + ChatColor.RESET + "" + ChatColor.AQUA + "Kinetic Hosting"
        );
    }

    public static Spleef getInstance()
    {
        return instance;
    }

    public boolean isAlive(EventPlayer player)
    {
        return alivePlayers.contains(player);
    }

    public void playerDied(EventPlayer player)
    {
        alivePlayers.remove(player);
        deadPlayers.add(player);
        player.getPlayer().setGameMode(org.bukkit.GameMode.SPECTATOR);

        for(EventPlayer alivePlayer : alivePlayers)
        {
            alivePlayer.addCoins(2);
        }
        if(alivePlayers.size() == 1)
        {
            alivePlayers.get(0).addCoins(100);

            String team = TeamHandler.getInstance().getTeamColor(alivePlayers.get(0)).toString();
            ChatColor color = TeamHandler.getInstance().getChatColor(alivePlayers.get(0));

            Bukkit.broadcastMessage(color + "Team " + team + " has won Round "+spleefGameNumber);

            onDisable();
        }
    }

    public void onEnable()
    {

        Location location = Bukkit.getWorld("Spleef").getSpawnLocation();
        location.setX(28);
        location.setY(41);
        location.setZ(25);
        alivePlayers.clear();
        deadPlayers.clear();
        spleefGameNumber++;
        World world = Bukkit.getWorld("Spleef");
        for(Team t : TeamHandler.getInstance().getTeams())
        {
            for(EventPlayer eventPlayer : t.getPlayers())
            {
                eventPlayer.getPlayer().teleport(location);
                alivePlayers.add(eventPlayer);
                eventPlayer.getPlayer().getInventory().clear();
            }
        }

        MinecraftMania.getInstance().setGameMode(GameMode.Spleef);
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
                            time = 11;
                            break;
                    }
                }
                else if(phase == 2)
                {
                    if (time <= 0)
                    {
                        scheduler.cancelTask(taskId1);
                        taskId1 = -1;
                        MinecraftMania.getInstance().setGameMode(GameMode.SpleefFight);
                        for(Team t : TeamHandler.getInstance().getTeams())
                        {
                            for(EventPlayer eventPlayer : t.getPlayers())
                            {
                                eventPlayer.getPlayer().sendTitle(ChatColor.RED + "Start", "", 0, 20, 0);
                                eventPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999, 0, false, false));
                            }
                        }
                    }
                    else if(time >=1)
                    {
                        if(time == 10)
                        {
                            for(Team t : TeamHandler.getInstance().getTeams())
                            {
                                for (EventPlayer eventPlayer : t.getPlayers())
                                {
                                    eventPlayer.getPlayer().teleport(Bukkit.getWorld("Spleef").getSpawnLocation());
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
                for(EventPlayer player : alivePlayers)
                {
                    if(player.getPlayer().getLocation().getY()<=10)
                    {
                        player.getPlayer().setHealth(0);
                    }
                }
            }
        }, 0L, 20L);
}

    public void onDisable()
    {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        if(taskId1 != -1)
        {
            scheduler.cancelTask(taskId1);
        }
        scheduler.cancelTask(taskId2);
        MinecraftMania.getInstance().setGameMode(GameMode.Hub);
        for(EventPlayer eventPlayer : alivePlayers)
        {
            eventPlayer.getPlayer().teleport(Bukkit.getWorld("Hub").getSpawnLocation());
            eventPlayer.getPlayer().getInventory().clear();
        }
        for(EventPlayer eventPlayer : deadPlayers)
        {
            eventPlayer.getPlayer().teleport(Bukkit.getWorld("Hub").getSpawnLocation());
            eventPlayer.getPlayer().getInventory().clear();
        }
    }
}
