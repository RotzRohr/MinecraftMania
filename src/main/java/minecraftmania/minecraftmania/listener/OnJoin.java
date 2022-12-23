package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.RED + "" + ChatColor.BOLD + "Minecraft Mania Event");
        MinecraftMania.getInstance().getBoards().put(player.getUniqueId(), board);
        MinecraftMania.getInstance().addPlayer(player);
    }
}
