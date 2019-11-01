import java.util.ArrayList;

public class MessageQueue {
	private ArrayList<String> messages;
	
	public MessageQueue() {
		messages = new ArrayList<String>();
	}
	
	public void addMessage(String message) {
		messages.add(message);
	}
	
	public String getMessage() {
		if(messages.size() == 0)
			return "";
		return messages.remove(0);
		
	}
}
