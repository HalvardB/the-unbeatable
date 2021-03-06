import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class Zombie {
    private int zombieX;
    private int zombieY;

    public Zombie(int zombieX, int zombieY) {
        this.zombieX = zombieX;
        this.zombieY = zombieY;
    }

    public char getCharacter() { return '\u03EA'; }

    public int getZombieX(){ return zombieX; }

    public void setZombieX(int newZombieX){ this.zombieX = newZombieX; }

    public void setZombieY(int newZombieY){ this.zombieY = newZombieY; }

    public int getZombieY(){ return zombieY; }

    public static void moveAndPrintZombies(List<Zombie> zombies, Terminal terminal, int playerX, int playerY) throws IOException {
        for (Zombie z : zombies) {
            // Remove zombie tail
            int oldZombieX = z.getZombieX();
            int oldZombieY = z.getZombieY();

            // Find difference between player and zombie
            int differenceX = playerX - oldZombieX;
            int differenceY = playerY - oldZombieY;

            // Adding new zombie position
            int zombieX = oldZombieX;
            int zombieY = oldZombieY;

            // Move X axis
            if(differenceX != 0)
                if (differenceX < 0) zombieX--;
                else zombieX++;

            if (differenceY != 0)
                if (differenceY < 0) zombieY--;
                else zombieY++;

            // Set Zombie X
            z.setZombieX(zombieX);
            z.setZombieY(zombieY);
            terminal.setCursorPosition(zombieX, zombieY);
            terminal.putCharacter(z.getCharacter());
            terminal.flush();

            // Remove zombie tail
            terminal.setCursorPosition(oldZombieX, oldZombieY);
            terminal.putCharacter(' ');
            terminal.flush();
        }
    }
}
