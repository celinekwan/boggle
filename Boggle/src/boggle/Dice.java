package boggle;
/* Dice class
 * June 15th, 2021
 * Class that helps generate and returns an 5*5 array of letters that form the board.
 */

public class Dice {

    /*
     * declare and initialize dice sets arrays: strings, constants
     */
    private static String[] diceSet1 = { "AAAFRS" , "AAEEEE" , "AAFIRS" , "ADENNN" , "AEEEEM" };
    private static String[] diceSet2 = { "AEEGMU" , "AEGMNN" , "AFIRSY" , "BJKQXZ" , "CCNSTW" };
    private static String[] diceSet3 = { "CEIILT" , "CEILPT" , "CEIPST" , "DDLNOR" , "DHHLOR" };
    private static String[] diceSet4 = { "DHHNOT" , "DHLNOR" , "EIIITT" , "EMOTTT" , "ENSSSU" };
    private static String[] diceSet5 = { "FIPRSY" , "GORRVW" , "HIPRRY" , "NOOTUW" , "OOOTTU" };

    /*
     * get a new dice set
     */
    public static char[][] getSet() {

        // String array to store chosen board
        String[] chosenSet = new String[5];
        // char 2D array to store board letters
        char[][] diceBoard = new char[5][5];

        /*
         * pick an integer between 1 and 5 (inclusive)
         */
        int random = (int) (Math.random()*(5-1))+1;

        /*
         * pick set based on random number
         */
        if (random==1) {
            chosenSet = diceSet1;
        } else if (random==2) {
            chosenSet = diceSet2;
        } else if (random==3) {
            chosenSet = diceSet3;
        } else if (random==4) {
            chosenSet = diceSet4;
        } else if (random==5) {
            chosenSet = diceSet5;
        }

        /*
         * transfer chosen set of strings into char 2D array
         */
        for (int row=0; row<5; row++) {
            for (int col=0; col<5; col++) {
                diceBoard[row][col] = chosenSet[row].charAt(col);
            }
        }

        // return board
        return diceBoard;

    }

}