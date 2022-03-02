/* TODO:
    1. Standardisera spelstorlek, välj ett bräde
    2. Skriv en metod i GameBoard som returnerar en samling med alla lediga platser, se till att player och agent bara lägger där.
    4. Skriv en metod i GameBoard som returnerar lediga platser som är nära en spelares brickor
    5. Skriv en metod som kan avgöra om någon har vunnit utifrån hur gameboard ser ut och som returnerar tecknet för vinnande spelare"
    5. (?)Förbered spelet för att kunna representeras som ett träd, vad innehåller noderna? Hur många barn? Egen klass iaf med datainflöde från GameBoard.
    6. (?)Skapa en klass MiniMaxAssistant som äger en instans Trädstrukturen, Agent ska ha en MiniMaxAssistant. (klassuppdelning för att kunna sortera bort irrelevanta detaljer på muntan.
    7. Vi kommer behöva testa alla metoder, programmera med det i åtanke. Hur kan vi testa?
     Trädet:
     Klassen MiniMaxAssistant:



 */

public class main {
    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop();
        gameLoop.run();

    }
}
