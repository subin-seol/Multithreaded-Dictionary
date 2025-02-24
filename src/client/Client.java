package client;

import util.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    private int PORT;
    private String host;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private boolean connectionSuccessful;

    public Client(String host, int port, ClientGUI gui) {
        this.PORT = port;
        this.host = host;

        if (port < Constants.MIN_PORT || port > Constants.MAX_PORT) {
            gui.displayError(Constants.PORT_NUMBER_OUT_OF_RANGE);
            connectionSuccessful = false;
            return;
        }

        try {
            socket = new Socket(host, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            connectionSuccessful = true;
        } catch (IOException e) {
            gui.displayError(Constants.CONNECTION_FAILURE + e.getMessage());
            connectionSuccessful = false;
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
                System.out.println(Constants.SERVER_CONNECTION_CLOSED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(Constants.PROVIDE_SERVER_DETAILS);
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println(Constants.INVALID_PORT_NUMBER);
            return;
        }

        String host = args[0];
        ClientGUI gui = new ClientGUI(host, port);
    }
}
