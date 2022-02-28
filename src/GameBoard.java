public class GameBoard {
    private int dimentions;
    private char[][] content;

    public GameBoard(int d) {
        this.dimentions = d;
        content = new char [d][d];
        initialise();
    }

    private void initialise() {
        for (int i = 0; i < dimentions; i++) {
            for (int j = 0; j < dimentions; j++) {
                content[i][j] = ' ';
            }
        }
     }

    public GameBoard() {
        this(5);
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

    public void placeBrick(int x, int y, char playerBrick) {
        content[x][y] = playerBrick;
    }

    public void evaluate() {
    }
}
