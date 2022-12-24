package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OnBreak implements Listener {
    private List<Block> brokenSnowBlocks;
    private static OnBreak instance;

    public OnBreak() {
        instance = this;
        brokenSnowBlocks = new ArrayList<>();
        brokenSnowBlocks.clear();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (MinecraftMania.getInstance().getGameMode() == GameMode.Spleef || event.getBlock().getType() != Material.SNOW_BLOCK && MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight) {
            event.setCancelled(true);
        }
        if (event.getBlock().getType() == Material.SNOW_BLOCK && MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight) {
            brokenSnowBlocks.add(event.getBlock());
            ItemStack snowball = new ItemStack(Material.SNOWBALL, 1);
            event.getPlayer().getInventory().addItem(snowball);
        }
    }

    public static OnBreak getInstance() {
        return instance;
    }

    public void replaceBrokenSnowBlocks() {
        for (Block block : brokenSnowBlocks) {
            block.setType(Material.SNOW_BLOCK);
        }
        brokenSnowBlocks.clear();
    }
}
