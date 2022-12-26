package minecraftmania.minecraftmania.listener.general;

import minecraftmania.minecraftmania.MinecraftMania;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBreak implements Listener
{
    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        if(!(MinecraftMania.getInstance().getEventPlayer(event.getPlayer().getUniqueId()).getPremissionLevel() >= 1))
        {
            event.setCancelled(true);
        }
    }
}
