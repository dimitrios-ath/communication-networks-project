import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

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

            String[] clientArgs = (String[]) ois.readObject();

            CommandHandler commandHandler = new CommandHandler(clientSocket, clientArgs);

            /*
            String line;
            while ((line = in.readLine()) != null) {
                // writing the received message from
                // client
                System.out.printf(
                        " Sent from the client: %s\n",
                        line);
                out.println(line);
            }*/
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
