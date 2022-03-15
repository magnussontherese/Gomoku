public class Move {
    private int score;
    private GameCoordinate thisMove;

    public Move(int score, GameCoordinate thisMove) {
        this.score = score;
        this.thisMove = thisMove;
    }

    public int getScore() {
        return score;
    }

    public GameCoordinate getThisMove() {
        return thisMove;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setThisMove(GameCoordinate thisMove) {
        this.thisMove = thisMove;
    }
}
