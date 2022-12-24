package minecraftmania.minecraftmania.games;

import minecraftmania.minecraftmania.MinecraftMania;
import minecraftmania.minecraftmania.board.FastBoard;
import minecraftmania.minecraftmania.listener.spleef.*;
import org.bukkit.event.HandlerList;

public class Spleef implements Game
{
    private static Spleef instance;
    @Override
    public void updateBoard(FastBoard board) {

    }

    @Override
    public void onEnable() {
        instance = this;
        initializeListeners();
    }

    public void initializeListeners() {
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(new SpleefOnDeath(), MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(new SpleefOnBreak(), MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(new SpleefOnProjectileHit(), MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(new SpleefOnHit(), MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(new SpleefOnCraft(), MinecraftMania.getInstance());
        MinecraftMania.getInstance().getServer().getPluginManager().registerEvents(new SpleefOnCollison(), MinecraftMania.getInstance());
    }

    public void disableListeners() {
        HandlerList.unregisterAll(new SpleefOnDeath());
        HandlerList.unregisterAll(new SpleefOnBreak());
        HandlerList.unregisterAll(new SpleefOnProjectileHit());
        HandlerList.unregisterAll(new SpleefOnHit());
        HandlerList.unregisterAll(new SpleefOnCraft());
        HandlerList.unregisterAll(new SpleefOnCollison());
    }

    @Override
    public void onDisable() {

    }

    public static Spleef getInstance()
    {
        return instance;
    }
}
