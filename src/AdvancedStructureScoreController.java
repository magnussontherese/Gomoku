public class AdvancedStructureScoreController implements ScoreController{
    private GameBoard gameBoard;
    private static final int winScore = 100000000;
    public AdvancedStructureScoreController(GameBoard board) {
        gameBoard = board;
    }

    @Override
    public int getBoardScore(boolean isComputerTurn) {
        return 0;
    }



}
