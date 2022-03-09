import java.util.HashSet;
import java.util.Random;

public class MiniMaxAssistant {
    ScoreController controller;

    private int counter = 0; //Räknare för testning av antal "varv" i miniMax

    public MiniMaxAssistant(GameBoard board) {
        controller = new ScoreController(board);
    }

    public GameCoordinate getBestMove(GameBoard board) {
        int currentMoveScore;
        int bestMoveScore = Integer.MIN_VALUE;
        GameCoordinate bestMove = null;
        HashSet<GameCoordinate> empties = board.getEmpties();
        //Denna loop går igenom alla för närvarande lediga plaser och försöker hitta den som genererar bäst score.
            for (GameCoordinate coordinate : empties) { //These are the nodes that needs to be evaluteted
                board.placeBrick(coordinate, 'o'); //Lägger ett move
                currentMoveScore = miniMaxSearch(board, 6, false, Integer.MIN_VALUE, Integer.MAX_VALUE); //Får ett score av det movet
                board.undoMove(coordinate); //Tar bort dummymovet
                if (currentMoveScore > bestMoveScore) { //Kontrollerar om ett annat move skulle vara bättre
                        bestMoveScore = currentMoveScore;
                        bestMove = coordinate;
                }
            }
        //Skriver ut hur många försök miniMax krävde för att göra ett val -- Endast för testning
        System.out.println("MiniMax took " + counter + " tries");
        counter = 0;
        System.out.println("The score chosen was: " + bestMoveScore);
        return bestMove;
    }

    //Simulates a game through a ceartain depth, has it's starting point in a actual move
    //Will then take times, always counting on that the human will make the "perfect" move
    public int miniMaxSearch(GameBoard board, int depth, boolean isMaximizing, int alpha, int beta) {
        counter ++;
        HashSet<GameCoordinate> available = board.getEmpties();
        if (depth == 0 || available.size() == 0) {
            int scoreToReturn = controller.getBoardScore(isMaximizing);
            return scoreToReturn;
        }
        int score;
        if (isMaximizing) {
            score = Integer.MIN_VALUE;
            for (GameCoordinate aFreeSpot : available) {
                board.placeBrick(aFreeSpot, 'o');
                score = miniMaxSearch(board, depth - 1, false, alpha, beta);
                board.undoMove(aFreeSpot);
                //score = Math.max(currentScore, score);
                if (score >= beta) {
                    break;//We prune in here
                }
                alpha = Math.max(alpha, score);
            }
            return score;
        } else {
            score = Integer.MAX_VALUE;
            for (GameCoordinate  aFreeSpot: available) {
                    board.placeBrick(aFreeSpot, 'x');
                    score = miniMaxSearch(board, depth - 1, true, alpha, beta);
                    board.undoMove(aFreeSpot);
                    //score = Math.min(currentScore, score);
                    if (score <= alpha) {
                       break;//We prune in here
                    }
                    beta = Math.min(beta, score);
            }
            return score;
        }
        //return score;
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


