
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class ClientTCP {
	
    private Socket socket;
    private Scanner scanner;
    
	public static void main(String[] args) throws Exception{
			    
        ClientTCP client = new ClientTCP(
                InetAddress.getByName(args[0]), 
                Integer.parseInt(args[1]));
        
        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();                
         
        
		// Establish a server connection through 1286 port
		/*Socket s = new Socket("localhost", 1286);
		
		//Access the input stream of the server socket
		InputStream sIn = s.getInputStream();
		
		//Wrap the socket input stream with data input stream
		DataInputStream socketDIS = new DataInputStream(sIn);
		
		//Read from the socket data input stream
		String testString = new String(socketDIS.readUTF());
		
		//Print the data read
		System.out.print(testString);
		
		//Clean up
		socketDIS.close();
		sIn.close();
		s.close();*/
	}
	
    private ClientTCP(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }
    
    private void start() throws IOException {
        String input;
        while (true) {
            input = scanner.nextLine();
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(input);
            out.flush();
        }
    }
	
}
