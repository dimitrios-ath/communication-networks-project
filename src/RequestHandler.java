import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Objects;

/**
 *  handle the incoming client request
 */
public class RequestHandler {

    // HashMap of authToken and associated account
    private static final HashMap<Integer, Account> Users = new HashMap<>();
    // HashMap of username and associated authToken
    private static final HashMap<String, Integer> UsernamesAndAuthTokens = new HashMap<>();
    private static final List<Message> Messages = new ArrayList<>();
    int fn_id;
    String[] clientArgs;
    PrintWriter out;

    /**
     *  constructor
     *  @param socket       client socket
     *  @param clientArgs   client command line arguments
     */
    public RequestHandler(Socket socket, String[] clientArgs) {
        this.clientArgs = clientArgs; // receive client command line arguments
        try {
            this.fn_id = Integer.parseInt(clientArgs[2]); // receive the given fn_id
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException | NumberFormatException ignored) {}
        ProcessRequest();
    }

    /**
     *  call appropriate method based on fn_id
     */
    private void ProcessRequest() {
        switch (fn_id) {
            case 1 -> CreateAccount();
            case 2 -> ShowAccounts();
            case 3 -> SendMessage();
            case 4 -> ShowInbox();
            case 5 -> ReadMessage();
            case 6 -> DeleteMessage();
        }
    }

    /**
     *  create new account
     */
    private void CreateAccount() {
        if (clientArgs[3].matches("^[a-zA-Z0-9_]*$")) { // if username format is correct
            if (!UsernamesAndAuthTokens.containsKey(clientArgs[3])) { // if username doesn't exist
                Random random = new Random(System.currentTimeMillis());
                int authToken = random.nextInt(8999) + 1000; // generate authToken
                boolean uniqueAuthToken = false;
                while (!uniqueAuthToken) {
                    if (!UsernamesAndAuthTokens.containsValue(authToken)) { // if authToken is unique
                        uniqueAuthToken = true;
                        // add authToken and associated account object to Users HashMap
                        Users.put(authToken, new Account(clientArgs[3], authToken));
                        // add username and associated authToken to UsernamesAndAuthTokens HashMap
                        UsernamesAndAuthTokens.put(clientArgs[3], authToken);
                        out.println(authToken); // send the authToken to client
                    } else {
                        // if previously generated authToken exists, generate a new one
                        authToken = random.nextInt(8999) + 1000;
                    }
                }
            } else {
                out.println("Sorry, the user already exists");
            }
        } else {
            out.println("Invalid Username");
        }
    }

    /**
     *  show all accounts
     */
    private void ShowAccounts() {
        int authToken;
        try {
            authToken = Integer.parseInt(clientArgs[3]);
            if (UsernamesAndAuthTokens.containsValue(authToken)) { // if given authToken is valid
                int counter = 1;
                for (Account account : Users.values()) {
                    out.println(counter + ". " + account.getUsername()); // send existing accounts to the client
                    counter++;
                }
            }
            else {
                out.println("Invalid Auth Token");
            }
        }
        catch (NumberFormatException ignored) {
            out.println("Invalid Auth Token");
        }
    }

    /**
     *  send new message
     */
    private void SendMessage() {
        int authToken;
        try {
            authToken = Integer.parseInt(clientArgs[3]);
            if (Users.containsKey(authToken)) { // if given authToken is valid
                if (UsernamesAndAuthTokens.containsKey(clientArgs[4])) { // if recipient exists
                    if (!Objects.equals(clientArgs[5], "")) { // if message body in not empty
                        // create new message object and add it to the messages list
                        Message message = new Message(Users.get(authToken).getUsername(), clientArgs[4], clientArgs[5]);
                        Messages.add(message);
                        // add the new message to the recipient messageBox
                        Users.get(UsernamesAndAuthTokens.get(clientArgs[4])).addMessage(message);
                        out.println("OK");
                    }
                    else {
                        out.println("Message body cannot be empty");
                    }
                } else {
                    out.println("User does not exist");
                }
            }
            else {
                out.println("Invalid Auth Token");
            }
        }
        catch (NumberFormatException ignored) {
            out.println("Invalid Auth Token");
        }
    }

    /**
     *  show inbox
     */
    private void ShowInbox() {
        int authToken;
        try {
            authToken = Integer.parseInt(clientArgs[3]);
            if (Users.containsKey(authToken)) { // if given authToken is valid
                boolean emptyInbox = true;
                for (Message message: Users.get(authToken).getMessageBox()) { // print every message in messageBox
                    emptyInbox = false;
                    if (message.isRead()) {
                        out.println(message.getId() + ". from: " + message.getSender());
                    }
                    else {
                        out.println(message.getId() + ". from: " + message.getSender() + "*");
                    }
                }
                if (emptyInbox) {
                    out.println("Empty inbox");
                }
            }
            else {
                out.println("Invalid Auth Token");
            }
        }
        catch (NumberFormatException ignored) {
            out.println("Invalid Auth Token");
        }
    }

    private void ReadMessage() {
        int authToken;
        try {
            authToken = Integer.parseInt(clientArgs[3]);
            if (Users.containsKey(authToken)) { // if given authToken is valid
                int messageID;
                try {
                    messageID = Integer.parseInt(clientArgs[4]);
                    Message requestedMessage = null;
                    for (Message message: Users.get(authToken).getMessageBox()) { // find the requested message
                        if (message.getId() == messageID) {
                            requestedMessage = message;
                            break;
                        }
                    }
                    if (requestedMessage != null) {
                        requestedMessage.setRead(true); // update the read field of the message
                        // send message content to the client
                        out.println("(" + requestedMessage.getSender() + ")" + requestedMessage.getBody());
                    }
                    else {
                        out.println("Message ID does not exist");
                    }
                } catch (NumberFormatException ignored) {
                    out.println("Invalid message ID");
                }
            }
            else {
                out.println("Invalid Auth Token");
            }
        }
        catch (NumberFormatException ignored) {
            out.println("Invalid Auth Token");
        }
    }

    /**
     *  delete message
     */
    private void DeleteMessage() {
        int authToken;
        try {
            authToken = Integer.parseInt(clientArgs[3]);
            if (Users.containsKey(authToken)) { // if given authToken is valid
                int messageID;
                try {
                    messageID = Integer.parseInt(clientArgs[4]);
                    Message requestedMessage = null;
                    for (Message message: Users.get(authToken).getMessageBox()) { // find the requested message
                        if (message.getId() == messageID) {
                            requestedMessage = message;
                            break;
                        }
                    }
                    if (requestedMessage != null) {
                        Users.get(authToken).getMessageBox().remove(requestedMessage); // remove from the messageBox
                        out.println("OK");
                    }
                    else {
                        out.println("Message does not exist");
                    }
                } catch (NumberFormatException ignored) {
                    out.println("Invalid message ID");
                }
            }
            else {
                out.println("Invalid Auth Token");
            }
        }
        catch (NumberFormatException ignored) {
            out.println("Invalid Auth Token");
        }
    }
}
