package minecraftmania.minecraftmania.listener.spleef;
import minecraftmania.minecraftmania.GameManager;
import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.GameMode;
import minecraftmania.minecraftmania.games.Spleef;
import minecraftmania.minecraftmania.handler.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SpleefOnChat implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        if(GameManager.getInstance().getCurrentGameMode() == GameMode.Spleef)
        {
            if(!Spleef.getInstance().isChatAllowed())
            {
                event.setCancelled(true);
            }
        }
        Player player = event.getPlayer();
        String senderMessage = event.getMessage();
        ChatColor senderTeamColour = TeamHandler.getInstance().getTeamChatColor(MinecraftMania.getInstance().getEventPlayer(player.getUniqueId()));
        if (senderTeamColour != ChatColor.WHITE)
        {
            String color = senderTeamColour.name();
            color = color.substring(0, 1).toUpperCase() + color.substring(1).toLowerCase();
            event.setFormat(senderTeamColour + "[" + color + "] " + player.getName() + ": " + ChatColor.WHITE + senderMessage);
        }
        else
        {
            event.setFormat(ChatColor.WHITE + player.getName() + ": " + ChatColor.WHITE + senderMessage);
        }
    }
}
