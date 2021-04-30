package sample;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatMessageSocket {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private TextArea messArea;
    private String name;

    public ChatMessageSocket(String name, Socket socket, TextArea messArea) throws IOException {
        this.socket = socket;
        this.messArea = messArea;
        this.name = name;

        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        receive();
    }

    private void receive() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String message = in.readLine();
                    if (message != null) {
                        messArea.setText(messArea.getText() + "\n" + message);
                    }

                } catch (IOException e) {
                    messArea.setText(messArea.getText() + "\nError: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void send(String msg) {
        msg = name + ": " + msg;
        messArea.setText(messArea.getText() + "\n" + msg);
        out.println(msg);
        out.flush();
    }

    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
