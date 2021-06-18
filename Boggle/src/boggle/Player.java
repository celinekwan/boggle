package boggle;
/* Player class
 * June 15th, 2021
 * Class for creating a player object
 */

public class Player {

    private int score = 0; //score of the player
    private boolean isTurn = false; //determines whether the player goes next

    //getter method for score
    public int getScore() {
        return score;
    }
    //setter method for score
    public void setscore(int point) {
        this.score = score;
    }
    //method that adds to the score
    public void addScore(int point) {
        score += point;
    }
    //method that subtracts from the score
    public void subtractScore(int point) {
        score -= point;
    }
    //method that determines the player's action for the next round
    public void setTurn() {
        if (isTurn == false) {
            isTurn = true;
        }
        else {
            isTurn = false;
        }
    }
    //getter method for player's action for the following round
    public boolean isTurn() {
        return isTurn;
    }

}
