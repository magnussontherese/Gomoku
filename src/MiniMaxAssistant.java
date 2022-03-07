import java.util.HashSet;

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
        double currentMoveScore;
        double bestMoveScore = 0;
        GameCoordinate bestMove = null;

        //Denna loop går igenom alla för närvarande lediga plaser och försöker hitta den som genererar bäst score.
        for (GameCoordinate coordinate : board.getEmpties()) {
            if (!coordinate.isOccupied()) {
                board.placeBrick(coordinate, 'o'); //Lägger ett move
                currentMoveScore = miniMaxSearch(board, 6, true, coordinate, Integer.MAX_VALUE, Integer.MIN_VALUE); //Får ett score av det movet
                board.undoMove(coordinate); //Tar bort dummymovet
                if (currentMoveScore > bestMoveScore) { //Kontrollerar om ett annat move skulle vara bättre
                    bestMoveScore = currentMoveScore;
                    bestMove = coordinate;
                }

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
    private double miniMaxSearch(GameBoard board, int depth, boolean isMaximizing, GameCoordinate prevMove, double alpha, double beta) {
        counter ++;
        HashSet<GameCoordinate> available = board.getEmpties();
        if (depth == 0 || available.size() == 0) { //We have reached the specified depth, we can decide a win, stop codiditon for recursion
            return controller.evaluateBoardForUser(!isMaximizing);
        }
        double bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (GameCoordinate ava : available) {
                board.placeBrick(ava, 'o');
                double currentScore = miniMaxSearch(board, depth - 1, false, ava, alpha, beta);
                board.undoMove(ava);
                bestScore = Math.max(currentScore, bestScore);
                alpha = Math.max(alpha, bestScore);
                if (alpha >= beta) {
                    return bestScore;
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (GameCoordinate  ava: available) {
                    board.placeBrick(ava, 'x');
                    double currentScore = miniMaxSearch(board, depth -1, true, ava, alpha, beta);
                    board.undoMove(ava);
                    bestScore = Math.min(currentScore, bestScore);
                    beta = Math.min(beta, bestScore);
                    if (beta <= alpha) {
                        return bestScore;
                    }
                }
        }
        return bestScore;
    }


    //Denna metod ska simuelera drag och generea värde för ett drag, denna kommer antagligen att vara rekursiv.
    private int miniMax(GameBoard board, int depth, boolean isMaximizing, GameCoordinate previousMove, int alpha, int beta) {
        counter ++;
//        System.out.println("Start of miniMax... \nDepth: " + depth);
        int winner = controller.checkWin2(previousMove, depth);
        if (winner != Integer.MIN_VALUE) {
            return winner;
        }


        if (isMaximizing) {
            int bestScore;
            bestScore = Integer.MIN_VALUE;

            for (GameCoordinate coordinate : board.getEmpties()) {
                if (!coordinate.isOccupied()) {
                    board.placeBrick(coordinate, 'o');
                    int currentScore = miniMax(board, depth + 1, false, coordinate, alpha, beta);
                    board.undoMove(coordinate);

                    bestScore = Math.max(currentScore, bestScore);
                    alpha = Math.max(alpha, bestScore);
                    if (alpha >= beta) {

//                        System.out.println("Inside break -- (alpha > beta)");

                        return bestScore;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore;
            bestScore = Integer.MAX_VALUE;

            for (GameCoordinate coordinate : board.getEmpties()) {
                if (!coordinate.isOccupied()) {
                    board.placeBrick(coordinate, 'x');
                    int currentScore = miniMax(board, depth + 1, true, coordinate, alpha, beta);
                    board.undoMove(coordinate);

                    bestScore = Math.min(currentScore, bestScore);
                    beta = Math.min(beta, bestScore);
                    if (beta <= alpha) {

//                        System.out.println("Inside break -- (beta < alpha)");

                        return bestScore;
                    }
                }
            }
        return bestScore;
        }
    }
}


