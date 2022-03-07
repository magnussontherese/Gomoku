import java.util.Set;

public class MiniMaxAssistant {
    WinController controller;

    private int counter = 0; //Räknare för testning av antal "varv" i miniMax


    public GameCoordinate evaluateBoardReturnBestMove(GameBoard board) {
        controller = new WinController(board);
        return getBestMove(board);
    }



    private GameCoordinate getBestMove(GameBoard board) {
        int currentMoveScore;
        int bestMoveScore = Integer.MIN_VALUE;
        GameCoordinate bestMove = null;

        //Denna loop går igenom alla för närvarande lediga plaser och försöker hitta den som genererar bäst score.
        for (GameCoordinate coordinate : board.getEmpties()) {
            if (!coordinate.isOccupied()) {
                board.placeBrick(coordinate, 'o');
                currentMoveScore = miniMax(board, 0, false, coordinate, Integer.MIN_VALUE, Integer.MAX_VALUE);
                board.undoMove(coordinate);
                if (currentMoveScore > bestMoveScore) {
                    bestMoveScore = currentMoveScore;
                    bestMove = coordinate;
                }
            }
        }

        //Skriver ut hur många försök miniMax krävde för att göra ett val -- Endast för testning
        System.out.println("MiniMax took " + counter + " tries");
        counter = 0;

        return bestMove;
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


