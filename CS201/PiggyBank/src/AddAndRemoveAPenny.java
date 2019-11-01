import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddAndRemoveAPenny implements Runnable {
	private static PiggyBank piggy = new AddAndRemoveAPenny().new PiggyBank();
	private boolean isWithdrawal;

	public void run() {
		// amount to be deposited or withdrawn is between 1 and 9, inclusively
		if (isWithdrawal) {
			piggy.withdraw((int)(Math.random() * 9 + 1));
		}
		else {
			piggy.deposit((int)(Math.random() * 9 + 1));
		}
	}

	public static void main(String [] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i=0; i < 100; i++) {
			AddAndRemoveAPenny penny = new AddAndRemoveAPenny();
			if (i < 50) { // exactly 50 threads will withdraw
				penny.isWithdrawal = false;
			}
			else { // and exactly 50 threads will deposit
				penny.isWithdrawal = true;
			}
			executor.execute(penny);
		}
		executor.shutdown();
		// wait until all tasks are finished
		while(!executor.isTerminated()) { }

		System.out.println("\n\nBalance = " + piggy.getBalance());
		System.out.println("Number of unsuccessful withdrawals: " + piggy.getNumUnavailableWithdrawals());
	}

	private class PiggyBank {
		private int balance = 0;
		private int numUnavailableWithdrawals = 0;
		public int getBalance() {
			return balance;
		}
		public synchronized void withdraw(int amount) {
			int counter = 0;
			while (balance < amount && counter < 10) {
				if (counter == 0) {
					System.out.print("\t" + counter + " Waiting for deposit to withdraw $" + amount);
					System.out.println(" from balance of $" + balance + "...");
				}
				counter++;
			}
			if (balance < amount) {
				numUnavailableWithdrawals++;
				System.out.println("\t$" + amount + " UNABLE TO BE WITHDRAWN FROM $" + balance);
			}
			else {
				balance -= amount;
				System.out.println("\t$" + amount + " withdrawn, leaving balance of $" + balance);
			}
		}
		public synchronized void deposit(int amount) {
			balance += amount;
			System.out.println("$" + amount + " deposited, leaving balance of $" + balance);
		}
		public int getNumUnavailableWithdrawals() {
			return numUnavailableWithdrawals;
		}
	}
}
