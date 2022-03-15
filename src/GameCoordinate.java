import java.util.Objects;

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
        return  x + "," + y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCoordinate that = (GameCoordinate) o;
        return x == that.x && y == that.y && isOccupied == that.isOccupied && owner == that.owner;
    }

    @Override
    public int hashCode() {
        return x*3 + y*5;
    }
}

