import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GameLoop {
    private GameBoard board;
    private WinController winController;
    private Agent agent = new Agent();
    private boolean running = true;
    private boolean isPlayerTurn = true;


    public void run(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the komoku board game! Please select your dimension (>= 5)");
        int dim = in.nextInt();
        System.out.println("How many in a row to win?");
        int wincount = in.nextInt();
        board = new GameBoard(dim, wincount);
        winController = new WinController(board);
        while (running && board.getEmpties().size() != 0) {
            GameCoordinate nextMove;
            if (isPlayerTurn) {
                String moveString = collectPlayerMove(in);//The human player makes a move
                nextMove = makeMove(moveString);
            } else {
                long startTime = System.currentTimeMillis();
                nextMove = computerPlay(); //The agent will play
                long endTime = System.currentTimeMillis();
                System.out.println("Finding best move took: " + (endTime - startTime) + " milliseconds");
            }
            board.print();
            char winner = winController.checkWin(nextMove);
            if(winner != Character.MIN_VALUE) {
                System.out.println(winner + " is the winner");
                break;
            }
        }
        if (board.getEmpties().size() == 0) {
            System.out.println("It's a tie");
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
        return agent.move(board); //The responability passed to agent, should make a competative move
    }

    private GameCoordinate makeMove(String playerMove) {
        String[] chopped = playerMove.split(",");
        int x = Integer.parseInt(chopped[0]);
        int y = Integer.parseInt(chopped[1]);
        GameCoordinate move = board.getCoordinate(x, y);
        return board.placeBrick(move, 'x');
    }
}