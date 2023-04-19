package client_dispatcher_server.client;

import client_dispatcher_server.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientMessageHandler implements Runnable {

	private Socket clientSocket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private ChatClient chatClient;
	private boolean connected = false;

	ClientMessageHandler(Socket clientSocket, ChatClient chatClient) {
		System.out.println("CLIENT_THREAD: Created");
		this.clientSocket = clientSocket;
		this.chatClient = chatClient;
		System.out.println("CLIENT_THREAD: Initialized");
		try {
			System.out.println("CLIENT_THREAD: TRY block");
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			System.out.println("CLIENT_THREAD: Input Output streams created");
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("CLIENT_THREAD: INPUT created");
			connected = true;
		} catch (Exception exception) {
			System.out.println("CLIENT_THREAD: Unknown output stream");
		}
	}

	public void run() {
		System.out.println("CLIENT_THREAD: Started");
		while (connected) {
			try {
				ChatMessage message = (ChatMessage) inputStream.readObject();
				chatClient.displayMessage(message.getMessage());
			} catch (IOException exception) {
				System.out.println("CLIENT_THREAD: Error reading object from input stream");
				dropConnection();
				connected = false;
			} catch (ClassNotFoundException exception) {
				System.out.println("CLIENT_THREAD: Casting class not found");
			}
		}
	}

	void sendMessage(String message) {
		try {
			outputStream.writeObject(new ChatMessage(message));
		} catch (IOException exception) {
			System.out.println("CLIENT_THREAD: Error sending message");
		}
	}

	void dropConnection() {
		try {
			outputStream.close();
			inputStream.close();
			clientSocket.close();
		} catch (IOException exception) {
			System.out.println("CLIENT_THREAD: Unable to drop connection");
		}
	}
}
