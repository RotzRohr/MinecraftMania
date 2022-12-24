package minecraftmania.minecraftmania.games;
import minecraftmania.minecraftmania.board.FastBoard;

public interface Game
{
    void updateBoard(FastBoard board);
    void onEnable();
    void onDisable();
    void initializeListeners();
    void disableListeners();
}
