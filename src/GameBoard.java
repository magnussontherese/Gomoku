import java.util.HashSet;

public class GameBoard {
    private final int dimentions;
    private int wincount;
    private GameCoordinate[][] content;

//Please observe, the board counts from 0...di in both dimentions
//The wincount represent the number of owned coordinates in a row that is needed to win.


    public GameBoard(int d) {
        this.dimentions = d;
        this.wincount = d;
        content = new GameCoordinate[d][d];
        initialise();
    }

    public GameBoard(int d, int wincount) {
        this.dimentions = d;
        this.wincount = wincount;
        content = new GameCoordinate[d][d];
        initialise();
    }

    public GameBoard() {
        this(5);
    }

    private void initialise() {
        for (int i = 0; i < dimentions; i++) {
            for (int j = 0; j < dimentions; j++) {
                content[i][j] = new GameCoordinate(i, j);
            }
        }
     }

    public void print() {
        System.out.println("GameBoard: ");
        for (int i = 0; i < dimentions; i++) {
            System.out.print("|");
            for (int j = 0; j < dimentions; j++) {
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

    public int getWincount() {
        return wincount;
    }

    public int getDimentions() {
        return dimentions;
    }

    public GameCoordinate getCoordinate(int x, int y){
        return content[x][y];
    }


    public GameCoordinate[][] getContent() {
        return content;
    }

    public HashSet<GameCoordinate> getEmpties() {
        HashSet<GameCoordinate> empty = new HashSet<>();
        for (int i = 0; i < dimentions; i++) {
            for (int j = 0; i < dimentions; j++) {
                if (!content[i][j].isOccupied()) {
                    empty.add(content[i][j]);
                }
            }
        }
        return empty;
    }

}
