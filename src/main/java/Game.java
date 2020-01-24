import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Terminal terminal;
    int playerX = 10;
    int playerY = 10;
    final char player = 'X';
    int winnerX = 0;
    int winnerY = 0;
    List<Zombie> zombieList = new ArrayList<>();
    int zombieCount = 0;

    public Game(Terminal terminal) {
        this.terminal = terminal;
    }

    public void startGame() throws IOException {

        // Remove old zombies
        if(zombieList.size() > 0){
            for (Zombie z : zombieList) {
                terminal.setCursorPosition(z.getZombieX(), z.getZombieY());
                terminal.putCharacter(' ');
                terminal.flush();
            }
        }

        // Moving player back to start
        this.playerY = 10;
        this.playerX = 10;

        Main.printMessage(terminal, "           ", 0);
        Main.printMessage(terminal, "                          ", 1);

        zombieList = new ArrayList<>();
        zombieCount++;

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
        for (int i = 0; i < zombieCount; i++){
            Zombie zombie = new Zombie((int)(Math.random() * 65), (int)(Math.random() * 20));
            zombieList.add(zombie);
            zombie.moveAndPrintZombies(this.zombieList, this.terminal, this.playerX, this.playerY);
        }
    }

    public void moveAndPrintZombies() throws IOException {
        for (Zombie z : zombieList) {
            z.moveAndPrintZombies(this.zombieList, this.terminal, this.playerX, this.playerY);
        }
    }

    public void restart() throws IOException {


        // Remove old zombies from game
        if(zombieList.size() > 0){
            for (Zombie z : zombieList) {
                terminal.setCursorPosition(z.getZombieX(), z.getZombieY());
                terminal.putCharacter(' ');
                terminal.flush();
            }
        }

        // Removing zombies from arraylist
        zombieList.clear();
        zombieCount = 0;

        // Moving player back to start
        this.playerY = 10;
        this.playerX = 10;

        Main.printMessage(terminal, "           ", 0);
        Main.printMessage(terminal, "                          ", 1);

        zombieList = new ArrayList<>();
        zombieCount++;

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
        for (int i = 0; i < zombieCount; i++){
            Zombie zombie = new Zombie((int)(Math.random() * 65), (int)(Math.random() * 20));
            zombieList.add(zombie);
            zombie.moveAndPrintZombies(this.zombieList, this.terminal, this.playerX, this.playerY);
        }
    }
}
