import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatRoom {

	private Vector<ServerThread> serverThreads;
	private Vector<Lock> locks;
	private Vector<Condition> conditions;
	private int currentClient = 0;
	
	public ChatRoom(int port) {
		try {
			System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			serverThreads = new Vector<ServerThread>();
			locks = new Vector<Lock>();
			conditions = new Vector<Condition>();
			while(true) {
				Socket s = ss.accept(); // blocking
				System.out.println("Connection from: " + s.getInetAddress());
				Lock l = new ReentrantLock();
				locks.add(l);
				Condition c = l.newCondition();
				conditions.add(c);
				ServerThread st = new ServerThread(s, this, l, c, (serverThreads.size() == currentClient) ? true : false);
				serverThreads.add(st);

			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	
	public void broadcast(String message, ServerThread st) {
		if (message != null) {
			if(message.contains("END_OF_MESSAGE")) {
				switchClient();
				System.out.println(message);
				for(ServerThread threads : serverThreads) {
					if (st != threads)
						threads.sendMessage("Client " + currentClient + "'s turn");
				}
			}
			else {
				System.out.println(message);
				for(ServerThread threads : serverThreads) {
					if (st != threads) {
						threads.sendMessage(message);
					}
				}
			}
		}
	}
	
	public void switchClient() {
		serverThreads.get(currentClient).awaitThis();
		
		if(currentClient + 1 == serverThreads.size())
			currentClient = 0;
		else
			currentClient++;
		
		System.out.println("ServerThreads size: " + serverThreads.size());
		
		for(int i = 0; i < serverThreads.size(); i++) {
			if(i == currentClient)
				serverThreads.get(i).turn(true, i == serverThreads.size()-1);
			else
				serverThreads.get(i).turn(false, i == serverThreads.size()-1);
		}
	}
	
	
	
	public static void main(String [] args) {
		ChatRoom cr = new ChatRoom(6789);
	}
}
