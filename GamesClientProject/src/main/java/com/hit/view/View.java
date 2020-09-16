package com.hit.view;

public interface View {
	
	void start(); //Starts the view
 	void updateViewNewGame(Character[] board); //Update the view when a new-game response message arrives
 	void updateViewGameMove(int gameState, Character[] board); //Updates the game board with the last move
}
