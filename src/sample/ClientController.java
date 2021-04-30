package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.Socket;

public class ClientController {
    private ChatMessageSocket messageSocket;
    private final String name = "Client";

    @FXML
    private TextField IPField;

    @FXML
    private TextField portField;

    @FXML
    private TextArea messArea;

    @FXML
    private TextField messField;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnSendMessage;

    public void initialize() {
        messField.setEditable(false);
    }

    public void btnConnectClicked(ActionEvent event) {
        try {
            int port = Integer.parseInt(portField.getText());
            String IPAddress = IPField.getText();

            messArea.setText(messArea.getText() + "\nConnecting to " + IPAddress + " on port " + port);

            Socket socket = new Socket(IPAddress, port);
            messageSocket = new ChatMessageSocket(name, socket, messArea);
            messArea.setText(messArea.getText() + "\nConnected to Server!");
            messField.setEditable(true);
        } catch (Exception e) {
            messArea.setText(messArea.getText() + "\nError: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void btnSendMessageClicked(ActionEvent event) {
        if (messField.getText().equals("")) {
            return;
        }
        messageSocket.send(messField.getText());
        messField.clear();
    }
}
