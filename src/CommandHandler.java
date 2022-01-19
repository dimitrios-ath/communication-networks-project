import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CommandHandler {

    PrintWriter out;
    private final Socket clientSocket;
    int fn_id;
    String[] args;

    public CommandHandler(Socket clientSocket, String[] clientArgs) {
        this.clientSocket = clientSocket;
        this.args = clientArgs;
        try {
            this.fn_id = Integer.parseInt(clientArgs[2]);
        } catch (NumberFormatException ignored) {}
        Handler();
    }

    public void Handler() {
        switch (fn_id) {
            case 1 -> {
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("command 1");
                    for (int i=0; i<10; i++) {
                        out.println("create account "+i);
                    }
                } catch (IOException ignored) {}

            }
            case 2 -> {
                System.out.println("command 2");
            }
            case 3 -> {
                System.out.println("command 3");
            }
            case 4 -> {
                System.out.println("command 4");
            }
            case 5 -> {
                System.out.println("command 5");
            }
            case 6 -> {
                System.out.println("command 6");
            }
        }
    }
}
