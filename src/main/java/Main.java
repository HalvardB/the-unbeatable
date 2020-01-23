import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = defaultTerminalFactory.createTerminal();

        int x = 10;
        int y = 10;
        final char player = 'X';

        terminal.setCursorPosition(x,y);
        terminal.putCharacter(player);
        terminal.setCursorVisible(false);
        terminal.flush();


        // Game loop
        boolean continueReadingInput = true;
        while(continueReadingInput){

            KeyStroke keyStroke = null;
            int oldX = x;
            int oldY = y;



            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            Character c = keyStroke.getCharacter();
            KeyType type = keyStroke.getKeyType();
            System.out.println("Keystroke: " + type + "\n" + "Character: " + c);

            // Quit the game
            if(c == Character.valueOf('q')){
                continueReadingInput = false;
                System.out.println("Quiting the game");
                terminal.close();
            }


            switch (type){
                case ArrowUp:
                    y -= 1;
                    break;
                case ArrowDown:
                    y += 1;
                    break;
                case ArrowLeft:
                    x -= 1;
                    break;
                case ArrowRight:
                    x += 1;
                    break;

            }
            terminal.setCursorPosition(oldX, oldY);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(x,y);
            terminal.putCharacter(player);
            terminal.flush();
        }
    }

}
