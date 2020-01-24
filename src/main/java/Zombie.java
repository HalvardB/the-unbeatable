import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class Zombie {
    private int zombieX;
    private int zombieY;
    private int zombieSpeed = 0;
    private char character = '#';

    public Zombie(int zombieX, int zombieY) {
        this.zombieX = zombieX;
        this.zombieY = zombieY;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getZombieX(){
        return zombieX;
    }

    public void setZombieX(int newZombieX){
        this.zombieX = newZombieX;
    }

    public void setZombieY(int newZombieY){
        this.zombieY = newZombieY;
    }

    public int getZombieY(){
        return zombieY;
    }

    public int getZombieSpeed() {
        return zombieSpeed;
    }

    public void setZombieSpeed(int zombieSpeed) {
        this.zombieSpeed = zombieSpeed;
    }

    public static Zombie createZombies(int x, int y) {
        Zombie zombie = new Zombie(x,y);
        return zombie;
    }

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
