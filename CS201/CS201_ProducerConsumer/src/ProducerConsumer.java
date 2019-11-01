import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumer {
	public static Buffer buffer = new Buffer();
	
	public static void main(String []args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new ProducerTask());
		executor.execute(new ConsumerTask());
		executor.shutdown();
	}
}

class ProducerTask implements Runnable {
	@Override
	public void run() {
		try {
			int i = 1;
			while(true) {
				System.out.println("Putting " + i + " into buffer.");
				ProducerConsumer.buffer.write(i);
				i++;
				Thread.sleep((int)(Math.random() * 3000));
			}
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
	}
	
}

class ConsumerTask implements Runnable {
	@Override
	public void run() {
		try {
			while(true) {
				int valueRead = ProducerConsumer.buffer.read();
				System.out.println("Consumer read " + valueRead);
				Thread.sleep((int)(Math.random() * 3000));
			}
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
	}
}
