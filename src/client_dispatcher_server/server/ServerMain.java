package client_dispatcher_server.server;

public final class ServerMain {
	private ServerMain() {

	}

	public static void main(String[] args) {
		//This is a workaround for a known issue when starting JavaFX applications
		ChatServer.startApp(args);
	}

}
