package client_dispatcher_server.model;

import java.io.Serializable;

public class DispatcherMessage implements Serializable {

	private static final long serialVersionUID = 1054127665620248324L;

	private String hostname;
	private MessageType type;
	private String ipAddress;
	private int portNumber;

	public DispatcherMessage(String hostname, String ipAddress, int portNumber, MessageType type) {
		this.hostname = hostname;
		this.ipAddress = ipAddress;
		this.type = type;
		this.portNumber = portNumber;
	}

	public String getLocation() {
		return ipAddress;
	}

	public MessageType getType() {
		return type;
	}

	public String getHostname() {
		return hostname;
	}

	public int getPortNumber() {
		return portNumber;
	}
}
