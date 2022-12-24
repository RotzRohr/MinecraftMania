package minecraftmania.minecraftmania.games;

import minecraftmania.minecraftmania.board.FastBoard;

public class Knockout implements Game
{
    private static Knockout instance;
    @Override
    public void updateBoard(FastBoard board) {

    }

    @Override
    public void onEnable() {
        instance = this;
        initializeListeners();
    }

    @Override
    public void onDisable() {
        disableListeners();
    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void disableListeners() {

    }

    public static Knockout getInstance()
    {
        return instance;
    }
}
