import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerClient{
	
	protected ObjectInputStream input  = null; // Receiving from server
	protected ObjectOutputStream output = null; // Sending to server
	protected Socket socket; // The socket object of the connected Client
	protected int score = 0;
	
	public ServerClient(Socket s) throws IOException {
		this.socket = s;
		if(!establishTwoWayConnection())
			throw new IOException();
		//this.start();
	}
	
	// Creates an easy IOStream between the client and server
	private boolean establishTwoWayConnection() {
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException uhe) {
			System.out.println("Unable to connect to " + socket.getInetAddress().toString());
			return false;
			
		} catch (IOException e) {
			System.out.println("Error connecting to server " + e.getMessage());
			return false;
		}
		return true;
	}
	
	protected void sendToClient(Object o) {
		try {
			output.writeObject(o);
			output.flush();
		} catch (IOException ioe) {
			System.out.println("IOE sending to client " + ioe.getMessage());
		}
	}
}
