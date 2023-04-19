package client_dispatcher_server.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

class ClientGUI extends Stage {
	private static final int SEND_BUTTON_MIN_WIDTH = 100;
	private static final int HEIGHT = 350;
	private static final int WIDTH = 480;
	private static final int INCOMING_TEXT_AREA_PREF_ROW_COUNT = 20;
	private static final int INCOMING_TEXT_AREA_PREF_COLUMN_COUNT = 10;
	private static final int INCOMING_TEXT_AREA_MIN_WIDTH = 250;
	private static final int CLIENT_TEXT_AREA_PREF_COLUMN_COUNT = 20;
	private static final int CLIENT_TEXT_AREA_PREF_ROW_COUNT = 4;
	private static final int H_GAP = 10;
	private static final int V_GAP = 10;
	private Label serverNameLabel;

	private TextField serverNameText;

	private Label dispatcherIpAddressLabel;
	private TextField dispatcherIpAddressText;

	private Label dispatcherPortNumberLabel;
	private TextField dispatcherPortNumberText;

	private Button connectButton;
	private Button disconnectButton;
	private Button sendButton;


	private TextArea incomingMessagesTextArea;
	private ScrollPane incomingMessageScroll;

	private TextArea clientMessageTextArea;
	private ScrollPane clientMessageScroll;

	void initGUI() {
		serverNameLabel = new Label("Server Name: ");
		serverNameText = new TextField();
		GridPane gridPane = new GridPane();
		dispatcherIpAddressLabel = new Label("Dispatcher IP address: ");
		dispatcherIpAddressText = new TextField("127.0.0.1");
		dispatcherIpAddressText.setEditable(false);

		dispatcherPortNumberLabel = new Label("Dispatcher port number: ");
		dispatcherPortNumberText = new TextField("50001");
		dispatcherPortNumberText.setEditable(false);

		connectButton = new Button("Connect");
		disconnectButton = new Button("Disconnect");
		disconnectButton.setDisable(true);
		sendButton = new Button("Send");
		sendButton.setMinWidth(SEND_BUTTON_MIN_WIDTH);
		incomingMessagesTextArea = new TextArea();
		incomingMessagesTextArea.setPrefColumnCount(INCOMING_TEXT_AREA_PREF_COLUMN_COUNT);
		incomingMessagesTextArea.setPrefRowCount(INCOMING_TEXT_AREA_PREF_ROW_COUNT);
		incomingMessagesTextArea.setMinWidth(INCOMING_TEXT_AREA_MIN_WIDTH);
		incomingMessagesTextArea.setEditable(false);
		incomingMessageScroll = new ScrollPane(incomingMessagesTextArea);
		incomingMessageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


		clientMessageTextArea = new TextArea();
		clientMessageTextArea.setPrefRowCount(CLIENT_TEXT_AREA_PREF_ROW_COUNT);
		clientMessageTextArea.setPrefColumnCount(CLIENT_TEXT_AREA_PREF_COLUMN_COUNT);
		clientMessageScroll = new ScrollPane(clientMessageTextArea);

		clientMessageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		gridPane.setVgap(V_GAP);
		gridPane.setHgap(H_GAP);

		final int rowFourIndex = 3;

		gridPane.add(createServerNamePane(), 0, 0);
		gridPane.add(createConnectButtonPane(), 0, 1);
		gridPane.add(createScrollableTextAreaPane(), 0, 2);
		gridPane.add(createSendButtonPane(), 1, rowFourIndex);

		this.setScene(new Scene(gridPane, WIDTH, HEIGHT));
		setResizable(false);
		setTitle("Client");


	}

	void setSendButtonActionListener(EventHandler<ActionEvent> handler) {
		sendButton.addEventHandler(ActionEvent.ACTION, handler);

	}

	void setConnectButtonActionListener(EventHandler<ActionEvent> handler) {
		connectButton.addEventHandler(ActionEvent.ACTION, handler);
	}

	void setDisconnectButtonActionListener(EventHandler<ActionEvent> handler) {
		disconnectButton.addEventHandler(ActionEvent.ACTION, handler);
	}

	String getServerName() {
		return serverNameText.getText();
	}

	String getClientMessage() {
		return clientMessageTextArea.getText();
	}

	void printMessage(String message) {
		incomingMessagesTextArea.appendText(message + "\n");
	}

	void blankClientMessageArea() {
		clientMessageTextArea.setText("");
	}

	void setConnectButtonVisible(boolean value) {
		connectButton.setDisable(!value);
		disconnectButton.setDisable(value);
	}

	private Pane createServerNamePane() {
		GridPane serverNamePane = new GridPane();

		serverNamePane.add(serverNameLabel, 0, 0);
		serverNamePane.add(serverNameText, 1, 0);

		serverNamePane.add(dispatcherIpAddressLabel, 0, 1);
		serverNamePane.add(dispatcherIpAddressText, 1, 1);

		serverNamePane.add(dispatcherPortNumberLabel, 0, 2);
		serverNamePane.add(dispatcherPortNumberText, 1, 2);

		return serverNamePane;
	}

	private Pane createConnectButtonPane() {
		GridPane buttonPane = new GridPane();
		buttonPane.setHgap(H_GAP);
		buttonPane.add(connectButton, 0, 0);
		buttonPane.add(disconnectButton, 1, 0);
		return buttonPane;
	}

	private Pane createScrollableTextAreaPane() {
		GridPane textAreaPane = new GridPane();

		textAreaPane.add(incomingMessageScroll, 0, 0);
		textAreaPane.add(clientMessageScroll, 0, 2);
		return textAreaPane;
	}

	private Pane createSendButtonPane() {
		GridPane sendButtonPane = new GridPane();

		sendButtonPane.add(sendButton, 0, 0);

		return sendButtonPane;
	}
}
