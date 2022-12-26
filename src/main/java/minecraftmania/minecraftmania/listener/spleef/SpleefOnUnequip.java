package minecraftmania.minecraftmania.listener.spleef;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SpleefOnUnequip implements Listener
{
    @EventHandler
    public void onUnequip(InventoryClickEvent event)
    {
        event.setCancelled(true);
    }
}
