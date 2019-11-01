import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	ServerSocket ss;
	ArrayList<ServerThread> threads = new ArrayList<ServerThread>();
	
	public Server() { 
		try {
			System.out.println("Trying to bind to port 6789");
			ss = new ServerSocket(6789);
			System.out.println("Bound to port 6789");
			
			// Allow infinite connections
			while(true) {
				Socket s = ss.accept();
				threads.add(new ServerThread(s));
				
				
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public static void main(String []args) {
		Server s = new Server();
	}
}
