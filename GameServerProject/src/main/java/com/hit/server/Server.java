package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hit.services.GameServerController;

public class Server implements PropertyChangeListener, Runnable {

	private ServerSocket server;
	private Socket someClient;
	private String response; // our changed property 
	private HandleRequest handleRequest;
	private GameServerController gameController;
	private int port;
	private int numberOfClients;
	static final int MAX_CLIENTS = 10; // max number of clients that are able to be connected with the server.
	static final int MAX_GAMES = 6; // I decide that only 4 games we can run simultaneously.

	public Server(int port) {
		numberOfClients = 0; // the number of clients that the server has connected.
		this.port = port;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		response = (String) evt.getNewValue();
		Thread t1 = new Thread(this);
		t1.start();
	}


	public void run() {

		if (response.equals("START")) {

			try {
				server = new ServerSocket(port);
				// If the server successfully binds to its port, then the server
				// object is successfully created
				ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTS);
				while (numberOfClients <= MAX_CLIENTS) { // I decided that up to 10 clients, the server can connect
					// with.

					someClient = server.accept();
					handleRequest = new HandleRequest(someClient, gameController);

					// handle client requests.
					executor.execute(handleRequest);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (response.equals("GAME_SERVER_CONFIG")) { // GAME_SERVER_CONFIG
			numberOfClients++;
			gameController = new GameServerController(MAX_GAMES);// I decide that only 6 games we can run
			// simultaneously.
		}
		else if (response.equals("SHUTDWON")) {
			try {

				server.close();
				numberOfClients--;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
