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

        Game game = new Game(terminal);
        game.startGame();

        // Print welcome message
        printMessage(terminal, "WELCOME to the UNBEATABLE game of LIFE!", 0);
        printMessage(terminal, "Run from the zombie and get to safety (block)!", 1);
        printMessage(terminal, "You are X",2 );

        int firstClick = 0;

        // Game loop
        boolean continueReadingInput = true;
        while (continueReadingInput) {

            KeyStroke keyStroke = null;
            int oldPlayerX = game.playerX;
            int oldPlayerY = game.playerY;

            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            // Remove welcome message
            if(firstClick == 0){
                printMessage(terminal, "                                       ", 0);
                printMessage(terminal, "                                              ", 1);
                printMessage(terminal, "         ",2 );
            }

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
                    game.playerY -= 1;
                    break;
                case ArrowDown:
                    game.playerY += 1;
                    break;
                case ArrowLeft:
                    game.playerX -= 1;
                    break;
                case ArrowRight:
                    game.playerX += 1;
                    break;

            }
            game.moveAndPrintZombies();

            //Check if player got killed (by moving to same position as zombie).
            if (isKilled(game.zombieList, game.playerX, game.playerY)) {
                printMessage(terminal, "GAME OVER!", 0);
                printMessage(terminal, "CONTINUE PLAYING? (y/n)", 1);
                terminal.setCursorPosition(oldPlayerX, oldPlayerY);
                terminal.putCharacter(' ');
                terminal.flush();

                terminal.setCursorPosition(game.playerX, game.playerY);
                terminal.putCharacter('#');
                terminal.flush();

                boolean quit = quitGame(terminal);
                if (!quit) {
                    printMessage(terminal, "           ", 0);
                    printMessage(terminal, "                          ", 1);
                    game.restart();
                }
            }

            if (hasWon(game.playerX, game.playerY)) {
                // System.out.println("hasWon");
                printMessage(terminal, "YOU WIN!", 0);
                printMessage(terminal, "CONTINUE PLAYING? (y/n)", 1);

                boolean quit = quitGame(terminal);
                if (!quit) {
                    printMessage(terminal, "           ", 0);
                    printMessage(terminal, "           ", 1);
                    game.startGame();
                }
            }

            // Print player character
            terminal.setCursorPosition(oldPlayerX, oldPlayerY);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(game.playerX, game.playerY);
            terminal.putCharacter(game.player);
            terminal.flush();
        }
    }

    private static boolean quitGame(Terminal terminal) throws InterruptedException, IOException {
        KeyStroke keyStroke = null;
        while (true) {
            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            Character c = keyStroke.getCharacter();

            if (c == Character.valueOf('n')) {
                System.out.println("Quiting the game");
                terminal.close();
                return true;
            }
            if (c == Character.valueOf('y')) {
                return false;
            }

        }
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

    public static void printMessage(Terminal terminal, String message, int k) throws IOException {

        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            terminal.setCursorPosition(i + 15, 12 + k);
            terminal.putCharacter(chars[i]);
        }
        terminal.flush();
    }
}
