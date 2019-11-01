package games;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.Session;

import database.Credits;

public class Jackpot extends Thread {
	
	private int total;
	private long jackpotTime = 1000 * 60;
	private volatile long jackpotStartTime;
	
	private  Queue<String> incomingBets;
			
	private Map<String, Integer> bets;
	
	public Jackpot() {
		incomingBets = new ConcurrentLinkedQueue<String>();
		resetJackpot();
		this.start();
	}

	/****
	 * Called periodically by the run method of Jackpot. Each time will attempt to add each bet in the queue
	 * to the running Jackpot total and update the clients via updateClient().
	 */
	private void internalPlaceBet(){
		while(!incomingBets.isEmpty()) {
			System.out.println("Adding Jackpot Bet");
			String inputString = incomingBets.poll();
			try {
				String[] input = inputString.split("_");

				if(input.length != 2)
					throw new IllegalArgumentException();
				
				int bet = Integer.parseInt(input[1]);
				if(bet < 1)
					return;
				
				
				//Kills null users
				if(input[0].equals("null"))
					return;
				
				//Remove the credits from the database
				int creds = Credits.updateCredits(input[0], -Integer.parseInt(input[1]));
				if(creds < 0)
					return;
								
				if(bets.containsKey(input[0])) {
					bets.put(input[0], bets.get(input[0]) + bet);
				}
				else {
					bets.put(input[0], bet);
				}
				
				total += bet;
				
				updateClient(input);
				
			} catch(IllegalArgumentException iae) {
				System.out.println("Place Bet - Malformed Input: " + inputString);
			} catch(Exception ex) {
				System.out.println("Jackpot Internal Bet Exception for " + inputString + " " + ex.getMessage());
			}
		}
	}
	
	/****
	 * Called indirectly by the client through ServerSocket.parseIncomingMessage(). Adds the new bet amount to the queue and the 
	 * Jackpot's run method with periodicly empty the queue and apply the bets.
	 * @param session - Not used
	 * @param args - Username, Bet<br>
	 * Username - The username of the client that just placed the bet<br>
	 * Bet - The amount of credits the client bet
	 */
	public void placeBet(Session session, Map<String, String> args) {
		try {
			String newBet = args.get("USERNAME") + "_" + args.get("BET");
			incomingBets.add(newBet);
		} catch (Exception ex) {
			System.out.println("Place Bet - Malformed Input from Client");
		}
	}

	
	/****
	 * Sends each client an update each time a new bet is placed<br>
	 * Format: "JACKPOT_BET_username_betAmount_jackpotTotal"
	 * @param session - Not used
	 * @param args - Username, Bet<br>
	 * 	Username - The client's username.<br>
	 * 	Bet - Amount the user has just bet.
	 */
	private void updateClient(String[] args){
		String username = args[0];
		String betAmt = args[1];
		String toSend = "JACKPOT_BET_" + username + "_" + betAmt + "_" + total;
		ServerSocket.sendToAllClients(toSend);
	}
	
	
	/****
	 * Sends the user the just joined Jackpot the initial information in the following<p>
	 * <b>Line 1:</b> "JACKPOT_TIME_timeleft_total"<br>
	 * <b>Line N+1:</b> "JACKPOT_BET_username_amount, where N is the number of clients connected"
	 * 
	 * @param session - The session for the client that just joined
	 * @param args - No args expected
	 */
	public void initSend(Session session, Map<String, String> args) {
		synchronized(session){
			String username = args.get("USERNAME");
			try {
				session.getBasicRemote().sendText("JACKPOT_TIME_" + (((jackpotStartTime + jackpotTime) - System.currentTimeMillis()) / 1000.0) + "_" + total);
				for(Map.Entry<String, Integer> entry: bets.entrySet()) {
					String send = "JACKPOT_BET_" + entry.getKey() + "_" + entry.getValue() + "_" + total;
					session.getBasicRemote().sendText(send);
				}
			} catch(IOException ioe) {
				System.out.println("ioe sending jackpot init to client " + ioe.getMessage());
			}
		}
	}

	/*****
	 * Internal call that resets the jackpot. Broadcasts to all clients the new state of the game.
	 */
	private void resetJackpot() {
		System.out.println("Resetting Jackpot");
		jackpotStartTime = System.currentTimeMillis();
		bets = new ConcurrentHashMap<String, Integer>();
		incomingBets = new ConcurrentLinkedQueue<String>();
		total = 0;
		ServerSocket.sendToAllClients("JACKPOT_RESET"); 
	}

	
	/****
	 * Internal call to generate the winning username. 
	 * @return
	 */
	private String generateWinner() {
		if(total == 0)
			return null;
		
		//Get winner from random number of weighted average
		int winner = (int)(Math.random() * total);				
		
		//Traverse the bets to see who won
		int lastValue = 0;
		for(Map.Entry<String, Integer> entry: bets.entrySet()) {
			// Winner
			if(winner < lastValue + entry.getValue())
				return entry.getKey();
			
			// Not Winner
			else 
				lastValue += entry.getValue();
				
		}
		
		//Default case
		//TODO Error check this in the future
		return null;
	}

	/****
	 * 
	 * Runs iteratively and checks if the jackpot timer is finished. If so, finds a winner and sends the reset command to the clients.
	 *
	 ****/
	public void run() {
		boolean didPeriodicUpdate = false;
		while(true) {

			//Periodic client update
			if(!didPeriodicUpdate && (System.currentTimeMillis() - jackpotStartTime) % 1000 > 0 && (System.currentTimeMillis() - jackpotStartTime) % 1000 < 5) {
				ServerSocket.sendToAllClients("JACKPOT_TIME_" + (((jackpotStartTime + jackpotTime) - System.currentTimeMillis()) / 1000.0) + "_" + total);
				didPeriodicUpdate = true;
			}
			else if((System.currentTimeMillis() - jackpotStartTime) % 1000 > 0 && (System.currentTimeMillis() - jackpotStartTime) % 1000 < 5)
				didPeriodicUpdate = true;
			else
				didPeriodicUpdate = false;
			
			//Less than 50 ms - Don't process the bets 
			if(System.currentTimeMillis() - jackpotStartTime <= jackpotTime - 50) {
				internalPlaceBet();
			}
			else if(System.currentTimeMillis() - jackpotStartTime >= jackpotTime) {
				String winner = generateWinner();
				System.out.println("Jackpot Winner : " + winner);
				if(winner != null) {
					ServerSocket.sendToAllClients("JACKPOT_WINNER_" + winner + "_" + total);
					Credits.updateCredits(winner, total);
				}
				try {
					resetJackpot();
				} catch (Exception ie) {
					System.out.println("ie waiting to reset Jackpot " + ie.getMessage());
				}
			}
		}
	}
}
	
