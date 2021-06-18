package boggle;
/* AI class
 * June 15th, 2021
 * Class for creating an AI object.
 */

import java.util.ArrayList;

public class AI {
	
	private int score = 0; // track score
	private char[][] board; // require declaration with other classes for the board
	private static ArrayList<String> wordList; // store an initial list of words 
    
    public AI (ArrayList<String> wordList) {
    	this.wordList = wordList;
    }
	
    // getter method for score 
	public int getScore() { 
		return score;
	}
	
	// setter method for score
	public void setscore(int point) {  
    	this.score = score;
    }
	
	// method that adds to the score 
	public void addScore(int points) { 
		score += points;
	}
	//method that subtracts from the score
	public void subtractScore(int points) { 
		score -= points;
	}
	//method that randomly pick a word from the wordlist
	public String getWord(ArrayList<String> wordList, int minLength) { 
		
		// set min and max boundary of random number generation
		int min = 0; 
		int max = wordList.size()-1;
		int randInt;
		String chosenWord; // store picked word 
		
		do {
			// generate random number
			randInt = (int) Math.floor(Math.random()*(max-min+1)+min);
			chosenWord = wordList.get(randInt); // random number is the picked word's position index in arraylist
		} while (chosenWord.length() < minLength);
		
		return chosenWord;
		
	}

}
