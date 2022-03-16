import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    public final int dimension;
    private final int wincount;
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
        ownedByComputer = new HashSet<>();
        ownedByComputer.addAll(other.getOwnedByComputer());
        ownedByHuman = new HashSet<>();
        ownedByHuman.addAll(other.getOwnedByHuman());
        available = new HashSet<>();
        available.addAll(other.getFree());
        initialise();
        initialiseFromOther(other);
    }

    private void initialiseFromOther(GameBoard other) {
        for (GameCoordinate gc : ownedByHuman) {
            placeBrick(gc, 'x');
        }
        for (GameCoordinate gc: ownedByComputer) {
            placeBrick(gc, 'o');
        }
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

    public void printAvailable() {
        HashSet<GameCoordinate> allRelevant = getAllToTry();
        for (GameCoordinate gc: allRelevant) {
            gc.setOwner('.');
        }
        print();
        for (GameCoordinate gc: allRelevant) {
            gc.removeOwner();
        }
    }

    public GameCoordinate placeBrick(GameCoordinate gameCoordinate, char playerBrick) {
        content[gameCoordinate.getX()][gameCoordinate.getY()] = gameCoordinate;
        gameCoordinate.setOwner(playerBrick);
        if(playerBrick == 'o'){
            ownedByComputer.add(gameCoordinate);
        } else if(playerBrick == 'x') {
            ownedByHuman.add(gameCoordinate);
        }
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
        return getRelevant();
    }

    public HashSet<GameCoordinate> getRelevant() {
        HashSet<GameCoordinate> relevant = new HashSet<>();
        HashSet <GameCoordinate> occupied = new HashSet<>();
        occupied.addAll(ownedByHuman);
        occupied.addAll(ownedByComputer);
        for (GameCoordinate gc : occupied) {
            relevant.addAll(getRelevantFromGameCoordinate(gc));
        }
        return relevant;
    }

    private Collection<GameCoordinate> getRelevantFromGameCoordinate(GameCoordinate gc) {
        HashSet <GameCoordinate> toReturn = new HashSet<>();
        GameCoordinate upper = getCoordinate(gc.getX() , gc.getY() + 1);
        GameCoordinate lower = getCoordinate(gc.getX() , gc.getY() - 1);

        GameCoordinate left = getCoordinate(gc.getX() +1 , gc.getY());
        GameCoordinate right = getCoordinate(gc.getX()-1 , gc.getY());

        GameCoordinate leftTop = getCoordinate(gc.getX()-1 , gc.getY()-1);
        GameCoordinate rightTop = getCoordinate(gc.getX()+1 , gc.getY()-1);

        GameCoordinate leftBottom = getCoordinate(gc.getX()-1 , gc.getY()+1);
        GameCoordinate rightBottom = getCoordinate(gc.getX()+1 , gc.getY()+1);

        if (left != null && !left.isOccupied()) toReturn.add(left);
        if (right != null && !right.isOccupied()) toReturn.add(right);
        if (upper != null && !upper.isOccupied()) toReturn.add(upper);
        if (lower != null && !lower.isOccupied()) toReturn.add(lower);
        if (leftTop != null && !leftTop.isOccupied()) toReturn.add(leftTop);
        if (rightTop != null && !rightTop.isOccupied()) toReturn.add(rightTop);
        if (leftBottom != null && !leftBottom.isOccupied()) toReturn.add(leftBottom);
        if (rightBottom != null && !rightBottom.isOccupied()) toReturn.add(rightBottom);

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
                if (!content[i][j].isOccupied())
                    empty.add(content[i][j]);
            }
        }
        return empty;
    }
}
