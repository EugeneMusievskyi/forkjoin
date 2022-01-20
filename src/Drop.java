import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Drop {

	private final List<Object> values = new ArrayList<>();
	private final int takeSize = 5;
	private final int maxSize = 70;

	private final ForkJoinPool forkJoinPool;

	public Drop(ForkJoinPool forkJoinPool) {
		this.forkJoinPool = forkJoinPool;
	}

	public synchronized List<Object> take() {
		while (values.size() < takeSize) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}

		List<Object> resultValues = values.stream().limit(5).collect(Collectors.toList());
		values.removeAll(resultValues);

		notifyAll();
		return resultValues;
	}

	public synchronized void put(Object value) {
		if (values.size() < maxSize) {
			values.add(value);
		} else {
			shutdown();
		}
		notifyAll();
	}

	private synchronized void shutdown() {
		while (values.size() > 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}

		forkJoinPool.shutdown();
	}
}
