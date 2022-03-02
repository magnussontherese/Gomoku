import java.util.Scanner;

public class GameLoop {
    private GameBoard board;
    private Agent agent = new Agent();
    private boolean running = true;
    private boolean isPlayerTurn = true;


    public void run(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the komoku board game! Please select your dimension (>= 5)");
        int dim = in.nextInt();
        board = new GameBoard(dim);
        while (running) {
            if (isPlayerTurn) {
                String moveString = collectPlayerMove(in);
                GameCoordinate move = makeMove(moveString);
                    if (board.checkWin(move,'x')){
                    System.out.println("x is the winner");
                    board.print();
                    break;
                }
            } else {
                GameCoordinate move = computerPlay();
                if (board.checkWin(move, 'o')){
                    System.out.println("o is the winner");
                    board.print();
                    break;
                }
            }
            board.print();
        }
    }

    private String collectPlayerMove(Scanner in) {
        System.out.println("Place your brick (x, y)");
        String playerMove = "";
        playerMove = in.next(); //We are always thinking that the human player chooses a free space and correct values :)
        isPlayerTurn = false;
        return playerMove;
    }

    private GameCoordinate computerPlay()  {
        isPlayerTurn = true;
        return agent.evaluateAndMove(board); //The responability passed to agent, should make a competative move
    }

    private GameCoordinate makeMove(String playerMove) {
        String[] chopped = playerMove.split(",");
        int x = Integer.parseInt(chopped[0]);
        int y = Integer.parseInt(chopped[1]);
        GameCoordinate move = board.getCoordinate(x, y);
        return board.placeBrick(move, 'x');
    }
}
