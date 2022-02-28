/* TODO:
    1. Standardisera spelstorlek, välj ett bräde
    2. Skriv en metod i GameBoard som returnerar en samling med alla lediga platser, se till att player och agent bara lägger där.
    4. Skriv en metod i GameBoard som returnerar lediga platser som är nära en spelares brickor

 */

public class main {
    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop();
        gameLoop.run();

    }
}
