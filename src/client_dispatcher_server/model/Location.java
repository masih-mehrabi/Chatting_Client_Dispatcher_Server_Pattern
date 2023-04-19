package client_dispatcher_server.model;

public class Location {

	private String ipAddress;
	private int portNumber;

	public Location(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		portNumber = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPortNumber() {
		return portNumber;
	}
}
