import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingServer {
    private ServerSocket serverSocket;
    private final int port;

    public MessagingServer(int port) {
        this.port = port;
        IncomingRequestsHandler();
    }

    private void IncomingRequestsHandler() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);

            // running infinite loop for getting client request
            while (true) {
                // socket object to receive incoming client requests
                Socket clientSocket = serverSocket.accept();
                // Displaying that new client is connected to server
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                // create a new thread object
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                // This thread will handle the client separately
                new Thread(clientHandler).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
