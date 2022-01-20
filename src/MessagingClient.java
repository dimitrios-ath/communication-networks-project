import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessagingClient {

    String address;
    int port;
    String[] args;

    /**
     *  constructor
     *  @param address      server address to connect
     *  @param port         server port to connect
     *  @param args         command line arguments to send to the server
     */
    public MessagingClient(String address, int port, String[] args) {
        this.address = address;
        this.port = port;
        this.args = args;
        SendRequest();
    }

    /**
     *  connects to server and waits for reply
     */
    private void SendRequest() {
        try {
            Socket socket = new Socket(address, port); // connect to the server
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // send String[] args to the server and wait for response
            oos.writeObject(args);

            // get the input stream of server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            while (response != null) { // print incoming messages from server
                System.out.println(response);
                response = in.readLine();
            }
            oos.close(); // close the socket
        } catch (IOException ignored) {}
    }
}
