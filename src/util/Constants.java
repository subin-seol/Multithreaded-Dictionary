package util;

public class Constants {
    // Port number range
    public static final int MIN_PORT = 1024;
    public static final int MAX_PORT = 65535;

    // Server messages
    public static final String SERVER_LISTENING = "-- Server Status -- Listening on port ";
    public static final String CLIENT_CONNECTED = "-- Server Status -- Client connected";
    public static final String SERVER_ERROR = "-- Server Status -- Error: ";
    public static final String CLIENT_REQUEST = "-- Client Request -- ";
    public static final String CLIENT_ERROR = "-- Server Status -- Error handling client: ";
    public static final String DICTIONARY_ERROR_LOADING = "-- Server Status -- Error loading dictionary: ";
    public static final String DICTIONARY_ERROR_SAVING = "-- Server Status -- Error saving dictionary: ";

    // Client error messages
    public static final String CONNECTION_FAILURE = "Connection failure: ";
    public static final String INVALID_PORT_NUMBER = "Please provide a valid port number.";
    public static final String PROVIDE_SERVER_DETAILS = "Please provide server address and server port.";
    public static final String PORT_NUMBER_OUT_OF_RANGE = "Port number must be between " + MIN_PORT + " and " + MAX_PORT + ".";
    public static final String FAILED_TO_CONNECT = "Failed to connect to server. Please check the server status and port number.";
    public static final String SERVER_CONNECTION_CLOSED = "-- Client Status -- Socket closed";
    public static final String CLIENT_GUI_TITLE = "Dictionary";

    // Dictionary messages
    public static final String WORD_NOT_FOUND = "Failure: Word not found in dictionary\n";
    public static final String WORD_ADDED_SUCCESS = "Success: Word added to dictionary\n";
    public static final String WORD_ADD_FAILURE = "Failure: Failed to save to dictionary\n";
    public static final String WORD_ALREADY_EXISTS = "Duplicate: Word already exists in the dictionary\n";
    public static final String WORD_REMOVED_SUCCESS = "Success: Word removed from dictionary\n";
    public static final String WORD_REMOVE_FAILURE = "Failure: Failed to save to dictionary\n";
    public static final String WORD_NOT_FOUND_REMOVE = "Not Found: Word not found in the dictionary\n";
    public static final String WORD_UPDATE_SUCCESS = "Success: Meanings updated\n";
    public static final String WORD_UPDATE_FAILURE = "Failure: Failed to save to dictionary\n";
    public static final String WORD_NOT_FOUND_UPDATE = "Not Found: Word not found in dictionary\n";

    // JSON formatting
    public static final int JSON_INDENT_FACTOR = 4;

    // GUI
    public static final int GUI_WIDTH = 600;
    public static final int GUI_HEIGHT = 600;
}
