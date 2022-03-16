import java.util.Scanner;

public class GameLoop {
    private GameBoard board;
    private SimpleStructureScoreController scoreController;
    private Agent agent;
    private boolean running = true;
    private boolean isPlayerTurn = false;


    public void run(){
        Scanner in = new Scanner(System.in);
        initialiseGameBoard(in);
        while (running) {
            if (isPlayerTurn) {
                String moveString = collectPlayerMove(in);//The human player makes a move
                makeMove(moveString);
            } else {
                computerPlay(); //The agent will play
            }
            board.printAvailable();
            if (handleWinners()){
                break;
            }
        }
    }

    private void initialiseGameBoard(Scanner in) {
        System.out.println("Welcome to the komoku board game! Please select your dimension (>= 5)");
        int dim = in.nextInt();
        System.out.println("How many in a row to win?");
        int wincount = in.nextInt();
        board = new GameBoard(dim, wincount);
        agent = new Agent(board);
        scoreController = new SimpleStructureScoreController(board);
    }

    private boolean handleWinners() {
        int winner = scoreController.getBoardScore(!isPlayerTurn);
        if (winner == SimpleStructureScoreController.WIN_SCORE){
            System.out.println('o' + " is the winner");
            return true;
        }
        if (winner == -SimpleStructureScoreController.WIN_SCORE){
            System.out.println('x' + " is the winner");
            return true;
        }
        if (board.getEmpties().size() == 0) {
            System.out.println("It's a tie");
            return true;
        }
        return false;
    }

    private String collectPlayerMove(Scanner in) {
        System.out.println("Place your brick (x, y)");
        String playerMove = "";
        playerMove = in.next();
        isPlayerTurn = false;
        return playerMove;
    }

    private GameCoordinate computerPlay()  {
        isPlayerTurn = true;
        return agent.move(); //The responability passed to agent, should make a competative move
    }

    private GameCoordinate makeMove(String playerMove) {
        String[] chopped = playerMove.split(",");
        int y = Integer.parseInt(chopped[0]);
        int x = Integer.parseInt(chopped[1]);
        GameCoordinate move = board.getCoordinate(x, y);
        System.out.println("Placed; x = " + move.getX() + " y = " + move.getY());

        return board.placeBrick(move, 'x');
    }
}