import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageTest {
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			System.out.println("\nTest " + i);
			MessageQueue mq = new MessageQueue();
			Messenger m = new Messenger(mq);
			Subscriber s = new Subscriber(mq);
			
			ExecutorService ex = Executors.newFixedThreadPool(2);
			ex.execute(m);
			ex.execute(s);
			ex.shutdown();
			
			while(!ex.isTerminated()) {Thread.yield();}
		}
	}
}
