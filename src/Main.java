import java.util.concurrent.ForkJoinPool;

public class Main {

	public static void main(String[] args) {
		var forkJoinPool = new ForkJoinPool();
		var drop = new Drop(forkJoinPool);

		forkJoinPool.submit(new ForkJoinConsumer(drop));
		for (int i = 0; i < 100; i++) {
			forkJoinPool.submit(new ForkJoinProducer(drop));
		}

		while (!forkJoinPool.isShutdown()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
	}
}
