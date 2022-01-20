import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    /**
     *  constructor
     *  @param socket        client socket
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    /**
     *  run is executed when the new thread is constructed
     */
    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            // get the output stream of client
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // get the input stream of client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            // read the command line arguments of the client
            String[] clientArgs = (String[]) ois.readObject();
            // create a new RequestHandler object to handle the request
            RequestHandler requestHandler = new RequestHandler(clientSocket, clientArgs);
        }
        catch (IOException | ClassNotFoundException ignored) {}
        finally { // cleanup
            try {
                if (out != null) {out.close();}
                if (in != null) {in.close(); clientSocket.close();}
            }
            catch (IOException ignored) {}
        }
    }
}
