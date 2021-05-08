package sample.controller.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.ChatMessageSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerController {
    private ChatMessageSocket messageSocket;
    private final String name = "Server";
    private ServerSocket serverSocket;
    private final int DEFAULT_PORT = 9888;
    private final ArrayList<ServerThread> threads = new ArrayList<>();

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
// PHẦN CỦA TÔI
            serverSocket = new ServerSocket(DEFAULT_PORT);
            ServerThread thread = new ServerThread();

// PHẦN CỦA GIÁO SƯ
//            int port = Integer.parseInt(portField.getText());
//            ServerSocket serverSocket = new ServerSocket(port);
//
//            Thread thread = new Thread(() -> {
//              try {
//                  messArea.setText(messArea.getText() + "\nListening on port " + port + " ...");
//
//                  Socket socket = serverSocket.accept();
//                  messArea.setText(messArea.getText() + "\nConnected to Client!");
//                  messageSocket = new ChatMessageSocket(name, socket, messArea);
//                  messField.setEditable(true);
//              } catch (IOException e) {
//                  messArea.setText(messArea.getText() + "\nError: " + e.getMessage());
//                  e.printStackTrace();
//              }
//            });
//>>>>>>> 193d383eba940d5aaad12689e9e64c4f95a32440
            thread.start();
        } catch (Exception e) {
            messArea.setText(messArea.getText() + "\nError: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void btnSendMessageClicked(ActionEvent event) throws IOException {
        if (messField.getText().equals("")) {
            return;
        }
        messageSocket.send(messField.getText());
        messField.clear();
    }

    public TextArea getMessArea() {
        return messArea;
    }

    public TextField getPortField() {
        return portField;
    }

    private class ServerThread extends Thread {
        private String srcName;
        private String dstName;

        @Override
        public void run() {
            try {
                messArea.setText(messArea.getText() + "\n" + "Listening on port " + DEFAULT_PORT + " ...");
                Socket socket = serverSocket.accept();
                messArea.setText(messArea.getText() + "\n" + "Connected to Client!");
//                messageSocket = new ChatMessageSocket(name, socket, this);
            } catch (IOException e) {
                messArea.setText(messArea.getText() + "\nError: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
