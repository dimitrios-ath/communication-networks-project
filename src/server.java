public class server {

    /**
     *  print usage of server
     */
    public static void usage() {
        System.out.println("Usage: java server <port number>");
    }

    /**
     *  server main method
     *  @param args      given command line arguments
     */
    public static void main(String[] args) {
        int port; // default port
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
                if (port>0 && port <= 65535) {
                    MessagingServer messagingServer = new MessagingServer(port);
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid port number\n");
                usage();
                System.exit(1);
            }
        } else {
            usage();
            System.exit(1);
        }
    }
}
