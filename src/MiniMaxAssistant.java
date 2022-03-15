import java.util.HashSet;
import java.util.Random;

public class MiniMaxAssistant {
    ScoreController controller;
    GameBoard board;

    private int counter = 0; //Räknare för testning av antal "varv" i miniMax

    public int getCounter() {
        return counter;
    }

    public MiniMaxAssistant(GameBoard board) {
        controller = (board.getDimension() == board.getWincount())? new SimpleStructureScoreController(board): new AdvancedStructureScoreController(board);
        this.board = board;
    }


    //   5x5 wc 5, Depth 6: with pruning first move on empty board: 7618146 tried moves, 3995 milliseconds (CHOSEN)
    //5x5 wc 5, Depth 6: with NO pruning first move on empty board: NOTSUCESSFUL within 10 seconds

    //   5x5 wc 5, Depth 5: with pruning first move on empty board: 1764366 tried moves, 1116 milliseconds
    //5x5 wc 5, Depth 5: with NO pruning first move on empty board: 134205625 tried moves,  81918 milliseconds

    //   5x5 wc 5, Depth 4: with pruning first move on empty board: 191763 tried moves, 167 milliseconds
    //5x5 wc 5, Depth 4: with NO pruning first move on empty board: 6693625 tried moved, 3816 milliseconds

    //Simulates a game through a ceartain depth, has it's starting point in a actual move
    //Will then take times, always counting on that the human will make the "perfect" move


    /**
     * MiniMax algoritm with alpha beta pruning. This algorithm will simulate moves on a "dummy" board and evaluate the score obtanied
     * from a board with the specified move.
     * The algorithm alternates betweeen a optimal computer player and a simulated optimal human player.
     * The scores are evaluated by a heuristic specified by the ScoreController when we have reached the specified depth,
     * and is then returned to the prior state by back-tracking the recursion.
     *
     * The pruning of the branches works as follows (in the alpha part). When the beta is smaller than the score obtaiend
     * by one computer move we can stop iterating; we do not need to check further, since the computer version of the
     * human player has already found a better option - a lower score.
     *
     *
     * For the (minimizing) human simulation it works similarily.
     *
     * @param board the current board to evaluate
     * @param depth the current depth at this point in the recursion
     * @param isMaximizing a boolean  describing if the algorithm should maximize or minimize
     * @param alpha variable that holds the minimun guaranteed score for the computer(maximizer), allows for pruning
     * @param beta variable that holds the maximum guaranteed score for the human(minimizer), allows for pruning
     *
     * @return the best score obtained from the initial board.
    */
    public int miniMaxSearch(GameBoard board, int depth, boolean isMaximizing, int alpha, int beta) {
        counter++;
        HashSet<GameCoordinate> available = board.getAllToTry();
        int score;
        if (depth == 0 || available.size() == 0) {
            return controller.getBoardScore(isMaximizing);
        }
        if (isMaximizing) {
            score = Integer.MIN_VALUE;
            for (GameCoordinate tryThis: available) {
                board.placeBrick(tryThis, 'o');
                score = Math.max(score, miniMaxSearch(board, depth-1, false, alpha, beta));
                board.undoMove(tryThis);
                alpha = Math.max(alpha, score); //Failsoft
                if (score >= beta) break;
            }
        } else {
            score = Integer.MAX_VALUE;
            for (GameCoordinate tryThis: available) {
                board.placeBrick(tryThis, 'x');
                score = Math.min(score, miniMaxSearch(board, depth-1, true, alpha, beta));
                board.undoMove(tryThis);
                beta = Math.min(beta, score);
                if (score <= alpha) break;

            }
        }
        return score;
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

    //    //Score for a move instead of a entire board?
    ////    public int miniMaxSearchForPosition(GameCoordinate gc, int depth, boolean isMaximizing, int alpha, int beta) {
    ////        HashSet<GameCoordinate> available = board.getEmpties();
    ////        int bestScore;
    ////        int currentScore;
    ////        if (depth == 0 || available.size() == 0) {
    ////            return controller.getPositionScore(gc, isMaximizing);
    ////        }
    ////        if (isMaximizing) {
    ////            bestScore = Integer.MIN_VALUE;
    ////            for (GameCoordinate tryThis: available) {
    ////                //board.placeBrick(tryThis, 'o');
    ////                currentScore = miniMaxSearchForPosition(tryThis, depth-1, false, alpha,beta);
    ////                bestScore = Math.max(currentScore, bestScore);
    ////                //board.undoMove(tryThis);
    ////                alpha = Math.max(alpha, currentScore); //Failsoft
    ////                if (beta <= alpha) {
    ////                    break;
    ////                }
    ////            }
    ////        } else {
    ////            bestScore = Integer.MAX_VALUE;
    ////            for (GameCoordinate tryThis: available) {
    ////                //board.placeBrick(tryThis, 'x');
    ////                currentScore = miniMaxSearchForPosition(tryThis, depth-1, true, alpha, beta);
    ////                bestScore = Math.min(bestScore, currentScore);
    ////                //board.undoMove(tryThis);
    ////                beta = Math.min(beta, currentScore);
    ////                if (beta <= alpha) {
    ////                    break;
    ////                }
    ////            }
    ////        }
    ////        return bestScore;
    ////    }
}


