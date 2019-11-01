package games;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;



@ServerEndpoint (value = "/websocket")
public class ServerSocket {
	
	protected static Map<Session, State> sessionMap = new ConcurrentHashMap<Session, State>();
	
	protected enum State {
		JACKPOT, ROULETTE, SLOTS, UNKNOWN
	}
	
	//Only ever one jackpot 
	private static Jackpot jackpot = new Jackpot();
	
	//Multiple slots indexed by the user's name
	private Slots slot = new Slots();
	
	private static Roulette roulette = new Roulette();
	
	/*****
	 * Sends a message to all the clients connected to the ServerSocket in the State.<br>
	 * If the message is Jackpot, the message superseeds the client's state and is sent regardless
	 * @param message - The message to send to all clients
	 */
	public static void sendToAllClients(String message) {
		
		String stateString = message.substring(0, message.indexOf("_"));
		State state = null;
		switch (stateString.toLowerCase()) {
			case "jackpot": state = State.JACKPOT; break;
			case "roulette": state = State.ROULETTE; break;
			case "slots": state = State.SLOTS; break;
			default: state = State.UNKNOWN; break;
		}
		
		//Send to specific state
		if(state != null && state != State.UNKNOWN) {
			try {
				for(Map.Entry<Session, State> oneSession : sessionMap.entrySet()) {
					if(oneSession.getValue() == state) {
						synchronized(oneSession) {
							oneSession.getKey().getBasicRemote().sendText(message);
						}
					}
				}
			} catch (IOException ioe) {
				System.out.println("ioe sending data to clients " + ioe.getMessage());
			}
		}
		
		else if(state == null || state == State.UNKNOWN) {
			System.out.println("Error grabbing state to send to correct clients");
		}
	}


	@OnOpen
	public void open(Session session) {
		System.out.println("Connection Made!");
		sessionMap.put(session, State.UNKNOWN);	
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message received: " + message);
		
		// Get the state of the client from the incoming message
		String stateString = message.substring(0, message.indexOf("_"));
		State state = null;
		switch (stateString.toLowerCase()) {
			case "jackpot": state = State.JACKPOT; break;
			case "roulette": state = State.ROULETTE; break;
			case "slots": state = State.SLOTS; break;
			default: state = State.UNKNOWN; break;
		}
		sessionMap.put(session, state);
		parseIncomingMessage(session, message);
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnected!");
		sessionMap.remove(session);
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("Error! " + error.getMessage());
	}
	
	private synchronized void parseIncomingMessage(Session session, String message) {
		
		System.out.println("Message: " + message);
		
		String[] tokens = message.split("_");
		
		// Format: GAMETYPE_FUNCTIONNAME_ARG1NAME_ARG1_ARG2NAME_ARG2_ARGNNAME_ARGN
		
		try {
			
			//Parse Class Type
			String gametype = tokens[0];
			Object game;
			if(gametype.equalsIgnoreCase("jackpot"))
				game = jackpot;
			else if(gametype.equalsIgnoreCase("roulette"))
				game = roulette;
			else if(gametype.equalsIgnoreCase("slots"))
				game = slot;
			else
				throw new ClassNotFoundException();
			
			//Parse Method Name
			Method method = ((Class<?>)game.getClass()).getDeclaredMethod(tokens[1], new Class[]{Session.class, Map.class});
			
			
			//Parse Arguments
			Map<String, String> args = new HashMap<String, String>();
			int tokenNum = 2;
			try {
				String currentArgName = tokens[tokenNum++];
				String currentArgValue = tokens[tokenNum++];
				while(currentArgName != null && currentArgValue != null) {
					args.put(currentArgName, currentArgValue);
					currentArgName = tokens[tokenNum++];
					currentArgValue = tokens[tokenNum++];
				}
			} catch(Exception ex) {}
			
			
			// Runs the correct method
			method.invoke(game, session, args);
			
			
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Parsing Error: Requested class doesn't exist");
		} catch (NoSuchMethodException nsme) {
			System.out.println("Parsing Error: Requested method doesn't exist " + nsme.getMessage());
		} catch (InvocationTargetException ite) {
			System.out.println("Parsing Error: Requested method could not be called " + ite.getMessage());
		} catch (IllegalAccessException iae) {
			System.out.println("Parsing Error: Requested method isn't accessible" + iae.getMessage());
		}
	}
}
