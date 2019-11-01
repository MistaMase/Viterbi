   import java.util.concurrent.ExecutorService;
   import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

   public class AddAPenny implements Runnable {
     private static PiggyBank piggy = new PiggyBank();
     private boolean isWithdrawl;

     public void run() {
       if(isWithdrawl)
    	   piggy.withdraw(1);
       else
    	   piggy.deposit(1);
     }

    public static void main(String [] args) {
      ExecutorService executor = Executors.newCachedThreadPool();
      for (int i=0; i < 100; i++) {
    	AddAPenny aap = new AddAPenny();
    	if(i < 50)
    		aap.isWithdrawl = true;
    	else
    		aap.isWithdrawl = false;
        executor.execute(aap);
      }
      executor.shutdown();
      // wait until all tasks are finished
      while(!executor.isTerminated()) { }

      System.out.println("Balance = " + piggy.getBalance());
    }
  }

  class PiggyBank {
    private int balance = 0;
    
    //Make a new lock
    private Lock lock = new ReentrantLock();
    
    // Prevents infinite loop on ~52
    private Condition depositMade = lock.newCondition();
    
    public int getBalance() {
      return balance;
    }
    
    public void withdraw(int amount) {
    	lock.lock();
    	try {
    		while(balance < amount) {
    			System.out.println("Waiting for a deposit.");
    			depositMade.await(); //Releases lock then goes to waiting state
    		}
    		balance -= amount;
    		System.out.println("Withdrew " + amount + " leaving balance=" + balance);
    	} catch(InterruptedException ie) {
    		System.out.println("ie: " + ie.getMessage());
    	} finally {
    		lock.unlock();
    	}
    }
    
    public /*synchronized*/ void deposit(int amount) {
    	
      //Other threads can't run simultaneously
      lock.lock();
      
      try {
      int newBalance = balance + amount;
      Thread.yield();
      balance = newBalance;
      System.out.println("Deposit made, leaving balance=" + balance);
      depositMade.signalAll();
      
      } finally {
    	  // Finally ensures the block is always unlocked
    	  lock.unlock();
      }
    }
  }
