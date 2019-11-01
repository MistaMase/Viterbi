import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatRoom {

	public ChatRoom(int port) {
		ServerSocket ss = null;
		Socket s = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			System.out.println("Trying to bind to port " + port);
			ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			s = ss.accept();
			
			//Gets IP Address of client
			System.out.println("Client connected: " + s.getInetAddress());
			
			//Read data from client - Read
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			br = new BufferedReader(isr);
			
			//Send data to client - Write
			pw = new PrintWriter(s.getOutputStream());
			
			//Read a line
			String line = br.readLine(); // Blocking line
			System.out.println("Received from client: " + line);
			
			//Send da message
			sendMessage(pw, "Thanks for sending me a message");
			
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			try {
				if(br != null)
					br.close();
				if(pw != null)
					pw.close();
				if(s != null)
					s.close();
				if(ss != null)
					ss.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing stuff: " + ioe.getMessage());
			}
		}
	}
	
	public void sendMessage(PrintWriter pw, String message) {
		//Send a line
		pw.println("Thank for sending me a message!");
		
		//Flushes the buffer and send the info to the client even if it's very short and doesn't fill the internal buffer
		pw.flush();
	}
	
	public static void main(String[] args) {
		ChatRoom cr = new ChatRoom(6789);
	}

}
