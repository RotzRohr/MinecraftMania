package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class OnCollison implements Listener
{
    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if(MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight)
        {
            Entity entity1 = event.getEntity();
            Entity entity2 = event.getEntity();
            if ((entity1 instanceof Player && !entity1.hasPermission("nopush.push")) || (entity2 instanceof Player && !entity2.hasPermission("nopush.push"))) {
                event.setCancelled(true);
            }
        }
    }
}
