package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

//CLI manages the server.
//Using Observer pattern.
//notify the server to open or close the server

public class CLI implements Runnable {

	private PropertyChangeSupport propertyChangeHandler;
	private InputStream in;
	private OutputStream out;

	public CLI(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		propertyChangeHandler = new PropertyChangeSupport(this); 

	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {

		propertyChangeHandler.addPropertyChangeListener(pcl);

	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {

		propertyChangeHandler.removePropertyChangeListener(pcl);

	}

	// write a response to the manager server (that decides to start/shutdown the server)
	public void writeResponse(String response) {
		PrintStream outObj = new PrintStream(out);
		outObj.println(response);

	}

	@Override
	public void run() {
		@SuppressWarnings("resource")
		Scanner inObj = new Scanner(in);

		PrintStream outObj = new PrintStream(out);

		String cmd;// input commands.

		while(true) {
			outObj.println("please enter your command");
			cmd = inObj.nextLine();

			if (cmd.equals("SHOW_STATS")) {
				propertyChangeHandler.firePropertyChange("response", null, "SHOW_STATS");

			}
			else if (cmd.equals("GAME_SERVER_CONFIG")) {
				propertyChangeHandler.firePropertyChange("response", null, "GAME_SERVER_CONFIG"); // notify All Observers about a change of a property.
				writeResponse("");

			}
			else if(cmd.equals("START")) {
				propertyChangeHandler.firePropertyChange("response", null, "START");
				writeResponse("START");

			}
			else if(cmd.equals("SHUTDOWN")) {
				propertyChangeHandler.firePropertyChange("response", null, "SHUTDOWN");
				writeResponse("ShutDown server");

			}
			else {
				writeResponse("Not a Valid command");

			}
		}

	}

}
