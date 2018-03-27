	import java.io.BufferedReader;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.net.InetAddress;
	import java.io.PrintWriter;

public class ServerTCP {
	
	private ServerSocket server;
	
	public static void main(String[] args) throws IOException{
		
        ServerTCP app = new ServerTCP(args[0]);
        
        System.out.println("\r\nRunning Server: " + 
                "Host=" + app.getSocketAddress().getHostAddress() + 
                " Port=" + app.getPort());
        
        app.listen();
        
	}

    public ServerTCP(String ipAddress) throws IOException {
        if (ipAddress != null && !ipAddress.isEmpty()) 
          this.server = new ServerSocket(0, 1, InetAddress.getByName(ipAddress));
        else 
          this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
    }
	
    protected void listen() throws IOException {
	Socket client = null;
        String data = null;
	EchoThread [] playerArray = new EchoThread[3];
	String clientAddress=null;
	int numPlayers = 0;	

	while(numPlayers <1){
		try{
		client = this.server.accept();
		}catch(IOException e){
		System.out.println("IO Error " + e);
		}
		
		playerArray[numPlayers] = new EchoThread(client);
		playerArray[numPlayers].start();

		System.out.println("socket of player" + numPlayers + " : " + playerArray[numPlayers].getSocket());
		numPlayers +=1;
	
		
        //Socket client = this.server.accept();
       clientAddress = client.getInetAddress().getHostAddress();
	//String peerName = client.getpeername();
	// int portName = client.getInetAddress().getPort();
        System.out.println("\r\nNew connection from player" + numPlayers + " (" + clientAddress  + ")");
      // System.out.println("port name " +portName); 
       }
	System.out.println("We left while loop 1");

	String line = "Ready to play!";
	DataOutputStream out;

	for(int i =0; i< numPlayers; i++){
		System.out.println("in the for loop" + line);

		Socket socket = playerArray[i].getSocket();
		System.out.println("this is the socket " +socket);
		out = new DataOutputStream(socket.getOutputStream());	
		out.writeBytes(line + "\n\r");
		out.flush();
	}
	
	System.out.println("After the for loop");
	
	//EchoThread thread = new EchoThread(this.socket);
	/* BufferedReader in = new BufferedReader(
              new InputStreamReader(server.getInputStream()));  
        
	 while ( (data = in.readLine()) != null ) {
           System.out.println("\r\nMessage from the Server: " + data);
       }*/
    }
    
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
}

class Player implements Runnable{
	private String threadName;
	private	Thread t;

	Player(String name){
	threadName = name;
}

public void run(){
	System.out.println(threadName + " is running");
}

public void start(){
	if(t==null){
		t = new Thread (this, threadName);
		t.start();
		}
	}
}
