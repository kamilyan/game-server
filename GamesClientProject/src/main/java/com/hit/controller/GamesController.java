package com.hit.controller;

import java.beans.PropertyChangeEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hit.model.Model;
import com.hit.view.View;

public class GamesController implements Controller {
	private Model model;
	private View view;


	public GamesController(Model model, View view) {
		this.model=model;
		this.view=view;

	}

	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

		String [] arr=propertyChangeEvent.getNewValue().toString().split("_");

		if("viewMessage".equals(propertyChangeEvent.getPropertyName()))
		{
			switch (arr[0]) {
			case "New-Game" : 
				model.newGame(arr[1], arr[2]); // type and opponent
				break;
			case "UpdatePlayerMove":
				//sending rows and cols for updating the player move on the board.
				model.updatePlayerMove(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
				break;
			case "End-Game":
				model.endGame();	
				break;
			case "Start-Game": 
				model.startGame();	
				break;
			}
		}
		else if("modelMessage".equals(propertyChangeEvent.getPropertyName()))
		{
			try {
				Character[] gameBoard = null;
				//decoding json format message from the server.
				JSONObject serverMessage = (JSONObject) new JSONParser().parse((String) propertyChangeEvent.getNewValue());

				switch ((String) serverMessage.get("type")) {
				case "New-Game":
					gameBoard=getBoard(serverMessage);
					view.updateViewNewGame(gameBoard);
					break;
				case "Update-Move":

					int state =((Long)serverMessage.get("State")).intValue();
					if (state>0) { // if it's legal move show board to the view.
						gameBoard=getBoard(serverMessage);
					}
					view.updateViewGameMove(state, gameBoard);
					break;
					
				case "Start-Game":
					gameBoard=getBoard(serverMessage); 
					view.updateViewNewGame(gameBoard);
					break;
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}	

		}
	}

	private Character[] getBoard(JSONObject json) {

		JSONArray board = (JSONArray) json.get("board");
		Character[] gameBoard = new Character[board.size()];

		for(int i=0; i<board.size();i++) {
			gameBoard[i] = board.get(i).toString().charAt(0);
		}
		return gameBoard;
	}


}


