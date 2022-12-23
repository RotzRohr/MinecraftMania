package minecraftmania.minecraftmania.commands;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            FastBoard board = new FastBoard(player);

            board.updateTitle(ChatColor.RED + "" + ChatColor.BOLD + "Minecraft Mania Event");
            MinecraftMania.getInstance().getBoards().put(player.getUniqueId(), board);
            MinecraftMania.getInstance().addPlayer(player);
        }
        return false;
    }
}
