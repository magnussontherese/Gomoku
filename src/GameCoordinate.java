public class GameCoordinate {
    private  int x;
    private  int y;
    private boolean isOccupied = false;
    private char owner;

    public GameCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.owner = ' ';
    }

    public GameCoordinate(int x, int y, char owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.isOccupied = true;
    }

    @Override
    public String toString() {
        return owner + "";
    }

    public void setOwner(char owner) {
        this.owner = owner;
        this.isOccupied = true;
    }

    public void removeOwner() {
        this.owner = ' ';
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getOwner() {
        return owner;
    }
}
