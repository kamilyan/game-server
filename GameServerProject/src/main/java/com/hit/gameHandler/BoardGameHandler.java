package com.hit.gameHandler;

import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.IGameAlgo.GameState;

public class BoardGameHandler {

	private IGameAlgo game;

	public BoardGameHandler(IGameAlgo game) {
		this.game= game;
	}

	public IGameAlgo.GameState playOneRound(GameMove playerMove) {
		// If the game is not over than update first the player move and after that computer move..
		if(GameState.IN_PROGRESS==game.getGameState(playerMove)) {
			game.updatePlayerMove(playerMove);
			if(GameState.IN_PROGRESS==game.getGameState(playerMove)) {
				game.calcComputerMove();
			}
		}

		return game.getGameState(playerMove);
	}

	public IGameAlgo getter() {
		return game;
	}
	public char[][] computerStartGame() {
		game.calcComputerMove();

		return game.getBoardState();
	}

	public char[][] getBoardState() {
		return game.getBoardState();
	}

}
