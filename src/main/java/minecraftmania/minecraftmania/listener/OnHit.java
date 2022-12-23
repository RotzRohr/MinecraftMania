package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnHit implements Listener
{
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player)
        {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();
            if(MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight||MinecraftMania.getInstance().getGameMode() == GameMode.Spleef)
            {
                damaged.damage(0);
                damaged.knockback(0,0,0);
            }
        }
    }
}
