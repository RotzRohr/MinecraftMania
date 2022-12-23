package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class OnBreak implements Listener
{
    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        if(MinecraftMania.getInstance().getGameMode() == GameMode.Spleef || event.getBlock().getType() != Material.SNOW_BLOCK &&MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight)
        {
            event.setCancelled(true);
        }
        if(event.getBlock().getType() == Material.SNOW_BLOCK && MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight)
        {
            ItemStack snowball = new ItemStack(Material.SNOWBALL, 1);
            event.getPlayer().getInventory().addItem(snowball);
        }
    }
}
