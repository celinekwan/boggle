package boggle;
/*
 * ICS4U Computer Science Independent Study Project (ISP)
 * Name of Program: Boggle Game
 * Group Members: Celine Kwan, Jeffrey Qin, Vincent Zhou
 * Date: June 15, 2021
 * Description: The project aims to create a user interface such that one or two players can
 * play together to find the words on a board of letters by connecting them.
 * Concepts used:
 * - Recursive Binary Search (for dictionary look up)
 * - File handling (for dictionary check)
 * - Recursion (for finding words)
 * - 2D arrays (for board creation)
 * - GUI (components and ActionListeners)
 * - Timers in Swing Applications
 * - Concurrency & Threading (SwingWorker)
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.swing.Timer;
import java.util.concurrent.ExecutionException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.SwingWorker; // worker threads and swingworker

public class BoggleGame extends JFrame implements ActionListener {

	private static JFrame myFrame;

	static CardLayout cardLayout;
	static JPanel cards; // a panel that uses CardLayout
	final static String INSTRUCTIONPANEL = "Menu";
	final static String GAMEPANEL = "Game";
	final static String VICTORYPANEL = "Winner";
	
	// ------------COLORS -----------------
	Color lightColor = new Color(241, 250, 238); // light blue color
	Color mediumColor = new Color(168, 218, 220); // medium blue color
	Color paleColor = new Color(250, 237, 205); // pale color

	// ------------MENU TAB -----------------

	// panels for menu tab
	private static JPanel menuPanel; // general panel
	private static JPanel instructionPanel; // panel for all the instructions
	private static JPanel settingPanel; // panel for all the settings
	// individual panels for settings
	private static JPanel numPlayerPanel;
	private static JPanel turnsPanel;
	private static JPanel maxPtsPanel;
	private static JPanel minLengthPanel;
	private static JPanel textFilePanel;
	private static JPanel textFileUserPanel;

	// layouts for menu tab
	private static GridLayout menuLayout;
	private static BoxLayout instructionLayout;
	private static BoxLayout settingLayout;
	private static GridLayout settingComponentLayout; // layout for each individual setting

	// Labels for instructions
	private static JLabel instruction1;
	private static JLabel instruction2;
	private static JLabel instruction3;
	private static JLabel instruction4;

	// Labels that list out all the settings
	private static JLabel settingInstruction1;
	private static JLabel settingInstruction2;
	private static JLabel settingInstruction3;
	private static JLabel settingInstruction4;
	private static JLabel settingInstruction5;

	private static JLabel warningLabel; // label that is shown when user enters invalid input
	private static JLabel emptyLineInstructionLabel;
	private static JLabel emptyLineInstructionLabel2;

	// Labels and interactive components for settings
	// number of turns
	private static JLabel turnsLabel;
	private static JTextField turnsTextField;
	// maximum number of points
	private static JLabel maxPtsLabel;
	private static JTextField maxPtsTextField;
	// minimum word length
	private static JLabel minLengthLabel;
	private static JTextField minLengthTextField;
	// number of players
	private static JLabel numPlayerLabel;
	private static JComboBox numPlayerComboBox;
	// txt file as dictionary
	private static JLabel textFileLabel;
	private static JTextField textFileTextField;
	private static JLabel textFileExtensionLabel;
	private static JButton textFileButton; // start button

	// ------------GAME TAB -----------------

	// panels for game tab
	private static JPanel gamePanel;// general panel
	private static JPanel leftPanel;
	private static JPanel midPanel;
	private static JPanel rightPanel;
	// Panels for player 1's interface
	private static JPanel leftTopPanel;
	private static JPanel leftMidPanel;
	private static JPanel leftBottomPanel;
	private static JPanel leftBottomSubPanel;
	private static JPanel leftBottomSubPanel2;
	// Panels for the middle interface (timer + board)
	private static JPanel midTopPanel;
	private static JTextArea wordGuessedCorrectlyTextArea;// displays all guessed words
	private static JPanel midMidPanel;
	private static JPanel midBottomPanel;
	// Panels for player 2's interface
	private static JPanel rightTopPanel;
	private static JPanel rightMidPanel;
	private static JPanel rightBottomPanel;
	private static JPanel rightBottomSubPanel;
	private static JPanel rightBottomSubPanel2;

	// Layouts for game tab
	private static BoxLayout gameLayout;
	private static GridLayout playerLayout; // General layout for players' interface
	private static GridLayout midLayout; // General layout for middle interface
	private static FlowLayout midBottomLayout;
	private static BoxLayout leftTopLayout;
	private static BoxLayout rightTopLayout;
	private static BoxLayout leftBottomLayout;
	private static BoxLayout rightBottomLayout;
	private static GridLayout bottomSubLayout;
	private static GridLayout boardLayout;
	private static BoxLayout midTopLayout;
	private static GridLayout fillLayout;// Empty layout used to fill in space

	// Interactive components for Players' interface
	// name label
	private static JLabel player1Label;
	private static JLabel player2Label;

	// number of points label
	private static JLabel player1PointLabel;
	private static JLabel player2PointLabel;
	// prompt for word label
	private static JLabel player1WordLabel;
	private static JLabel player2WordLabel;
	// text field for entering word
	private static JTextField player1TextField;
	private static JTextField player2TextField;
	// enter word button
	private static JButton player1EnterButton;
	private static JButton player2EnterButton;

	// Components for the middle part of the interface
	private static JLabel timerLabel; // timer
	private static JButton[][] diceButton; // Array of buttons that represents the board
	private static JButton resetButton;
	private static JButton exitButton;
	private static JButton restartButton;

	private static ImageIcon icon;
	private static ImageIcon headsIcon = createImageIcon("images/small-heads.gif"); // from https://tenor.com/bCh3s.gif
	private static ImageIcon tailsIcon = createImageIcon("images/small-tails.gif"); // from https://tenor.com/bCh3s.gif
	private static String message;

	// ------------VICTORY TAB -----------------
	// Panels for victory tab
	private static JPanel victoryPanel;
	private static JPanel victoryTopPanel;
	private static JPanel victoryMidPanel;
	private static JPanel victoryBottomPanel;

	// Layouts for victory tab
	private static BoxLayout victoryPanelLayout;
	private static BoxLayout victoryPanelTopLayout;
	private static GridLayout victoryPanelMidLayout;

	// Labels for victory tab
	private static JLabel victoryPanelReachedReasonLabel;
	private static JLabel victoryLabel;
	// name labels
	private static JLabel player1VictoryLabel;
	private static JLabel player2VictoryLabel;
	// total points labels
	private static JLabel player1VictoryPointLabel;
	private static JLabel player2VictoryPointLabel;

	/*
	 * -----------LOGIC VARIABLES---------------
	 */
	private static int numOfTurns;
	private static double TURNRECORDER = 0;
	private static double currentPlayer; // 1 for player 1, 2 for player2/AI
	private static int numOfPoints = 0;
	private static int currentHighestPoint = 0;
	private static int minWordLength;
	private static ArrayList<String> dictionary = new ArrayList<String>();
	private static boolean isFound;
	private static String wordFound; // saves the word found

	private static int numOfPlayers;
	private static int firstToPlay;

	private static Player player1 = new Player();
	private static Player player2 = new Player();
	private static AI ai = new AI(dictionary);
	private static ArrayList<String> wordsGuessed;
	private static String guess; // temporarily save guessed word

	private static char[][] boardValues;

	private static int player1FinalScore;
	private static int player2FinalScore;

	// timer
	private static Timer myTimer;
	private static int elapsedSeconds = 0;
	private static final int TOTAL_SECONDS = 15;
	private static final int ONE_SECOND = 1000;
	private static final String PRESS_BUTTON_AGAIN = "Find A Word Within %02d Seconds!";
	private static final String YOU_LOSE = "Go faster next time!";

	public void addComponentToPane(Container pane) {
		fillLayout = new GridLayout(1, 1);

		/*
		 * --------MENU---------------
		 */
		// set up general panel
		menuPanel = new JPanel();
		menuLayout = new GridLayout(2, 1);
		menuPanel.setLayout(menuLayout);

		// set up instruction panel
		instructionPanel = new JPanel();
		instructionLayout = new BoxLayout(instructionPanel, BoxLayout.Y_AXIS);
		instructionPanel.setLayout(instructionLayout);
		instructionPanel.setBackground(mediumColor);

		// initiate instructions and add instructions to panel
		instruction1 = new JLabel("Please complete the following setup before you start the game.");
		settingInstruction1 = new JLabel("1. The number of players.");
		settingInstruction2 = new JLabel(
				"2. The number of turns. Whoever has the most points after the last turn wins.");
		settingInstruction3 = new JLabel("3. The number of points a player needs to earn to win.");
		settingInstruction4 = new JLabel("4. The minimum length for a valid word.");
		settingInstruction5 = new JLabel("5. The file that will be used as a list of valid words.");
		emptyLineInstructionLabel = new JLabel(" ");
		emptyLineInstructionLabel2 = new JLabel(" ");
		instruction2 = new JLabel(
				"You will take turns looking for words in the array by connecting the dice together.");
		instruction3 = new JLabel("Points will be added and subtracted based on the length of the word guessed.");
		instruction4 = new JLabel("Player with the most points will win. Good luck!");
		instructionPanel.add(instruction1);
		instructionPanel.add(settingInstruction1);
		instructionPanel.add(settingInstruction2);
		instructionPanel.add(settingInstruction3);
		instructionPanel.add(settingInstruction4);
		instructionPanel.add(settingInstruction5);
		instructionPanel.add(emptyLineInstructionLabel);
		instructionPanel.add(instruction2);
		instructionPanel.add(instruction3);
		instructionPanel.add(instruction4);
		instructionPanel.add(emptyLineInstructionLabel2);
		warningLabel = new JLabel("");
		instructionPanel.add(warningLabel);

		// set up setting panel
		settingPanel = new JPanel();
		settingLayout = new BoxLayout(settingPanel, BoxLayout.Y_AXIS);
		settingPanel.setLayout(settingLayout);

		// initiate interactive components for settings
		// number of players
		numPlayerLabel = new JLabel("Indicate the number of players: ");
		String[] numPlayerArray = { "1", "2" };
		numPlayerComboBox = new JComboBox<String>(numPlayerArray);
		// number of turns
		turnsLabel = new JLabel("Enter the number of turns: ");
		turnsTextField = new JTextField("", 5);
		onlyDigitAllowed(turnsTextField); // call the method of onlyDigitAllowed for the textfield to only accept digit
											// values
		// maximum number of points
		maxPtsLabel = new JLabel("Enter the max no. of points: ");
		maxPtsTextField = new JTextField("", 5);
		onlyDigitAllowed(maxPtsTextField); // call the method of onlyDigitAllowed for the textfield to only accept digit
		// minimum word length
		minLengthLabel = new JLabel("Enter the min. word length: ");
		minLengthTextField = new JTextField("", 5);
		onlyDigitAllowed(minLengthTextField); // only accept digit
		// text file for dictionary
		textFileLabel = new JLabel("Enter the name of the wordlist file: ");
		textFileTextField = new JTextField();
		textFileExtensionLabel = new JLabel(".txt");
		textFileButton = new JButton("BEGIN");

		// set up individual panels for each setting
		settingComponentLayout = new GridLayout();
		// number of players
		numPlayerPanel = new JPanel(settingComponentLayout);
		numPlayerPanel.add(numPlayerLabel);
		numPlayerPanel.add(numPlayerComboBox);
		// number of turns
		turnsPanel = new JPanel(settingComponentLayout);
		turnsPanel.add(turnsLabel);
		turnsPanel.add(turnsTextField);
		turnsPanel.setBackground(mediumColor);
		// max number of points
		maxPtsPanel = new JPanel(settingComponentLayout);
		maxPtsPanel.add(maxPtsLabel);
		maxPtsPanel.add(maxPtsTextField);
		// minimum word length
		minLengthPanel = new JPanel(settingComponentLayout);
		minLengthPanel.add(minLengthLabel);
		minLengthPanel.add(minLengthTextField);
		minLengthPanel.setBackground(mediumColor);
		// text file to be used for dictionary
		textFileUserPanel = new JPanel(settingComponentLayout);
		textFileUserPanel.add(textFileTextField);
		textFileUserPanel.add(textFileExtensionLabel);
		textFileUserPanel.add(textFileButton);
		textFilePanel = new JPanel(settingComponentLayout);
		textFilePanel.add(textFileLabel);
		textFilePanel.add(textFileUserPanel);

		// add components to setting panel
		settingPanel.add(numPlayerPanel);
		settingPanel.add(turnsPanel);
		settingPanel.add(maxPtsPanel);
		settingPanel.add(minLengthPanel);
		settingPanel.add(textFilePanel);

		// add instruction and settings panel to menu panel
		menuPanel.add(instructionPanel);
		menuPanel.add(settingPanel);
		textFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Begin Button Clicked.");

				if (!turnsTextField.getText().equals("") && !maxPtsTextField.getText().equals("")
						&& !minLengthTextField.getText().equals("") && !textFileTextField.getText().equals(""))
				// conditions such that the button can only be called when the user enters all
				// settings
				{
					delayedAction(); // the actions for when the begin button is clicked
				} else {
					// set the warning label text to be at the desired location
					warningLabel.setText(
							"                                                                                         Please enter all settings!");
				}
			}
		});

		/*
		 * --------GAME---------------
		 */
		// set up general game panels
		gamePanel = new JPanel();
		gameLayout = new BoxLayout(gamePanel, BoxLayout.X_AXIS);
		gamePanel.setLayout(gameLayout);

		// set up the three major panels
		// initiate layouts
		playerLayout = new GridLayout(3, 1); // 3 rows, 1 col
		midLayout = new GridLayout(3, 1);
		// set up panel for player 1
		leftPanel = new JPanel();
		leftPanel.setLayout(playerLayout);
		// set up panel for player 2
		rightPanel = new JPanel();
		rightPanel.setLayout(playerLayout);
		// set up middle panel
		midPanel = new JPanel();
		midPanel.setLayout(midLayout);

		// Set up top panel for player 1 that displays name and total points
		leftTopPanel = new JPanel();
		leftTopLayout = new BoxLayout(leftTopPanel, BoxLayout.Y_AXIS);
		leftTopPanel.setLayout(leftTopLayout);
		leftTopPanel.setBackground(paleColor);
		player1Label = new JLabel("Player 1");
		player1PointLabel = new JLabel("Point: 0");
		// add components to panel
		leftTopPanel.add(player1Label);
		leftTopPanel.add(player1PointLabel);
		leftPanel.add(leftTopPanel);

		// Set up middle panel for player 1
		leftMidPanel = new JPanel();
		leftMidPanel.setBackground(lightColor);
		leftPanel.add(leftMidPanel);

		// set up bottom panel for player 1 that allows user to enter word
		leftBottomPanel = new JPanel();
		leftBottomLayout = new BoxLayout(leftBottomPanel, BoxLayout.Y_AXIS);
		leftBottomPanel.setLayout(leftBottomLayout);
		leftBottomSubPanel = new JPanel();
		leftBottomSubPanel.setBackground(paleColor);
		bottomSubLayout = new GridLayout(1, 2);
		leftBottomSubPanel.setLayout(bottomSubLayout);
		// initialize interactive components and add them to panel
		player1WordLabel = new JLabel("Word: ");
		player1TextField = new JTextField();
		leftBottomSubPanel.add(player1WordLabel);
		leftBottomSubPanel.add(player1TextField);
		leftBottomSubPanel2 = new JPanel(fillLayout);
		player1EnterButton = new JButton("X");
		leftBottomSubPanel2.add(player1EnterButton);
		player1EnterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				player1EnterButton.setText("Submit Word");

				System.out.println("Player 1's Enter Button Clicked.");
				// checktimer
				checkTimerPlayer1();

			}
		});
		// add individual panels to player 1's general panel
		leftBottomPanel.add(leftBottomSubPanel);
		leftBottomPanel.add(leftBottomSubPanel2);
		leftPanel.add(leftBottomPanel);

		// set up middle panel
		midTopPanel = new JPanel();
		midTopLayout = new BoxLayout(midTopPanel, BoxLayout.Y_AXIS);
		midTopPanel.setLayout(midTopLayout);
		timerLabel = new JLabel("Timer:"); // timer label
		wordGuessedCorrectlyTextArea = new JTextArea("Word Guessed Correctly: " + "\n"); // text area that displays all
																							// words guessed
		// add components to panel
		midTopPanel.add(wordGuessedCorrectlyTextArea);
		midTopPanel.add(timerLabel);

		// set up panel for board
		midMidPanel = new JPanel();
		boardLayout = new GridLayout(5, 5);
		midMidPanel.setLayout(boardLayout);
		midMidPanel.setBackground(mediumColor);

		/* set default button values */
		diceButton = new JButton[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				diceButton[i][j] = new JButton(i + " " + j);
				midMidPanel.add(diceButton[i][j]);
			}
		}
		/* end set buttons */

		// set up the bottom panel for middle that offers reset, refresh, and exit
		// options
		midBottomPanel = new JPanel();
		midBottomLayout = new FlowLayout();
		midBottomPanel.setLayout(midBottomLayout);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Reset Button Clicked.");
				// randomize board

				/* ----GET BOARD VALUES------- */
				boardValues = Dice.getSet();

				/* ----SET BOARD VALUES TO GAME GUI----- */
				for (int row = 0; row < 5; row++) {
					for (int col = 0; col < 5; col++) {
						diceButton[row][col].setText(Character.toString(boardValues[row][col]));
					}
				}

				System.out.println("Reset Button: Board Reset.");
			}
		});

		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { // when the exit button is clicked
				victoryPanelReachedReasonLabel.setText("Exit Button Clicked");
				showVictoryPanelThread(); // call the method to display the victory panel
				System.out.println("Exit Button Clicked.");
			}
		});
		restartButton = new JButton("Restart"); // when the restart button is clicked
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Restart Button Clicked.");
				gamePanel.setVisible(false); // set the gamePanel to be invisible and menuPanel to be visible
				menuPanel.setVisible(true);
				stopTimer(); // stop timer
				restart(); // reset all necessary values and objects

			}
		});

		midBottomPanel.add(resetButton);
		midBottomPanel.add(exitButton);
		midBottomPanel.add(restartButton);

		midPanel.add(midTopPanel);
		midPanel.add(midMidPanel);
		midPanel.add(midBottomPanel);

		// Set up top panel for player 2 that displays name and total points
		rightTopPanel = new JPanel();
		rightTopLayout = new BoxLayout(rightTopPanel, BoxLayout.Y_AXIS);
		rightTopPanel.setLayout(rightTopLayout);
		rightTopPanel.setBackground(paleColor);
		player2Label = new JLabel("Player 2");
		player2PointLabel = new JLabel("Point: 0");
		// add components to panel
		rightTopPanel.add(player2Label);
		rightTopPanel.add(player2PointLabel);
		rightPanel.add(rightTopPanel);

		// set up middle panel for player 2.
		rightMidPanel = new JPanel();
		rightMidPanel.setBackground(lightColor);
		rightPanel.add(rightMidPanel);

		// set up bottom panel for player 2 that allows the player to enter words
		rightBottomPanel = new JPanel();
		rightBottomLayout = new BoxLayout(rightBottomPanel, BoxLayout.Y_AXIS);
		rightBottomPanel.setLayout(rightBottomLayout);
		rightBottomPanel.setBackground(paleColor);		rightBottomSubPanel = new JPanel();
		rightBottomSubPanel.setLayout(bottomSubLayout);
		rightBottomSubPanel.setBackground(paleColor);
		// initialize interactive components and add them to panel
		player2WordLabel = new JLabel("Word: ");
		player2TextField = new JTextField();
		rightBottomSubPanel.add(player2WordLabel);
		rightBottomSubPanel.add(player2TextField);
		rightBottomSubPanel2 = new JPanel(fillLayout);
		player2EnterButton = new JButton("X");
		rightBottomSubPanel2.add(player2EnterButton);
		player2EnterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				player2EnterButton.setText("Submit Word");

				System.out.println("Player 2's Enter Button Clicked.");
				// checktimer
				checkTimerPlayer2();

			}
		});

		// add individual panels to player 2's general panel
		rightBottomPanel.add(rightBottomSubPanel);
		rightBottomPanel.add(rightBottomSubPanel2);
		rightPanel.add(rightBottomPanel);

		// add all panels to the main panel
		gamePanel.add(leftPanel);
		gamePanel.add(midPanel);
		gamePanel.add(rightPanel);

		/*
		 * --------VICTORY-------------
		 */

		// set up victory general panel
		victoryPanel = new JPanel();
		victoryPanelLayout = new BoxLayout(victoryPanel, BoxLayout.Y_AXIS);
		victoryPanel.setLayout(victoryPanelLayout);
		victoryPanel.setBackground(mediumColor);

		// set up the top part of victory panel
		victoryTopPanel = new JPanel();
		victoryPanelTopLayout = new BoxLayout(victoryTopPanel, BoxLayout.Y_AXIS);
		victoryTopPanel.setLayout(victoryPanelTopLayout);
		// initialize individual components and add them to panel
		victoryPanelReachedReasonLabel = new JLabel("");
		victoryLabel = new JLabel("Winner: ");
		victoryTopPanel.add(victoryPanelReachedReasonLabel);
		victoryTopPanel.add(victoryLabel);
		victoryTopPanel.setBackground(mediumColor);

		// set up the middle part of victory panel
		victoryMidPanel = new JPanel();
		victoryPanelMidLayout = new GridLayout(1, 2);
		victoryMidPanel.setLayout(victoryPanelMidLayout);
		// add labels that display players
		player1VictoryLabel = new JLabel("Player 1: ");
		player2VictoryLabel = new JLabel("Player 2: ");
		victoryMidPanel.add(player1VictoryLabel);
		victoryMidPanel.add(player2VictoryLabel);
		victoryMidPanel.setBackground(lightColor);
		
		// set up the bottom part of victory panel
		victoryBottomPanel = new JPanel(victoryPanelMidLayout);
		victoryBottomPanel.setBackground(paleColor);

		player1VictoryPointLabel = new JLabel();
		player2VictoryPointLabel = new JLabel();
		victoryBottomPanel.add(player1VictoryPointLabel);
		victoryBottomPanel.add(player2VictoryPointLabel);

		// add individual panels to the general victory panel
		victoryPanel.add(victoryTopPanel);
		victoryPanel.add(victoryMidPanel);
		victoryPanel.add(victoryBottomPanel);

		// add panels to cards
		cards = new JPanel(new CardLayout());
		cards.setPreferredSize(new Dimension(1000,600)); // dimensions
		cards.add(menuPanel, INSTRUCTIONPANEL);
		cards.add(gamePanel, GAMEPANEL);
		cards.add(victoryPanel, VICTORYPANEL);

		pane.add(cards, BorderLayout.CENTER);
	}

	/*
	 * ------------------METHODS----------------------
	 */

	/**
     * method to stop timer when restart button is clicked
     */
    public void stopTimer() {
    	if (myTimer != null && myTimer.isRunning()) { // if timer is running at the time player 1's enter button is pressed
            myTimer.stop(); // stop timer
            myTimer = null;
    	}
    }
	
	/**
	 * reset method is used when the restart button is called: it is going to reset
	 * all necessary players and boards
	 */
	public static void restart() {
		if (numOfPlayers == 1) {
			player1 = new Player();
			ai = new AI(dictionary);
		} else if (numOfPlayers == 2) {
			player2 = new Player();
			player1 = new Player();
		}
		numOfPlayers = 0;
		numOfTurns = 0;
		TURNRECORDER = 0;
		currentPlayer = 0;
		numOfPoints = 0;
		minWordLength = 0;
		dictionary.clear(); // clear the dictionary arraylist created
		wordsGuessed.clear();
		firstToPlay = 0;

		// set all textfields from the menu panel to show no words
		turnsTextField.setText("");
		maxPtsTextField.setText("");
		minLengthTextField.setText("");
		textFileTextField.setText("");
		wordGuessedCorrectlyTextArea.setText("Word Guessed Correctly: " + "\n");

		// set all textfields and labels from the game panel to show no words
		player1TextField.setText("");
		player2TextField.setText("");
		player1PointLabel.setText("");
		player2PointLabel.setText("");

		// reset the board
		/* ----GET BOARD VALUES------- */
		boardValues = Dice.getSet();

		/* ----SET BOARD VALUES TO GAME GUI----- */
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				diceButton[row][col].setText(Character.toString(boardValues[row][col]));
			}
		}

	}

	/**
	 * method for starting & stopping timers after clicking player 1's enter button
	 * - if timer hasn't started when button clicked -> start timer - if timer has
	 * started when button clicked: get entered word & update score & move to next
	 * player if word is submitted
	 */
	private void checkTimerPlayer1() {
		if (myTimer != null && myTimer.isRunning()) { // if timer is running at the time player 1's enter button is
														// pressed
			myTimer.stop(); // stop timer
			myTimer = null;

			// do these after timer is stopped

			// disable player 1's button

			player1EnterButton.setText("X");
			player1EnterButton.setBackground(null);
			player1EnterButton.setOpaque(true);
			player1EnterButton.setEnabled(false);

			// get inputted word from player 1 + update score
			guess = player1TextField.getText().toUpperCase();
			player1TextField.setText(""); // reset text field

			System.out.println("-guess: " + guess + "-");

			if (guess.length() < minWordLength) { // word is shorter than min word length
				timerLabel.setText("Word is too small. Min length is " + minWordLength);
				delayedTrackTurnThreadCall(); // continue to next turn
				System.out.println("Word is too small. Min length is " + minWordLength);
			} else if (wordsGuessed.contains(guess)) { // word is guessed already
				timerLabel.setText("Word " + guess + " is guessed already.");
				delayedTrackTurnThreadCall(); // continue to next turn
				System.out.println("Word " + guess + " is guessed already.");
			} else {
				wordsGuessed.add(guess); // add guessed word to word tracking list
				wordFound = getWord(boardValues, guess); // check if word is found on game board
				System.out.println("Word Found: " + wordFound);
				System.out.println("-after search-");
				if (wordFound != null) { // connected letters found
					isFound = inDictionaryOrNot(dictionary, guess); // if words is a valid word from dictionary
				}
				if (isFound) {
					// word is found and valid
					System.out.println("p1's word is found.");
					timerLabel.setText("p1's word " + guess + " is found."); // show message
					player1.addScore(guess.length()); // update score
					wordGuessedCorrectlyTextArea.append(guess + "\n"); // show guessed word in text area on GUI
				} else {
					System.out.println("p1's word is NOT found.");
					timerLabel.setText("p1's word " + guess + " is NOT found."); // show message

					if (player1.getScore() <= guess.length()) { // will get negative score
						player1.setscore(0); // so reset to 0
					} else {
						player1.subtractScore(guess.length()); // update score
					}
				}
				player1PointLabel.setText("Point: " + Integer.toString(player1.getScore())); // update score label on
																								// GUI
				isFound = false; // set isFound to false again
				System.out.println("Call trackTurnThread() to move to next player.");
				delayedTrackTurnThreadCall(); // move to next turn
			}

			// update info about current player
			currentPlayer++;
			TURNRECORDER += 0.5;
			player1EnterButton.setEnabled(false); // set the player1 button to be unable to click, but the player2
													// button to be able to click
			player2EnterButton.setEnabled(true);

			System.out.println("Player 1's turn ended.");

			System.out.println("Call trackTurnThread() to move to next player.");

		} else { // if timer not started at the time when button is pressed
			elapsedSeconds = 0;
			myTimer = new Timer(ONE_SECOND, new TimerListener()); // create new 15s timer
			myTimer.start(); // start timer
			String text = String.format(PRESS_BUTTON_AGAIN, TOTAL_SECONDS); // "find timer within xx seconds"
			timerLabel.setText(text); // display the above message on timerLabel
		}
	}

	/**
	 * method for starting & stopping timers after clicking player 1's enter button
	 * - if timer hasn't started when button clicked -> start timer - if timer has
	 * started when button clicked: get entered word & update score & move to next
	 * player if word is submitted
	 */
	private void checkTimerPlayer2() {
		if (myTimer != null && myTimer.isRunning()) { // if timer is running at the time player 1's enter button is
														// pressed
			myTimer.stop();
			myTimer = null;

			// do these after timer is stopped

			// disable player 2's button
			player2EnterButton.setText("Button disabled");
			player2EnterButton.setBackground(null);
			player2EnterButton.setOpaque(true);
			player2EnterButton.setEnabled(false);

			// get inputted word from player 2 + update score
			guess = player2TextField.getText().toUpperCase();
			player2TextField.setText("");
			System.out.println("-guess: " + guess + "-");

			if (guess.length() < minWordLength) { // word is shorter than min word length
				timerLabel.setText("Word is too small. Min length is " + minWordLength);
				delayedTrackTurnThreadCall(); // continue to next turn
				System.out.println("Word is too small. Min length is " + minWordLength);
			} else if (wordsGuessed.contains(guess)) { // word is guessed already
				timerLabel.setText("Word " + guess + " is guessed already.");
				delayedTrackTurnThreadCall(); // continue to next turn
				System.out.println("Word " + guess + " is guessed already.");
			} else {
				wordsGuessed.add(guess); // add guessed word to word tracking list
				wordFound = getWord(boardValues, guess); // check if word is found on game board
				System.out.println("Word Found: " + wordFound);
				System.out.println("-after search-");
				if (wordFound != null) { // connected letters found
					isFound = inDictionaryOrNot(dictionary, guess); // if words is a valid word from dictionary
				}
				if (isFound) {
					// word is found and valid
					System.out.println("p2's word is found."); // show message
					timerLabel.setText("p2's word " + guess + " is found."); // show guessed word in text area on GUI
					player2.addScore(guess.length());
					wordGuessedCorrectlyTextArea.append(guess + "\n");
				} else {
					System.out.println("p2's word is NOT found.");
					timerLabel.setText("p2's word " + guess + " is NOT found."); // show message

					if (player2.getScore() <= guess.length()) { // will get negative score
						player2.setscore(0); // so reset to 0
					} else {
						player2.subtractScore(guess.length()); // update score
					}
				}
				player2PointLabel.setText("Point: " + Integer.toString(player2.getScore())); // update score

				isFound = false; // set isFound to false again
				
				System.out.println("Call trackTurnThread() to move to next player.");
				delayedTrackTurnThreadCall();
			}

			// update info about current player
			currentPlayer++;
			TURNRECORDER += 0.5;
			player1EnterButton.setEnabled(true); // set the buttons to be able to click correspondingly
			player2EnterButton.setEnabled(false);

			System.out.println("Player 2's turn ended.");

		} else { // if timer not started at the time when button is pressed
			elapsedSeconds = 0;
			myTimer = new Timer(ONE_SECOND, new TimerListener()); // create new 15s timer
			myTimer.start(); // start timer
			String text = String.format(PRESS_BUTTON_AGAIN, TOTAL_SECONDS); // "find timer within xx seconds"
			timerLabel.setText(text); // display the above message on timerLabel
		}

	}

	/**
	 * class for countdown timer
	 */
	private static class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			elapsedSeconds++;

			if (elapsedSeconds == TOTAL_SECONDS) { // if countown reaches 0
				myTimer.stop(); // stop timer

				/*
				 * update timerLabel to tell player their time is up & disable their button
				 */

				if (currentPlayer % 2 != 0 && numOfPlayers == 2) { // player 1 vs player 2, player 1's time is up
					timerLabel.setText(YOU_LOSE + " Player 2 goes next.");
					player1TextField.setText("");
					player1EnterButton.setText("Button disabled");
					player1EnterButton.setBackground(null);
					player1EnterButton.setOpaque(true);
				} else if (currentPlayer % 2 != 0 && numOfPlayers == 1) { // player 1 vs AI, player 1's time is up
					timerLabel.setText(YOU_LOSE + " AI goes next.");
					player1TextField.setText("");
					player1EnterButton.setText("X");
					player1EnterButton.setBackground(null);
					player1EnterButton.setOpaque(true);
				} else if (numOfPlayers == 2 && currentPlayer % 2 == 0) { // player 1 vs player 2, player 2's time is up
					timerLabel.setText(YOU_LOSE + " Player 1 goes next.");
					player2TextField.setText("");
					player2EnterButton.setText("X");
					player2EnterButton.setBackground(null);
					player2EnterButton.setOpaque(true);
				} else if (numOfPlayers == 1 && currentPlayer % 2 == 0) { // player 1 vs AI, AI's time is up
					timerLabel.setText("AI is too slow! Player 1 goes next.");
				}

				// update current player details
				currentPlayer++;
				TURNRECORDER += 0.5;

				System.out.println("Call trackTurnThread() to move to next player.");
				// move to next player
				delayedTrackTurnThreadCall();

			} else {
				// show player the countdown by updating timerLabel
				String text = String.format(PRESS_BUTTON_AGAIN, TOTAL_SECONDS - elapsedSeconds);
				timerLabel.setText(text);
			}
		}
	}

	/**
	 * method for staying in menu panel until "Begin" button is pressed, then show
	 * coin toss pane
	 */
	private static void delayedAction() {
		Timer timer = new Timer(10, new ActionListener() { // delay whatever is in actionPerformed method by 10 ms
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// initialize max turns, max points, min word length, and no. of players based
				// on menu input
				numOfTurns = Integer.parseInt(turnsTextField.getText());
				numOfPoints = Integer.parseInt(maxPtsTextField.getText());
				minWordLength = Integer.parseInt(minLengthTextField.getText());
				numOfPlayers = Integer.parseInt(numPlayerComboBox.getSelectedItem().toString());
				resetButton.setEnabled(false); // only allow user to press reset after two turns

				// check results in console
				System.out.println("Max No. of Turns: " + numOfTurns + "\nMax No. of Points: " + numOfPoints
						+ "\nMin word length: " + minWordLength + "\nNo. of Human Players: " + numOfPlayers);

				// initialize dictionary - based on user input into textFileTextField
				try {
					getDictionary(textFileTextField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace(); // generate error message if file isn't found
				}

				/* ------------COIN TOSS PANE------------------- */

				// generate random number
				if (Math.random() < 0.5) {
					icon = headsIcon; // heads
					if (numOfPlayers == 1) { // if player 1 vs AI
						message = "You go first!"; // player 1 is first
					} else { // if player 1 vs player 2
						message = "Player 1 goes first!"; // player 1 is first
						player1EnterButton.setEnabled(true); // if player goes first, enabling player 1 button but
																// disabling player 2
						player2EnterButton.setEnabled(false);
					}
					// track who goes first
					firstToPlay = 1;
					currentPlayer = 1;
				} else {
					icon = tailsIcon;
					if (numOfPlayers == 1) {
						message = "AI goes first!";
					} else {
						message = "Player 2 goes first!";
						player2EnterButton.setEnabled(true);
						player1EnterButton.setEnabled(false);
					}
					firstToPlay = 2;
					currentPlayer = 2;
				}
				System.out.println(
						"First to play (1 for p1, 2 for p2): " + firstToPlay + "\nCurrent player: " + currentPlayer);

				/* ------------COIN TOSS PANE ------------------- */
				System.out.println("Show Coin Toss Pane.");
				showJOptionPane();

				// call game pane thread
				showGamePanelThread();

			}
		});
		timer.setRepeats(false); // 10ms delay runs only once
		timer.start(); // start delay
	}

	/**
	 * thread for AI's turn
	 */
	private static void aiTurnThread() {
		SwingWorker swAiTurn = new SwingWorker() { // create new background thread
			@Override
			protected Object doInBackground() throws Exception { // logic for what happens in thread

				// get word + update score
				System.out.println("AI's turn.");
				timerLabel.setText("AI is guessing.");

				// call method for guessing word and updating score based on guess
				delayedAiTurnCall();

				return null;
			}
		};

		swAiTurn.execute(); // schedules this SwingWorker for execution on a worker thread.
	}

	/**
	 * method for executing the delayed turn call for the AI
	 */
	private static void delayedAiTurnCall() {
		Timer timer = new Timer(2000, new ActionListener() { // wait for 2s before executing actionPerformed
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guess = ai.getWord(dictionary, minWordLength).toUpperCase(); // get a word from dictionary that isn't
																				// shorter than minwordlength

				System.out.println("-guess: " + guess + "-");

				if (guess.length() < minWordLength) { // if guess is shorter than min word length
					timerLabel.setText("Word is too small. Min length is " + minWordLength); // tell player 1 that AI's
																								// word is too small
					delayedTrackTurnThreadCall(); // move to next player (player 1)
					System.out.println("Word is too small. Min length is " + minWordLength);
				} else if (wordsGuessed.contains(guess)) { // if word is already guessed
					timerLabel.setText("Word " + guess + " is guessed already.");
					delayedTrackTurnThreadCall(); // move to next player (player 1)
					System.out.println("Word " + guess + " is guessed already.");
				} else if (!search(boardValues, guess)) { // if the word cannot be found by connecting letters on the
															// board
					timerLabel.setText("Word " + guess + " is NOT found");
					delayedTrackTurnThreadCall(); // move to p1
					System.out.println("Word is not present on the board");
				} else {
					wordsGuessed.add(guess); // add guessed word to word tracking list
					wordFound = getWord(boardValues, guess); // check if word is found on game board
					System.out.println("Word Found: " + wordFound);
					System.out.println("-after search-");
					if (wordFound != null) { // connected letters found
						isFound = inDictionaryOrNot(dictionary, guess); // if words is a valid word from dictionary
					}
					if (isFound) {
						// word is found and valid
						System.out.println("AI's word is found."); // show message
						timerLabel.setText("AI's word " + guess + " is found.");
						ai.addScore(guess.length());
						wordGuessedCorrectlyTextArea.append(guess + "\n");
					} else {
						System.out.println("AI's word is NOT found.");
						timerLabel.setText("AI's word " + guess + " is NOT found."); // show message

						if (ai.getScore() <= guess.length()) { // will get negative score
							ai.setscore(0); // so reset to 0
						} else {
							ai.subtractScore(guess.length()); // update score
						}
					}
					player2PointLabel.setText("Point: " + Integer.toString(ai.getScore()));

					System.out.println("Call trackTurnThread() to move to next player.");
					delayedTrackTurnThreadCall(); // move to next turn
				}

				// update info about current player
				currentPlayer++;
				TURNRECORDER += 0.5;

				System.out.println("AI's turn ended.");
			}
		});
		timer.setRepeats(false);// 2s delay runs only once
		timer.start(); // start timer
	}

	/**
	 * wait for 2s before calling trackTurnThread to move to next person
	 */
	private static void delayedTrackTurnThreadCall() {
		Timer timer = new Timer(2000, new ActionListener() { // 2s = 2000ms
			@Override
			public void actionPerformed(ActionEvent arg0) {
				trackTurnThread(); // call trackTurnThread after 2s
			}
		});
		timer.setRepeats(false); // timer runs only once
		timer.start(); // timer starts
	}

	/**
	 * method for creating heads/tails icon
	 * 
	 * @param path
	 * @return
	 */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = BoggleGame.class.getResource(path);// find image location

		// image should be in an image folder
		// the image folder should be in the same folder as this Boggle class

		if (imgURL != null) {
			return new ImageIcon(imgURL); // return gif
		} else {
			System.err.println("Couldn't find file: " + path); // can't find image resource
			return null; // doesn't return gif
		}
	}

	/**
	 * show coin toss pane
	 */
	private static void showJOptionPane() {

		// param: JFrame to display on, message (who's going first), title of this pop
		// up,
		// type of JOptionPane (INFORMATION_MESSAGE means one with custom title, custom
		// icon)
		// pass icon to show (heads/tails gif)
		JOptionPane.showMessageDialog(myFrame, message, "Coin toss dialog", JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * open file that contains dictionary words & save words into dictionary
	 * ArrayList
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private static void getDictionary(String fileName) throws Exception {
		File file = new File(fileName + ".txt"); // create file
		Scanner scan = new Scanner(file); // create file scanner

		while (scan.hasNextLine()) { // there's still more words in the file
			dictionary.add(scan.nextLine()); // save words into dictionary ArrayList
		}

		dictionary.removeAll(Collections.singleton(null)); // remove all null values in the arraylist in case there's
															// empty space

		scan.close(); // close scanner

	}

	/**
	 * search word in dictionary file
	 * 
	 * @param dictionary is the arraylist created
	 * @param word       is the word the player entered
	 * @return true when the word exists in dictionry and false otherwise
	 */
	public static boolean inDictionaryOrNot(ArrayList<String> dictionary, String word) {

		int index = binarySearchRec(dictionary, 0, dictionary.size() - 1, word); // call the binary search method to
																					// determine the index of the word
		System.out.println(index);
		if (index == -1) { // if the binary search returns -1, or not found
			return false; // word not found in dictionary
		}
		return true; // word found in dictionary -> valid
	}

	/**
	 * recursive binary search method to search through the entire dictionary
	 * 
	 * @param arr  is the arraylist, or dictionary, passed in
	 * @param l    is the leftmost element of index
	 * @param r    is the rightmost element of index
	 * @param word is the word user entered
	 * @return the index of the word when found, and -1 when the word does not exist
	 *         in the file
	 */
	public static int binarySearchRec(ArrayList<String> arr, int l, int r, String word) {
		if (r >= l) {
			int mid = l + (r - l) / 2;

			// If the element is present at the middle itself
			if (arr.get(mid).equalsIgnoreCase(word)) {
				return mid;
			}

			// If element is smaller than mid, then it can only be present in left subarray
			if (arr.get(mid).compareToIgnoreCase(word) > 0) {
				return binarySearchRec(arr, l, mid - 1, word);
			}

			// Else the element can only be present in right subarray
			else {
				return binarySearchRec(arr, mid + 1, r, word);
			}
		}

		// Reach here and return -1 when element is not present in array
		System.out.println("Recursive method end reached!");
		return -1;
	}

	/**
	 * the method used for allowing jtextfield to only accept digit
	 * 
	 * @param textField is the JTextField passed in
	 */
	public static void onlyDigitAllowed(JTextField textField) {
		textField.addKeyListener(new KeyAdapter() { // add key listener to the textfield to track things entered
			public void keyPressed(KeyEvent e) {
				String value = textField.getText();
				int l = value.length();
				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyChar() == '\b') { // the condition for
																								// allowing only certain
																								// values be entered in
																								// the textfield
					textField.setEditable(true); // when only certain values passed it, it allows entering
					warningLabel.setText("");
				} else {
					textField.setEditable(false);
					// set the warning sign to the user for entering digit at the desired location
					warningLabel.setText(
							"                                                                                         Enter Digit Please!");
				}
			}
		});
	}

	/**
	 * determine if word entered is found on the board connected
	 * 
	 * @param board
	 * @param word
	 * @return
	 */
	public static String getWord(char[][] board, String word) {
		if (search(board, word) == true) { // if letters are found in board connected
			return word; // return word as string
		}
		return null; // return nothing
	}

	/**
	 * search through the entire board for the first letter of the word entered, and
	 * call searchWord to recursively check surrounding that letter
	 * 
	 * @param board is the 2d array of char
	 * @param word  is the word user entered
	 * @return true when it's found and false otherwise
	 */
	public static boolean search(char[][] board, String word) {
		for (int i = 0; i < board.length; i++) { // loop through the entire 2d array
			for (int k = 0; k < board[i].length; k++) {
				if (word.charAt(0) == board[i][k] && searchWord(board, word, i, k, -1, -1, 1)) { // return true when the
																									// first letter of
																									// the word exists
																									// on the board and
																									// all other letters
																									// exist surrounding
																									// that first letter
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * search the 5x5 grid around a letter
	 * 
	 * @param board is the 2d char passed in
	 * @param word  is the word user entered
	 * @param iniR  is the initial index of row that we are starting to search
	 * @param iniC  is the initial index of column that we are starting to search
	 * @param preIR is the previous letter index of row that we don't need to go
	 *              back again
	 * @param preIC is the previous letter index of column that we don't need to go
	 *              back
	 * @param index is the index of the string at which the letter we are searching
	 * @return
	 */
	public static boolean searchWord(char[][] board, String word, int iniR, int iniC, int preIR, int preIC, int index) {
		boolean found = false; // set a tempopary boolean variable to false
		for (int i = iniR - 1; i <= iniR + 1; i++) { // loop through the 8 letters surrounding the initial letter on the
														// board
			for (int k = iniC - 1; k <= iniC + 1; k++) {
				if (!(i == iniR && k == iniC) && (i >= 0 && i < board.length) && (k >= 0 && k < board[i].length)
						&& !(i == preIR && k == preIC)) { // conditions for the places that shouldn't be searched
					if ((word.length() == index + 1)
							&& Character.toLowerCase(board[i][k]) == Character.toLowerCase(word.charAt(index))) { // base
																													// case,
																													// if
																													// the
																													// last
																													// letter
																													// of
																													// the
																													// word
																													// exists
																													// on
																													// the
																													// board
						found = true;
						break;
					} else if (Character.toLowerCase(board[i][k]) == Character.toLowerCase(word.charAt(index))) { // recursive
																													// case,
																													// if
																													// the
																													// letter
																													// of
																													// the
																													// word
																													// exists
																													// on
																													// the
																													// board
						found = searchWord(board, word, i, k, iniR, iniC, index + 1); // recursively call the same
																						// method to loop through all
																						// letter surrounding it
					}
				}
			}
		}
		return found; // return the boolean variable
	}

	/**
	 * thread to track whose turn it is & show this by updating the status of the
	 * buttons (enabled/disabled)
	 */
	private static void trackTurnThread() {
		// create a worker thread
		// GUI specific threads are called SwingWorker
		// https://www.geeksforgeeks.org/swingworker-in-java/
		SwingWorker swPlayGame = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception { // contains logic of background task
				
				if(TURNRECORDER >= 2) { // let the user to be able to click reset only until two turns
                    resetButton.setEnabled(true);
                }
				
				System.out.println("In trackTurnThread().");

				// print info about new turn to player on console
				System.out.println("-------------------------" + "\nTurn Recorder: " + TURNRECORDER
						+ "\nMax num of turns: " + numOfTurns + "\nCurrent highest points: " + currentHighestPoint
						+ "\nMax num of Points: " + numOfPoints + "\nCurrent Player: " + currentPlayer
						+ "\n-------------------------");

				// update current highest point
				if (numOfPlayers == 1) { // player 1 vs AI
					if (player1.getScore() >= ai.getScore()) { // player 1 has higher score
						currentHighestPoint = player1.getScore();
					} else {
						currentHighestPoint = ai.getScore(); // AI has higher score
					}
				} else if (numOfPlayers == 2) { // player 1 vs player 2
					if (player1.getScore() >= player2.getScore()) { // player 1 has higher score
						currentHighestPoint = player1.getScore();
					} else {
						currentHighestPoint = player2.getScore(); // player 2 has higher score
					}
				}

				// check if max no. of turns is reached or max no. of points is reached
				// if not, continue
				if (TURNRECORDER < numOfTurns && currentHighestPoint < numOfPoints) {
					timerLabel.setText("");
					/* ----------player 1 vs AI----------- */
					if (numOfPlayers == 1) {
						if (currentPlayer % 2 != 0) { // player 1's turn
							System.out.println("********Player 1's turn********");
							timerLabel.setText("Player 1, click your button!"); // timerlabel prompt
							player1EnterButton.setText("Click to Start Timer"); // button prompt
							player1EnterButton.setBackground(Color.GREEN); // color button
							player1EnterButton.setOpaque(true);
							player1EnterButton.setEnabled(true);
						}
						if (currentPlayer % 2 == 0) { // AI's turn
							System.out.println("********AI's turn********");
							aiTurnThread(); // call thread to guess a word
						}
					}
					/* ----------player 1 vs 2 ----------- */
					else if (numOfPlayers == 2) {

						if (currentPlayer % 2 != 0) { // player 1's turn

							System.out.println("********Player 1's turn********");
							timerLabel.setText("Player 1, click your button!"); // timerlabel prompt
							player1EnterButton.setText("Click to Start Timer"); // button prompt
							player1EnterButton.setBackground(Color.GREEN); // enable button
							player1EnterButton.setOpaque(true);
						}
						if (currentPlayer % 2 == 0) { // player 2's turn

							System.out.println("********Player 2's turn********");
							timerLabel.setText("Player 2, click your button!"); // timerlabel prompt
							player2EnterButton.setText("Click to Start Timer"); // button prompt
							player2EnterButton.setBackground(Color.GREEN);
							player2EnterButton.setOpaque(true);
							player2EnterButton.setEnabled(true);
						}
					}

				} else {// max turn/points is reached
					System.out.println("Check if max turns/points are reached.");
					if (TURNRECORDER >= numOfTurns) { // max turns is reached
						System.out.println("Max No. of TURNS Reached (" + Integer.toString(numOfTurns) + ").");
						victoryPanelReachedReasonLabel
								.setText("Max No. of TURNS Reached (" + Integer.toString(numOfTurns) + ").");// show why
																												// victory
																												// panel
																												// is
																												// showed
																												// to
																												// user:
																												// max
																												// turns
																												// reached
					} else if (currentHighestPoint >= numOfPoints) {
						System.out.println(("Max No. of POINTS Reached (" + Integer.toString(numOfPoints) + " )."));
						victoryPanelReachedReasonLabel
								.setText("Max No. of POINTS Reached (" + Integer.toString(numOfPoints) + ").");// show
																												// why
																												// victory
																												// panel
																												// is
																												// showed
																												// to
																												// user:
																												// max
																												// points
																												// reached
					}
					System.out.println("Call showVictoryPanelThread() from trackTurnThread().");
					showVictoryPanelThread(); // show victory panel
				}

				return null;
			}
		};
		swPlayGame.execute(); // schedules this SwingWorker for execution on a worker thread.
	}

	/**
	 * method for updating labels' info on victory panel
	 */
	private static void getVictoryPanelInfo() {

		System.out.println("In getVictoryPanelInfo().");

		player1FinalScore = player1.getScore(); // get player 1's score

		// edit details of victory pane
		player1VictoryPointLabel.setText("Points for Player 1: " + Integer.toString(player1FinalScore)); // display
																											// points
																											// for
																											// player 1
																											// in GUI
																											// label
		System.out.println("Points for Player 1: " + Integer.toString(player1FinalScore));

		// if player 1 vs player 2
		if (numOfPlayers == 2) {

			player2FinalScore = player2.getScore(); // get player 2's score
			player2VictoryPointLabel.setText("Points for Player 2: " + Integer.toString(player2FinalScore)); // display
																												// points
																												// for
																												// player
																												// 2 in
																												// GUI
																												// label
			System.out.println("Points for Player 2: " + Integer.toString(player2FinalScore));

			if (player2FinalScore > player1FinalScore) { // player 2 wins
				victoryLabel.setText("Winner: Player 2!");
				System.out.println("Set Victory Label Text: Winner: Player 2!");
			} else if (player1FinalScore > player2FinalScore) { // player 1 wins
				victoryLabel.setText("Winner: Player 1!");
				System.out.println("Set Victory Label Text: Winner: Player 1!");
			} else { // tie
				victoryLabel.setText("You are Tied!");
				System.out.println("Set Victory Label Text: You are tied!");
			}
		}

		// if player 1 vs AI
		else if (numOfPlayers == 1) {
			player2FinalScore = ai.getScore();
			player2VictoryPointLabel.setText("Points for AI: " + Integer.toString(player2FinalScore));
			System.out.println("Points for AI: " + Integer.toString(player2FinalScore));
			if (player2FinalScore > player1FinalScore) { // player 2 wins
				victoryLabel.setText("Winner: AI !");
				System.out.println("Set Victory Label Text: Winner: AI !");
			} else if (player1FinalScore > player2FinalScore) { // player 1 wins
				victoryLabel.setText("Winner: Player 1!");
				System.out.println("Set Victory Label Text: Winner: Player 1!");
			} else { // tie
				victoryLabel.setText("You are Tied!");
				System.out.println("Set Victory Label Text: You are Tied!");
			}
		}
		System.out.println("End of getVictoryPanelInfo().");
	}

	/**
	 * thread for updating info on & showing victory panel
	 */
	private static void showVictoryPanelThread() {
		// create a worker thread
		// GUI specific threads are called SwingWorker
		// https://www.geeksforgeeks.org/swingworker-in-java/
		SwingWorker swShowVictoryPanel = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception { // contains logic of background task

				System.out.println("In showVictoryPanelThread().");
				System.out.println("Call getVictoryPanelInfo() - start.");
				getVictoryPanelInfo(); // update victory panel labels according to scores
				System.out.println("Call getVictoryPanelInfo() - end.");

				// show next card (menu -> game)
				System.out.println("Show victory panel - start.");
				cardLayout.show(cards, VICTORYPANEL);// show victory panel
				System.out.println("Show victory panel - end.");

				return null;
			}
		};
		swShowVictoryPanel.execute(); // schedules this SwingWorker for execution on a worker thread.
	}

	/**
	 * thread for showing game panel
	 */
	private static void showGamePanelThread() {
		// create a worker thread
		// GUI specific threads are called SwingWorker
		// https://www.geeksforgeeks.org/swingworker-in-java/
		SwingWorker swShowGamePanel = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception { // contains logic of the background task
				System.out.println("In showGamePanelThread().");

				// paint without player 2 label, textfield, and button
				if (numOfPlayers == 1) {
					rightBottomPanel.removeAll(); // remove all components
					rightBottomPanel.revalidate();
					rightBottomPanel.repaint();
				}

				// paint with player 2 labels, textfields, and button
				else if (numOfPlayers == 2) {
					rightBottomPanel.add(rightBottomSubPanel); // add all components for player 2
					rightBottomPanel.add(rightBottomSubPanel2);
					rightPanel.add(rightBottomPanel);
				}

				/* ----GET BOARD VALUES------- */
				boardValues = Dice.getSet(); // pick one of the boards from Dice class and pass into boardValues char 2D
												// array
				wordsGuessed = new ArrayList<String>(); // instantiate list of words guessed

				/* ----SET BOARD VALUES TO GAME GUI----- */
				for (int row = 0; row < 5; row++) { // for every row in 2D array
					for (int col = 0; col < 5; col++) { // for every col in every row
						// set letter from char[][] array to buttons on game panel board GUI
						diceButton[row][col].setText(Character.toString(boardValues[row][col]));
					}
				}

				/* ------------GAME PANE------------------- */
				cardLayout = (CardLayout) (cards.getLayout()); // create a cardLayout -
																// https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
				cardLayout.show(cards, GAMEPANEL); // show game pane - https://stackoverflow.com/a/11744004/13916590
				System.out.println("Show Game Panel.");

				trackTurnThread();// check who's turn is next

				return null;
			}
		};

		swShowGamePanel.execute(); // schedules this SwingWorker for execution on a worker thread.
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * ------------METHODS FOR HOW THE PROGRAM RUNS------------
	 */

	/**
	 * method for creating and setting up the window.
	 */
	private static void createAndShowGUI() {

		myFrame = new JFrame("Boggle"); // instantiate the JFrame
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if "x" on window is clicked, then exit program

		BoggleGame demo = new BoggleGame();
		demo.addComponentToPane(myFrame.getContentPane());

		// Display the window.
		myFrame.pack();
		myFrame.setVisible(true);
	}

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		JFrame myFrame = new JFrame("Boggle"); // instantiate the JFrame
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if "x" on window is clicked, then exit program

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();// create and set up window (start game!)
			}
		});
	}

}
