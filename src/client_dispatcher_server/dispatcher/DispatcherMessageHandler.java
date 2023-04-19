package client_dispatcher_server.dispatcher;

import client_dispatcher_server.model.DispatcherMessage;
import client_dispatcher_server.model.Location;
import client_dispatcher_server.model.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class DispatcherMessageHandler implements Runnable {

	private final Socket clientSocket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private final Dispatcher dispatcher;

	DispatcherMessageHandler(Dispatcher dispatcher, Socket clientSocket) {
		System.out.println("DISPATCHER_THREAD: Thread creation began");
		this.dispatcher = dispatcher;
		this.clientSocket = clientSocket;
		try {
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			System.out.println("DISPATCHER_THREAD: Input and Output streams initialized");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void run() {
		while (!Thread.interrupted()) {
			try {
				DispatcherMessage message = (DispatcherMessage) inputStream.readObject();
				System.out.println("DISPATCHER_THREAD: Message read");
				processMessage(message);
			} catch (IOException exception) {
				try {
					inputStream.close();
					outputStream.close();
					clientSocket.close();
					System.out.println("DISPATCHER_THREAD: Connection closed.");
					break;
				} catch (Exception exception1) {
					System.out.println("DISPATCHER_THREAD: Error closing down socket connection");
				}
			} catch (ClassNotFoundException exception) {
				exception.printStackTrace();
			}
		}
	}

	private void processMessage(DispatcherMessage message) {
		DispatcherMessage response;
		switch (message.getType()) {
			case REGISTER:
				Location location = new Location(message.getLocation(), message.getPortNumber());
				dispatcher.registerServer(message.getHostname(), location);
				dispatcher.logRegisterServer(message.getHostname(), location);
				response = createResponse("OK", 0);
				break;
			case QUERY:
				Location serverLocation = dispatcher.getServerLocation(message.getHostname());
				
				
				if (serverLocation == null) {
					response = createResponse("unknown host", 0);
				} else {
					response = createResponse(serverLocation.getIpAddress(), serverLocation.getPortNumber());
				}
				break;
			default:
				response = createResponse("Unknown request type sent", 0);
				break;
		}

		try {
			outputStream.writeObject(response);
		} catch (IOException exception) {
			System.out.println("DISPATCHER_THREAD: Error processing message");
			exception.printStackTrace();
		}
	}

	private DispatcherMessage createResponse(String ipAddr, int portNumber) {
		return new DispatcherMessage("dispatcher", ipAddr, portNumber, MessageType.RESPONSE);
	}
}
