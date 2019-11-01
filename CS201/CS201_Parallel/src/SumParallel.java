import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumParallel {
	public static void main(String []args) {
		long minNumber = 0;
		long maxNumber = 1_000_000_000;
		long start = System.currentTimeMillis();
		int numThreads = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of logical cores: " + numThreads);
		ForkJoinPool pool = new ForkJoinPool();
		SumP sum[] = new SumP[numThreads];
		long first = minNumber;
		long last = maxNumber / numThreads;
		for(int i = 0; i < numThreads; i++) {
			sum[i] = new SumP(first, last);
			pool.execute(sum[i]);
			first = last + 1;
			last += (maxNumber / numThreads);
		}
		long num = 0;
		for(int i = 0; i < numThreads; i++)
			//Returns te value from computer if inheriting RecursiveTask<>
			num += sum[i].join();
		long end = System.currentTimeMillis();
		System.out.println("Time with parallelism: " + (end-start));
		System.out.println("Sum(" + minNumber + ".." + maxNumber + ") = " + num);
	}
}

class SumP extends RecursiveTask<Long> {
	private long minNum;
	private long maxNum;
	public SumP(long minNum, long maxNum) {
		this.minNum = minNum;
		this.maxNum = maxNum;
	}
	
	public Long compute() {
		long sum = 0;
		for(long i = minNum; i <= maxNum; i++)
			sum += i;
		return sum;
	}
}
