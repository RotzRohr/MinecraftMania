package minecraftmania.minecraftmania.listener.spleef;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SpleefOnProjectileHit implements Listener
{
    @EventHandler
    public void OnProjectileHit(ProjectileHitEvent event)
    {
        Entity projectile = event.getEntity();
        if (projectile.getType() == EntityType.SNOWBALL) {
            Block hitBlock = event.getHitBlock();
            if (hitBlock != null) {
                if(hitBlock.getType() == Material.SNOW_BLOCK)
                {
                    hitBlock.breakNaturally();
                    SpleefOnBreak.getInstance().addBlock(hitBlock);
                }
            }
        }
    }
}
