public class SumNoParallel {
	public static void main(String []args) {
		long minNumber = 0;
		long maxNumber = 1_000_000_000;
		long start = System.currentTimeMillis();
		Sum sum = new Sum(minNumber, maxNumber);
		long num = sum.compute();
		long end = System.currentTimeMillis();
		System.out.println("Time without parallelism: " + (end-start));
		System.out.println("Sum(" + minNumber + ".." + maxNumber + ") = " + num);
	}
}

class Sum {
	private long minNum;
	private long maxNum;
	public Sum(long minNum, long maxNum) {
		this.minNum = minNum;
		this.maxNum = maxNum;
	}
	
	public long compute() {
		long sum = 0;
		for(long i = minNum; i <= maxNum; i++)
			sum += i;
		return sum;
	}
}
