package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;

public class AnotherClientController extends ClientController {
    private ChatMessageSocket messageSocket;
    private String anotherName;

    @FXML
    private TextField IPField;

    @FXML
    private TextField portField;

    @FXML
    private TextArea msgDisplay;

    @FXML
    private TextField msgInput;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnSendMessage;

//    public void writeMessageToDisplay(String message) {
//        msgDisplay.setText(msgDisplay.getText() + message + "\n");
//    }

    public void initialize() {
        anotherName = "anotherClient";
    }

    public void btnConnectClicked(ActionEvent event) {
        try {
            int port = Integer.parseInt(portField.getText());
            writeMessageToDisplay("Connecting to localhost...");
            Socket socket = new Socket("localhost", port);
            messageSocket = new ChatMessageSocket(anotherName, "Client", socket, this);
            writeMessageToDisplay("Connected to Server!");
        } catch (Exception e) {
            String errorMsg = "Error: " + e.getMessage();
            writeMessageToDisplay(errorMsg);
            e.printStackTrace();
        }
    }

    public void btnSendMessageClicked(ActionEvent event) throws IOException {
        if (msgInput.getText().equals("")) {
            return;
        }
        messageSocket.send(msgInput.getText());
        msgInput.clear();
    }
}
