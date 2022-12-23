package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.event.EventPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener
{
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        FastBoard board = MinecraftMania.getInstance().getBoards().remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
        MinecraftMania.getInstance().getEventPlayer(player).setOnline(false);
    }
}
