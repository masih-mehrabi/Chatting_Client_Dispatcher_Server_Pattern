package client_dispatcher_server.client;

import client_dispatcher_server.model.Location;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Application {

	private ClientGUI clientGUI;
	private ClientMessageHandler clientMessageHandler;
	private final ClientDispatcherCommunicationHandler clientDispatcherCommunicationHandler =
			new ClientDispatcherCommunicationHandler();

	public static void startApp(String[] args) {
		launch(args);
	}

	/**
	 * This method is setting up everything for local testing.
	 */
	@Override
	public void start(Stage primaryStage) {
		startGUI();
	}

	private void startGUI() {
		clientGUI = new ClientGUI();
		clientGUI.initGUI();


		// on send
		clientGUI.setSendButtonActionListener(event -> {
			clientMessageHandler.sendMessage("Client> " + clientGUI.getClientMessage());
			clientGUI.printMessage("Client> " + clientGUI.getClientMessage());
			clientGUI.blankClientMessageArea();
		});

		// on login
		clientGUI.setConnectButtonActionListener(event -> {
			connect(clientGUI.getServerName());
			clientGUI.setConnectButtonVisible(false);
		});

		// on disconnect
		clientGUI.setDisconnectButtonActionListener(event -> {
			disconnect();
			clientGUI.setConnectButtonVisible(true);
		});
		clientGUI.show();
	}

	void displayMessage(String message) {
		clientGUI.printMessage(message);
	}

	private void connect(String serverName) {
		try {
			Location serverLocation = clientDispatcherCommunicationHandler.getServerLocation(serverName);
			
			if (serverLocation != null) {
				if (isUnknownHost(serverLocation)) {
					System.out.println("CLIENT_ELEMENT: Server not found. Shutting down!");
				} else {
					System.out.println("Ip address: " + serverLocation.getIpAddress() + ". Port number: "
							+ serverLocation.getPortNumber());
					Socket clientSocket = new Socket(serverLocation.getIpAddress(), serverLocation.getPortNumber());
					System.out.println("CLIENT_ELEMENT: Socket created successfully");
					clientMessageHandler = new ClientMessageHandler(clientSocket, this);
					System.out.println("CLIENT_ELEMENT: Client thread created");
					new Thread(clientMessageHandler).start();
					System.out.println("CLIENT_ELEMENT: Client thread started");
				}
			}
		} catch (UnknownHostException exception) {
			System.out.println("CLIENT_ELEMENT: Unknown host: localhost");
			exception.printStackTrace();
		} catch (IOException exception) {
			System.out.println("CLIENT_ELEMENT: Problem connecting to socket");
			exception.printStackTrace();
		}
	}

	private static boolean isUnknownHost(Location serverLocation) {
		return serverLocation.getIpAddress().equalsIgnoreCase("unknown host");
	}

	private void disconnect() {
		clientMessageHandler.dropConnection();
	}
}
