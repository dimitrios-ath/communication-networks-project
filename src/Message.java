public class Message {

    private static int idCounter = 0;
    private final int id;
    private boolean isRead = false;
    private String sender;
    private String receiver;
    private String body;

    public Message(String sender, String receiver, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.id = idCounter;
        idCounter++;
    }

    public int getId() {return id;}
    public boolean isRead() {return isRead;}
    public void setRead(boolean read) {isRead = read;}
    public String getSender() {return sender;}
    public void setSender(String sender) {this.sender = sender;}
    public String getReceiver() {return receiver;}
    public void setReceiver(String receiver) {this.receiver = receiver;}
    public String getBody() {return body;}
    public void setBody(String body) {this.body = body;}

}
