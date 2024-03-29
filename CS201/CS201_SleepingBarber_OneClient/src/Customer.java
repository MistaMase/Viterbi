import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer extends Thread {

	private int customerName;
	private SleepingBarber sb1;
	private SleepingBarber sb2;
	private Lock customerLock;
	private Condition gettingHaircutCondition;
	public Customer(int customerName, SleepingBarber sb1, SleepingBarber sb2) {
		this.customerName = customerName;
		this.sb1 = sb1;
		this.sb2 = sb2;
		customerLock = new ReentrantLock();
		gettingHaircutCondition = customerLock.newCondition();
	}
	
	public int getCustomerName() {
		return customerName;
	}
	public void startingHaircut(String barberName) {
		Util.printMessage("Customer " + customerName + " is getting hair cut by " + barberName + ".");
		
		//Saves which barber is asleep
		if(sb1.barberName.equals(barberName))
			SleepingBarber.setBarber1Sleep(false);
		else
			SleepingBarber.setBarber1Sleep(false);
	}
	public void finishingHaircut(String barberName) {
		Util.printMessage("Customer " + customerName + " is done getting hair cut from " + barberName + ".");
		
		//Saves which barber is asleep
		if(sb1.barberName.equals(barberName))
			SleepingBarber.setBarber1Sleep(true);
		else
			SleepingBarber.setBarber2Sleep(true);
		
		
		try {
			customerLock.lock();
			gettingHaircutCondition.signal();
		} finally {
			customerLock.unlock();
		}
	}
	public void run() {
		boolean seatsAvailable = SleepingBarber.addCustomerToWaiting(this);
		if (!seatsAvailable) {
			Util.printMessage("Customer " + customerName + " leaving...no seats available.");
			return;
		}
		
		//Wake up correct barber
		if(SleepingBarber.isBarber1Sleep())
			sb1.wakeUpBarber();
		else if(!SleepingBarber.isBarber1Sleep() && SleepingBarber.isBarber2Sleep())
			sb2.wakeUpBarber();
		
		
		try {
			customerLock.lock();
			gettingHaircutCondition.await();
		} catch (InterruptedException ie) {
			System.out.println("ie getting haircut: " + ie.getMessage());
		} finally {
			customerLock.unlock();
		}
		Util.printMessage("Customer " + customerName + " is leaving.");
	}
}
