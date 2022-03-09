import java.util.HashSet;

public class GameBoard {
    public final int dimension;
    private int wincount;
    private GameCoordinate[][] content;

//Please observe, the board counts from 0...di in both dimentions
//The wincount represent the number of owned coordinates in a row that is needed to win.


    public GameBoard(int d) {
        this.dimension = d;
        this.wincount = d;
        content = new GameCoordinate[d][d];
        initialise();
    }

    public GameBoard(int d, int wincount) {
        this.dimension = d;
        this.wincount = wincount;
        content = new GameCoordinate[d][d];
        initialise();
    }

    public GameBoard() {
        this(5);
    }

    private void initialise() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                content[i][j] = new GameCoordinate(i, j);
            }
        }
     }

    public void print() {
        System.out.println("GameBoard: ");
        System.out.println("  0 1 2 3 4 (X)");
        for (int i = 0; i < dimension; i++) {
            System.out.print(i +"|");
            for (int j = 0; j < dimension; j++) {
                System.out.print(content[i][j]);
                System.out.print("|");
            }
            System.out.println("");

        }
    }

    public GameCoordinate placeBrick(GameCoordinate gameCoordinate, char playerBrick) {
        content[gameCoordinate.getX()][gameCoordinate.getY()] = gameCoordinate;
        gameCoordinate.setOwner(playerBrick);
        return gameCoordinate;
    }

    public void undoMove(GameCoordinate coordinate) {
        coordinate.removeOwner();
    }

    public int getWincount() {
        return wincount;
    }

    public int getDimension() {
        return dimension;
    }

    public GameCoordinate getCoordinate(int x, int y){
        return content[x][y];
    }


    public GameCoordinate[][] getContent() {
        return content;
    }

    public HashSet<GameCoordinate> getEmpties() {
        HashSet<GameCoordinate> empty = new HashSet<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (!content[i][j].isOccupied()) {
                    empty.add(content[i][j]);
                }
            }
        }
        return empty;
    }



}
