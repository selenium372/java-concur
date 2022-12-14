package zz.jmp.concurrency.features.parallel_stream;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UsingParallelStreams {

	public static void main(String[] args) {
		// Creating Parallel Streams from existing collection
		new ArrayList<>().parallelStream();

		// Making Stream Parallel
		IntStream.rangeClosed(0, 30_000) // source
				.parallel().mapToObj(BigInteger::valueOf).map(UsingParallelStreams::isPrime) // Intermediate operations
				.collect(Collectors.toList()); // Terminal Operations

		// Each operation run in parallel, out of order
		IntStream.rangeClosed(0, 20) // source
				.parallel().mapToObj(Integer::toString) // Intermediate operation
				.forEach(System.out::print); // Terminal operation

		System.out.println("\n");

		// Runs sequentially, in order.
		IntStream.rangeClosed(0, 20)
			.mapToObj(Integer::toString)
			.forEach(System.out::print);
		
		System.out.println("\n");

		dummyPerformanceCheck();
	}

	private static void dummyPerformanceCheck() {

		// Sequential Stream
		var start1 = System.currentTimeMillis();
		IntStream.rangeClosed(0, 50_000)
				.mapToObj(BigInteger::valueOf)
				.map(UsingParallelStreams::isPrime)
				.collect(Collectors.toList());
		var end1 = System.currentTimeMillis();
		var time1 = (end1 - start1) / 1000;
		System.out.println("Sequential: " + time1);

		// Parallel Stream
		var start2 = System.currentTimeMillis();
		IntStream.rangeClosed(0, 50_000)
				.parallel()
				.mapToObj(BigInteger::valueOf)
				.map(UsingParallelStreams::isPrime)
				.collect(Collectors.toList());
		var end2 = System.currentTimeMillis();
		var time2 = (end2 - start2) / 1000;
		System.out.println("Parallel: " + time2);
	}

	// thanks to linski on
	// https://stackoverflow.com/questions/15862271/java-compute-intensive-task
	public static boolean isPrime(BigInteger n) {
		var counter = BigInteger.ONE.add(BigInteger.ONE);
		var isPrime = true;
		while (counter.compareTo(n) == -1) {
			if (n.remainder(counter).compareTo(BigInteger.ZERO) == 0) {
				isPrime = false;
				break;
			}
			counter = counter.add(BigInteger.ONE);
		}
		return isPrime;
	}
}
