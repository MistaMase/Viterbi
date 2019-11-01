import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	private static final int CAPACITY = 1;
	private LinkedList<Integer> queue = new LinkedList<Integer>();
	private Lock bufferLock = new ReentrantLock();
	private Condition notFull = bufferLock.newCondition();
	private Condition notEmpty = bufferLock.newCondition();
	
	public void write(int value) {
		//synchronized(queue) {
		bufferLock.lock();
			try {
				while(queue.size() == CAPACITY) {
					System.out.println("Waiting for queue to not be full.");
					//queue.wait();
					notFull.await();
				}
				queue.add(value);
				//queue.notify();
				notEmpty.signal();
			} catch (InterruptedException ie) {
				System.out.println("ie in write: " + ie.getMessage());
			} finally {
				bufferLock.unlock();
			}
		//}
	}
	
	public int read() {
		int value = 0;
		//synchronized(queue) {
		bufferLock.lock();
			try {
				while(queue.isEmpty()) {
					System.out.println("Waiting for queue to not be empty.");
					//queue.wait();
					notEmpty.await();
				}
				value = queue.remove();
				//queue.notify();
				notFull.signal();
			} catch (InterruptedException ie) {
				System.out.println("ie in read:" + ie.getMessage());
			} finally {
				bufferLock.unlock();
			}
		//}
		return value;
	}
}
