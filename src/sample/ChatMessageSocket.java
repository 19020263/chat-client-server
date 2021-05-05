package sample;

import java.io.*;
import java.net.Socket;

public class ChatMessageSocket {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientController controller;
//    private ObjectInputStream inputStream;
//    private ObjectOutputStream outputStream;
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

//        inputStream = new ObjectInputStream(socket.getInputStream());
//        outputStream = new ObjectOutputStream(socket.getOutputStream());

        System.out.println(socket.toString());

        receive();
    }

    private void receive() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
//                    Message msgToReceive = (Message) inputStream.readObject();
//                    String message = msgToReceive.getMessageContent();
                    String message = in.readLine();
                    if (message != null) {
                        controller.writeMessageToDisplay(message);
                    }

                } catch (IOException e) {
                    String errorMsg = "Error: " + e.getMessage();
                    controller.writeMessageToDisplay(errorMsg);
                    e.printStackTrace();
                }
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });
        thread.start();
    }

    public void send(String msg) throws IOException {
//        Message msgToSend = new Message(name, dstName, msg);
        String msgToDisplay = name + ": " + msg;
        controller.writeMessageToDisplay(msgToDisplay);
        out.println(msgToDisplay);
        out.flush();
//        outputStream.writeObject(msgToSend);
    }

    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
//            inputStream.close();
//            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
