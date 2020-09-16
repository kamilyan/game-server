package com.hit.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GamesView implements View {

	private PropertyChangeSupport support;
	private DisplayAGame game;

	public GamesView() {
		support = new PropertyChangeSupport(this);
	}
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		support.addPropertyChangeListener(propertyChangeListener);
	}
	public void start() {//Starts the view
		game = new DisplayAGame(support);

	}

	public void updateViewGameMove(int gameState,Character[] board) { //Updates the game board with the last move
		game.updateViewGameMove(gameState, board);
	}

	public void updateViewNewGame(Character[] board) {//Update the view when a new-game response message arrives
		game.updateViewNewGame(board);
	}
}
