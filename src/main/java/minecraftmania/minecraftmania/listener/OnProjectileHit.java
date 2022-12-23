package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class OnProjectileHit implements Listener
{
    @EventHandler
    public void OnProjectileHit(ProjectileHitEvent event) {
        Entity projectile = event.getEntity();
        if (projectile.getType() == EntityType.SNOWBALL && MinecraftMania.getInstance().getGameMode() == GameMode.SpleefFight) {
            Block hitBlock = event.getHitBlock();
            if (hitBlock != null) {
                if(hitBlock.getType() == Material.SNOW_BLOCK)
                {
                    hitBlock.setType(Material.AIR);
                }
            } else {
                // Snowball hit an entity
            }
        }
    }
}
