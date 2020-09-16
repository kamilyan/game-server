package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GamesClient {

	private int  port;
	private Socket socket;
	private String serverMessage;

	public GamesClient(int serverPort) 
	{
		this.port=serverPort;

	}

	public void	closeConnection()
	{
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void	connectToServer() {
		try {
			socket=new Socket("localhost",this.port);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String sendMessage(String message, boolean hasResponse)
	{

		try {
			connectToServer();
			//should be convert to a JSON object for sending the message to the server
			JSONObject clientMessage = (JSONObject) new JSONParser().parse(message);
			ObjectOutputStream sendingMessageToServer = new ObjectOutputStream(socket.getOutputStream());

			sendingMessageToServer.writeObject(clientMessage);
			sendingMessageToServer.flush();
			
			//reading a response from the server.
			ObjectInputStream readingFromTheServer = new ObjectInputStream(socket.getInputStream());
			if (hasResponse) {
				serverMessage =((JSONObject) readingFromTheServer.readObject()).toString();
			} 
			sendingMessageToServer.close();
			readingFromTheServer.close();

			closeConnection();

		}catch (IOException | ClassNotFoundException | ParseException e) {

			e.printStackTrace();
		}

		return serverMessage;

	}

}
