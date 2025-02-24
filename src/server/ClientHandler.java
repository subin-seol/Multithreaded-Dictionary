package server;

import java.io.*;
import java.net.Socket;

import util.Constants;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private String dictionaryFile;

    public ClientHandler(Socket socket, String dictionaryFile) {
        this.clientSocket = socket;
        this.dictionaryFile = dictionaryFile;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(Constants.CLIENT_REQUEST + message);
                String[] request = message.split(" ", 2);
                String response;

                switch (request[0]) {
                    case "Query":
                        response = handleQuery(request[1]);
                        break;
                    case "Add":
                        response = handleAdd(request[1]);
                        break;
                    case "Remove":
                        response = handleRemove(request[1]);
                        break;
                    case "Update":
                        response = handleUpdate(request[1]);
                        break;
                    default:
                        response = "Not supported operation\n";
                        break;
                }

                out.write(response);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(Constants.CLIENT_ERROR + e.getMessage());
        }
    }

    private String handleQuery(String word) {
        return Dictionary.queryWord(dictionaryFile, word);
    }

    private String handleAdd(String wordAndMeaning) {
        return Dictionary.addWord(dictionaryFile, wordAndMeaning);
    }

    private String handleRemove(String word) {
        return Dictionary.removeWord(dictionaryFile, word);
    }

    private String handleUpdate(String wordAndMeaning) {
        return Dictionary.updateWord(dictionaryFile, wordAndMeaning);
    }
}
