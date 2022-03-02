public class GameBoard {
    private final int dimentions;
    private GameCoordinate[][] content;

//Pleade observe, the board counts from 0...di in both dimentions

    public GameBoard(int d) {
        this.dimentions = d;
        content = new GameCoordinate[d][d];
        initialise();
    }

    public GameBoard() {
        this(5);
    }


    private void initialise() {
        for (int i = 0; i < dimentions; i++) {
            for (int j = 0; j < dimentions; j++) {
                content[i][j] = new GameCoordinate(i, j);
            }
        }
     }


    public void print() {
        System.out.println("GameBoard: ");
        for (int i = 0; i < dimentions; i++) {
            System.out.print("|");
            for (int j = 0; j < dimentions; j++) {
                System.out.print(content[i][j]);
                System.out.print("|");
            }
            System.out.println("");
        }
    }


    public GameCoordinate placeBrick(GameCoordinate gameCoordinate, char playerBrick) {
        content[gameCoordinate.getX()][gameCoordinate.getY()] = gameCoordinate;
        gameCoordinate.setOwner(playerBrick);
        return gameCoordinate;
    }



    public int getDimentions() {
        return dimentions;
    }

    public GameCoordinate getCoordinate(int x, int y){
        return content[x][y];
    }

    public boolean checkWin(GameCoordinate move, char player) {
        //check col
        for(int i = 0; i < dimentions; i++){
            if(content[move.getX()][i].getOwner() != player)
                break;
            if(i == dimentions-1){
                return true;
            }
        }

        //check row
        for(int i = 0; i < dimentions; i++){
            if(content[i][move.getY()].getOwner() != player)
                break;
            if(i == dimentions-1){
                return true;
            }
        }

        //check diag
        if(move.getX() == move.getY()){
            //we're on a diagonal
            for(int i = 0; i < dimentions; i++){
                if(content[i][i].getOwner() != player)
                    break;
                if(i == dimentions-1){
                    return true;
                }
            }
        }

        //check anti diag (thanks rampion)
        if(move.getX() + move.getY() == dimentions - 1){
            for(int i = 0; i < dimentions; i++){
                if(content[i][(dimentions-1)-i].getOwner() != player)
                    break;
                if(i == dimentions-1){
                    return true;
                }
            }
        }
        return false;
    }


    //Metoder avsedda för att lösa delproblem med enbart 3 i rad
//
//
//    public char checkWin(char x) {
//    //1. Gå igenom alla platser
//        //2. en modellerad vist är samma tecken på dessacord t.ex 1,0, 1,1, 1,2 eller 0,1  1,1, 2,1
//        ArrayList<GameCoordinate[]> horizontals = new ArrayList<>();
//        ArrayList<GameCoordinate[]> verticals = new ArrayList<>();
//        ArrayList<GameCoordinate> allCoordinates = new ArrayList<>();
//
//
//        for (int i = 0; i < dimentions; i++) {
//            for(int j = 0; j<dimentions; j++) {
//                allCoordinates.add(content[i][j]);
//            }
//        }
//        String vertical = "";
//        for(int j = 0; j < allCoordinates.size(); j ++){
//            for (int i = 0; i < dimentions; i++) {
//                vertical += allCoordinates.get(i).getOwner();
//            }
//        }
//
//
//
//        if (vertical.charAt(0) == vertical.charAt(1) && vertical.charAt(0) == vertical.charAt(2)){
//            return vertical.charAt(0);
//        }
//        char deciding = vertical.charAt(0);
//        for(char ch: vertical.toCharArray()){
//            if
//        }
//
//        for (char ch : vertical.toCharArray()) {
//            int curr = vertical.indexOf(ch);
//            if ( curr % dimentions == 0 ) {
//
//            }
//        }
//
//        if (vertical.charAt(3) == vertical.charAt(4) && vertical.charAt(3) == vertical.charAt(5)){
//            return vertical.charAt(3);
//        }
//        return Character.MIN_VALUE;
//    }


//    public HashSet<GameCoordinate> getEmpties(){
//        HashSet<GameCoordinate> empties = new HashSet<>();
//        for (int i = 0; i < dimentions; i++){
//            for (int j = 0; j < dimentions; j++){
//               if (!content[i][j].isOccupied()){
//                   empties.add(content[i][j]);
//               }
//            }
//        }
//        return empties;
//    }
}
