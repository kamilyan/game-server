package com.hit.services;

import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;

public class GameServerController {
	private GamesService games;

	public GameServerController(int capacity) {
		games = new GamesService(capacity);
	}

	public int newGame(String gameType, String opponent) {
		return games.newGame(gameType, opponent);

	}
	public void endGame(Integer gameId) throws UnknownIdException{
		games.endGame(gameId);

	}
	public char[][] restartGame(Integer gameId) throws UnknownIdException{
		return games.restartGame(gameId);
	}
	public IGameAlgo.GameState updateMove(Integer gameId, GameBoard.GameMove playerMove) throws UnknownIdException{
		return games.updateMove(gameId, playerMove);

	}
	public char[][] computerStartGame(Integer gameId) throws UnknownIdException {
		return games.computerStartGame(gameId);
	}
	public char[][] getBoardState(Integer gameId) throws UnknownIdException {
		return games.getBoardState(gameId);
	}
}
