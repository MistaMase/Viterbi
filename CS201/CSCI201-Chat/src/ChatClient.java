import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
	
	public ChatClient(String hostname, int port) {
		Socket s = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			System.out.println("Trying to connect to " + hostname + ":" + port);
			s = new Socket(hostname, port);
			System.out.println("Successfully connected to " + hostname + ":" + port);
			
			//Set up correct input and output
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			br = new BufferedReader(isr);
			pw = new PrintWriter(s.getOutputStream());
			
			sendMessage(pw, "Hello from the client");
			String line = br.readLine();
			System.out.println("Recieved from server: " + line);
			
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			try {
				if(s != null)
					s.close();
				if(pw != null)
					pw.close();
				if(br != null)
					br.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing stuff: " + ioe.getMessage());
			}
		}
	}
	
	public void sendMessage(PrintWriter pw, String message) {
		pw.println(message);
		pw.flush();
	}
	
	public static void main(String[] args) {
		ChatClient cc = new ChatClient("localhost", 6789);
	}
}
