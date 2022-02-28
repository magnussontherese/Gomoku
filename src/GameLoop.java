import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class GameLoop {
    private GameBoard board;
    private Agent agent;
    private boolean running = true;
    private boolean isPlayerTurn = true;
    private Random rnd = new Random();

    public void run(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the komoku board game! Please select your dimension (>= 5)");
        int dim = in.nextInt();
        board = new GameBoard(dim);
        while (running) {
            if (isPlayerTurn) {
                System.out.println("Place your brick (x, y)");
                String playerMove = "";
                playerMove = in.next(); //We are always thinking that the player chooses a free space and correct values :)
                makeMove(playerMove);
                isPlayerTurn = false;
            } else {
                computerPlay();
                isPlayerTurn = true;
            }
            board.print();
            board.evaluate();
        }
    }

    private void evaluate() {
    }

    private void computerPlay()  {
        System.out.println("ComputerPlaying...");
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        agent.evaluateAndMove(board);
        int x = rnd.nextInt( 5);//Just for now
        int y = rnd.nextInt(5);
        board.placeBrick(x, y, 'o');

    }

    private void makeMove(String playerMove) {
        String[] chopped = playerMove.split(",");
        int x = Integer.parseInt(chopped[0]);
        int y = Integer.parseInt(chopped[1]);
        board.placeBrick(x, y, 'x');
    }
}
