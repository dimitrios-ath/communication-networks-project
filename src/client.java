public class client {

    /**
     *  print usage of client
     */
    public static void Usage() {
        System.out.println("Usage: java client <ip> <port number> <FN_ID> <args>\n");
        System.out.println("Available options:\n");
        System.out.println("\tCreate account:\t java client <ip> <port number> 1 <username>");
        System.out.println("\tShow accounts:\t java client <ip> <port number> 2 <authToken>");
        System.out.println("\tSend message:\t java client <ip> <port number> 3 <authToken> <recipient> <message_body>");
        System.out.println("\tShow inbox:\t java client <ip> <port number> 4 <authToken>");
        System.out.println("\tRead message:\t java client <ip> <port number> 5 <authToken> <message_id>");
        System.out.println("\tDelete message:\t java client <ip> <port number> 6 <authToken> <message_id>\n");
    }

    /**
     *  client main method
     *  @param args      given command line arguments
     */
    public static void main(String[] args) {
        String address;
        int port, fn_id;
        // if argument number is correct
        if (args.length >= 4 && args.length<=6) {
            address = args[0];
            try {
                port = Integer.parseInt(args[1]);
                // if given port is valid
                if (port>0 && port <= 65535) {
                    try {
                        fn_id = Integer.parseInt(args[2]);
                        // if given fn_id is valid
                        if (fn_id > 0 && fn_id <= 6) {
                            boolean validArgs = false;
                            // based on fn_id decide if the given arguments are correct (client side check)
                            switch (fn_id) {
                                case 1, 2, 4 -> validArgs = (args.length == 4);
                                case 5, 6 -> validArgs = (args.length == 5);
                                case 3 -> validArgs = (args.length == 6);
                            }
                            if (validArgs) {
                                // if given arguments are correct, create a new MessagingClient object
                                MessagingClient messagingClient = new MessagingClient(address, port, args);
                            } else {
                                System.out.println("Invalid arguments for option " + fn_id + "\n");
                                Usage();
                                System.exit(1);
                            }
                        }
                    } catch (NumberFormatException ignored) {
                        System.out.println("Invalid FN_ID\n");
                        Usage();
                        System.exit(1);
                    }
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid port number\n");
                Usage();
                System.exit(1);
            }
        } else {
            Usage();
            System.exit(1);
        }
    }
}
