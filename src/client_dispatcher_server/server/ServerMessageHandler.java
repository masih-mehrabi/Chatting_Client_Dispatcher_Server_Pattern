package client_dispatcher_server.server;

import client_dispatcher_server.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ServerMessageHandler implements Runnable {

	private final String threadId;
	private Socket clientSocket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private ServerConnectionHandler serverConnectionHandler;
	private boolean connected = false;

	ServerMessageHandler(Socket clientSocket, ServerConnectionHandler serverConnectionHandler, String threadId) {
		this.threadId = threadId;
		if (clientSocket != null) {
			this.clientSocket = clientSocket;
			this.serverConnectionHandler = serverConnectionHandler;
			try {
				inputStream = new ObjectInputStream(clientSocket.getInputStream());
				outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				connected = true;
				System.out.println("SERVER_THREAD: Input Output stream created");
			} catch (IOException e) {
				System.out.println("SERVER_THREAD: Unable to get input stream");
				e.printStackTrace();
			}
		}
	}

	public void run() {
		// Handles incoming messages
		while (connected) {
			try {
				ChatMessage chatMessage = (ChatMessage) inputStream.readObject();
				serverConnectionHandler.messageResponse(threadId, chatMessage.getMessage());
			} catch (IOException exception) {
				System.out.println("SERVER_THREAD: Unable to read input stream");
				connected = false;
				try {
					clientSocket.close();
					break;
				} catch (IOException ioException) {
					System.out.println("SERVER_THREAD: Input socket problem!");
				}
			} catch (ClassNotFoundException exception) {
				System.out.println("SERVER_THREAD: Class Message not found!");
			}
		}
	}

	void sendMessage(String message) {
		try {
			outputStream.writeObject(new ChatMessage(message));
		} catch (IOException e) {
			System.out.println("SERVER_THREAD: Unable to send object");
		}
	}
}
