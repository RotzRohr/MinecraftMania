package minecraftmania.minecraftmania.listener;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.games.Game;
import minecraftmania.minecraftmania.games.GameMode;
import minecraftmania.minecraftmania.handler.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        if(MinecraftMania.getInstance().getGameMode() != GameMode.Spleef)
        {
            Player player = event.getPlayer();
            String senderMessage = event.getMessage();
            ChatColor senderTeamColour = TeamHandler.getInstance().getChatColor(MinecraftMania.getInstance().getEventPlayer(player));
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
        else
        {
            event.setCancelled(true);
        }
    }
}
