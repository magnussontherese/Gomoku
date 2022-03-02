public class GameBoard {
    private final int dimentions;
    private GameCoordinate[][] content;

//Pleade observe, the board counts from 0...di in both dimentions

    public GameBoard(int d) {
        this.dimentions = d;
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


    public int getDimentions() {
        return dimentions;
    }

    public GameCoordinate getCoordinate(int x, int y){
        return content[x][y];
    }


    public GameCoordinate[][] getContent() {
        return content;
    }

}
