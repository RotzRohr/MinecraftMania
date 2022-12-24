package minecraftmania.minecraftmania.listener;
import minecraftmania.minecraftmania.MinecraftMania;
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
        if(MinecraftMania.getInstance().wasOnServer(p.getUniqueId()))
        {
            p.sendMessage("Welcome back!");
            MinecraftMania.getInstance().updatePlayer(p);
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
