package client_dispatcher_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ServerConnectionHandler implements Runnable {

	private static final int THREAD_ID_UPPER_BOUND = 100;
	private final ChatServer chatServer;
	private final HashMap<String, ServerMessageHandler> serverMessageHandler;
	private ServerSocket serverSocket;

	public ServerConnectionHandler(ChatServer chatServer, int portNumber) {
		serverMessageHandler = new HashMap<>();
		this.chatServer = chatServer;
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("SERVER_ELEMENT: Socket created");
		} catch (IOException exception) {
			System.out.println("SERVER_ELEMENT: Could not create socket on port " + portNumber);
		}
	}

	private void printMessage(String message) {
		chatServer.showMessage(message);
	}

	public void registerToDispatcher(String hostname, String ipAddress, int portNumber) {
		ServerDispatcherCommunicationHandler serverDispatcherCommunicationHandler = new ServerDispatcherCommunicationHandler();
		serverDispatcherCommunicationHandler.register(hostname, ipAddress, portNumber);
	}

	void messageResponse(String threadId, String message) {
		printMessage("Thread " + threadId + ": " + message);
		for (Map.Entry<String, ServerMessageHandler> thread : serverMessageHandler.entrySet()) {
			if (thread.getKey().compareTo(threadId) != 0) {
				thread.getValue().sendMessage(message);
			}
		}
	}

	public void run() {
		while (!Thread.interrupted()) {
			ServerMessageHandler serverMessageHandlerForThread;
			try {
				String threadId = String.valueOf(ThreadLocalRandom.current().nextInt(0, THREAD_ID_UPPER_BOUND));
				while (this.serverMessageHandler.containsKey(threadId)) {
					threadId = String.valueOf(ThreadLocalRandom.current().nextInt(0, THREAD_ID_UPPER_BOUND));
				}
				serverMessageHandlerForThread = new ServerMessageHandler(serverSocket.accept(), this, threadId);
				this.serverMessageHandler.put(threadId, serverMessageHandlerForThread);
				System.out.println("SERVER_ELEMENT: Connection accepted");
				new Thread(serverMessageHandlerForThread).start();
				System.out.println("SERVER_ELEMENT: New thread started");
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
}
