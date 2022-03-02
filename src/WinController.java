public class WinController {

    //The control to check a win is managed from a recent move.
    //Therefore we will only check the GameCoordinates of interest for the win.
    private final GameBoard gameBoard;

    public WinController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    //Returns a boolean representing the win for a change at a game coordinate. The new owner of the game coordinate can be the winner.
    public char checkWin(GameCoordinate move) {
        if (checkColummnWin(move)) return move.getOwner();
        if (checkRowWin(move)) return move.getOwner();
        if (checkDiagonalWin(move)) return move.getOwner();
        if (checkAntiDiagonalWin(move)) return move.getOwner();
        return Character.MIN_VALUE;
    }

    boolean checkAntiDiagonalWin(GameCoordinate move) {
        if (move.getX() + move.getY() == gameBoard.getDimentions() - 1) {
            for (int i = 0; i < gameBoard.getDimentions(); i++) {
                if (gameBoard.getContent()[i][(gameBoard.getDimentions() - 1) - i].getOwner() != move.getOwner())
                    break;
                if (i == gameBoard.getDimentions() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkDiagonalWin(GameCoordinate move) {
        if (move.getX() == move.getY()) {
            //we're on a diagonal
            for (int i = 0; i < gameBoard.getDimentions(); i++) {
                if (gameBoard.getContent()[i][i].getOwner() != move.getOwner())
                    break;
                if (i == gameBoard.getDimentions() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkRowWin(GameCoordinate move) {
        for (int i = 0; i < gameBoard.getDimentions(); i++) {
            if (gameBoard.getContent()[i][move.getY()].getOwner() != move.getOwner())
                break;
            if (i == gameBoard.getDimentions() - 1) {
                return true;
            }
        }
        return false;
    }

    boolean checkColummnWin(GameCoordinate move) {
        for (int i = 0; i < gameBoard.getDimentions(); i++) {
            if (gameBoard.getContent()[move.getX()][i].getOwner() != move.getOwner())
                break;
            if (i == gameBoard.getDimentions() - 1) {
                return true;
            }
        }
        return false;
    }
}