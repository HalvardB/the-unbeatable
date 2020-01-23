import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = defaultTerminalFactory.createTerminal();

        List<Zombie> zombieList = new ArrayList<>();

        int playerX = 1;
        int playerY = 1;
        final char player = 'X';

        // Add Zombie1
        Zombie zombie1 = createZombies(35, 10);
        zombieList.add(zombie1);
        moveAndPrintZombies(zombieList,terminal, playerX, playerY);

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
            moveAndPrintZombies(zombieList,terminal, playerX, playerY);

            //Check if player got killed (by moving to same position as zombie).
            if (isKilled(zombieList, playerX, playerY)) {
                System.out.println("isKilled");
                terminal.setCursorPosition(oldPlayerX, oldPlayerY);
                terminal.putCharacter(' ');
                terminal.flush();
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter('#');
                terminal.flush();
                break;
            }

            if (hasWon(playerX, playerY)) {
                System.out.println("hasWon");
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

    public static boolean hasWon(int playerX, int playerY) {
        int winnerX = 0;
        int winnerY = 0;

            if (winnerX == playerX && winnerY == playerY) {
                return true;
            }
        return false;
    }

    /*
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

     */

    public static void moveAndPrintZombies(List<Zombie> zombies, Terminal terminal, int playerX, int playerY) throws IOException {
        for (Zombie z : zombies) {
            // Remove zombie tail
            int oldZombieX = z.getZombieX();
            int oldZombieY = z.getZombieY();


            // Find difference between player and zombie
            int differenceX = playerX - oldZombieX;
            int differenceY = playerY - oldZombieY;

            // Adding new zombie position
            int zombieX = oldZombieX + z.getZombieSpeed();
            int zombieY = oldZombieY + z.getZombieSpeed();

            // Move X axis
            if(differenceX != 0){
                if(differenceX < 0){
                    // Redusere X
                    zombieX--;

                } else {
                    // Øke X
                    zombieX++;
                }
            }

            // Move Y axis
            if(differenceY != 0){
                if(differenceY < 0){
                    // Redusere X
                    zombieY--;

                } else {
                    // Øke X
                    zombieY++;
                }
            }


            // Set Zombie X
            z.setZombieX(zombieX);
            z.setZombieY(zombieY);
            terminal.setCursorPosition(zombieX, zombieY);
           // System.out.println("zombieX, zombieY: " + zombieX + " , " + zombieY); check  zombie position
            terminal.putCharacter(z.getCharacter());

            // Remove zombie tail
            terminal.setCursorPosition(oldZombieX, oldZombieY);
            terminal.putCharacter(' ');

            terminal.flush();
        }
    }
}
