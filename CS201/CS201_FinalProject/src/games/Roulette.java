package games;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.Session;

import database.Credits;
import games.ServerSocket;


// args.get("NUMBER")
public class Roulette extends Thread {
	

	
	private long spinTime = 1000*5;
	private long timeBetween = 1000*60;
	private volatile long rouletteStartTime;
	
	private  Queue<String> incomingBets;
	
	//37 Even, 38 odd, 39 Red, 40 black
	//Vector<Bet> bets = new Vector<>();
	
	//All of the user bets, each user has a vector of bets
	private Map<String, Vector<Bet>> userBets;
	
	//Only used at the end, calculations are placed here 
	private Map<String, Integer> userPayouts;
	
	public Roulette() {
		incomingBets = new ConcurrentLinkedQueue<String>();
		resetRoulette();
		this.start();
	}
	

	private void internalPlaceBet(){
		while(!incomingBets.isEmpty()) {
			String inputString = incomingBets.poll();
			try {
				String[] input = inputString.split("_");
				if(input.length != 8)
					throw new IllegalArgumentException();
				
				//Null users (Guests) can't place bets
				if(input[3].equalsIgnoreCase("null"))
					return;
				
				int bet;
				int number;
				String name;
				Bet b;
				try {
					bet = Integer.parseInt(input[7]);
					number = Integer.parseInt(input[5]);
					name = input[3];
					b = new Bet(number,bet, name);
				}
				catch(Exception e) {
					System.out.println("Invalid bet");
					return;
				}
				
				
				if(bet < 1)
					return;
				if(userBets.containsKey(name)) {
					if(Credits.updateCredits(name, -bet) > -1)
						userBets.get(name).add(b);
				}
				else {
					if(Credits.updateCredits(name, -bet) > -1) {
						userBets.put(name, new Vector<Bet>());
						userBets.get(name).add(b);
					}
				}
								
				updateClient(input);
				
			} catch(IllegalArgumentException iae) {
				System.out.println("Place Bet - Malformed Input: " + inputString);
			} catch(Exception ex) {
				System.out.println("Roulette Internal Bet Exception for " + inputString + " " + ex.getMessage());
			}
		}
	}
	// ROULETTE_placeBET_USERNAME_username_NUMBER_number_BET_bet
	public void placeBet(Session session, Map<String, String> args) {
		try {
			String newBet = "ROULETTE_placeBet_USERNAME_" + args.get("USERNAME") + "_NUMBER_" + args.get("NUMBER") + "_BET_" + args.get("BET");
			
			incomingBets.add(newBet);
		} catch (Exception ex) {
			System.out.println("Place Bet - Malformed Input from Client");
		}
	}
	
	// ROULETTE_placeBET_USERNAME_username_NUMBER_number_BET_bet
	private void updateClient(String[] args){
		String username = args[3];
		String betAmt = args[7];
		String number = args[5];
		String toSend = "ROULETTE_BET_" + username + "_" + betAmt + "_" + number;
		ServerSocket.sendToAllClients(toSend);
	}
	
	public void initSend(Session session, Map<String, String> args) {
		synchronized(session) {
			try {
				session.getBasicRemote().sendText("ROULETTE_TIME_" + (((rouletteStartTime + timeBetween) - System.currentTimeMillis()) / 1000.0));
				for(Entry<String, Vector<Bet>> entry: userBets.entrySet()) {
					for(int i = 0; i < entry.getValue().size(); i++) {
						String send = "ROULETTE_BET_" + entry.getKey() + "_" + entry.getValue().get(i) + "_" +  entry.getValue().get(i).getValue();
						session.getBasicRemote().sendText(send);
					}
					
				}
			} catch(IOException ioe) {
				System.out.println("ioe sending roulette init to client " + ioe.getMessage());
			}
		}
	}
	private void resetRoulette() {
		System.out.println("Resetting Roulette");
		rouletteStartTime = System.currentTimeMillis();
		userBets = new ConcurrentHashMap<String, Vector<Bet>>();
		userPayouts = new ConcurrentHashMap<String, Integer>();
		incomingBets = new ConcurrentLinkedQueue<String>();
		ServerSocket.sendToAllClients("ROULETTE_RESET"); 
	}
	
	public void run() {
		boolean didPeriodicUpdate = false;
		while(true) {
			//Periodic update
			if(!didPeriodicUpdate && (System.currentTimeMillis() - rouletteStartTime) > 1000 && (System.currentTimeMillis() - rouletteStartTime) % 1000 > 0 
					&& (System.currentTimeMillis() - rouletteStartTime) % 1000 < 5 ) {
				ServerSocket.sendToAllClients("ROULETTE_TIME_" + (((rouletteStartTime + timeBetween) - System.currentTimeMillis()) / 1000.0));
				didPeriodicUpdate = true;
			}
			else if((System.currentTimeMillis() - rouletteStartTime) % 1000 > 0 && (System.currentTimeMillis() - rouletteStartTime) % 1000 < 5)
				didPeriodicUpdate = true;
			else
				didPeriodicUpdate = false;
			
			//Less than 50 ms - Don't process the bets 
			if(System.currentTimeMillis() - rouletteStartTime <= timeBetween - 50) {
				internalPlaceBet();
			}
			else if(System.currentTimeMillis() - rouletteStartTime >= timeBetween) {
				playGame();
				
				
				ServerSocket.sendToAllClients("ROULETTE_ROLL_" + roll);
				for(Entry<String, Integer> entry: userPayouts.entrySet()) {
					int totalCredits = 0;
					if(entry.getValue() != 0)
						totalCredits = Credits.updateCredits(entry.getKey(), entry.getValue());
					String send = "ROULETTE_USERNAME_" + entry.getKey() + "_PAYOUT_" + entry.getValue() + "_" + totalCredits;	
					System.out.println("Roulette Sending: " + send);
					ServerSocket.sendToAllClients(send);
				}
				
				
				try {
					resetRoulette();
				} catch (Exception ie) {
					System.out.println("ie waiting to reset Roulette " + ie.getMessage());
				}
			}
		}
	}
	
	
	
	private int roll;
	
	public void playGame() {
		roll = 1 + (int)(Math.random() * ((36 - 1) + 1));
		System.out.println("Roulette: Roll is " + roll);
		
		calculate(roll);
	}
	
	public void calculate(int square){
		//int total = 0;
		boolean even;
		boolean red;
		
		if(square % 2 == 0) {
			even = true;
		}
		else {
			even = false;
		}
		
		if(square == 1 || square == 3 || square == 5 || square == 7 || square == 9 ||
		square == 12 || square == 14 || square == 16 || square == 18 ||
		square == 19 || square == 21 || square == 23 || square == 25 ||
		square == 27 || square == 30 || square == 32 || square == 34 || square == 36) {
			red = true;
		}
		else {
			red = false;
		}
		
		for(Entry<String, Vector<Bet>> entry: userBets.entrySet()) {
			//Going through all of the users and getting all of their bets
			//Total win per user is calculated and put in the vector userPayouts
			int total = 0;
			for(int i = 0; i < entry.getValue().size(); i++) {
				if(entry.getValue().get(i).getValue() == square) {
					total += sPayout(entry.getValue().get(i).getBetAmount());
				}
				else if(entry.getValue().get(i).getValue() == 37) {
					if(even) {
						total += eoPayout(entry.getValue().get(i).getBetAmount());
					}
				}
				else if(entry.getValue().get(i).getValue() == 38) {
					if(!even) {
						total += eoPayout(entry.getValue().get(i).getBetAmount());
					}
				}
				else if(entry.getValue().get(i).getValue() == 39) {
					if(red) {
						total += rbPayout(entry.getValue().get(i).getBetAmount());
					}
				}
				else if(entry.getValue().get(i).getValue() == 40) {
					if(!red) {
						total += eoPayout(entry.getValue().get(i).getBetAmount());
					}
				}
			}

			if(userPayouts.containsKey(entry.getKey())) {
				System.out.println("Duplicate username in Roulette");
			}
			userPayouts.put(entry.getKey(), total);
			
			
			
		}
		
	}
	
	private int eoPayout(int bet) {
		return bet*2;
	}
	private int rbPayout(int bet) {
		return bet*2;
	}
	private int sPayout(int bet) {
		return bet*35;
	}

	

}
class Bet{
	public Bet(int value, int amount, String user) {
		this.value = value;
		this.amount = amount;
	}
	private int value;
	private int amount;
	
	public int getValue() {
		return this.value;
	}
	
	public int getBetAmount() {
		return this.amount;
	}
}
