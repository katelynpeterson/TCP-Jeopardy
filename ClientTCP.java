
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
	//Start the client and pass the Server's IP and port		    
        ClientTCP client = new ClientTCP(
        InetAddress.getByName(args[0]), 
        Integer.parseInt(args[1]));
        
	//Print out that we successfully connected to server
        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        //start threads
	client.listenThread.start();
        client.respondThread.start();      
	}
	
	//Constructor of ClientTCP
	//Create objects
    private ClientTCP(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
        listenThread = new Thread();
        respondThread = new Thread();
    }
    
	//Method for the listening thread
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
					//print out message from server
    					if(!line.equals(null)){
	    					System.out.println("Message from the server: " + line);
	    					out.writeBytes(line + "\n\r");
	    					out.flush();
    					//if the message says the answer was correct, interupt the responding thread
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
    
	//method for respond thread
    public void respond() {
    	    	
    	String input;
    	try {
	    	while (true) {
	            input = scanner.nextLine();
	            PrintWriter out;
			//send what the client says to the server
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
