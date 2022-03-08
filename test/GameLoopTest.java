import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLoopTest {


    @Test
    public void assertHorizonScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(0,1), 'o');
        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(0,3), 'o');
        gb.placeBrick(new GameCoordinate(0,4), 'o');
        int boardScore = sc.horizon('o');
        assertEquals(5, boardScore);
    }

    @Test
    public void assertHorizonScoreForCompNotFullRow() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(0,1), 'o');
        gb.placeBrick(new GameCoordinate(0,2), 'o');

        int boardScore = sc.horizon('o');
        assertEquals(3, boardScore);
    }

    @Test
    public void assertHorizonScoreForCompNotFullRowWithGap() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(0,3), 'o');
        gb.placeBrick(new GameCoordinate(0,4), 'o');

        int boardScore = sc.horizon('o');
        assertEquals(3, boardScore);
    }
    @Test
    public void assertHorizonScoreForCompNotFullRowWithBlock() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(0,0), 'x');
        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(0,3), 'o');
        gb.placeBrick(new GameCoordinate(0,4), 'o');

        int boardScore = sc.getBoardScore();
        assertEquals(2, boardScore);
    }

    @Test
    public void assertHorizonScoreForCompBetterRowBelow() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(0,3), 'o');
        gb.placeBrick(new GameCoordinate(0,4), 'o');

        gb.placeBrick(new GameCoordinate(1,1), 'o');
        gb.placeBrick(new GameCoordinate(1,2), 'o');
        gb.placeBrick(new GameCoordinate(1,3), 'o');
        gb.placeBrick(new GameCoordinate(1,4), 'o');
        gb.print();
        int boardScore = sc.horizon('o');
        assertEquals(4, boardScore);
    }

    @Test
    public void assertHorizonScoreForHuman() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,0), 'x');
        gb.placeBrick(new GameCoordinate(0,1), 'x');
        gb.placeBrick(new GameCoordinate(0,2), 'x');
        gb.placeBrick(new GameCoordinate(0,3), 'x');
        gb.placeBrick(new GameCoordinate(0,4), 'x');
        int boardScore = sc.horizon('x');
        assertEquals(5, boardScore);
    }



    @Test
    public void assertVerticalScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(1,0), 'o');
        gb.placeBrick(new GameCoordinate(2,0), 'o');
        gb.placeBrick(new GameCoordinate(3,0), 'o');
        gb.placeBrick(new GameCoordinate(4,0), 'o');
        int boardScore = sc.vertical('o');
        assertEquals(5, boardScore);
    }

    @Test
    public void assertVerticalOnSecondScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,1), 'o');
        gb.placeBrick(new GameCoordinate(1,1), 'o');
        gb.placeBrick(new GameCoordinate(2,1), 'o');
        gb.placeBrick(new GameCoordinate(3,1), 'o');
        gb.placeBrick(new GameCoordinate(4,1), 'o');
        int boardScore = sc.vertical('o');
        assertEquals(5, boardScore);
    }

    @Test
    public void assertVerticalOnTwoVertsScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        gb.placeBrick(new GameCoordinate(0,1), 'o');
        gb.placeBrick(new GameCoordinate(1,1), 'o');
        gb.placeBrick(new GameCoordinate(2,1), 'o');

        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(1,2), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        gb.placeBrick(new GameCoordinate(3,2), 'o');

        int boardScore = sc.vertical('o');
        assertEquals(4, boardScore);
    }

    @Test
    public void assertVerticalWichHoleScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(1,2), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        gb.placeBrick(new GameCoordinate(4,2), 'o');

        int boardScore = sc.vertical('o');
        assertEquals(3, boardScore);
    }

    @Test
    public void assertVerticalWithBlockScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(1,2), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        gb.placeBrick(new GameCoordinate(3,2), 'x');
        gb.placeBrick(new GameCoordinate(4,2), 'o');

        int boardScore = sc.vertical('o');
        assertEquals(3, boardScore);
    }

    @Test
    public void assertScoreForCompBothVertAndHorizon() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(1,2), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        //VertScore for comp is 3

        gb.placeBrick(new GameCoordinate(4,0), 'o');
        gb.placeBrick(new GameCoordinate(4,1), 'o');
        gb.placeBrick(new GameCoordinate(4,2), 'o');
        gb.placeBrick(new GameCoordinate(4,3), 'o');
        //Horizonal is 4

        int boardScore = sc.getBoardScore(); //7-0 = 7
        assertEquals(7, boardScore);
    }


    @Test
    public void assertScoreForAllBothVertAndHorizon() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,2), 'o');
        gb.placeBrick(new GameCoordinate(1,2), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        //VertScore for comp is 3

        gb.placeBrick(new GameCoordinate(4,0), 'x');
        gb.placeBrick(new GameCoordinate(4,1), 'x');
        gb.placeBrick(new GameCoordinate(4,2), 'x');
        gb.placeBrick(new GameCoordinate(4,3), 'x');
        //Horizonal is 4

        int boardScore = sc.getBoardScore(); //3-4 = -1
        assertEquals(-1, boardScore);
    }

    @Test
    public void assertDiagonalScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(1,1), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        gb.placeBrick(new GameCoordinate(3,3), 'o');

        int boardScore = sc.diagonal('o');
        assertEquals(4, boardScore);
    }

    @Test
    public void assertAntiDiagonalScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,4), 'o');
        gb.placeBrick(new GameCoordinate(1,3), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        gb.placeBrick(new GameCoordinate(3,1), 'o');

        int boardScore = sc.diagonal('o');
        assertEquals(4, boardScore);
    }

    @Test
    public void assertAntiDiagonalVsDiagonalScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,4), 'o');
        gb.placeBrick(new GameCoordinate(1,3), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        gb.placeBrick(new GameCoordinate(3,1), 'o');

        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(1,1), 'o');

        int boardScore = sc.diagonal('o');
        assertEquals(4, boardScore);
    }
    @Test
    public void assertDiagonalVsAntiDiagonalScoreForComp() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);

        gb.placeBrick(new GameCoordinate(0,4), 'o');
        gb.placeBrick(new GameCoordinate(1,3), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');

        gb.placeBrick(new GameCoordinate(0,0), 'o');
        gb.placeBrick(new GameCoordinate(1,1), 'o');
        gb.placeBrick(new GameCoordinate(2,2), 'o');
        gb.placeBrick(new GameCoordinate(3,3), 'o');

        int boardScore = sc.diagonal('o');
        assertEquals(4, boardScore);
    }

    @Test
    public void assertCheckStreakFprBlockers() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        GameCoordinate [] array = new GameCoordinate[gb.getDimension()];
        array[0] =new GameCoordinate(0,0, 'o');
        array[1] =new GameCoordinate(0,1, 'o');
        array[2] =new GameCoordinate(0,2, 'x');
        array[3] =new GameCoordinate(0,3 );
        array[4] =new GameCoordinate(0,4 );


        int boardScore = sc.checkStreak(array, 'o');
        assertEquals(-10, boardScore);
    }

    @Test
    public void assertCheckStreakWithBlockers() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        GameCoordinate [] array = new GameCoordinate[gb.getDimension()];
        array[0] =new GameCoordinate(0,0, 'x');
        array[1] =new GameCoordinate(0,1, 'o');
        array[2] =new GameCoordinate(0,2, 'o');
        array[3] =new GameCoordinate(0,3 );
        array[4] =new GameCoordinate(0,4 );


        int boardScore = sc.checkStreak(array, 'o');
        assertEquals(-10, boardScore);
    }

    @Test
    public void assertFindBlockingsForTwoBlockersOneIsWall() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        GameCoordinate [] array = new GameCoordinate[gb.getDimension()];
        array[0] =new GameCoordinate(0,0, 'o');
        array[1] =new GameCoordinate(0,1, 'o');
        array[2] =new GameCoordinate(0,2, 'x');
        array[3] =new GameCoordinate(0,3 );
        array[4] =new GameCoordinate(0,4 );


        int blocks = sc.findBlockings(array, 2, 0 , 'o');
        assertEquals(2, blocks);
    }

    @Test
    public void assertFindBlockingsForBlockersOneIsWall() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        GameCoordinate [] array = new GameCoordinate[gb.getDimension()];
        array[0] =new GameCoordinate(0,0, 'o');
        array[1] =new GameCoordinate(0,1, 'o');
        array[2] =new GameCoordinate(0,2);
        array[3] =new GameCoordinate(0,3 );
        array[4] =new GameCoordinate(0,4 );


        int blocks = sc.findBlockings(array, 2, 0 , 'o');
        assertEquals(1, blocks);
    }

    @Test
    public void assertFindBlockingsForBlockersNoBlocks() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        GameCoordinate [] array = new GameCoordinate[gb.getDimension()];
        array[0] =new GameCoordinate(0,0);
        array[1] =new GameCoordinate(0,1, 'o');
        array[2] =new GameCoordinate(0,2, 'o');
        array[3] =new GameCoordinate(0,3 );
        array[4] =new GameCoordinate(0,4 );


        int blocks = sc.findBlockings(array, 2, 1 , 'o');
        assertEquals(0, blocks);
    }

    @Test
    public void assertFindBlockingsForBlockersBothSidesBlocks() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        GameCoordinate [] array = new GameCoordinate[gb.getDimension()];
        array[0] =new GameCoordinate(0,0, 'x');
        array[1] =new GameCoordinate(0,1, 'o');
        array[2] =new GameCoordinate(0,2, 'o');
        array[3] =new GameCoordinate(0,3 , 'x');
        array[4] =new GameCoordinate(0,4 );
        int blocks = sc.findBlockings(array, 2, 1 , 'o');
        assertEquals(2, blocks);
    }

    @Test
    public void assertFindBlockingsForBlockersOnLastlocks() {
        GameBoard gb = new GameBoard(5);
        ScoreController sc = new ScoreController(gb);
        GameCoordinate [] array = new GameCoordinate[gb.getDimension()];
        array[0] =new GameCoordinate(0,0);
        array[1] =new GameCoordinate(0,1, 'o');
        array[2] =new GameCoordinate(0,2, 'o');
        array[3] =new GameCoordinate(0,3 , 'o');
        array[4] =new GameCoordinate(0,4 , 'o');
        int blocks = sc.findBlockings(array, 4, 1 , 'o');
        assertEquals(1, blocks);
    }



}