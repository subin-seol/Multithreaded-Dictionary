package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.Constants;

public class Server {
    private int PORT;
    private String dictionaryFile;

    public Server(int port, String dictionaryFile) {
        this.PORT = port;
        this.dictionaryFile = dictionaryFile;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);
            System.out.println(Constants.SERVER_LISTENING + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(Constants.CLIENT_CONNECTED);
                new ClientHandler(clientSocket, dictionaryFile).start();
            }
        } catch (IOException e) {
            System.out.println(Constants.SERVER_ERROR + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please provide port number and path to dictionary file");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String dictionaryFile = args[1];
        Server server = new Server(port, dictionaryFile);
        server.start();
    }
}
