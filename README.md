# Boggle
A game of Boggle. Created for ICS4U Final Project.
## What is Boggle? 
The game of Boggle is played on a square board with random letters. The object is to find words formed on the board by contiguous sequences of letters. Letters are considered to be touching if they are horizontally, vertically, or diagonally adjacent. Words can contain duplicate letters, but a single letter on the board may not appear twice in a single word
### Dice Distribution
Boggle Deluxe is played with a 5x5 grid whose 25 dice and letters are distributed according to this layout:

AAAFRS&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
AEEGMU&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
CEIILT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
DHHNOT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
FIPRSY

AAEEEE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
AEGMNN&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
CEILPT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
DHLNOR&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
GORRVW

AAFIRS&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
AFIRSY&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
CEIPST&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
EIIITT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
HIPRRY

ADENNN&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
BJKQXZ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
DDLNOR&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
EMOTTT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
NOOTUW

AEEEEM&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
CCNSTW&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
DHHLOR&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
ENSSSU&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
OOOTTU

## Game - Features and Frames
### Menu
- Displays instructions
- Allows the user to 
  - choose between single player mode (plays against AI) and two player mode
  - set the maximum number of turns (1 turn = both players went)
  - set the minimum number of letters in the word
  - enter the dictionary .txt file containing valid English words
- Includes checks for valid user input
  - the number of points, turns, and length of words should be integers
  - all the text fields should be filled out when "Begin" button is pressed
- A pop up shows who goes first
### Game
- Display a board of random letters using a Graphical User Interface
- The user gets 1 point for every letter identified correctly
- Important messages and prompts are shown in the text above the board
- Each user clicks their button to start a timer 
  - a word has to be entered in 15 seconds, or else, the game moves to the next player
- If the word is valid, it is displayed in a text area, and their score increments
- If the word is not valid, the player is informed of this, and their score decrements
- If the other player is AI, the player is informed of its guesses via the text above the board
- The reset button is used to "shake up" the board
  - it can be used after the second turn is completed
- The exit button is used to end the game and show results (winner & scores)
- The restart button is used to restart the game
### Results
- Displays the winner and scores for both players
