import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Subscriber extends Thread {
	MessageQueue mq = null;
	
	public Subscriber(MessageQueue mq) {
		this.mq = mq;
	}
	
	public void run() {
		int numMessages = 0;
		while(numMessages < 20) {
			String msg = mq.getMessage();
			if(msg != "") {
				numMessages++;
				
				String pattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat simpleDateFormat =
				        new SimpleDateFormat(pattern, new Locale("da", "DK"));

				String dateAndTime = simpleDateFormat.format(new Date());
				
				System.out.println("Read message \"" + msg + "\"" + " at " + dateAndTime);
			}
			else {
				String pattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat simpleDateFormat =
				        new SimpleDateFormat(pattern, new Locale("da", "DK"));

				String dateAndTime = simpleDateFormat.format(new Date());
				
				System.out.println("No message to read at " + dateAndTime);
			}
			
			try {
				Thread.sleep((long) Math.random() % 1000);
			} catch (InterruptedException ie) {
				System.out.println("ie: " + ie.getMessage());
			}
		}
	}
}
