import java.util.Random;

public class Agent {
    MiniMaxAssistant miniMaxAssistant;

    //Shpould implement minmax algoritm with some heuristic and also pruning
    public GameCoordinate evaluateAndMove(GameBoard board) {
        System.out.println("ComputerPlaying...");

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return makeMove(board);
        //board.placeBrick(chosen[0], chosen[1], 'o');
    }

    private GameCoordinate makeMove(GameBoard board) {
        GameCoordinate randomMove = getRandomMove(board);
        return board.placeBrick(randomMove, 'o');
    }

    private GameCoordinate getRandomMove(GameBoard board) {
        Random rnd = new Random();
        int x = rnd.nextInt(board.getDimentions());
        int y = rnd.nextInt(board.getDimentions());
        GameCoordinate current = board.getCoordinate(x,y);

        while (current.isOccupied()) {
             x = rnd.nextInt(board.getDimentions());
             y = rnd.nextInt(board.getDimentions());
             current = board.getCoordinate(x,y);
        }
        return current;
    }


}
