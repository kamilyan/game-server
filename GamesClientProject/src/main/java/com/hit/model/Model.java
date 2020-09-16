package com.hit.model;

public interface Model {
	void newGame(String gameType, String opponentType); //Sends the server a "new-game" request
	void updatePlayerMove(int row, int col); //Sends the server an "update-move" message
	void endGame();
	void startGame();
}
