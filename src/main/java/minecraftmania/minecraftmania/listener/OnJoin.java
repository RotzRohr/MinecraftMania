package minecraftmania.minecraftmania.listener;
import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class OnJoin implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        FastBoard board = new FastBoard(p);
        board.updateTitle(ChatColor.RED + "" + ChatColor.BOLD + "Minecraft Mania Event");
        MinecraftMania.getInstance().getBoards().put(p.getUniqueId(), board);
        if(MinecraftMania.getInstance().wasOnServer(p.getUniqueId()))
        {
            p.sendMessage("Welcome back!");
            MinecraftMania.getInstance().updatePlayer(p);
            MinecraftMania.getInstance().getEventPlayer(p.getUniqueId()).setOnline(false);
        }
        else
        {
            p.sendMessage("Welcome to the server for the first time!");
            p.sendMessage("use /help to see the commands");
            MinecraftMania.getInstance().newPlayer(p);

        }

        p.sendMessage("Welcome to the server! We are currently in test phase");

    }
}
