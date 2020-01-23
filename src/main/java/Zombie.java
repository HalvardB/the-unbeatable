public class Zombie {
    private int x;
    private int y;
    private char character = '#';

    public Zombie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
