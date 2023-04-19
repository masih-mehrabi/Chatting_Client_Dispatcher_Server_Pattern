package client_dispatcher_server.server;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ChatServer extends Application {

	private ServerGUI serverGUI;

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
		serverGUI = new ServerGUI();
		serverGUI.initGUI();
		serverGUI.setRegisterButtonActionListener(new ServerOnRegister(this));
		serverGUI.show();
	}

	void showMessage(String message) {
		serverGUI.displayMessage(message);
	}

	private class ServerOnRegister implements EventHandler<javafx.event.ActionEvent> {
		private final ChatServer chatServer;

		ServerOnRegister(ChatServer chatServer) {
			this.chatServer = chatServer;
		}

		@Override
		public void handle(javafx.event.ActionEvent event) {
			System.out.println(serverGUI.getServerName() + " " + serverGUI.getIpAddress() + " " + serverGUI.getPortNumber());
			ServerConnectionHandler serverConnectionHandler =
					new ServerConnectionHandler(chatServer, Integer.parseInt(serverGUI.getPortNumber()));
			Thread thread = new Thread(serverConnectionHandler);
			thread.start();
			serverGUI.setRegisterButtonVisibility(false);
			serverConnectionHandler.registerToDispatcher(serverGUI.getServerName(),
					serverGUI.getIpAddress(),
					Integer.parseInt(serverGUI.getPortNumber()));
		}
	}
}
