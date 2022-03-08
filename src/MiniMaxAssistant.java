import java.util.HashSet;
import java.util.Random;

public class MiniMaxAssistant {
    ScoreController controller;

    private int counter = 0; //Räknare för testning av antal "varv" i miniMax

    public MiniMaxAssistant(GameBoard board) {
        controller = new ScoreController(board);
    }

    public GameCoordinate evaluateBoardReturnBestMove(GameBoard board) {
        return getBestMove(board);
    }


    private GameCoordinate getBestMove(GameBoard board) {
        int currentMoveScore;
        int bestMoveScore = Integer.MIN_VALUE;
        GameCoordinate bestMove = null;
        HashSet<GameCoordinate> empties = board.getEmpties();
        //Denna loop går igenom alla för närvarande lediga plaser och försöker hitta den som genererar bäst score.
            for (GameCoordinate coordinate : empties) { //These are the nodes that needs to be evaluteted
                board.placeBrick(coordinate, 'o'); //Lägger ett move
                currentMoveScore = miniMaxSearch(board, 6, false, coordinate, Integer.MIN_VALUE, Integer.MAX_VALUE); //Får ett score av det movet
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
    private int miniMaxSearch(GameBoard board, int depth, boolean isMaximizing, GameCoordinate prevMove, int alpha, int beta) {
        counter ++;
        HashSet<GameCoordinate> available = board.getEmpties();
        if (depth == 0 || available.size() == 0) {
            return controller.getBoardScore();
        }
        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (GameCoordinate aFreeSpot : available) {
                board.placeBrick(aFreeSpot, 'o');
                int currentScore = miniMaxSearch(board, depth - 1, false, aFreeSpot, alpha, beta);
                board.undoMove(aFreeSpot);
                bestScore = Math.max(currentScore, bestScore);
                alpha = Math.max(alpha, bestScore);
                if (alpha >= beta) {
                    return bestScore;//We prune in here
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (GameCoordinate  aFreeSpot: available) {
                    board.placeBrick(aFreeSpot, 'x');
                    int currentScore = miniMaxSearch(board, depth - 1, true, aFreeSpot, alpha, beta);
                    board.undoMove(aFreeSpot);
                    bestScore = Math.min(currentScore, bestScore);
                    beta = Math.min(beta, bestScore);
                    if (beta <= alpha) {
                        return bestScore; //We prune in here
                    }
                }
        }
        return bestScore;
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


