import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MessagingClient {
    String address;
    int port;
    String[] args;

    public MessagingClient(String address, int port, String[] args) {
        this.address = address;
        this.port = port;
        this.args = args;
        SendRequest();
    }

    public void SendRequest() {
        try (Socket socket = new Socket(address, port)) {

        // writing to server
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // reading from server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(args);

        String x = in.readLine();
        while (x != null) {
            System.out.println("reply: " + x);
            x = in.readLine();
        }
        oos.close();



        /*
        // object of scanner class
        Scanner sc = new Scanner(System.in);
        String line = null;

        while (!"exit".equalsIgnoreCase(line)) {

            // reading from user
            line = sc.nextLine();

            // sending the user input to server
            out.println(line);
            out.flush();

            // displaying server reply
            System.out.println("Server replied " + in.readLine());
        }

        // closing the scanner object
        sc.close();*/
    }
    catch (IOException ignored) {}}

}
