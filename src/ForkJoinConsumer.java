import java.util.concurrent.RecursiveAction;

public class ForkJoinConsumer extends RecursiveAction {

	private final Drop drop;

	public ForkJoinConsumer(Drop drop) {
		this.drop = drop;
	}

	@Override
	protected void compute() {
		while (true) {
			var values = drop.take();
			System.out.println(values);
		}
	}
}
