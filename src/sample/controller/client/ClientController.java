package sample.controller.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.ChatMessageSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientController {
    private ChatMessageSocket messageSocket;
    private String name;
    private Socket socket;

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
        socket = new Socket();
        msgDisplay.setEditable(false);
        msgInput.setEditable(false);
    }

    public void btnConnectClicked(ActionEvent event) {
        try {
            int port = Integer.parseInt(portField.getText());
            writeMessageToDisplay("Connecting to localhost...");
            socket.bind(new InetSocketAddress("localhost", port));
            name = "Alice";
            messageSocket = new ChatMessageSocket(name, "anotherClient", socket, this);
            writeMessageToDisplay("Connected to Server!");
            msgInput.setEditable(true);
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

    public void closeSocket() throws IOException {
        socket.close();
    }
}
