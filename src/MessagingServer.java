import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingServer {

    private ServerSocket serverSocket;
    private final int port;

    /**
     *  constructor
     *  @param port     listening port
     */
    public MessagingServer(int port) {
        this.port = port;
        NewClientHandler();
    }

    /**
     *  listens for incoming connections and accepts them
     */
    private void NewClientHandler() {
        try {
            serverSocket = new ServerSocket(port); // create a ServerSocket on the given port
            serverSocket.setReuseAddress(true);
            while (true) { // always listen for connections
                Socket clientSocket = serverSocket.accept(); // accept incoming connection
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start(); // handle the client with ClientHandler() in a new thread
            }
        }
        catch (IOException ignored) {}
        finally { // cleanup
            if (serverSocket != null) {
                try {serverSocket.close();}
                catch (IOException ignored) {}
            }
        }
    }
}
