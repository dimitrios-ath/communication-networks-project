# Communication Networks Project

_Name/AEM:_ Dimitrios Athanasiadis - 3724

**Usage:**
```
Client: java -jar Client.jar <ip> <port number> <FN_ID> <args>
Server: java -jar Server.jar <port number>
```


**General Classes:**

* _Account.java_
  * Holds information about a specific account, such as username, associated authToken and message box.
* _Message.java_
  * Holds information about a specific message, such as sender, receiver, body and message read state. Additionally, 
  each message has an auto-increment identifier.

**Client Classes:**

* _client.java_
  * The client main class. If the command line arguments are **valid**, it creates a MessagingClient object,
    otherwise it prints the usage message.
* _MessagingClient.java_
  * Creates a **socket** and connects to the server, on the given address and port. It sends the entered command 
  line arguments to the server and waits for response.

**Server Classes:**

* _server.java_
  * The server _main_ class. If the command line arguments are **valid**, it creates a MessagingServer object, 
  otherwise it prints the usage message.
* _MessagingServer.java_
  * Creates a **socket** on the given port and listens for connections. For every new client, a ClientHandler 
  **object** is created, in a new **thread**.
* _ClientHandler.java_
  * ClientHandler class instances are intended to be executed by a new **thread**. In _run()_ method the command 
  line **arguments** of client are received and passed to the **RequestHandler**. 
* _RequestHandler.java_
  * Processes client requests. Based on the request **FN_ID**, calls the appropriate method. The **supported** 
  methods are:
    1. _CreateAccount():_ creates a new account
    2. _ShowAccounts():_ shows registered accounts
    3. _SendMessage():_ sends new message to user
    4. _ShowInbox():_ shows user inbox 
    5. _ReadMessage():_ reads a message from inbox
    6. _DeleteMessage():_ delete a message from inbox