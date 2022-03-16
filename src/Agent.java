import java.util.HashSet;
import java.util.Random;

public class Agent {
    private MiniMaxAssistant miniMaxAssistant;
    private GameBoard board;
    private char myBrick = 'o';
    private int depthForAgent;
    private int nrMoves = 0;

    public Agent(GameBoard board) {
        this.board = board;
        this.miniMaxAssistant = new MiniMaxAssistant(board);
        depthForAgent = 3;
    }


    public GameCoordinate starterMove() {
        nrMoves++;
        HashSet<GameCoordinate> available = board.getEmpties();
        if (available.contains(new GameCoordinate(board.getDimension() / 2, board.getDimension() / 2))) {
            return board.getContent()[board.getDimension() / 2][board.getDimension() / 2];
        }
        return getRandomMove();
    }

    public GameCoordinate move() {
        System.out.println("ComputerPlaying...");
        if (nrMoves < 1)
            return board.placeBrick(starterMove(), myBrick);

        return board.placeBrick(getBestWithMiniMax(), myBrick);
    }



        private GameCoordinate getBestWithMiniMax(){
            nrMoves++;
            int currentMoveScore = 0;
            int bestMoveScore = Integer.MIN_VALUE;
            GameCoordinate bestMove = null;
            GameBoard dummy = new GameBoard(board);
            HashSet<GameCoordinate> empties = dummy.getAllToTry(); //All for simlpeStructure, relevant for more complex structure
            for (GameCoordinate thisCoor : empties) {//All simulating moves takes place on dummyBoard
                dummy.placeBrick(thisCoor, myBrick);
                currentMoveScore = miniMaxAssistant.miniMaxSearch(dummy, depthForAgent, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                dummy.undoMove(thisCoor);
                if (currentMoveScore > bestMoveScore) {
                    bestMoveScore = currentMoveScore;
                    bestMove = thisCoor;
                }
            }
            System.out.println("Score chosen: " + bestMoveScore);
            System.out.println("Number of tries was: " + miniMaxAssistant.getCounter());
            return bestMove;
        }


        private GameCoordinate getRandomMove (){
            Random rnd = new Random();
            int x = rnd.nextInt(board.getDimension());
            int y = rnd.nextInt(board.getDimension());
            GameCoordinate current = board.getCoordinate(x, y);

            while (current.isOccupied()) {
                x = rnd.nextInt(board.getDimension());
                y = rnd.nextInt(board.getDimension());
                current = board.getCoordinate(x, y);
            }
            return current;
        }

        public void setDepthForAgent ( int depthForAgent){
            this.depthForAgent = depthForAgent;
        }
    }

