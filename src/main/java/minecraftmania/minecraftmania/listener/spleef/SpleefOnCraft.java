package minecraftmania.minecraftmania.listener.spleef;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class SpleefOnCraft implements Listener
{
    @EventHandler
    public void onCraft(CraftItemEvent event)
    {
        event.setCancelled(true);
    }
}
