import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ServerThread extends Thread {

	private PrintWriter pw;
	private BufferedReader br;
	private ChatRoom cr;
	private Lock l;
	private Condition c;
	protected boolean turn = false;
	
	public ServerThread(Socket s, ChatRoom cr, Lock l, Condition c, boolean turn) {
		try {
			this.turn = turn;
			this.cr = cr;
			this.l = l;
			this.c = c;
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.println("Turn: " + turn);
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}

	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public void turn(boolean turn, boolean last) {
		this.turn = turn;
		System.out.println("Turn Now: " + turn);
		if(last)
			c.signalAll();
	}
	
	public void run() {
		try {
			while(turn) {
				l.lock();
				String line = br.readLine();
				cr.broadcast(line, this);
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
		} 
	}
	
	public void awaitThis() {
		try {
			c.await();
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
	}
}
