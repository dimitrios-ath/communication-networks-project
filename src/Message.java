public class Message {

    private static int idCounter = 1;
    private final int id; // auto-increment message id
    private boolean isRead = false; // isRead is set to false when the message is created
    private String sender;
    private String receiver;
    private String body;

    /**
     *  constructor
     *  @param sender       message sender
     *  @param receiver     message recipient
     *  @param body         message body
     */
    public Message(String sender, String receiver, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.id = idCounter;
        idCounter++; // increment idCounter everytime id is assigned to message for uniqueness
    }

    /**
     *  getters
     */
    public boolean isRead() {return isRead;}
    public int getId() {return id;}
    public String getSender() {return sender;}
    public String getReceiver() {return receiver;}
    public String getBody() {return body;}

    /**
     *  setters
     */
    public void setRead(boolean read) {isRead = read;}
    public void setSender(String sender) {this.sender = sender;}
    public void setReceiver(String receiver) {this.receiver = receiver;}
    public void setBody(String body) {this.body = body;}
}
