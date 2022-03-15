import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SimpleStructureScoreController implements  ScoreController{
    private GameBoard gameBoard;
    public static final int WIN_SCORE = 1000000;

    public SimpleStructureScoreController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getBoardScore(boolean isComputerTurn) {
        HashSet<GameCoordinate> ownedByComputer = gameBoard.getOwnedByComputer();
        HashSet<GameCoordinate> ownedByHuman = gameBoard.getOwnedByHuman();
        if (gameBoard.getEmpties().size() == 0) return 0;
        int scoreForComputer = 0;
        int scoreForHuman = 0;
        if (isComputerTurn) {
             scoreForHuman  = findSimpleScore(ownedByHuman, false);
             scoreForComputer = findSimpleScore(ownedByComputer, true);
        } else {
             scoreForHuman = findSimpleScore(ownedByHuman, true);
             scoreForComputer = findSimpleScore(ownedByComputer, false);
        }
        if (scoreForHuman >= WIN_SCORE)
            return -WIN_SCORE;
        if (scoreForComputer >= WIN_SCORE)
            return WIN_SCORE;
        return scoreForComputer - scoreForHuman;
    }

    public int findSimpleScore(HashSet<GameCoordinate> ownedBy, boolean isMyTurn) {
        int vertScore = vertical(ownedBy, isMyTurn);
        int horScore = horizon(ownedBy, isMyTurn);
        int digScore = diagonal(ownedBy, isMyTurn);
        if (vertScore == gameBoard.getWincount()|| horScore == gameBoard.getWincount() || digScore == gameBoard.getWincount())
            return WIN_SCORE;
        return vertScore+horScore+digScore;
    }

    public int diagonal(HashSet<GameCoordinate> ownedBy, boolean isMyTurn) {
        HashSet<GameCoordinate> coordinatesOnDiagonal= new HashSet<>();
        HashSet<GameCoordinate> coordinatesOnAntiDiagonal= new HashSet<>();
        for (int i = 0; i < gameBoard.getDimension(); i ++) {
            if (ownedBy.contains(gameBoard.getCoordinate(i,i))) coordinatesOnDiagonal.add(gameBoard.getCoordinate(i,i));
        }
        int j = 0;
        for (int i = gameBoard.getDimension()-1; i >= 0; i--) {
            if (ownedBy.contains(gameBoard.getCoordinate(i,j))) coordinatesOnAntiDiagonal.add(gameBoard.getCoordinate(i,j));
            j++;
        }
        return Math.max(coordinatesOnAntiDiagonal.size(), coordinatesOnDiagonal.size());
    }


    public int vertical(HashSet<GameCoordinate> ownedBy, boolean isMyTurn) { //Same y value
        int bestStreak = 0;
        HashMap<Integer, HashSet<GameCoordinate>> coordinateByY= new HashMap<>();
        for (GameCoordinate gc : ownedBy) {
            HashSet<GameCoordinate> forThisY = coordinateByY.getOrDefault(gc.getY(), new HashSet<>());
            forThisY.add(gc);
            coordinateByY.putIfAbsent(gc.getY(), forThisY);
        }
        for (Integer i : coordinateByY.keySet()) {
            //int currentStreakScore = evaluateVertical(coordinateByY.get(i), i);
            int currentStreakScore = evaluateEnemy(coordinateByY.get(i));
            if (currentStreakScore > bestStreak) {
                bestStreak = currentStreakScore;
            }
        }
        return bestStreak;
    }

       public int evaluateEnemy(HashSet<GameCoordinate> gameCoordinates) {
        char player = ' ';
        int streakValue = gameCoordinates.size();
        for (GameCoordinate gc : gameCoordinates) {
            player = gc.getOwner();
            break;
        }
        HashSet<GameCoordinate> ownedByComp = gameBoard.getOwnedByComputer();
        HashSet<GameCoordinate> ownedByHuman = gameBoard.getOwnedByHuman();
        for (GameCoordinate gc : gameCoordinates) {
            if (player == 'o') {
                for (GameCoordinate humanGc : ownedByHuman) {
                    if (humanGc.getY() == gc.getY()) streakValue--;
                }
            }
            if (player == 'x') {
                for (GameCoordinate compGc : ownedByComp) {
                    if (compGc.getY() == gc.getY()) streakValue--;
                }
            }

        }
        return streakValue;
    }

    private int evaluateVertical(HashSet<GameCoordinate> gameCoordinates, int y) {
        ArrayList<GameCoordinate> verticalRow = new ArrayList<>();
        for (GameCoordinate gc : gameCoordinates) {
            verticalRow.set(gc.getX(), gc);
        }
        ArrayList<GameCoordinate> inARow = new ArrayList<>();
        ArrayList<GameCoordinate> bestInARow = new ArrayList<>();
        for (GameCoordinate gameCoordinate : verticalRow) {
            if(gameCoordinate == null && inARow.size() == 0){
                inARow.add(gameCoordinate);
            } else if (gameCoordinate == null && inARow.size() > 0) { //We are no longer on a streak, save the sofar streak.
                if (inARow.size() > bestInARow.size())
                    bestInARow = inARow;
                inARow.clear();
            }  else {
                inARow.add(gameCoordinate);
            }
        }


        // --o--oo--o

        int blockers = 0;
        for (GameCoordinate gc : gameCoordinates){
            GameCoordinate upper = gameBoard.getCoordinate(gc.getX() - 1, gc.getY());
            GameCoordinate lower = gameBoard.getCoordinate(gc.getX() + 1, gc.getY());
            if (upper == null) blockers++; //blocked by wall
            if(lower == null) blockers++;
            if (!(upper == null) && upper.isOccupied() && !(gameCoordinates.contains(upper))) blockers++; //blocked by enemy
            if (!(lower == null) && lower.isOccupied() && !(gameCoordinates.contains(lower))) blockers++;
            if (blockers == 2) return 0;
        }
        if (blockers == 1) return gameCoordinates.size()-2;
        return gameCoordinates.size();
    }




    private int evaluateVertStreak(HashMap<Integer, HashSet<GameCoordinate>> coordinateByY) {
        int bestObtained = 0;
        for (Integer i : coordinateByY.keySet()) {
            ArrayList<GameCoordinate> streak = new ArrayList<>();
            for (GameCoordinate gc : coordinateByY.get(i)) {
                streak.set(gc.getX(), gc);
            }
            bestObtained = Math.max(bestObtained, evaluateStreak(streak));
        }
        return 0;
    }

    private int evaluateStreak(ArrayList<GameCoordinate> row) {
        ArrayList<GameCoordinate> bestStreak = new ArrayList<>();
        for (int i = 0; i < row.size(); i ++) {
            ArrayList<GameCoordinate> currentStreak = new ArrayList<>();
            if (!(row.get(i) == null) && currentStreak.size() == 0) {
                 currentStreak.add(row.get(i));
            } else if ((row.get(i) == null) && currentStreak.size() > 0) {
                bestStreak = currentStreak;
                currentStreak.clear();
            }
        }
        return bestStreak.size();
    }

    public int horizon(HashSet<GameCoordinate> ownedBy, boolean isMyTurn) { //Same x value
        int bestStreak = 0;
        HashMap<Integer, HashSet<GameCoordinate>> coordinateByX= new HashMap<>();
        for (GameCoordinate gc : ownedBy) {
            HashSet<GameCoordinate> forThisX = coordinateByX.getOrDefault(gc.getX(), new HashSet<>());
            forThisX.add(gc);
            coordinateByX.putIfAbsent(gc.getX(), forThisX);
        }
        for (Integer i : coordinateByX.keySet()) {
            int streakScore = evaluateHorizontalStreak(coordinateByX.get(i));
            if(streakScore > bestStreak)
                bestStreak = streakScore;

        }
        return bestStreak;
    }

    private int evaluateHorizontalStreak(HashSet<GameCoordinate> gameCoordinates) {
        int blockers = 0;
        for (GameCoordinate gc : gameCoordinates){
            GameCoordinate left = gameBoard.getCoordinate(gc.getX(), gc.getY() -1);
            GameCoordinate right = gameBoard.getCoordinate(gc.getX(),gc.getY() + 1);
            if (left == null) blockers++; //blocked by wall
            if(right == null) blockers++;
            if (!(left == null) && left.isOccupied() && !(gameCoordinates.contains(left))) blockers++; //blocked by enemy
            if (!(right == null) && right.isOccupied() && !(gameCoordinates.contains(right))) blockers++;
            if (blockers == 2) return 0;
        }
        if (blockers == 1) return gameCoordinates.size()-2;
        return gameCoordinates.size();
    }


    public int checkStreak(GameCoordinate[] gameCoordinates, char player, boolean isMyTurn) { //Får in en array från spelplanen att kontrollera och betygsätta utifrån en player
        //Det kan vara en diagonal, antidiagonal, verikal eller horisontell
       if (gameBoard.dimension == gameBoard.getWincount())
            return scoreCounterForSimpleGameStructure(gameCoordinates,player, hasEnemy(gameCoordinates, player));
        int aStreakInThisArray = 0;
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
            int opeEnds = findOpenEnds(gameCoordinates, bestStreakInThisArray, startForStreak, player);
            return dumbScoreForThisStreak(bestStreakInThisArray,opeEnds);
        }
        return 0;
    }

    private int scoreCounterForSimpleGameStructure(GameCoordinate[] gameCoordinates, char player, boolean hasEnemy) {
        //This method only checks for enemies in the streaks, if there are one opponent in the streak, the streak is worthless
        //It works only if the winCount is equivalent to the dimension of the board.
        if (hasEnemy) {
            return 0;
        } else {
            int numInThis = 0;
            for (int i = 0; i < gameCoordinates.length; i++) {
                if (gameCoordinates[i].getOwner() == player ) {
                    numInThis++;
                }
            }
            if (numInThis == gameBoard.getWincount())
                return 1000000;
            return numInThis*numInThis*numInThis*numInThis;
        }
    }

    private int dumbScoreForThisStreak(int bestStreakInThisArray, int opeEnds) {
        int leftToWin = gameBoard.getWincount() - bestStreakInThisArray;
        if (leftToWin == 0) {
            return bestStreakInThisArray*100000;
        }
        if (opeEnds == 0) { //We are closed on both sides, worthless streak
            return 0;
        }
        if (leftToWin == 1) { //One blocking on four in streak means open right side or leftside
            return bestStreakInThisArray*10000;
        }

        if (leftToWin == 2) { //One blocking on four in streak means open right side or leftside
            if (opeEnds == 1){
                return bestStreakInThisArray*500;
            } else {
                return bestStreakInThisArray*1000;
            }
        }
        return bestStreakInThisArray;
    }

    private boolean hasEnemy(GameCoordinate[] gameCoordinates, char player) {
        for (int i = 0 ; i < gameBoard.getDimension(); i++) {
            if (gameCoordinates[i].getOwner() != player && gameCoordinates[i].isOccupied())
                return true;
        }
        return false;
    }

    public int findOpenEnds(GameCoordinate[] gameCoordinates, int bestStreakInThisArray, int startForStreak, char player) {
        int indexBeforeFirstInStreak = startForStreak - 1;
        int indexAfterLastInStreak = (startForStreak + bestStreakInThisArray);
        int openEnds = 2; //Blockings can be either a enemie or a wall
        if (indexBeforeFirstInStreak < 0) { //This value will genereate indexOutOfBounds
            openEnds--; //The index before the streak is a wall, we are blocked by wall, closed end to left
        } else {
            if (gameCoordinates[indexBeforeFirstInStreak].isOccupied() && gameCoordinates[indexBeforeFirstInStreak].getOwner() != player) {
                openEnds--;
            }
        }
        if (indexAfterLastInStreak >= gameBoard.getDimension()){ //This value will genereate indexOutOfBounds
            openEnds--;//The index after  the streak is a wall, we are blocked by wall, closed end to right
        } else {
            if (gameCoordinates[indexAfterLastInStreak].isOccupied() && gameCoordinates[indexAfterLastInStreak].getOwner() != player) {
                openEnds--;
            }
        }
        return openEnds;
    }


    private int scoreForThisStreakCount(int bestStreakInThisArray, int openEnds,  boolean isMyTurn, boolean hasEnemy) {
        int leftToWin = gameBoard.getWincount() - bestStreakInThisArray;
        if ((openEnds == 0 && bestStreakInThisArray > 0) || hasEnemy) {//We are closed on both sides, worthless streak
            return 0;
        }
        if (leftToWin == 0) { //This is a win
            return 20000000;
        }

        if (leftToWin == 1) {
            if (openEnds == 1) {
                if (isMyTurn) return 1000000;
                return 50;
            } if (openEnds == 2) {
                if (isMyTurn) return 1000000;
                return 500000;
            }
        }

        if(leftToWin == 2) {
            if (openEnds == 1) {
                if (isMyTurn) {
                    return 8;
                } else {
                    return 5;
                }
            } else if(openEnds == 2) {
                if (isMyTurn)
                    return 10000;
                return 50;
            }
        }


        if(leftToWin == 3) {
            if (openEnds == 2) {
                return 7;
            } else if(openEnds == 1) {
                return 3;
            }
        }

        if(leftToWin == 4) {
            if (openEnds == 2) {
                return 2;
            } else if(openEnds == 1) {
                return 1;
            }
        }

        return bestStreakInThisArray*2;
    }


}





