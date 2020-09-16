package com.hit.services;

import java.util.HashMap;
import java.util.Map;

import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameHandler.BoardGameHandler;
import com.hit.games.CatchTheBunnyRandom;
import com.hit.games.CatchTheBunnySmart;
import com.hit.games.TicTacTow.BoardSigns;
import com.hit.games.TicTacTowRandom;
import com.hit.games.TicTacTowSmart;

public class GamesService {

	private int capacity; // Maximum of games that the player can play in parallel.
	private int game_Id; // The ID of a specific game.
	private int numberOfGames; // The number of games, simultaneously the player is playing.
	private BoardGameHandler gameHandler; // used for control the board game steps.
	private Map<Integer,BoardGameHandler> games; // Cache memory for storing all the games, that the player is playing.

	public GamesService(int capacity) {

		this.capacity=capacity;
		games =  new HashMap<Integer,BoardGameHandler>();
		game_Id=0; //initialize member.
		numberOfGames=0;
	}

	//new Game function is supposed to create new game and add it to the cache memory
	public int newGame(String gameType,String opponent) {

		if (numberOfGames< capacity) { //if the number of games is less than the capacity, we can create a new game.

			String choosenGame = new StringBuilder().append(gameType).append(opponent).toString();
			IGameAlgo ourGame=null;
			switch(choosenGame) {
			case "Tic Tac ToeRandom":
				ourGame = new TicTacTowRandom(3,3);

				break;
			case "Tic Tac ToeSmart":
				ourGame = new TicTacTowSmart(3,3);
				break;

			case "Catch The BunnyRandom":
				ourGame= new CatchTheBunnyRandom(9,9);
				break;
			case "Catch The BunnySmart":
				ourGame= new CatchTheBunnySmart(9,9);
				break;

			}

			//choose an id for the game: 
			do {
				//give a new ID for the next new game.
				game_Id++;
				if(game_Id==capacity) { //i should get game_Id values between 0 to game_id%capacity. 
					game_Id=0;
				}
			}
			while(games.containsKey(new Integer(game_Id)));

			gameHandler = new BoardGameHandler(ourGame);
			games.put(new Integer(game_Id),gameHandler);

			//increasing the number of games.
			numberOfGames++;

			return game_Id;
		}
		else { //The maximum number of simultaneous games was reached
			return -1;
		}
	}

	//update Player move function
	// supposed to update the board with the new position of the player.
	public IGameAlgo.GameState updateMove(Integer gameId, GameMove playerMove) throws UnknownIdException {

		if (games.containsKey(gameId)) { //check if the game exists in the cache memory.
			BoardGameHandler currentGame= games.get(gameId);// get our current game.
			IGameAlgo.GameState state = currentGame.playOneRound(playerMove);

			return state;
		}
		else {
			throw new UnknownIdException(new Exception(),"ID was not found");
		}

	}
	
	//return the board game of a specific game..
	public char[][] getBoardState(Integer gameId) throws UnknownIdException{

		if(games.containsKey(gameId)) {
			BoardGameHandler currentGame= games.get(gameId);
			return currentGame.getBoardState();
		}
		else {
			throw new UnknownIdException(new Exception(),"ID was not found");
		}
	}
	
	//let the computer start the game first.
	public char[][] computerStartGame(Integer gameId) throws UnknownIdException{

		if(games.containsKey(gameId)) {
			BoardGameHandler currentGame= games.get(gameId);
			currentGame.computerStartGame();
			return currentGame.getBoardState();
		}
		else {
			throw new UnknownIdException(new Exception(),"ID was not found");
		}
	}

	//end the game and remove it from the cache memory.
	public void endGame(Integer gameId) throws UnknownIdException {


		if(games.containsKey(gameId)) {
			games.remove(gameId);
			numberOfGames--; // decreasing the number of games, simultaneously is playing.	

		}
		else {
			throw new UnknownIdException(new Exception(),"ID was not found");
		}
	}

	//restart the game (note: this function is not implemented in the client side)
	public char[][] restartGame(Integer gameId) throws UnknownIdException {
		if(games.containsKey(gameId)) {
			BoardGameHandler currentGame= games.get(gameId);
			char[][] board =  currentGame.getBoardState();
			for(int i=0;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					board[i][j] = BoardSigns.BLANK.getSign();
				}
			}
			return board;
		}
		else {
			throw new UnknownIdException(new Exception(),"ID not found");
		}
	}


}
