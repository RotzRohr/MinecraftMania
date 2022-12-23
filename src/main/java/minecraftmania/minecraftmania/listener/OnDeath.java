package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.games.GameMode;
import minecraftmania.minecraftmania.games.Spleef;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeath implements Listener
{
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        if(MinecraftMania.getInstance().getGameMode()== GameMode.SpleefFight)
        {
            Player p =(Player) event.getPlayer();
            if(Spleef.getInstance().isAlive(MinecraftMania.getInstance().getEventPlayer(p)))
            {
                event.setDeathMessage("");
                Bukkit.broadcastMessage("§6"+p.getName()+"§c is out of the game!");
                EventPlayer ep = MinecraftMania.getInstance().getEventPlayer(p);
                Spleef.getInstance().playerDied(ep);
            }
        }
    }
}
