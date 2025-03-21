package minecraftmania.minecraftmania.games;
import minecraftmania.minecraftmania.board.FastBoard;

public interface Game
{
    public void updateBoard(FastBoard board);
    public void onEnable();
    public void onDisable();
}
