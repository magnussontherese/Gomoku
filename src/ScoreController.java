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
    public double boardScore(char player) {
            // Get board score of both players.
           // double humanScore = generateScore( 'x');
            //double agentScore = generateScore( 'o');
            // Calculate relative score of white against black
            return  generateScore(player);
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



    private int generateScore(char player) {
        return evalHorizon(player) + evalVertical(player) + evalDiagonal(player);
    }

    private int evalDiagonal(char player) {

        int inARow = 0;
        int blockings = 2;
        int score = 0;
        // From bottom-left to top-right diagonally
        for (int k = 0; k <= 2 * (gameBoard.getDimension() - 1); k++) {
            int iStart = Math.max(0, k - gameBoard.getDimension() + 1);
            int iEnd = Math.min(gameBoard.getDimension() - 1, k);
            for (int i = iStart; i <= iEnd; ++i) {
                int j = k - i;

                if(gameBoard.getContent()[i][j].getOwner() == player) {
                    inARow++;
                }
                else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blockings--;
                        score += getInARowScore(inARow, blockings, player);
                        inARow = 0;
                        blockings = 1;
                    }
                    else {
                        blockings = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blockings, player);
                    inARow = 0;
                    blockings = 2;
                }
                else {
                    blockings = 2;
                }

            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blockings, player);

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

                if(gameBoard.getContent()[i][j].getOwner() == player) {
                    inARow++;
                }
                else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blockings--;
                        score += getInARowScore(inARow, blockings, player);
                        inARow = 0;
                        blockings = 1;
                    }
                    else {
                        blockings = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blockings, player);
                    inARow = 0;
                    blockings = 2;
                }
                else {
                    blockings = 2;
                }

            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blockings, player);

            }
            inARow = 0;
            blockings = 2;
        }

        return score;
    }

    private int evalVertical(char player) {
        int inARow = 0;
        int blockings = 2;
        int score = 0;
        for(int j=0; j<gameBoard.getDimension(); j++) {
            for(int i=0; i<gameBoard.getDimension(); i++) {
                if(gameBoard.getContent()[i][j].getOwner() == player) {
                    inARow++;
                }
                else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blockings--;
                        score += getInARowScore(inARow, blockings, player);
                        inARow = 0;
                        blockings = 1;
                    }
                    else {
                        blockings = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blockings, player);
                    inARow = 0;
                    blockings = 2;
                }
                else {
                    blockings = 2;
                }
            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blockings, player);

            }
            inARow = 0;
            blockings = 2;

        }
        return score;
    }

    private int evalHorizon(char player) {
        int inARow = 0;
        int blocking = 2; //Set to the maximun for starters
        int score = 0;

        for(int i=0; i<gameBoard.getDimension(); i++) {
            for(int j=0; j<gameBoard.getContent()[0].length; j++) {
                if(gameBoard.getContent()[i][j].getOwner() == player) {
                    inARow++; // If we have the coordinate, we should increase the "inARow"
                } else if(!gameBoard.getContent()[i][j].isOccupied()) {
                    if(inARow > 0) {
                        blocking--; //We are on a "streak" since inARow > 0, no blocking
                        score += getInARowScore(inARow, blocking, player);
                        inARow = 0;
                        blocking = 1;
                    }
                    else {
                        blocking = 1;
                    }
                }
                else if(inARow > 0) {
                    score += getInARowScore(inARow, blocking, player);
                    inARow = 0;
                    blocking = 2;
                }
                else {
                    blocking = 2;
                }
            }
            if(inARow > 0) {
                score += getInARowScore(inARow, blocking, player);;
            }
            inARow = 0;
            blocking = 2;
        }
        return score;
    }


    private double getInARowScore(int inARow, int blockings, char player) {
        if(blockings == 2 && inARow < 5) return 0; //We are blovked on both sides, not good, return a low score
        if (inARow >= 5) { //If we have 5 we are the winners
            return winScore;
        } else  { //Otherwise we should investigare the heruistics
            return countScoreWithVariatingBlockings(inARow, blockings, player);
        }
    }

    //Heuristics based on inARow and the opponents blocking
    private double countScoreWithVariatingBlockings(int inARow, int blockings, char player) {
        final int winGuarantee = 1000000;
        if (inARow == 4) {
            if (player == 'o') //If it's our turn we will win.
                return winGuarantee;
            if (blockings == 0) return winGuarantee / 4;
            return 200;
        }

        if (inARow == 3) {
            if (blockings == 0) {
                if (player == 'o') return 5000;
                return 200;
            } else {
                if (player == 'o') return 10;
                return 5;
            }
        }

        if (inARow == 2) {
            if (blockings == 0) {
                if (player == 'o') return 7;
                return 5;
            }
            return 3;
        }

        if (inARow == 1) {
            return 1;
        }
        return 0;
    }
}


