package minecraftmania.minecraftmania.listener.spleef;
import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.Spleef;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SpleefOnBreak implements Listener {
    private static SpleefOnBreak instance;
    private List<Block> brokenSnowBlocks;

    public SpleefOnBreak() {
        instance = this;
        brokenSnowBlocks = new ArrayList<>();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        if(Spleef.getInstance().isPlayerAlive(MinecraftMania.getInstance().getEventPlayer(player.getUniqueId()))) {
            if (event.getBlock().getType().equals(Material.SNOW_BLOCK) && Spleef.getInstance().isBreakBlocksAllowed()) {
                brokenSnowBlocks.add(event.getBlock());
                ItemStack snowball = new ItemStack(Material.SNOWBALL, 1);
                event.getPlayer().getInventory().addItem(snowball);
            } else {
                event.setCancelled(true);
            }
        }
    }

    public void replaceBrokenSnowBlocks()
    {
        for(Block block : brokenSnowBlocks)
        {
            block.setType(Material.SNOW_BLOCK);
        }
    }

    public void addBlock(Block block)
    {
        brokenSnowBlocks.add(block);
    }

    public static SpleefOnBreak getInstance() {
        return instance;
    }
}
