package com.hit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GamesModel implements Model {
	GamesClient client;
	private PropertyChangeSupport support;
	private int ID;

	public GamesModel() {
		support = new PropertyChangeSupport(this);
		client = new GamesClient(34567);
	}
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		support.addPropertyChangeListener(propertyChangeListener);
	}

	@SuppressWarnings("unchecked")
	public void newGame(String gameType, String opponentType) { //Sends the server a "new-game" request

		//composing a message for the server by encoding to json format.
		JSONObject clientMessage = new JSONObject();
		clientMessage.put("type", "New-Game");
		clientMessage.put("game", gameType);
		clientMessage.put("opponent", opponentType);

		//sending the message to the server for retrieving data.
		String serverMessage = client.sendMessage(clientMessage.toJSONString(), true);

		try {

			// decoding the response from the server.
			JSONObject serverResponse = (JSONObject) new JSONParser().parse(serverMessage);

			//saving the id game for later updating player's moves.
			ID = ((Long)serverResponse.get("ID")).intValue();

		} catch ( ParseException e) {
			e.printStackTrace();
		}

		support.firePropertyChange("modelMessage", null, serverMessage);
	}


	@SuppressWarnings("unchecked")
	public void updatePlayerMove(int row, int col) { //Sends the server an "update-move" message
		JSONObject clientMessage = new JSONObject(); // composing Json message
		//encoding a json message as a response:
		clientMessage.put("type", "Update-Move");
		clientMessage.put("ID", new Integer(ID));
		clientMessage.put("row", new Integer(row));
		clientMessage.put("col", new Integer(col));


		String serverMessage = client.sendMessage(clientMessage.toJSONString(), true);

		support.firePropertyChange("modelMessage", null, serverMessage);
	}

	@SuppressWarnings("unchecked")
	public void startGame() {
		JSONObject clientMessage = new JSONObject();
		clientMessage.put("type", "Start-Game");
		clientMessage.put("ID", new Integer(ID));
		String serverMessage= client.sendMessage(clientMessage.toString(), true);
		support.firePropertyChange("modelMessage", null, serverMessage);

	}

	@SuppressWarnings("unchecked")
	public void endGame() {
		JSONObject clientMessage=new JSONObject();
		clientMessage.put("type", "Stop-Game");
		clientMessage.put("ID", new Integer(ID));
		client.sendMessage(clientMessage.toString(), false);
	}

}
