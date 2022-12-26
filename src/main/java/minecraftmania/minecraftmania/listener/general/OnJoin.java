package minecraftmania.minecraftmania.listener.general;
import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.handler.TeamHandler;
import minecraftmania.minecraftmania.team.TeamColor;
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
            MinecraftMania.getInstance().getEventPlayer(p.getUniqueId()).setOnline(true);
        }
        else if(MinecraftMania.getInstance().containsPlayer(p.getUniqueId()))
        {
            MinecraftMania.data d = null;
            for(MinecraftMania.data da : MinecraftMania.getInstance().getDatalist())
            {
                if(da.getUUID().equals(p.getUniqueId()))
                {
                    d = da;
                }
            }
            MinecraftMania.getInstance().newPlayer(p);
            MinecraftMania.getInstance().getEventPlayer(p.getUniqueId()).setPoints(d.getPoints());
            MinecraftMania.getInstance().getEventPlayer(p.getUniqueId()).setPremissionLevel(d.getPermissionLevel());
            TeamHandler.getInstance().addPlayerToTeam(MinecraftMania.getInstance().getEventPlayer(p.getUniqueId()), TeamColor.valueOf(d.getTeam()));
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
