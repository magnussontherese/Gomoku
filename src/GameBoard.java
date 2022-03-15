import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    public final int dimension;
    private int wincount;
    private GameCoordinate[][] content;
    private HashSet<GameCoordinate> ownedByComputer;
    private HashSet<GameCoordinate> ownedByHuman;
    private HashSet<GameCoordinate> available;


    public GameBoard(int d) {
        this.dimension = d;
        this.wincount = d;
        content = new GameCoordinate[d][d];
        ownedByComputer = new HashSet<>();
        ownedByHuman = new HashSet<>();
        available = new HashSet<>();
        initialise();
    }
    public GameBoard(GameBoard other) {
        this.dimension = other.getDimension();
        this.wincount = other.wincount;
        content = new GameCoordinate[dimension][dimension];
        ownedByComputer = new HashSet<>(other.ownedByComputer);
        ownedByHuman = new HashSet<>(other.ownedByHuman);
        available = new HashSet<>();
        initialise();
    }

    public GameBoard(int d, int wincount) {
        this.dimension = d;
        this.wincount = wincount;
        content = new GameCoordinate[d][d];
        ownedByComputer = new HashSet<>();
        ownedByHuman = new HashSet<>();
        available = new HashSet<>();
        initialise();
    }

    public GameBoard() {
        this(5);
    }

    private void initialise() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                content[i][j] = new GameCoordinate(i, j);
                available.add(content[i][j]);
            }
        }
     }

    public void print() {
        System.out.println("GameBoard: ");
        System.out.print("  ");
        for (int i = 0; i < dimension; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");
        for (int i = 0; i < dimension; i++) {
            System.out.print(i +"|");
            for (int j = 0; j < dimension; j++) {
                System.out.print(content[i][j].getOwner());
                System.out.print("|");
            }
            System.out.println("");
        }
    }

    public GameCoordinate placeBrick(GameCoordinate gameCoordinate, char playerBrick) {
        content[gameCoordinate.getX()][gameCoordinate.getY()] = gameCoordinate;
        gameCoordinate.setOwner(playerBrick);
        if(playerBrick == 'o') ownedByComputer.add(gameCoordinate);
        if (playerBrick == 'x') ownedByHuman.add(gameCoordinate);
        available.remove(gameCoordinate);
        return gameCoordinate;
    }

    public void undoMove(GameCoordinate coordinate) {
        available.add(coordinate);
        if (coordinate.getOwner() == 'o')
            ownedByComputer.remove(coordinate);
        if (coordinate.getOwner() == 'x')
            ownedByHuman.remove(coordinate);
        coordinate.removeOwner();

    }

    public int getWincount() {
        return wincount;
    }

    public int getDimension() {
        return dimension;
    }

    public GameCoordinate getCoordinate(int x, int y){
        if (x < 0 || x > dimension -1)
            return null;
        if (y < 0 || y > dimension -1)
            return null;
        return content[x][y];
    }


    public GameCoordinate[][] getContent() {
        return content;
    }

    public HashSet<GameCoordinate> getEmpties() {
        return available;
    }

    public HashSet<GameCoordinate> getAllOwnedBy(char player) {
        if (player == 'o')
            return ownedByComputer;
        return  ownedByHuman;
    }

    public HashSet<GameCoordinate> getAllToTry() {
        if (wincount == dimension) {
            return getFree();
        } else {
            return getRelevant();
        }

    }

    public HashSet<GameCoordinate> getRelevant() {
        HashSet<GameCoordinate> relevant = new HashSet<>();
        HashSet <GameCoordinate> occupied = new HashSet<>(ownedByHuman);
        occupied.addAll(ownedByComputer);
        for (GameCoordinate gc : occupied) {
            relevant.addAll(getRelevantFromGameCoordinate(gc));
        }
        return relevant;
    }

    private Collection<GameCoordinate> getRelevantFromGameCoordinate(GameCoordinate gc) {
        HashSet <GameCoordinate> toReturn = new HashSet<>();

        if (available.contains(new GameCoordinate(gc.getX() , gc.getY() + 1))) toReturn.add(content[gc.getX()] [gc.getY() +1]);
        if (available.contains(new GameCoordinate(gc.getX() , gc.getY() - 1))) toReturn.add(content[gc.getX()] [gc.getY() -1]);

        if (available.contains(new GameCoordinate(gc.getX() -1 , gc.getY() - 1)))toReturn.add(content[gc.getX() -1] [gc.getY() -1]);
        if (available.contains(new GameCoordinate(gc.getX() -1 , gc.getY() + 1))) toReturn.add(content[gc.getX() -1] [gc.getY() +1]);
        if (available.contains(new GameCoordinate(gc.getX() -1 , gc.getY())))toReturn.add(content[gc.getX() -1] [gc.getY()]);

        if (available.contains(new GameCoordinate(gc.getX() +1 , gc.getY() - 1)))toReturn.add(content[gc.getX() +1] [gc.getY() -1]);
        if (available.contains(new GameCoordinate(gc.getX() +1 , gc.getY() + 1)))toReturn.add(content[gc.getX() +1] [gc.getY() +1]);
        if (available.contains(new GameCoordinate(gc.getX() +1 , gc.getY())))toReturn.add(content[gc.getX() +1] [gc.getY()]);

        return toReturn;
    }

    public HashSet<GameCoordinate> getOwnedByComputer() {
        return ownedByComputer ;
    }

    public HashSet<GameCoordinate> getOwnedByHuman() {
        return ownedByHuman;
    }



    public HashSet<GameCoordinate> getFree() {
        HashSet<GameCoordinate> empty = new HashSet<>();
        for (int i = 0 ; i < dimension; i ++){
            for (int j = 0; j < dimension; j++) {
                if (!content[i][j].isOccupied()) empty.add(content[i][j]);
            }
        }
        return empty;
    }
}
