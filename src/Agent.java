import java.util.Random;

public class Agent {
    private MiniMaxAssistant miniMaxAssistant;
    public Agent(GameBoard board) {
        this.miniMaxAssistant = new MiniMaxAssistant(board);
    }

    //Shpould implement minmax algoritm with some heuristic and also pruning
    public GameCoordinate move(GameBoard board) {
        System.out.println("ComputerPlaying...");

        return evaluateAndMakeBestMove(board);
        //board.placeBrick(chosen[0], chosen[1], 'o');
    }

    private GameCoordinate evaluateAndMakeBestMove(GameBoard board) {
        GameCoordinate bestMove = miniMaxAssistant.evaluateBoardReturnBestMove(board);
//        GameCoordinate randomMove = getRandomMove(board);
        return board.placeBrick(bestMove, 'o');
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


}
