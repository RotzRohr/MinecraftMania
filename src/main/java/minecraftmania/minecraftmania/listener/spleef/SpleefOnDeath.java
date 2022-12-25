package minecraftmania.minecraftmania.listener.spleef;
import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.event.EventPlayer;
import minecraftmania.minecraftmania.games.Spleef;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SpleefOnDeath implements Listener
{
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        Player p =(Player) event.getPlayer();
        EventPlayer ep = MinecraftMania.getInstance().getEventPlayer(p.getUniqueId());
        if(Spleef.getInstance().isPlayerAlive(ep))
        {
            event.setDeathMessage("");
            Bukkit.broadcastMessage("§6"+p.getName()+"§c is out of the game!");
            Spleef.getInstance().playerDied(ep);
        }
    }
}
