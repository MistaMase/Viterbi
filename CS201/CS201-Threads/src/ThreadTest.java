public class ThreadTest{
	public static void main(String []args) {
		System.out.println("First Line:");
		for(int i = 1; i <= 100; i++) {
			Thread t = new MyThread(i);
			t.start();
		}
		System.out.println("Last Line:");
	}
}


class MyThread extends Thread {
	private int num;
	public MyThread(int num) {
		this.num = num;
	}
	
	public void run() {
		System.out.println(num + " starting");
		System.out.println(num + " ending");
		while(true) {}
	}
}