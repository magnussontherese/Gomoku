public class WinController {

    //The control to check a win is managed from a recent move.
    //Therefore we will only check the GameCoordinates of interest for the win, the coordinates related "for a win" to the current move

    private final GameBoard gameBoard;

    public WinController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

/*    public int checkWin() {
        char winner = Character.MIN_VALUE;


        // horizontal:
        for (int ii = 0; ii < gameBoard.getDimentions(); ii++) {
            if (gameBoard.getContent()[ii][0].getOwner() == gameBoard.getContent()[ii][1].getOwner() &&
                    gameBoard.getContent()[ii][0].getOwner() == gameBoard.getContent()[ii][2].getOwner()) {
                winner = gameBoard.getContent()[ii][0].getOwner();
            }
        }

        // vertical:
        for (int ii = 0; ii < gameBoard.getDimentions(); ii++) {
            if (gameBoard.getContent()[0][ii].getOwner() == gameBoard.getContent()[1][ii].getOwner() &&
                    gameBoard.getContent()[0][ii].getOwner() == gameBoard.getContent()[2][ii].getOwner()) {
                winner = gameBoard.getContent()[0][ii].getOwner();
            }
        }

        // diagonal:
        if (gameBoard.getContent()[0][0].getOwner() == gameBoard.getContent()[1][1].getOwner() &&
                gameBoard.getContent()[0][0].getOwner() == gameBoard.getContent()[2][2].getOwner()) {
            winner = gameBoard.getContent()[0][0].getOwner();
        }

        // anti_diagonal:
        if (gameBoard.getContent()[0][2].getOwner() == gameBoard.getContent()[1][1].getOwner() &&
                gameBoard.getContent()[0][2].getOwner() == gameBoard.getContent()[2][0].getOwner()) {
            winner = gameBoard.getContent()[1][1].getOwner();
        }

        if (winner == Character.MIN_VALUE && gameBoard.getEmpties().size() == 0) {
            return 0;
        } else if (winner == 'o') {
            return 1;
        } else if (winner == 'x'){
            return -1;
        } else {
            return Integer.MIN_VALUE;
        }

    }*/

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
}


