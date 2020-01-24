import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Terminal terminal;
    int playerX = 1;
    int playerY = 1;
    final char player = 'X';
    int winnerX = 0;
    int winnerY = 0;
    List<Zombie> zombieList = new ArrayList<>();

    public Game(Terminal terminal) {
        this.terminal = terminal;
    }

    public void startGame() throws IOException {

        // Add winner position
        terminal.setCursorPosition(winnerX,winnerY);
        terminal.putCharacter('\u2588');
        terminal.flush();

        // Add player
        terminal.setCursorPosition(this.playerX, this.playerY);
        terminal.putCharacter(this.player);
        terminal.setCursorVisible(false);
        terminal.flush();

        // Add Zombie1
        Zombie zombie1 = new Zombie(35, 10);
        zombieList.add(zombie1);
        this.moveAndPrintZombies();
    }

    public void moveAndPrintZombies() throws IOException {
        for (Zombie z : zombieList) {
            Zombie.moveAndPrintZombies(this.zombieList, this.terminal, this.playerX, this.playerY);
        }
    }




}
