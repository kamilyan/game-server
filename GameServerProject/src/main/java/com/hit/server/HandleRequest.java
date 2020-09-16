package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.services.GameServerController;

public class HandleRequest implements Runnable {
	private Socket socket;
	private GameServerController controller;
	private int gameId;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private JSONObject serverReply;
	private JSONArray playersPosition;
	private char[][] board;


	public HandleRequest(Socket s, GameServerController controller)
			throws IOException {
		this.socket=s;
		this.controller= controller;

		//reading json messages from the inputStream of the socket.
		reader = new ObjectInputStream(socket.getInputStream());
		//writing json messages to the outputStream of the socket.
		writer  = new ObjectOutputStream(socket.getOutputStream());

		// The server should reply to the client
		// so we should compose a json message with all the relevant data.
		serverReply = new JSONObject();
		// should also send the board of the game (encoding it to a json array format)
		playersPosition = new JSONArray();

	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			//messages that arrives from the client side.
			JSONObject userMessage = (JSONObject) reader.readObject();

			//get the type and name of the game from the user message.
			String type =(String)userMessage.get("type"); // (could be "new-Game or update-game and etc...")
			String game = (String)userMessage.get("game"); //(could be "tic tac toe or catch the bunny");

			switch (type) {

			case "New-Game" :

				//get the opponent algorithm (smart or random)
				String opponent = (String)userMessage.get("opponent");

				//It's a new game, use the controller for creating the new game.
				gameId = controller.newGame(game, opponent);

				//encoding a json message as a response:
				serverReply.put("type","New-Game");
				serverReply.put("ID", new Integer(gameId));

				//get the board of this specific game.
				board = controller.getBoardState(new Integer(gameId));

				//Case it's the TicTacTow game.
				if (game.equals("Tic Tac Toe"))  {
					//enconding board to json format as array.
					encodingBoardGameToJsonFormat(board);
					//Last response our board game:
					serverReply.put("board", playersPosition);

				}
				else { // Another case it's catch the bunny game
					if (game.equals("Catch The Bunny"))  {
						encodingBoardGameToJsonFormat(board);
						//Last response our board game:
						serverReply.put("board", playersPosition);
					}
				}
				//encoding board to json format
				serverReply.put("board", playersPosition);
				////send a response to the client.
				writer.writeObject(serverReply);
				writer.flush();
				break;

			case "Update-Move":
				//decoding the message from the user for updating the board.
				int Row = ((Long)userMessage.get("row")).intValue();
				int Col = ((Long)userMessage.get("col")).intValue();
				//find the id game
				gameId = ((Long)userMessage.get("ID")).intValue();
				
				// create move object for updating the next player's move.
				GameBoard.GameMove mov = new GameBoard.GameMove(Row,Col);
				//call the controller for updating the next player's move on board.
				IGameAlgo.GameState state = controller.updateMove(gameId, mov);
				
				//composing JSON message format for sending to the client.
				serverReply.put("type", "Update-Move");
				serverReply.put("ID", new Integer(gameId));
				serverReply.put("State",new Integer(state.getIntValue()));
			
				//get the board game from the server for sending to the view (client) 
				board = controller.getBoardState(gameId);
				// encoding this board to json format for sending it.
				encodingBoardGameToJsonFormat(board);
				serverReply.put("board", playersPosition);
				
				//sending the package to the client.
				writer.writeObject(serverReply); 
				writer.flush();
				break;

			case "Start-Game": //In case the client wants the computer to start game.
				
				//get the id game for controlling the correct game.
				gameId = ((Long)userMessage.get("ID")).intValue();
				
				//start composing a message for the client.
				serverReply.put("type", "Start-Game");
				serverReply.put("ID", new Integer(gameId));
				
				// let the computer to start the game.
				board= controller.computerStartGame(gameId);
				
				// encoding to json format the board game.
				encodingBoardGameToJsonFormat(board);
				serverReply.put("board", playersPosition);
				serverReply.put("board", playersPosition);
				
				// sending the packet to the client.
				writer.writeObject(serverReply); 
				writer.flush();
				break;

			case "Stop-Game":// In case, the client wants to end the game.
				gameId = ((Long)userMessage.get("ID")).intValue();
				controller.endGame(gameId);
				
				break;
			} //end switch

			
		} catch (IOException | UnknownIdException | ClassNotFoundException  e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("unchecked")
	private void encodingBoardGameToJsonFormat(char[][] board) {
		if (board.length==3) {
			for (int i=0;i<3;i++)
				for(int j=0;j<3;j++) {
					if (board[i][j] == 'O') {
						playersPosition.add("O");
					}
					else if(board[i][j] == 'X') {
						playersPosition.add("X");
					}
					else { // the cell is blank
						playersPosition.add("-");
					}
				}
		}
		else {
			for (int i=0;i<9;i++)
				for(int j=0;j<9;j++) {
					if (board[i][j] == 'P') {
						playersPosition.add("K");
					}
					else if(board[i][j] == 'R') {
						playersPosition.add("B");
					}
					else { // the cell is blank
						playersPosition.add("-");
					}
				}
		}
	}

}
