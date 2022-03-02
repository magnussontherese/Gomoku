import java.util.HashSet;
import java.util.Set;

public class MiniMaxAssistant {

    //Här skrivs algoritmen.
    //Om den blir för stor får vi bryta ut delar till en annan klass.
    private String tenPoints = "ooooo";
    private String sevenPoints = "oooo";
    private String fivePoints = "ooo";
    private String threePoints = "oo";
    private String onePoint = "o";

    public GameCoordinate evaluateBoardReturnBestMove(GameBoard board) {
        Set<GameCoordinate> availableCoordinates = board.getEmpties();
        GameCoordinate bestMove = getBestMove(availableCoordinates);
        return bestMove;
    }

    private GameCoordinate getBestMove(Set<GameCoordinate> availableCoordinates) {
        int currentMoveScore = 0;
        int bestMoveScore = 0;
        GameCoordinate bestMove = null;
        //Denna loop går igenom alla för närvarande ledigaplaser och försöker hitta den som genererar bäst score.
        for (GameCoordinate gc: availableCoordinates) {
            currentMoveScore = simulateMove(gc);
            if (currentMoveScore > bestMoveScore){
                bestMoveScore = currentMoveScore;
                bestMove = gc;
            }
            availableCoordinates.remove(gc);//När vi har kontrollerat en plats så kan vi gå vidare.
        }
        return bestMove;
    }

    //Denna metod ska simuelera drag och generea värde för ett drag, denna kommer antagligen att vara rekursiv.
    private int simulateMove(GameCoordinate gc) {
        return 0;
    }


}
