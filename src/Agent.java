import java.util.Random;

public class Agent {
    MiniMaxAssistant miniMaxAssistant;

    //Shpould implement minmax algoritm with some heuristic and also pruning
    public void evaluateAndMove(GameBoard board) {
        Random rnd = new Random();
        System.out.println("ComputerPlaying...");

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int x = rnd.nextInt(board.getDimentions());
        int y = rnd.nextInt(board.getDimentions());
        int [] chosen = miniMaxAssistant.evaluateBoard(board);
        board.placeBrick(x, y, 'o');
        //board.placeBrick(chosen[0], chosen[1], 'o');
    }


}
