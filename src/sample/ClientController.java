package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.Socket;

public class ClientController {
    private ChatMessageSocket messageSocket;
    private String name;

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

    public void writeMessageToDisplay(String message) {
        msgDisplay.setText(msgDisplay.getText() + message + "\n");
    }

    public void initialize() {
        name = "Client";
        msgDisplay.setEditable(false);
    }

    public void btnConnectClicked(ActionEvent event) {
        try {
            int port = Integer.parseInt(portField.getText());
// PHẦN CỦA TÔI
            writeMessageToDisplay("Connecting to localhost...");
            Socket socket = new Socket("localhost", port);
            messageSocket = new ChatMessageSocket(name, "anotherClient", socket, this);
            writeMessageToDisplay("Connected to Server!");
// PHẦN CỦA GIÁO SƯ
//            String IPAddress = IPField.getText();
//
//            messArea.setText(messArea.getText() + "\nConnecting to " + IPAddress + " on port " + port);
//
//            Socket socket = new Socket(IPAddress, port);
//            messageSocket = new ChatMessageSocket(name, socket, messArea);
//            messArea.setText(messArea.getText() + "\nConnected to Server!");
//            messField.setEditable(true);
//>>>>>>> 193d383eba940d5aaad12689e9e64c4f95a32440
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
