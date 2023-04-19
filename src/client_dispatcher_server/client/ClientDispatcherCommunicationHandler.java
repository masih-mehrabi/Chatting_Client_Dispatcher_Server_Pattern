package client_dispatcher_server.client;

import client_dispatcher_server.model.DispatcherMessage;
import client_dispatcher_server.model.MessageType;
import client_dispatcher_server.model.Location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientDispatcherCommunicationHandler {

	private static final int DISPATCHER_PORT = 50001;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	ClientDispatcherCommunicationHandler() {
		try {
			Socket dispatcherSocket = new Socket("localhost", DISPATCHER_PORT);
			System.out.println("DISPATCHER_CLIENT: Socket created");
			outputStream = new ObjectOutputStream(dispatcherSocket.getOutputStream());
			System.out.println("DISPATCHER_CLIENT: Output stream created");
			inputStream = new ObjectInputStream(dispatcherSocket.getInputStream());
			System.out.println("DISPATCHER_CLIENT: Input stream created");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

		Location getServerLocation(String hostname) {
		DispatcherMessage message = new DispatcherMessage(hostname, "na", 0, MessageType.QUERY);
		try {
			outputStream.writeObject(message);
			message = (DispatcherMessage) inputStream.readObject();
			System.out.println(message.getLocation());
		} catch (IOException | ClassNotFoundException exception) {
			exception.printStackTrace();
		}

		return new Location(message.getLocation(), message.getPortNumber());
	}
}
