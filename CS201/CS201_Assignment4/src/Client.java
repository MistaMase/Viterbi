import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Client extends Thread {
	private ObjectInputStream input  = null; // Receiving from server
	private ObjectOutputStream output = null; // Sending to server
	private Socket socket;
	private ReentrantLock turn = new ReentrantLock();
	private Condition canBeActive = turn.newCondition();
	private Board board = null;
	private boolean isGame = true;
	
	// Called when the client is instantiated clientside
	public Client(String hostname, int port) throws IOException, ClassNotFoundException {
		Socket socket = new Socket(hostname, port);
		this.socket = socket;
		if(!establishTwoWayConnection()) {
			throw new IOException();
		}
				
		// Read number of players in server
		Integer numberInGame = (Integer)input.readObject();
		
		if(numberInGame == 1)
			sendPlayers();
		
		this.start();
		clientStuff();
	}
	
	private void clientStuff() {
		while(isGame) {
			try {
				turn.lock();
				canBeActive.await();
				
				if(!this.isAlive())
					return;
				//Now it's the player's turn
				Scanner sc = new Scanner(System.in);
				
				//Remove any extra keys typed
				//while(sc.hasNext())
				//	sc.next();
				
				boolean isValid = false;
				String direction = "";
				while(!isValid) {		
					//Ask playerTurn to answer across or down
					System.out.print("Would you like to answer a question across (a) or down (d)? ");
					direction = sc.nextLine().toLowerCase();
					if((direction.equals("a") && board.acrossWordsRemaining.size() > 0) || (direction.equals("d") && board.downWordsRemaining.size() > 0))
						isValid = true;
					else 
						System.out.println("That is not a valid option.");
				}
				
				isValid = false;
				Integer number = 0;
				while(!isValid) {
					//Ask playerTurn what number to answer
					System.out.print("Which number? ");
					try {
						number = Integer.parseInt(sc.nextLine());
						if(direction.equalsIgnoreCase("a")) {
							if(board.boardEntry.containsKey(number+"A") && board.acrossWordsRemaining.contains(board.boardEntry.get(number+"A").get(0)))
								isValid = true;
						}
						else if(direction.equalsIgnoreCase("d")){
							if(board.boardEntry.containsKey(number+"D") && board.downWordsRemaining.contains(board.boardEntry.get(number+"D").get(0)))
								isValid = true;
						}
						if(!isValid)
							System.out.println("That is not a valid option.");
						
					} catch(NumberFormatException nfe) {
						System.out.println("That is not a valid option.");
					}
				}
				
				//Ask playerTurn what their guess is
				System.out.print("What is your guess for " + number + " " + (direction.equals("a") ? "across" : "down") + "? ");
				String guess = sc.nextLine().toLowerCase();
				
				String line = new String(guess + "_" + number + "_" + (direction.equals("a") ? "across" : "down"));
				try {
					output.writeObject(line);
					output.flush();
				} catch(IOException ioe) {}
								
			} catch (InterruptedException ie) {
				System.out.println("IE in main client thread. " + ie.getMessage()); 
			}
		}
	}

	
	// Creates an easy IOStream between the client and server
	private boolean establishTwoWayConnection() {
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
			
		} catch (UnknownHostException uhe) {
			System.out.println("Unable to connect to " + socket.getInetAddress() + " on port " + socket.getPort());
			return false;
			
		} catch (IOException e) {
			System.out.println("Unable to connect to server " + e.getMessage());
			return false;
		}
		return true;
	}
	
	// Sends the initial number of players. Only called on the first client that connects.
	protected void sendPlayers() throws NumberFormatException {
		System.out.print("How many players will there be? ");
		Scanner sc = new Scanner(System.in);
		Integer players = Integer.parseInt(sc.next());
		try {
			output.writeObject(players);
			output.flush();
		} catch (IOException e) {
			System.out.println("Error sending players to server");
		}
	}
	
	// Reads incoming messages from the server and responds accordingly
	public void run() {
		try {
			while(isGame) {
				try {
					turn.lock();
					Object incoming = input.readObject();
					// Print client message
					
					//Simply skip for empty messages
					if(incoming == null)
						continue;
					
					try {
						if(((String)incoming).equals("TURN")) {
							try {
								canBeActive.signal();
							} finally {
								turn.unlock();
								try {
									Thread.sleep(10);
								} catch (InterruptedException ie) {
									System.out.println("IE sleeping thread");
								}
								Thread.yield();
							}
						}
						else if(((String)incoming).equals("END")) {
							input.close();
							output.close();
							socket.close();
							isGame = false;
						}
						else if(((String)incoming).substring(0, 8).equals("Message:"))
							System.out.println(((String)incoming).substring(8));
						continue;
					
					// Clearly wasn't a string
					} catch (ClassCastException cce) {}
					
					// Print the board
					try {
						board = (Board)incoming;
						
						// Prints the board locally
						for(int i = 0; i < board.boardSize; i++) {			
							for(int j = 0; j < board.boardSize; j++) {
								//if(board.board[i][j].isFirstTileOfWord())
									//System.out.print(board.board[i][j].tileNumber)
								//else
									System.out.print(" ");
								if(board.board[i][j].tileValue != 0)
									System.out.print(board.board[i][j].tileValue);
								else
									System.out.print(" ");
							}
							System.out.println();
						}
						
						// Prints the remaining questions
						if(board.acrossWordsRemaining.size() > 0)
							System.out.println("ACROSS");
						for(int i = 0; i < 10; i++)
							if(board.boardEntry.containsKey("" + i + "A") && board.acrossWordsRemaining.contains(board.boardEntry.get(""+i+"A").get(0)))
							 System.out.println("" + i + " " + board.boardEntry.get(""+i+"A").get(1));
						
						if(board.downWordsRemaining.size() > 0)
							System.out.println("DOWN");
						for(int i = 0; i < 10; i++)
							if(board.boardEntry.containsKey("" + i + "D") && board.downWordsRemaining.contains(board.boardEntry.get(""+i+"D").get(0)))
							 System.out.println("" + i + " " + board.boardEntry.get(""+i+"D").get(1));
						
						continue;
						
					// Clearly wasn't the board
					} catch (ClassCastException cce) {}
				} finally {
					if(turn.isHeldByCurrentThread())
						turn.unlock();
				}
			}
		} catch (IOException ioe) {
			System.exit(0);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error reading from server: " + cnfe.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to 201 Crossword (104 Scrabble but funner)");
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the server hostame: ");
		String host = sc.next();
		System.out.print("Enter the server port: ");
		int port = 0;
		Client client = null;
		try {
			port = Integer.parseInt(sc.next());
			client = new Client(host, port);
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid port. Rerun and try again");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Exception connecting to the server. Rerun and try again.");
		}
	}
}