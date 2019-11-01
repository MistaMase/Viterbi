package games;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import javax.websocket.Session;

import database.Credits;

public class Slots extends Thread {
	
	volatile boolean play = false;
	private String username = "null";
	private volatile long slotStartTime;
	private Session userSession;
	
	public Slots() {
		this.start();
	}

	
	public void initSend(Session session, Map<String, String> args) {
		this.userSession = session;
	}
	
	public void placeBet(Session session, Map<String, String> args) {
		try {
			String newBet = args.get("USERNAME") + "_20";
			username = args.get("USERNAME");
			if(Credits.updateCredits(username, -20) > -1)
				play = true;
		} catch (Exception ex) {
			System.out.println("Place Bet - Malformed Input from Client");
		}
	}
	
	public void resetSlots() {
		slotStartTime = System.currentTimeMillis();
		synchronized(userSession) {
			try {
				userSession.getBasicRemote().sendText("SLOTS_RESET");
			} catch(IOException ioe) {
				System.out.println("Slots - Unable to send client slot reset");
			}
		}
		play = false;
	}
	
	
	public void run() {
		
		
		while(true) {
			if(play) {
				Vector<Integer> numbers = new Vector<Integer>();
				numbers = chooseReward();
				System.out.println("User won " + makeString(numbers));
				int totalCredits = 0;
				if(numbers.get(3) >= 0)
					totalCredits = Credits.updateCredits(username, numbers.get(3));
				synchronized(userSession) {
					try {
						userSession.getBasicRemote().sendText("SLOTS_USERNAME_" + username + "_PAYOUT_" + makeString(numbers) + "_" + totalCredits);
					} catch (IOException ioe) {
						System.out.println("Slots - unable to send client roll " + ioe.getMessage());
					}
				}
				resetSlots();
				play = false;
			}
		}
		
	}
	
	
	
	public static String makeString(Vector<Integer> num) {
		String result = "";
		for (int i = 0; i < 4; i++) {
			result += Integer.toString(num.get(i));
			if (i != 3) result += "_";
		}
		return result;
	}
	
	/**
	 * @param args
	 * NONE
	 */
	public static Vector<Integer> chooseReward() {
		Vector<Integer> slotChoices = new Vector<Integer>();
		int num = (int)(Math.random() * 1000);
		if (num >= 0 && num <= 399) {
			int pair = (int)(Math.random() * 3);
			int match = (((int)(Math.random() * 4))) + 1;
			int diff1 = (((int)(Math.random() * 4))) + 1;
			int diff2 = (((int)(Math.random() * 4))) + 1;
			if (pair == 0) {
				slotChoices.add(match);
				while (diff1 == match) {
					diff1 = (((int)(Math.random() * 4))) + 1;
				}
				slotChoices.add(diff1);
				while (diff2 == match || diff2 == diff1)
					diff2 = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff2);
			}
			else if (pair == 1) {
				while (diff1 == match)
					diff1 = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff1);
				slotChoices.add(match);
				while (diff2 == match || diff2 == diff1)
					diff2 = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff2);
			}
			else {
				while (diff1 == match)
					diff1 = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff1);
				while (diff2 == match || diff2 == diff1)
					diff2 = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff2);
				slotChoices.add(match);
			}
			slotChoices.add(0);
		}
		else if (num >= 400 && num <= 499) {
			int pair = (int)(Math.random() * 3);
			int match = (((int)(Math.random() * 4))) + 1;
			int diff = (((int)(Math.random() * 4))) + 1;
			if (pair == 0) {
				slotChoices.add(match);
				slotChoices.add(match);
				while (diff == match)
					diff = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff);
			}
			else if (pair == 1) {
				while (diff == match)
					diff = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff);
				slotChoices.add(match);
				slotChoices.add(match);
			}
			else {
				slotChoices.add(match);
				while (diff == match)
					diff = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(diff);
				slotChoices.add(match);
			}
			slotChoices.add(5);
		}
		else if (num >= 500 && num <= 599) {
			int order = (int)(Math.random() * 3);
			int one = (((int)(Math.random() * 4))) + 1;
			int two = (((int)(Math.random() * 4))) + 1;
			if (order == 0) {
				slotChoices.add(5);
				slotChoices.add(one);
				while (one == two)
					two = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(two);
			}
			else if (order == 1) {
				slotChoices.add(one);
				while (one == two)
					two = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(two);
				slotChoices.add(5);
			}
			else {
				slotChoices.add(one);
				slotChoices.add(5);
				while (one == two)
					two = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(two);
			}
			slotChoices.add(10);
		}
		else if (num >= 600 && num <= 699) {
			int slot = (((int)(Math.random() * 4))) + 1;
			for (int i = 0; i < 3; i++) {
				slotChoices.add(slot);
			}
			slotChoices.add(20);
		}
		else if (num >= 700 && num <= 749) {
			int pair = (int)(Math.random() * 3);
			int match = (((int)(Math.random() * 4))) + 1;
			if (pair == 0) {
				slotChoices.add(5);
				slotChoices.add(match);
				slotChoices.add(match);
			}
			else if (pair == 1) {
				slotChoices.add(match);
				slotChoices.add(5);
				slotChoices.add(match);
			}
			else {
				slotChoices.add(match);
				slotChoices.add(match);
				slotChoices.add(5);
			}
			slotChoices.add(40);
		}
		else if (num >= 750 && num <= 799) {
			int order = (int)(Math.random() * 3);
			int one = (((int)(Math.random() * 4))) + 1;
			int two = (((int)(Math.random() * 4))) + 1;
			if (order == 0) {
				slotChoices.add(6);
				slotChoices.add(one);
				while (one == two)
					two = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(two);
			}
			else if (order == 1) {
				slotChoices.add(one);
				while (one == two)
					two = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(two);
				slotChoices.add(6);
			}
			else {
				slotChoices.add(one);
				slotChoices.add(6);
				while (one == two)
					two = (((int)(Math.random() * 4))) + 1;
				slotChoices.add(two);
			}
			slotChoices.add(60);
		}
		else if (num >= 800 && num <= 849) {
			int pair = (int)(Math.random() * 3);
			int match = (((int)(Math.random() * 4))) + 1;
			if (pair == 0) {
				slotChoices.add(6);
				slotChoices.add(match);
				slotChoices.add(match);
			}
			else if (pair == 1) {
				slotChoices.add(match);
				slotChoices.add(6);
				slotChoices.add(match);
			}
			else {
				slotChoices.add(match);
				slotChoices.add(match);
				slotChoices.add(6);
			}
			slotChoices.add(80);
		}
		else if (num >= 850 && num <= 899) {
			int pair = (int)(Math.random() * 3);
			int one = (((int)(Math.random() * 4))) + 1;
			if (pair == 0) {
				slotChoices.add(5);
				slotChoices.add(5);
				slotChoices.add(one);
			}
			else if (pair == 1) {
				slotChoices.add(one);
				slotChoices.add(5);
				slotChoices.add(5);
			}
			else {
				slotChoices.add(5);
				slotChoices.add(one);
				slotChoices.add(5);
			}
			slotChoices.add(80);
		}
		else if (num >= 900 && num <= 939) {
			int order = (int)(Math.random() * 6);
			if (order == 0) {
				slotChoices.add(6);
				slotChoices.add(5);
				slotChoices.add((((int)(Math.random() * 4))) + 1);
			}
			else if (order == 1) {
				slotChoices.add(5);
				slotChoices.add(6);
				slotChoices.add((((int)(Math.random() * 4))) + 1);
			}
			else if (order == 2) {
				slotChoices.add(6);
				slotChoices.add((((int)(Math.random() * 4))) + 1);
				slotChoices.add(5);
			}
			else if (order == 3) {
				slotChoices.add(5);
				slotChoices.add((((int)(Math.random() * 4))) + 1);
				slotChoices.add(6);
			}
			else if (order == 4) {
				slotChoices.add((((int)(Math.random() * 4))) + 1);
				slotChoices.add(5);
				slotChoices.add(6);
			}
			else {
				slotChoices.add((((int)(Math.random() * 4))) + 1);
				slotChoices.add(6);
				slotChoices.add(5);
			}
			slotChoices.add(100);
		}
		else if (num >= 940 && num <= 979) {
			int pair = (int)(Math.random() * 3);
			int one = (((int)(Math.random() * 4))) + 1;
			if (pair == 0) {
				slotChoices.add(6);
				slotChoices.add(6);
				slotChoices.add(one);
			}
			else if (pair == 1) {
				slotChoices.add(one);
				slotChoices.add(6);
				slotChoices.add(6);
			}
			else {
				slotChoices.add(6);
				slotChoices.add(one);
				slotChoices.add(6);
			}
			slotChoices.add(150);
		}
		else if (num >= 980 && num <= 989) {
			for (int i = 0; i < 3; i++) {
				slotChoices.add(5);
			}
			slotChoices.add(200);
		}
		else if (num >= 990 && num <= 993) {
			int pair = (int)(Math.random() * 3);
			if (pair == 0) {
				slotChoices.add(5);
				slotChoices.add(5);
				slotChoices.add(6);
			}
			else if (pair == 1) {
				slotChoices.add(5);
				slotChoices.add(6);
				slotChoices.add(5);
			}
			else {
				slotChoices.add(6);
				slotChoices.add(5);
				slotChoices.add(5);
			}
			slotChoices.add(300);
		}
		else if (num >= 994 && num <= 997) {
			int pair = (int)(Math.random() * 3);
			if (pair == 0) {
				slotChoices.add(5);
				slotChoices.add(6);
				slotChoices.add(6);
			}
			else if (pair == 1) {
				slotChoices.add(6);
				slotChoices.add(6);
				slotChoices.add(5);
			}
			else {
				slotChoices.add(6);
				slotChoices.add(5);
				slotChoices.add(6);
			}
			slotChoices.add(600);
		}
		else {
			for (int i = 0; i < 3; i++) {
				slotChoices.add(6);
			}
			slotChoices.add(1000);
		}
		return slotChoices;
	}
}
