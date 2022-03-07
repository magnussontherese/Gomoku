public class ScoreController {

    //The control to check a win is managed from a recent move.
    //Therefore we will only check the GameCoordinates of interest for the win, the coordinates related "for a win" to the current move


    private final GameBoard gameBoard;
    private static final int winScore = 100000000;


    //We need to notice what the winning score for the agent might be
    private int winningScore= Integer.MAX_VALUE;

    public ScoreController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    //The computer is "white"
    public double evaluateBoardForUser(boolean userTurn) {
    //evaluationCount++;

    // Get board score of both players.
    double userScore = generateScore( true, userTurn);
    double computerScore = generateScore( false, userTurn);

    if(userScore == 0) userScore = 1.0;

    // Calculate relative score of white against black
    return computerScore / userScore;
    }

    private int generateScore(boolean scoreIsForUser, boolean userTurn) {
        return evalHorizon(scoreIsForUser, userTurn) + evalVertical(scoreIsForUser, userTurn) + evalDiagonal(scoreIsForUser, userTurn);
    }



    private int evalDiagonal(boolean isScoreForUser, boolean isUserTurn) {

        int inARow = 0;
        int blockings = 2;
        int score = 0;
        // From bottom-left to top-right diagonally
        for (int k = 0; k <= 2 * (gameBoard.getDimension() - 1); k++) {
            int iStart = Math.max(0, k - gameBoard.getDimension() + 1);
            int iEnd = Math.min(gameBoard.getDimension() - 1, k);
            for (int i = iStart; i <= iEnd; ++i) {
                int j = k - i;

                if(gameBoard.getContent()[i][j].getOwner() == (isUserTurn ? 'x' : 'o')) {
                    inARow++;
                }
                else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blockings--;
                        score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);
                        inARow = 0;
                        blockings = 1;
                    }
                    else {
                        blockings = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);
                    inARow = 0;
                    blockings = 2;
                }
                else {
                    blockings = 2;
                }

            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);

            }
            inARow = 0;
            blockings = 2;
        }
        // From top-left to bottom-right diagonally
        for (int k = 1-gameBoard.getDimension(); k < gameBoard.getDimension(); k++) {
            int iStart = Math.max(0, k);
            int iEnd = Math.min(gameBoard.getDimension() + k - 1, gameBoard.getDimension()-1);
            for (int i = iStart; i <= iEnd; ++i) {
                int j = i - k;

                if(gameBoard.getContent()[i][j].getOwner() == (isUserTurn ? 'x' : 'o')) {
                    inARow++;
                }
                else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blockings--;
                        score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);
                        inARow = 0;
                        blockings = 1;
                    }
                    else {
                        blockings = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);
                    inARow = 0;
                    blockings = 2;
                }
                else {
                    blockings = 2;
                }

            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);

            }
            inARow = 0;
            blockings = 2;
        }

        return score;
    }

    private int evalVertical(boolean isScoreForUser, boolean isUserTurn) {
        int inARow = 0;
        int blockings = 2;
        int score = 0;
        for(int j=0; j<gameBoard.getDimension(); j++) {
            for(int i=0; i<gameBoard.getDimension(); i++) {
                if(gameBoard.getContent()[i][j].getOwner() == (isUserTurn ? 'x' : 'o')) {
                    inARow++;
                }
                else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blockings--;
                        score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);
                        inARow = 0;
                        blockings = 1;
                    }
                    else {
                        blockings = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);
                    inARow = 0;
                    blockings = 2;
                }
                else {
                    blockings = 2;
                }
            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blockings, isScoreForUser == isUserTurn);

            }
            inARow = 0;
            blockings = 2;

        }
        return score;
    }

    private int evalHorizon(boolean isScoreForUser, boolean isUserTurn) {
        int inARow = 0;
        int blocking = 2; //Set to the maximun for starters
        int score = 0;

        for(int i=0; i<gameBoard.getDimension(); i++) {
            for(int j=0; j<gameBoard.getContent()[0].length; j++) {
                if(gameBoard.getContent()[i][j].getOwner() == (isUserTurn ? 'x' : 'o')) {
                    inARow++; // If we have the coordinate, we should increase the "inARow"
                } else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blocking--; //We are on a "streak" since inARow > 0, no blocking
                        score += getInARowScore(inARow, blocking, isScoreForUser == isUserTurn);
                        inARow = 0;
                        blocking = 1;
                    }
                    else {
                        blocking = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blocking, isScoreForUser == isUserTurn);
                    inARow = 0;
                    blocking = 2;
                }
                else {
                    blocking = 2;
                }
            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blocking, isScoreForUser == isUserTurn);
            }
            inARow = 0;
            blocking = 2;
        }
        return score;
    }


    private double getInARowScore(int inARow, int blockings, boolean currentTurn) {
        if(blockings == 2 && inARow < 5) return 0; //We are blocked on both sides, not good, return a low score
        if (inARow >= 5) { //If we have 5 we are the winners
            return winScore;
        } else  { //Otherwise we should investigare the heruistics
            return countScoreWithVariatingBlockings(inARow, blockings, currentTurn);
        }
    }

    //Heuristics based on inARow and the opponents blocking
    private double countScoreWithVariatingBlockings(int inARow, int blockings, boolean currentTurn) {
        final int winGuarantee = 1000000;

        switch(inARow) {
            case 5: {
                // 5 consecutive wins the game
                return winScore;
            }
            case 4: {
                // 4 consecutive stones in the user's turn guarantees a win.
                // (User can win the game by placing the 5th stone after the set)
                if(currentTurn) return winGuarantee;
                else {
                    // Opponent's turn
                    // If neither side is blocked, 4 consecutive stones guarantees a win in the next turn.
                    if(blockings == 0) return winGuarantee/4;
                        // If only a single side is blocked, 4 consecutive stones limits the opponents move
                        // (Opponent can only place a stone that will block the remaining side, otherwise the game is lost
                        // in the next turn). So a relatively high score is given for this set.
                    else return 200;
                }
            }
            case 3: {
                // 3 consecutive stones
                if(blockings == 0) {
                    // Neither side is blocked.
                    // If it's the current player's turn, a win is guaranteed in the next 2 turns.
                    // (User places another stone to make the set 4 consecutive, opponent can only block one side)
                    // However the opponent may win the game in the next turn therefore this score is lower than win
                    // guaranteed scores but still a very high score.
                    if(currentTurn) return 50000;
                        // If it's the opponent's turn, this set forces opponent to block one of the sides of the set.
                        // So a relatively high score is given for this set.
                    else return 200;
                }
                else {
                    // One of the sides is blocked.
                    // Playmaker scores
                    if(currentTurn) return 10;
                    else return 5;
                }
            }
            case 2: {
                // 2 consecutive stones
                // Playmaker scores
                if(blockings == 0) {
                    if(currentTurn) return 7;
                    else return 5;
                }
                else {
                    return 3;
                }
            }
            case 1: {
                return 1;
            }
        }
        // More than 5 consecutive stones?
        return winScore*2;
    }




    //Returns a boolean representing the win for a change at a game coordinate. The new owner of the game coordinate can be the winner.
    public char checkWin(GameCoordinate move) {
        if (checkColumnWin(move)) return move.getOwner();
        if (checkRowWin(move)) return move.getOwner();
        if (checkDiagonalWin(move)) return move.getOwner();
        if (checkAntiDiagonalWin(move)) return move.getOwner();
        return Character.MIN_VALUE; //If there is no win, there is no winner, represented by the character minvalue
    }

    boolean checkAntiDiagonalWin(GameCoordinate move) {
        if (move.getX() + move.getY() == gameBoard.getDimension() - 1) {
            for (int i = 0; i < gameBoard.getDimension(); i++) {
                if (gameBoard.getContent()[i][(gameBoard.getDimension() - 1) - i].getOwner() != move.getOwner())
                    break; //The loops breaks whenever i encounter the other player in the "winner" sequence
                if (i == gameBoard.getWincount() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    //Instead of deciding a win, we need to evaluate scores on "the vertical", "the horizontal"....
    boolean checkDiagonalWin(GameCoordinate move) {
        if (move.getX() == move.getY()) {
            //we're on a diagonal
            for (int i = 0; i < gameBoard.getDimension(); i++) {
                if (gameBoard.getContent()[i][i].getOwner() != move.getOwner())
                    break;
                if (i == gameBoard.getWincount() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkRowWin(GameCoordinate move) {
        for (int i = 0; i < gameBoard.getDimension(); i++) {
            if (gameBoard.getContent()[i][move.getY()].getOwner() != move.getOwner())
                break;
            if (i == gameBoard.getWincount()- 1) {
                return true;
            }
        }
        return false;
    }

    boolean checkColumnWin(GameCoordinate move) {
        for (int i = 0; i < gameBoard.getDimension(); i++) {
            if (gameBoard.getContent()[move.getX()][i].getOwner() != move.getOwner())
                break;
            if (i == gameBoard.getWincount() - 1) {
                return true;
            }
        }
        return false;
    }

    //To genereate the scores for decisionmaking, needs change.
    public int checkWin2(GameCoordinate move, int depth) {
        char winner = Character.MIN_VALUE;
        if (checkColumnWin(move)) {
            winner = move.getOwner();
        } else if (checkRowWin(move)) {
            winner = move.getOwner();
        } else if (checkDiagonalWin(move)) {
            winner = move.getOwner();
        } else if (checkAntiDiagonalWin(move)) {
            winner = move.getOwner();
        }

        return validateScore(winner, depth);
    }

    private int validateScore(char winner, int depth) {
        if (winner == Character.MIN_VALUE && gameBoard.getEmpties().size() == 0) {
            return 0;
        } else if (winner == 'o') {
            return 1;
        } else if (winner == 'x') {
            return -1;
        } else {
            return Integer.MIN_VALUE;
        }
    }


//    Denna variant av hur en 'poäng' räknas ut användes av: *source*

//    else if (winner == 'o') {
//        return (int) Math.pow(gameBoard.dimension, 2) - depth;
//    } else if (winner == 'x') {
//        return (((int) Math.pow(gameBoard.dimension, 2)) * -1) + depth;
//    }


}


