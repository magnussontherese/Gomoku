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
                playerPlay(in);
            } else {
                computerPlay();
            }
            board.print();
            board.evaluateWin();
        }
    }

    private void playerPlay(Scanner in) {
        System.out.println("Place your brick (x, y)");
        String playerMove = "";
        playerMove = in.next(); //We are always thinking that the player chooses a free space and correct values :)
        makeMove(playerMove);
        isPlayerTurn = false;
    }

    private void computerPlay()  {
        agent.evaluateAndMove(board); //The responability passed to agent, should make a competative move
        isPlayerTurn = true;
    }

    private void makeMove(String playerMove) {
        String[] chopped = playerMove.split(",");
        int x = Integer.parseInt(chopped[0]);
        int y = Integer.parseInt(chopped[1]);
        board.placeBrick(x, y, 'x');
    }
}
