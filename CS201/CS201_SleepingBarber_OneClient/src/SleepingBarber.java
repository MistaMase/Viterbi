import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SleepingBarber extends Thread {

	private static int maxSeats = 3;
	private static int totalCustomers = 10;
	private static Vector<Customer> customersWaiting = new Vector<Customer>();
	private Lock barberLock;
	private Condition sleepingCondition;
	private static boolean moreCustomers = true;
	private static boolean barber1Sleep = true;
	private static boolean barber2Sleep = true;
	public String barberName;
	
	public SleepingBarber(String name) {
		this.barberName = name;
		barberLock = new ReentrantLock();
		sleepingCondition = barberLock.newCondition();
		this.start();
	}
	public static synchronized boolean addCustomerToWaiting(Customer customer) {
		if (customersWaiting.size() == maxSeats) {
			return false;
		}
		Util.printMessage("Customer " + customer.getCustomerName() + " is waiting");
		customersWaiting.add(customer);
		String customersString = "";
		for (int i=0; i < customersWaiting.size(); i++) {
			customersString += customersWaiting.get(i).getCustomerName();
			if (i < customersWaiting.size() - 1) {
				customersString += ",";
			}
		}
		Util.printMessage("Customers currently waiting: " + customersString);
		return true;
	}
	
	public void wakeUpBarber() {
		try {
			barberLock.lock();
			sleepingCondition.signal();
		} finally {
			barberLock.unlock();
		}
	}
	public void run() {
		while(moreCustomers) {
			while(!customersWaiting.isEmpty()) {
				Customer customer = null;
				synchronized(this) {
					customer = customersWaiting.remove(0);
				}
				customer.startingHaircut(this.barberName);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					System.out.println("ie cutting customer's hair" + ie.getMessage());
				}
				customer.finishingHaircut(this.barberName);
				Util.printMessage(barberName + ": Checking for more customers...");		
			}
			try {
				barberLock.lock();
				Util.printMessage(barberName + ": No customers, so time to sleep...");
				sleepingCondition.await();
				Util.printMessage(barberName + ": Someone woke me up!");
			} catch (InterruptedException ie) {
				System.out.println("ie while sleeping: " + ie.getMessage());
			} finally {
				barberLock.unlock();
			}
		}
		Util.printMessage("All done for today!  Time to go home!");
		
	}
	public static boolean isBarber1Sleep() {
		return barber1Sleep;
	}
	public static boolean isBarber2Sleep() {
		return barber2Sleep;
	}
	public static void setBarber1Sleep(boolean barber1Sleep) {
		SleepingBarber.barber1Sleep = barber1Sleep;
	}
	public static void setBarber2Sleep(boolean barber2Sleep) {
		SleepingBarber.barber2Sleep = barber2Sleep;
	}
	public static void main(String [] args) {
		SleepingBarber sb1 = new SleepingBarber("Barber 1");
		SleepingBarber sb2 = new SleepingBarber("Barber 2");
		ExecutorService executors = Executors.newCachedThreadPool();
		for (int i=0; i < SleepingBarber.totalCustomers; i++) {
			Customer customer = new Customer(i, sb1, sb2);
			executors.execute(customer);
			try {
				Random rand = new Random();
				int timeBetweenCustomers = rand.nextInt(2000);
				Thread.sleep(timeBetweenCustomers);
			} catch (InterruptedException ie) {
				System.out.println("ie in customers entering: " + ie.getMessage());
			}
		}
		executors.shutdown();
		while(!executors.isTerminated()) {
			Thread.yield();
		}
		Util.printMessage("No more customers coming today...");
		SleepingBarber.moreCustomers = false;
		sb1.wakeUpBarber();
		sb2.wakeUpBarber();
	}
}
