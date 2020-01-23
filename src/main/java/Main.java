import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = defaultTerminalFactory.createTerminal();

        List<Zombie> zombieList = new ArrayList<>();

        int playerX = 10;
        int playerY = 10;
        final char player = 'X';

        // Add Zombie1
        Zombie zombie1 = createZombies(35, 10);
        zombieList.add(zombie1);
        printZombies(zombieList,terminal);

        // Add player
        terminal.setCursorPosition(playerX, playerY);
        terminal.putCharacter(player);
        terminal.setCursorVisible(false);
        terminal.flush();

        // Game loop
        boolean continueReadingInput = true;
        while (continueReadingInput) {

            KeyStroke keyStroke = null;
            int oldPlayerX = playerX;
            int oldPlayerY = playerY;

            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            Character c = keyStroke.getCharacter();
            KeyType type = keyStroke.getKeyType();
            System.out.println("Keystroke: " + type + "\n" + "Character: " + c);

            // Quit the game
            if (c == Character.valueOf('q')) {
                continueReadingInput = false;
                System.out.println("Quiting the game");
                terminal.close();
            }

            switch (type) {
                case ArrowUp:
                    playerY -= 1;
                    break;
                case ArrowDown:
                    playerY += 1;
                    break;
                case ArrowLeft:
                    playerX -= 1;
                    break;
                case ArrowRight:
                    playerX += 1;
                    break;

            }
            moveZombies(zombieList);
            printZombies(zombieList,terminal);

            //Check if player got killed (by moving to same position as zombie).
            if (isKilled(zombieList, playerX, playerY)) {
                break;
            }


            // Print player character
            terminal.setCursorPosition(oldPlayerX, oldPlayerY);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(playerX, playerY);
            terminal.putCharacter(player);
            terminal.flush();
        }


    }

    public static Zombie createZombies(int x, int y) {
        Zombie zombie = new Zombie(x,y);
        return zombie;
    }

    public static boolean isKilled(List<Zombie> zombies, int playerX, int playerY) {

        for (Zombie z : zombies) {
            if (z.getZombieX() == playerX && z.getZombieY() == playerY) {
                return true;
            }
        }
        return false;
    }

    public static void moveZombies (List<Zombie> zombies){
        for (Zombie z : zombies) {
            int zombieX = z.getZombieX();
            int newZombieX = zombieX + z.getZombieSpeed();
            z.setZombieX(newZombieX);

        }
    }
    public static void printZombies(List<Zombie> zombies, Terminal terminal) throws IOException {
        for (Zombie z : zombies) {
            terminal.setCursorPosition(z.getZombieX(), z.getZombieY());
            terminal.putCharacter(z.getCharacter());
            terminal.flush();
        }
    }
}
