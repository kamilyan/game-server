package com.hit.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class DisplayAGame implements ActionListener {

	private JFrame frame;
	private JLayeredPane layeredPane;
	private JPanel panel_catchTheBunny;
	private JPanel panel_ticTacToe;
	private PropertyChangeSupport support;
	private String type; // the game type;
	private String Opponent; // the opponent;
	private String viewMessage;
	private JButton[][] ticTacTowBoard;
	private JButton[][] catchTheBunny;
	private JButton btnNewGame;
	private JButton btnStartGame;
	private boolean flagTicTacTow;
	private boolean flagCatchTheBunny;
	private boolean flagNewGame;
	private boolean flagStartGame;
	private JRadioButton rdbtnSmart;
	private JRadioButton rdbtnRandom;
	private ButtonGroup bg2;
	private ButtonGroup bg1;
	private JButton btnEndgame;

	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DisplayAGame(PropertyChangeSupport support) {
		this.support= support;
		start();
		flagTicTacTow= false;
		flagCatchTheBunny=false;
		flagNewGame=false;
		flagStartGame=false;


	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 477);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setSize(450,480);
		//Display the window.

		frame.setResizable(true);

		frame.setVisible(true);

		JLabel lblNewLabel = new JLabel("Pick a game");
		lblNewLabel.setBounds(28, 11, 73, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblPickAnOpponent = new JLabel("Pick an opponent");
		lblPickAnOpponent.setBounds(149, 11, 109, 14);
		frame.getContentPane().add(lblPickAnOpponent);

		btnNewGame = new JButton("New-Game");
		btnNewGame.addActionListener(this);
		btnNewGame.setActionCommand("New-Game");
		btnNewGame.setEnabled(false);
		btnNewGame.setBounds(268, 11, 109, 23);
		frame.getContentPane().add(btnNewGame);


		JRadioButton rdbtnNewRadioButton = new JRadioButton("TicTacTow");
		rdbtnNewRadioButton.setBounds(28, 32, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton);


		JRadioButton rdbtnCatchTheBunny = new JRadioButton("CatchTheBunny");
		rdbtnCatchTheBunny.setBounds(28, 58, 122, 23);
		frame.getContentPane().add(rdbtnCatchTheBunny);
		bg1 = new ButtonGroup();
		bg1.add(rdbtnNewRadioButton);
		bg1.add(rdbtnCatchTheBunny);


		rdbtnSmart = new JRadioButton("Smart");
		rdbtnSmart.setBounds(149, 32, 109, 23);
		frame.getContentPane().add(rdbtnSmart);

		rdbtnRandom = new JRadioButton("Random");
		rdbtnRandom.setBounds(148, 58, 109, 23);
		frame.getContentPane().add(rdbtnRandom);
		bg2 = new ButtonGroup();
		bg2.add(rdbtnSmart);
		bg2.add(rdbtnRandom);

		rdbtnNewRadioButton.addActionListener(this);
		rdbtnCatchTheBunny.addActionListener(this);
		rdbtnRandom.addActionListener(this);
		rdbtnSmart.addActionListener(this);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 103, 414, 324);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));

		panel_catchTheBunny = new JPanel();
		layeredPane.add(panel_catchTheBunny, "name_215135907488667");
		panel_catchTheBunny.setLayout(new GridLayout(9,9));

		catchTheBunny = new JButton[9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				catchTheBunny[row][col] = new JButton("");
				catchTheBunny[row][col].addActionListener(this);
				panel_catchTheBunny.add(catchTheBunny[row][col]);
				catchTheBunny[row][col].setActionCommand("UpdatePlayerMove" + "_" + row + "_" + col);
				catchTheBunny[row][col].setEnabled(false);
			}
		}

		panel_ticTacToe = new JPanel();
		layeredPane.add(panel_ticTacToe, "name_215226042083401");
		panel_ticTacToe.setLayout(new GridLayout(3,3));

		btnStartGame = new JButton("Start-Game");
		btnStartGame.setBounds(268, 43, 109, 23);
		btnStartGame.addActionListener(this);
		btnStartGame.setEnabled(false);
		btnStartGame.setActionCommand("Start-Game");
		frame.getContentPane().add(btnStartGame);

		btnEndgame = new JButton("End-Game");
		btnEndgame.setBounds(268, 69, 109, 23);

		btnEndgame.addActionListener(this);
		btnEndgame.setActionCommand("End-Game");
		frame.getContentPane().add(btnEndgame);
		btnEndgame.setEnabled(false);

		ticTacTowBoard = new JButton[3][3];
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				ticTacTowBoard[row][col] = new JButton("");
				ticTacTowBoard[row][col].setEnabled(false);
				ticTacTowBoard[row][col].addActionListener(this);
				panel_ticTacToe.add(ticTacTowBoard[row][col]);
				ticTacTowBoard[row][col].setActionCommand("UpdatePlayerMove" + "_" + row + "_" + col);
			}
		}	


	}

	public void actionPerformed(ActionEvent e) {
		if ("New-Game".equals(e.getActionCommand())) 
		{
			viewMessage = "New-Game" + "_" + type + "_" + Opponent;

			support.firePropertyChange("viewMessage",null, viewMessage);
			btnEndgame.setEnabled(true);
			btnStartGame.setEnabled(true);
		}
		//radio button
		else if ("TicTacTow".equals(e.getActionCommand())) {
			switchPanels(panel_ticTacToe);
			type = "Tic Tac Toe";
			flagTicTacTow= true;
			lockButtons(ticTacTowBoard);
			lockButtons(catchTheBunny);

		} else if ("CatchTheBunny".equals(e.getActionCommand())) {
			switchPanels(panel_catchTheBunny);
			type = "Catch The Bunny";
			flagCatchTheBunny=true;
			lockButtons(ticTacTowBoard);
			lockButtons(catchTheBunny);

		} else if ("Smart".equals(e.getActionCommand())) {
			Opponent = e.getActionCommand();
			if(flagTicTacTow || flagCatchTheBunny) {
				btnNewGame.setEnabled(true);
				lockButtons(ticTacTowBoard);
				lockButtons(catchTheBunny);
				flagNewGame=true;
				flagStartGame=true;
			}
		} else if ("Random".equals(e.getActionCommand())) {
			Opponent = e.getActionCommand();
			if(flagTicTacTow || flagCatchTheBunny) {
				btnNewGame.setEnabled(true);
				lockButtons(ticTacTowBoard);
				lockButtons(catchTheBunny);
				flagNewGame=true;
				flagStartGame=true;
			}
		}
		else if (e.getActionCommand().contains("UpdatePlayerMove")) {
			if(flagNewGame || flagStartGame) {
				support.firePropertyChange("viewMessage", null, e.getActionCommand());
			}
		}
		else if ("End-Game".equals(e.getActionCommand())) {
			type = "End-Game";
			JOptionPane.showMessageDialog(frame, "The Gave Is Over!");
			support.firePropertyChange("viewMessage", null, e.getActionCommand());
			flagTicTacTow= false;
			flagCatchTheBunny=false;
			flagNewGame=false;
			flagStartGame=false;
			bg2.clearSelection();
			bg1.clearSelection();
			lockButtons(ticTacTowBoard);
			lockButtons(catchTheBunny);
			btnNewGame.setEnabled(false);
			btnStartGame.setEnabled(false);
			btnEndgame.setEnabled(false);

		}
		else if("Start-Game".equals(e.getActionCommand())) {
			support.firePropertyChange("viewMessage", null, e.getActionCommand());
			btnEndgame.setEnabled(true);
		}
	}
	public void switchPanels(JPanel panel_catchTheBunny) {
		layeredPane.removeAll();
		layeredPane.add(panel_catchTheBunny);
		layeredPane.repaint();
		layeredPane.revalidate();
		btnNewGame.setEnabled(false);
		btnStartGame.setEnabled(false);
		flagTicTacTow= false;
		flagCatchTheBunny=false;
		flagNewGame=false;
		flagStartGame=false;
		bg2.clearSelection();

		btnEndgame.setEnabled(false);

		lockButtons(ticTacTowBoard);

		lockButtons(catchTheBunny);


	}


	public void lockButtons(JButton[][] board) {

		if(board.length==3) {
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					board[row][col].setEnabled(false);

				}
			}
		}
		else {
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					board[row][col].setEnabled(false);
				}
			}
		}
	}

	public void updateViewGameMove(int gameState,Character[] board) { //Updates the game board with the last move
		if (gameState >0 ) {
			if (board.length == 9 ) // Tic Tac Tow board game. 
			{
				int i = 0;

				for (int row = 0; row < 3; row++)
					for (int col = 0; col < 3; col++) {
						if (String.valueOf(board[i]).equals("-")) {

							ticTacTowBoard[row][col].setText("");;

						} else if (String.valueOf(board[i]).equals("O")) {
							ticTacTowBoard[row][col].setText("O");
						} else if (String.valueOf(board[i]).equals("X")) {
							ticTacTowBoard[row][col].setText("X");

						}
						i++;
					}
			}
			if (board.length== 81) {
				int i =0;
				for (int row = 0; row < 9; row++)
					for (int col = 0; col < 9; col++) {

						if (String.valueOf(board[i]).equals("-")) {
							catchTheBunny[row][col].setText("");

						} else if (String.valueOf(board[i]).equals("K")) {
							catchTheBunny[row][col].setText("K");

						} else if (String.valueOf(board[i]).equals("B")) {

							catchTheBunny[row][col].setText("B");
						}
						i++;

					}
			}
		}

		if (gameState == 0) {
			JOptionPane.showMessageDialog(frame, "Illegal Move!!!!");
		}

		else if (gameState == 1) {
			JOptionPane.showMessageDialog(frame, "You Won!");

		} else if (gameState == 2) {
			JOptionPane.showMessageDialog(frame, "You Lost!");

		} else if (gameState == 3) {

			JOptionPane.showMessageDialog(frame, "It's A Tie!");
		}

	}

	public void updateViewNewGame(Character[] board) {//Update the view when a new-game response message arrives

		if (board.length== 81) {
			int i =0;
			for (int row = 0; row < 9; row++)
				for (int col = 0; col < 9; col++) {

					if (String.valueOf(board[i]).equals("-")) {
						catchTheBunny[row][col].setText("");

					} else if (String.valueOf(board[i]).equals("K")) {
						catchTheBunny[row][col].setText("K");

					} else if (String.valueOf(board[i]).equals("B")) {

						catchTheBunny[row][col].setText("B");
					}
					i++;
					catchTheBunny[row][col].setEnabled(true);

				}
		}
		if (board.length == 9 ) // Tic Tac Tow board game. 
		{
			int i = 0;

			for (int row = 0; row < 3; row++)
				for (int col = 0; col < 3; col++) {
					if (String.valueOf(board[i]).equals("-")) {

						ticTacTowBoard[row][col].setText("");;

					} else if (String.valueOf(board[i]).equals("O")) {
						ticTacTowBoard[row][col].setText("O");
					} else if (String.valueOf(board[i]).equals("X")) {
						ticTacTowBoard[row][col].setText("X");

					}
					i++;
					ticTacTowBoard[row][col].setEnabled(true);
				}
		}

	}
}

