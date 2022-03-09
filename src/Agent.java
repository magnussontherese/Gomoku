import java.util.HashSet;
import java.util.Random;

public class Agent {
    private MiniMaxAssistant miniMaxAssistant;
    private char myBrick = 'o';
    private int depthForAgent = 5;
    private boolean isFirstGo = true;

    public Agent(GameBoard board) {
        this.miniMaxAssistant = new MiniMaxAssistant(board);
    }

    public GameCoordinate move(GameBoard board) {
        System.out.println("ComputerPlaying...");
        GameCoordinate bestMove = evaluateWithMiniMaxGetBestMove(board);
        return board.placeBrick(bestMove, myBrick);
    }

    private GameCoordinate evaluateWithMiniMaxGetBestMove(GameBoard board) {
        int currentMoveScore = 0;
        int bestMoveScore = Integer.MIN_VALUE;
        GameCoordinate bestMove = null;
        HashSet<GameCoordinate> empties = board.getEmpties();

        for (GameCoordinate thisCoor : empties) {
            board.placeBrick(thisCoor, myBrick);
            currentMoveScore = miniMaxAssistant.miniMaxSearch(board, depthForAgent, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.undoMove(thisCoor);
            if (currentMoveScore > bestMoveScore) {
                bestMoveScore = currentMoveScore;
                bestMove = thisCoor;
            }
        }

        if (isFirstGo) {
            isFirstGo = false;
            setDepthForAgent(5);
        }
        System.out.println("Score chosen: "+ bestMoveScore);
        return bestMove;
    }

    private GameCoordinate getRandomMove(GameBoard board) {
        Random rnd = new Random();
        int x = rnd.nextInt(board.getDimension());
        int y = rnd.nextInt(board.getDimension());
        GameCoordinate current = board.getCoordinate(x,y);

        while (current.isOccupied()) {
             x = rnd.nextInt(board.getDimension());
             y = rnd.nextInt(board.getDimension());
             current = board.getCoordinate(x,y);
        }
        return current;
    }

    public void setDepthForAgent(int depthForAgent) {
        this.depthForAgent = depthForAgent;
    }
}
