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
	
	public static void main(String args[]) throws Exception{
		
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
    public ServerTCP(String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty()) 
          this.server = new ServerSocket(0, 1, InetAddress.getByName(ipAddress));
        else 
          this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
    }
	
    private void listen() throws Exception {
        String data = null;
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));  
        
        while ( (data = in.readLine()) != null ) {
            System.out.println("\r\nMessage from " + clientAddress + ": " + data);
        }
    }
    
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
}
