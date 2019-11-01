import java.io.IOException;
import java.net.ServerSocket;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends Thread {
	private int port = 3456;
	
	private Vector<ServerClient> players = null;
	private Board board = null;
	private ServerSocket ss = null;
	private int playerTurn = 0;
	private ReentrantLock lock = new ReentrantLock();
	
	public Server() {
		System.out.println("Listening on port " + port);
		this.start();
	}
	
	
	private void runGame() {
		try {
			ss = new ServerSocket(port);
			System.out.println("Waiting for players ...");
		} catch (IOException ioe) {
			System.out.println("ioe creating server " + ioe.getMessage());
		}
		
		players = new Vector<ServerClient>();
		
		//Accept incoming connection
		ServerClient serverClient;
		try {
			serverClient = new ServerClient(ss.accept());
			players.add(serverClient);
			System.out.println("Connection from " + players.get(0).socket.getInetAddress());
			
			// Send to the client how many players are in the server
			players.get(0).sendToClient(new Integer(players.size()));
			
			// Read line from client for how many players
			Integer desiredPlayers = 1;
			try {
				desiredPlayers = (Integer)players.get(0).input.readObject();
			} catch (IOException | ClassNotFoundException ex) {
				System.out.println("Unable to read number of players from client. Please restart and try again");
			}
					
			// Print to console
			System.out.println("Number of Players: " + desiredPlayers);
			
			//Generate board
			board = new Board("temp");
						
			// Sit on the blocking accept call until all players have been added to the game
			while(players.size() < desiredPlayers) {
				System.out.println("Waiting for player " + (players.size() + 1));
				
				// Send message to client(s) for them to print
				for(ServerClient player: players)
					player.sendToClient("Message:Waiting for player " + (players.size() + 1));
				
				// Generate the board each time a new client
				board = new Board("temp");
				
				// Accept new clients
				serverClient = new ServerClient(ss.accept());
				players.add(serverClient);
				System.out.println("Connection from " + players.get(players.size()-1).socket.getInetAddress());
				
				//Notify all old players a new client joined
				for(int i = 0; i < players.size() - 1; i++) {
					players.get(i).sendToClient("Message:Connection from " + players.get(players.size()-1).socket.getInetAddress());
				}
				
				// Notify the new client of how many players are in the game
				players.get(players.size()-1).sendToClient(new Integer(players.size()));
			}
						
			// Tell all the clients that the game is beginning
			for(ServerClient player: players) 
				player.sendToClient("Message:The game is beginning");
			
			
			System.out.println("Successfully loaded " + desiredPlayers + " player(s)");
			System.out.println("Game can now begin");
			System.out.println("Sending game board");
			
		} catch (IOException e) {
			System.out.println("ioe in setup process " + e.getMessage());
		}
		
		//Main game loop
		while(true) {
			//Send game board (which has the questions) to everyone
			broadcast(board);

			//Notify client who's turn it is
			//Sends "TURN" to client for them to deal with accordingly
			players.get(playerTurn).sendToClient("TURN");
			
			
			//Clientside - Get user's input
			boolean correctAnswer = false;
			try {
				correctAnswer = clientAnswer();
			} catch(IOException ioe) {
				System.out.println("IOE getting client's answer");
			}
							
			if(correctAnswer)
				players.get(playerTurn).score++;
			else
				incrementPlayerTurn();	
			
			if(board.acrossWordsRemaining.size() == 0 && board.downWordsRemaining.size() == 0)
				break;
		}
		
		//Broadcast end game scores
		System.out.println("The game has concluded. Sending scores.");
		broadcast("Final Score");
		int indexOfHighestPlayer = -1;
		for(ServerClient player: players) {
			broadcast("Player " + (players.indexOf(player)+1) + " - " + player.score + " correct answer.");
			if(indexOfHighestPlayer == -1 || player.score > players.get(indexOfHighestPlayer).score)
				indexOfHighestPlayer = players.indexOf(player);
		}
		broadcast("Player " + (indexOfHighestPlayer+1) + " is the winner.");
		for(ServerClient player: players) {
			player.sendToClient("END");
			try {
				player.output.close();
				player.input.close();
				player.socket.close();
			} catch(IOException ioe) {}
		}
		try {
			ss.close();
		} catch (IOException ioe) {}
	}
	
	public void run() {
		while(true) {
			try {
				lock.lock();
				runGame();
			} finally {
				lock.unlock();
			}
		}
	}
	
	// Returns if the answer they gave is the correct answer
	// Throws an IOException for any formatting errors
	private boolean clientAnswer() throws IOException {
		//Broadcast playerTurn's guess to everyone except playerTurn
		//Excepts string format from client: "guess_number_across/down"
		String userGuess = "";
		try {
			userGuess = (String)players.get(playerTurn).input.readObject();
		} catch (ClassNotFoundException | IOException e) {
			throw new IOException();
		}
				
		StringTokenizer st = new StringTokenizer(userGuess, "_");
		
		//TODO Actually error check this
		if(st.countTokens() != 3)
			throw new IOException();
		
		String guess = st.nextToken();
		
		//TODO Actually deal with this error
		Integer number = 0;
		try {
			number = Integer.parseInt(st.nextToken());
		} catch (NumberFormatException nfe) {
			throw new IOException();
		}
		String direction = st.nextToken().toUpperCase();
		
		//Broadcast result of playerTurn's guess to everyone including playerTurn
		broadcast("Player " + (playerTurn + 1) + " guessed " + guess + " for " + number + " " + direction, playerTurn);
				
		//Broadcast if the player guessed correctly
		synchronized(board) {
			boolean correctGuess = false;
			if(board.boardEntry.containsKey(""+number+direction.charAt(0)) && board.boardEntry.get(""+number+direction.charAt(0)).get(0).equalsIgnoreCase(guess))
				correctGuess = true;
			if(correctGuess) {
				broadcast("That is correct!");
				if(direction.charAt(0) == 'A')
					board.acrossWordsRemaining.remove(guess);
				else
					board.downWordsRemaining.remove(guess);
			}
			else
				broadcast("That is incorrect!");
			return correctGuess;
		}
	}
	
	// Broadcasts an object (usually the board) to all clients
	private void broadcast(Object o) {
		for(ServerClient player: players) {
			try {
				player.output.reset();
				player.output.writeObject(o);
				player.output.flush();
			} catch (IOException ioe) {
				System.out.println("Error sending to client.");
			}
		}
	}
	
	// Broadcasts message to all clients
	private void broadcast(String message) {
		for(ServerClient player: players)
			player.sendToClient("Message:" + message);
	}
	
	private void broadcast(String message, int excludingClientNumber) {
		for(int i = 0; i < players.size(); i++)
			if(i != excludingClientNumber)
				players.get(i).sendToClient("Message:" + message);
	}
	
	private void incrementPlayerTurn() {
		if(playerTurn + 1 == players.size())
			playerTurn = 0;
		else
			playerTurn++;
	}
	
	
	
	
	public static void main(String []args) {
		Server s = new Server();
	}
	
}
