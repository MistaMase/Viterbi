public class ThreadMaster extends Thread{

	public static void main(String [] args) {
		System.out.println("First Line");
		Thread ta = new Thread(new MyThread("A"));
		Thread tb = new Thread(new MyThread("B"));
		Thread tc = new Thread(new MyThread("C"));
		ta.start();
		tb.start();
		tc.start();
		try {
			ta.join();
			tb.join();
			tc.join();
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
		System.out.println("Last Line");
	}

}

class MyThread implements Runnable{
	private String name;
	public MyThread(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for(int i = 0; i <= 20; i++) {
			System.out.print(name + i + " ");
			/*try {
				if(name.equals("a"))
					Thread.sleep(1000);
				else if(name.equals("b"))
					Thread.sleep(500);
				else
					Thread.sleep(200);
			} catch (InterruptedException ie) {
				System.out.println("ie: " + ie.getMessage());
			}
			*/
		}
		System.out.println();
	}
	
}
