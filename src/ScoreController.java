public class ScoreController {

    private final GameBoard gameBoard;
    private static final int winScore = 100000000;

    //TODO: Needs better scoring algoritm.

    public ScoreController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getBoardScore() {
        int scoreForComputer = findScore('o');
        int scoreForHuman  = findScore('x');
        return scoreForComputer - scoreForHuman;
    }

    private int findScore(char player) {

        return horizon(player) + vertical(player) + diagonal(player);
    }

    public int diagonal(char player) {
        GameCoordinate[] aDiagonalArray = new GameCoordinate[gameBoard.getDimension()];
        for(int i = 0; i < gameBoard.getDimension() ; i ++) {
            aDiagonalArray[i] = gameBoard.getContent()[i][i];
        }
        int bestStreakInDiagonalArray = checkStreak(aDiagonalArray, player);
        int y = gameBoard.getDimension();
        for (int i = 0; i < gameBoard.getDimension(); i ++) {
            aDiagonalArray[i] = gameBoard.getContent()[i][--y];
        }
        int bestStreakInAntiDiagonal = checkStreak(aDiagonalArray, player);
        return Math.max(bestStreakInDiagonalArray, bestStreakInAntiDiagonal);
    }

    public int horizon(char player) {
        int bestHorizontalStreakInAllBoard = 0;
        for (int i = 0; i < gameBoard.getDimension(); i++) {
            int bestStreakInThisHorizonArray = checkStreak(gameBoard.getContent()[i],player);
            if (bestStreakInThisHorizonArray > bestHorizontalStreakInAllBoard) {
                bestHorizontalStreakInAllBoard = bestStreakInThisHorizonArray;
            }
        }
        return bestHorizontalStreakInAllBoard;
    }

    public int vertical(char player) {
        int bestVerticalStreakInAllBoard = 0;
        GameCoordinate[] aVerticalArray = new GameCoordinate[gameBoard.getDimension()];
        for (int j = 0 ; j < gameBoard.getDimension() ;j++){
            for (int i = 0; i < gameBoard.getDimension(); i++) {
                aVerticalArray[i] = gameBoard.getContent()[i][j];
            }
            int bestStreakInVerticalArray = checkStreak(aVerticalArray, player);
            if (bestStreakInVerticalArray > bestVerticalStreakInAllBoard) {
                bestVerticalStreakInAllBoard = bestStreakInVerticalArray;
            }
        }
        return bestVerticalStreakInAllBoard;
    }

    public int checkStreak(GameCoordinate[] gameCoordinates, char player) { //Får in en array från spelplanen att kontrollera och betygsätta utifrån en player
        //Det kan vara en diagonal, antidiagonal, verikal eller horisontell
        int aStreakInThisArray =  0;
        int bestStreakInThisArray= 0;
        int startForStreak = 0;
        for (int i = 0; i < gameCoordinates.length; i++) {
            if(gameCoordinates[i].getOwner() == player) {
                aStreakInThisArray++;
                startForStreak = i;
            } else if (gameCoordinates[i].isOccupied() && gameCoordinates[i].getOwner() != player && aStreakInThisArray > 0){ // Vi är öppna eller blockerade
                aStreakInThisArray = 0;
            } else {
                aStreakInThisArray = 0;
            }
            if (aStreakInThisArray > bestStreakInThisArray) {
                bestStreakInThisArray = aStreakInThisArray;
            }
        }
        if (bestStreakInThisArray > 0) {
            int enemies = findBlockings(gameCoordinates, bestStreakInThisArray, startForStreak, player);
            return scoreForThisStreakCount(bestStreakInThisArray, enemies);
        }
        return 0;
    }

    public int findBlockings(GameCoordinate[] gameCoordinates, int bestStreakInThisArray, int startForStreak, char player) {
        int indexBeforeFirstInStreak = startForStreak - 1;
        int indexAfterLastInStreak = (startForStreak + bestStreakInThisArray);
        int blockings = 0; //Blockings can be either a enemie or a wall
        if (indexBeforeFirstInStreak < 0) { //This value will genereate indexOutOfBounds
            blockings++; //The index before the streak is a wall, we are blocked by wall, closed end to left
        } else {
            if (gameCoordinates[indexBeforeFirstInStreak].isOccupied() && gameCoordinates[indexBeforeFirstInStreak].getOwner() != player) {
                blockings++;
            }
        }
        if (indexAfterLastInStreak >= gameBoard.getDimension()){ //This value will genereate indexOutOfBounds
            blockings++;//The index after  the streak is a wall, we are blocked by wall, closed end to right
        } else {
            if (gameCoordinates[indexAfterLastInStreak].isOccupied() && gameCoordinates[indexAfterLastInStreak].getOwner() != player) {
                blockings++;
            }
        }
        return blockings;
    }


    private int scoreForThisStreakCount(int bestStreakInThisArray, int blockings) {
        int leftToWin = gameBoard.getWincount() - bestStreakInThisArray;
        if (leftToWin == 0) {
            return bestStreakInThisArray*100000;
        }
        if (blockings == 2) { //We are closed on both sides, worthless streak
            return 0;
        }
        if (leftToWin == 1) { //One blocking on four in streak means open right side or leftside
            return bestStreakInThisArray*10000;
        }

        if (leftToWin == 2) { //One blocking on four in streak means open right side or leftside
            if (blockings == 1){
                return bestStreakInThisArray*500;
            } else {
                return bestStreakInThisArray*1000;
            }
        }
        return bestStreakInThisArray;
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
    public int checkWin2(GameCoordinate move) {
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

        return validateScore(winner);
    }

    private int validateScore(char winner) {
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


