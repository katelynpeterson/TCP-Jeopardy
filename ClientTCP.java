
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class ClientTCP implements Runnable{
	
    private Socket socket;
    private Scanner scanner;
    Thread listenThread;	
    Thread respondThread;
    
	public static void main(String[] args) throws Exception{
			    
        ClientTCP client = new ClientTCP(
        InetAddress.getByName(args[0]), 
        Integer.parseInt(args[1]));
        
        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.listenThread.start();
        client.respondThread.start();      
	}
	
    private ClientTCP(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
        listenThread = new Thread();
        respondThread = new Thread();
    }
    
    public void listen() {
    	String output;
    	try {
    		InputStream inp = null;
    		BufferedReader brinp = null;
    		DataOutputStream out = null;
			inp = socket.getInputStream();
			brinp = new BufferedReader(new InputStreamReader(inp));
			out = new DataOutputStream(socket.getOutputStream());
    		String line;
    		while(true){
	    			
    				line = brinp.readLine();
    				if ((line == null) || line.equalsIgnoreCase("QUIT")){
    					socket.close();
    					return;
    				}
    				else{
    					if(!line.equals(null)){
	    					System.out.println("Message from the server: " + line);
	    					out.writeBytes(line + "\n\r");
	    					out.flush();
    					
    						if(line.equalsIgnoreCase("correct")) {
    							//flush the output stream in the respond or stop the thread and restart it
    							respondThread.interrupt();
    						}
    					}
	    			}
	    		}
    		}catch(IOException e){
    			e.printStackTrace();
    			return;
    		}
    }
    
    public void respond() {
    	    	
    	String input;
    	try {
	    	while (true) {
	            input = scanner.nextLine();
	            PrintWriter out;
				
				out = new PrintWriter(this.socket.getOutputStream(), true);
				out.println(input);
				out.flush();
	    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		respond();
		listen();
	}
	
}
