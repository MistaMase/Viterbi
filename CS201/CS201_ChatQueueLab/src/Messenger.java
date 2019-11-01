import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Messenger extends Thread {
	private MessageQueue mq = null;

	public Messenger(MessageQueue mq) {
		this.mq = mq;
	}
	
	public void run() {
		for(int i = 0; i < 20; i++) {
			mq.addMessage("Iteration: " + i);
			
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat simpleDateFormat =
			        new SimpleDateFormat(pattern, new Locale("da", "DK"));

			String dateAndTime = simpleDateFormat.format(new Date());
			
			System.out.println("Added message \"Iteration: " + i + "\" at " + dateAndTime);
			try {
				Thread.sleep((long) (Math.random() % 1000));
			} catch (InterruptedException ie) {
				System.out.println("ie: " + ie.getMessage());
			}
		}
		
	}
	
}
