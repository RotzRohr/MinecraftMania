package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class OnCraft implements Listener
{
    @EventHandler
    public void onCraft(CraftItemEvent event)
    {
        if(MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight||MinecraftMania.getInstance().getGameMode() == GameMode.Spleef)
        {
            event.setCancelled(true);
        }
    }
}
