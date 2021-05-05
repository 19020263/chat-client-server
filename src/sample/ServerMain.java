package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {
    private final int DEFAULT_PORT = 9888;
    private ServerSocket serverSocket;
    private final ArrayList<ServerThread> threads = new ArrayList<>();

    public ServerMain() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("Listening on port " + DEFAULT_PORT + " ...");

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread thread = new ServerThread("Client", "anotherClient", this, socket);
                threads.add(thread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ServerThread> getThreads() {
        return threads;
    }

    public void removeThread(ServerThread thread) {
        threads.remove(thread);
    }

    private class ServerThread extends Thread {
        private String senderName;
        private String receiverName;
        private ServerMain server;
        private BufferedWriter sender;
        private Socket socket;

        ServerThread(String senderName, String receiverName, ServerMain serverMain, Socket socket) {
            this.senderName = senderName;
            this.receiverName = receiverName;
            this.server = serverMain;
            this.socket = socket;
        }

        private void send(String msg) throws IOException {
            sender.write(msg);
            sender.newLine();
            sender.flush();
        }

        @Override
        public void run() {
            try {
                System.out.println("Connected to Client!");
                BufferedReader receiveMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                while (true) {
                    String message = receiveMessage.readLine();
                    System.out.println(message);

                    for (ServerThread thread : threads) {
                        thread.send(message);
                    }
//                    sender.write(message);
//                    sender.newLine();
//                    sender.flush();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ServerMain();
    }
}
