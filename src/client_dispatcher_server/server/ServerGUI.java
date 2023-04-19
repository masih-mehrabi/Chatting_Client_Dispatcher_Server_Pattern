package client_dispatcher_server.server;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


class ServerGUI extends Stage {

	private static final int TEXT_AREA_PREF_COLUMN_COUNT = 40;
	private static final int TEXT_AREA_PREF_ROW_COUNT = 40;
	private static final int SCENE_HEIGHT = 350;
	private static final int SCENE_WIDTH = 480;

	private Label serverNameLabel;
	private Label dispatcherIpAddressLabel;
	private Label portNumberLabel;
	private Label dispatcherPortNumberLabel;

	private TextField serverNameText;
	private TextField dispatcherIpAddressText;
	private TextField portNumberText;
	private TextField dispatcherPortNumberText;

	private Button registerButton;

	private TextArea textArea;
	private ScrollPane scrollPane;


	void initGUI() {
		serverNameLabel = new Label("Server Name: ");
		serverNameText = new TextField();
		GridPane gridPane = new GridPane();

		portNumberLabel = new Label("Server port number: ");
		portNumberText = new TextField();

		dispatcherIpAddressLabel = new Label("Dispatcher IP address: ");
		dispatcherIpAddressText = new TextField("127.0.0.1");
		dispatcherIpAddressText.setEditable(false);

		dispatcherPortNumberLabel = new Label("Dispatcher port number: ");
		dispatcherPortNumberText = new TextField("50001");
		dispatcherPortNumberText.setEditable(false);

		textArea = new TextArea();
		textArea.setPrefColumnCount(TEXT_AREA_PREF_COLUMN_COUNT);
		textArea.setPrefRowCount(TEXT_AREA_PREF_ROW_COUNT);
		textArea.setEditable(false);
		scrollPane = new ScrollPane(textArea);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


		registerButton = new Button("Register");


		gridPane.add(createServerPanel(), 0, 0);
		gridPane.add(createButtonPanel(), 0, 1);
		gridPane.add(createTextAreaPanel(), 0, 2);
		setResizable(false);
		setTitle("Server");
		this.setScene(new Scene(gridPane, SCENE_WIDTH, SCENE_HEIGHT));

	}

	void setRegisterButtonActionListener(EventHandler<ActionEvent> handler) {
		registerButton.addEventHandler(ActionEvent.ACTION, handler);
	}

	String getServerName() {
		return serverNameText.getText();
	}

	String getIpAddress() {
		return dispatcherIpAddressText.getText();
	}

	String getPortNumber() {
		return portNumberText.getText();
	}

	void displayMessage(String string) {
		textArea.appendText(string + "\n");
	}

	void setRegisterButtonVisibility(boolean value) {
		registerButton.setDisable(!value);
	}

	private Pane createServerPanel() {
		final int rowFourIndex = 3;
		GridPane serverPane = new GridPane();
		serverPane.add(serverNameLabel, 0, 0);
		serverPane.add(serverNameText, 1, 0);
		serverPane.add(portNumberLabel, 0, 1);
		serverPane.add(portNumberText, 1, 1);
		serverPane.add(dispatcherIpAddressLabel, 0, 2);
		serverPane.add(dispatcherIpAddressText, 1, 2);
		serverPane.add(dispatcherPortNumberLabel, 0, rowFourIndex);
		serverPane.add(dispatcherPortNumberText, 1, rowFourIndex);

		return serverPane;
	}

	private Pane createButtonPanel() {
		GridPane buttonPane = new GridPane();


		buttonPane.add(registerButton, 0, 0);
		return buttonPane;
	}

	private Pane createTextAreaPanel() {
		GridPane textAreaPanel = new GridPane();

		textAreaPanel.add(scrollPane, 0, 0);
		return textAreaPanel;
	}
}
