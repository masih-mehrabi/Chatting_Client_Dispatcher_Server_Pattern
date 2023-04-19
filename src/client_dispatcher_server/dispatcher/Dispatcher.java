package client_dispatcher_server.dispatcher;

import client_dispatcher_server.model.Location;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;


public class Dispatcher extends Application {

	private DispatcherGUI dispatcherGUI;
	private static final int DISPATCHER_PORT = 50001;
	private Map<String, Location> locationMap = new HashMap<>();
	public static void startApp(String[] args) {
		launch(args);
	}

	public void startListening() {
		System.out.println("start listening");
		try {
			ServerSocket dispatcherSocket = new ServerSocket(DISPATCHER_PORT);
			while (!Thread.interrupted()) {
				DispatcherMessageHandler handler = new DispatcherMessageHandler(this, dispatcherSocket.accept());
				System.out.println("DISPATCHER: Connection accepted");
				new Thread(handler).start();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private void startGUI() {
		dispatcherGUI = new DispatcherGUI();
		dispatcherGUI.initGUI();
		dispatcherGUI.displayMessage("Starting... Ready!");
		dispatcherGUI.show();
	}

	void registerServer(String name, Location location) {
		getLocationMap().put(name, location);
		logRegisterServer(name, location);
		
	}

	public Location getServerLocation(String name) {
		Location location = getLocationMap().get(name);
		logGetServerLocation(name, location);
		
		return location;
	}

	void logRegisterServer(String name, Location location) {
		if (dispatcherGUI != null) {
			dispatcherGUI.displayMessage("Server '" + name + "' registered.");
			dispatcherGUI.displayMessage("IP address: " + location.getIpAddress());
			dispatcherGUI.displayMessage("Port number: " + location.getPortNumber());
			dispatcherGUI.displayMessage(getLocationMap().values().toString());
		}
	}

	void logGetServerLocation(String name, Location location) {
		if (dispatcherGUI != null) {
			dispatcherGUI.displayMessage("Querying for: " + name);
			if (location == null) {
				dispatcherGUI.displayMessage("Server not found.");
				
			} else {
				dispatcherGUI.displayMessage("Query successful.");
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {
		startGUI();
		Thread t = new Thread(this::startListening);
		t.setDaemon(true);
		t.start();
	}
	
	public Map<String, Location> getLocationMap() {
		return locationMap;
	}
}
