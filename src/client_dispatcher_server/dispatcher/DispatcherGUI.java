package client_dispatcher_server.dispatcher;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


class DispatcherGUI extends Stage {


	private static final int HEIGHT = 400;
	private static final int WIDTH = 400;
	private TextArea logText;

	void initGUI() {

		GridPane pane = new GridPane();
		pane.setPrefSize(WIDTH, HEIGHT);

		logText = new TextArea();
		logText.setMinHeight(HEIGHT);
		logText.setEditable(false);
		ScrollPane scrollPane = new ScrollPane(logText);
		scrollPane.setMinHeight(HEIGHT);
		pane.add(scrollPane, 1, 2);

		setTitle("Dispatcher logger");
		setResizable(false);
		setScene(new Scene(pane, WIDTH, HEIGHT));

	}


	void displayMessage(String message) {
		logText.appendText(message + "\n");
	}
}
