	import java.io.BufferedReader;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.net.InetAddress;

public class ServerTCP {
	
	private ServerSocket server;
	
	public static void main(String[] args) throws IOException{
		
        ServerTCP app = new ServerTCP(args[0]);
        
        System.out.println("\r\nRunning Server: " + 
                "Host=" + app.getSocketAddress().getHostAddress() + 
                " Port=" + app.getPort());
        
        app.listen();
        
	//A new Service registered with the 1286 port
	/*ServerSocket ss = new ServerSocket(1286);
	
	//Accept the connection request made with the server socket
	Socket s = ss.accept();
	
	//Establish the output stream from the socket connection
	OutputStream socketOutStream = s.getOutputStream();
	DataOutputStream socketDOS = new DataOutputStream(socketOutStream);
	
	//Communicate with the socket data stream with a message
	socketDOS.writeUTF("Hello World!");
	
	//CleanUp
	socketDOS.close();
	socketOutStream.close();
	s.close();
	ss.close();*/
		
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

	int numPlayers = 0;	

	while(numPlayers <3){
		try{
		client = this.server.accept();
		}catch(IOException e){
		System.out.println("IO Error " + e);
		}

		new EchoThread(client).start();
		numPlayers +=1;
	
		
        //Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
	//String peerName = client.getpeername();
	// int portName = client.getInetAddress().getPort();
        System.out.println("\r\nNew connection from player" + numPlayers + " (" + clientAddress  + ")");
      // System.out.println("port name " +portName); 
       }
//	 BufferedReader in = new BufferedReader(
  //              new InputStreamReader(client.getInputStream()));  
        
       // while ( (data = in.readLine()) != null ) {
         //   System.out.println("\r\nMessage from " + clientAddress + ": " + data);
       // }
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
