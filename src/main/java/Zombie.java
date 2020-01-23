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
}
