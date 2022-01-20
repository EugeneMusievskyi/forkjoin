import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class ForkJoinProducer extends RecursiveAction {

	private final Drop drop;
	private final Random random = new Random();

	public ForkJoinProducer(Drop drop) {
		this.drop = drop;
	}

	@Override
	protected void compute() {
		while (true) {
			drop.put(random.nextInt(10_000));
		}
	}
}
