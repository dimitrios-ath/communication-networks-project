import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String username;
    private final int authToken;
    private final List<Message> messageBox;

    /**
     *  constructor
     *  @param username      username
     *  @param authToken     generated authToken
     */
    public Account(String username, int authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messageBox = new ArrayList<>();
    }

    /**
     *  getters
     */
    public String getUsername() {return username;}
    public int getAuthToken() {return authToken;}
    public List<Message> getMessageBox() {return messageBox;}

    /**
     *  add message object to messageBox list
     *  @param message       message to add
     */
    public void addMessage(Message message) {
        messageBox.add(message);
    }

}
