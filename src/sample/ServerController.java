package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private ChatMessageSocket messageSocket;
    private final String name = "Server";

    @FXML
    private TextField portField;

    @FXML
    private TextArea messArea;

    @FXML
    private TextField messField;

    @FXML
    private Button btnListen;

    @FXML
    private Button btnSendMessage;

    public void initialize() {
        messField.setEditable(false);
    }

    public void btnListenClicked(ActionEvent event) {
        try {
            int port = Integer.parseInt(portField.getText());
            ServerSocket serverSocket = new ServerSocket(port);

            Thread thread = new Thread(() -> {
              try {
                  messArea.setText(messArea.getText() + "\nListening on port " + port + " ...");

                  Socket socket = serverSocket.accept();
                  messArea.setText(messArea.getText() + "\nConnected to Client!");
                  messageSocket = new ChatMessageSocket(name, socket, messArea);
                  messField.setEditable(true);
              } catch (IOException e) {
                  messArea.setText(messArea.getText() + "\nError: " + e.getMessage());
                  e.printStackTrace();
              }
            });
            thread.start();
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
