public class Zombie {
    private int zombieX;
    private int zombieY;
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

    public int getZombieY(){
        return zombieY;
    }

}
