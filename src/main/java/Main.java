import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = defaultTerminalFactory.createTerminal();

        Game game = new Game(terminal);
        game.startGame();

        // Print welcome message
        printMessage(terminal, "WELCOME to the UNBEATABLE game of LIFE!", -1);
        printMessage(terminal, "Are you able to survive the hungry zombies?", 1);
        printMessage(terminal, "Instructions to survive:", 3);
        printMessage(terminal, "-Avoid the zombies using your left, right, up and down arrows", 4);
        printMessage(terminal, "-You are " + '\u03D8' +", and the zombie(s) are " + '\u03EA', 5);
        printMessage(terminal, "-Safe point is the white square", 6);
        printMessage(terminal, "-Press 'q' to quit", 7);
        printMessage(terminal, "How many levels can you reach before you become zombie food?", 9);

        // Game loop
        boolean continueReadingInput = true;
        while (continueReadingInput) {

            KeyStroke keyStroke;
            int oldPlayerX = game.playerX;
            int oldPlayerY = game.playerY;

            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            // Remove welcome message
            printMessage(terminal, "                                       ", -1);
            printMessage(terminal, "                                           ", 1);
            printMessage(terminal, "                        ", 3);
            printMessage(terminal, "                                                             ", 4);
            printMessage(terminal, "                                                     ", 5);
            printMessage(terminal, "                               ", 6);
            printMessage(terminal, "                       ", 7);
            printMessage(terminal, "                                                            ", 9);

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

            if (game.playerX == 80) {
                game.playerX = 0;
            }
            if (game.playerX < 0) {
                game.playerX = 79;
            }
            if (game.playerY == 24) {
                game.playerY = 0;
            }
            if (game.playerY < 0) {
                game.playerY = 23;
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
                terminal.putCharacter('\u03EA');
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
                    printMessage(terminal, "                    ", 1);
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
        KeyStroke keyStroke;
        while (true) {
            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            Character c = keyStroke.getCharacter();

            if (c == Character.valueOf('n')) {
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
        int winnerX = 70;
        int winnerY = 13;

        return winnerX == playerX && winnerY == playerY;
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
