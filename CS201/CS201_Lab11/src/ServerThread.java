import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class ServerThread extends Thread {
	Socket client = null;
	PrintWriter pw = null;
	BufferedReader br = null;
	
	public ServerThread(Socket s) {
		try {
			System.out.println("New Thread");
			client = s;
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			br = new BufferedReader(isr);
			pw = new PrintWriter(client.getOutputStream());
			
			String header = br.readLine();
			
			
			File f = new File("index.html");
			if(f.exists())
				System.out.println("Found file");
			else
				System.out.println("Can't find file");
			BufferedReader brf = new BufferedReader(new FileReader(f));
			String line;
			System.out.println("Let's send it");
			
			//Print the response 
			pw.write("HTTP/1.1 200 OK");
			pw.write("Content-Type: text/html; charset");
			while((line = brf.readLine()) != null) {
				pw.write(line);
				System.out.println("Sending it");
			}
			
			pw.write("Connection: close");
			
			pw.flush();

			System.out.println("Finished sending it");
			
			
			start();
			
			
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run() {
	}
}
