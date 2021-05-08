package sample;

import sample.controller.client.ClientController;

import java.io.*;
import java.net.Socket;

public class ChatMessageSocket {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientController controller;
    private String name;
    private String dstName;

    public ChatMessageSocket(
        String name,
        String dstName,
        Socket socket,
        ClientController controller
    ) throws IOException {
        this.socket = socket;
        this.name = name;
        this.dstName = dstName;
        this.controller = controller;

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
                        controller.writeMessageToDisplay(message);
                    }

                } catch (IOException e) {
                    String errorMsg = "Error: " + e.getMessage();
                    controller.writeMessageToDisplay(errorMsg);
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void send(String msg) {
        String msgToSend = name + ": " + msg;
        String msgToDisplay = "You: " + msg;
        controller.writeMessageToDisplay(msgToDisplay);
        out.println(msgToSend);
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
