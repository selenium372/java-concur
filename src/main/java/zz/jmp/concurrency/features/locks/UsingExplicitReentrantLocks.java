package zz.jmp.concurrency.features.locks;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class UsingExplicitReentrantLocks {

	// Equivalent to Intrinsic Locks
	private ReentrantLock reentrantLock = new ReentrantLock();

	private boolean state;

	/**
	 * Simplest way to use
	 */
	public void lockMyHearth() {
		reentrantLock.lock();
		Object obj = new Object();
		Integer str1 = 1;
		synchronized (this) {

		}
		try {
			System.out.println("Changing stated in a serialized way");
			state = !state;
			System.out.println("Changed: " + state);
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * Try lock - Timed and polled lock acquisition
	 * 
	 * @throws InterruptedException
	 */
	public void lockMyHearthWithTiming() throws InterruptedException {

		if (!reentrantLock.tryLock(1l, TimeUnit.SECONDS)) {
			System.err.println("Failed to acquire the lock - it's already held.");
		} else {
			try {
				System.out.println("Simulating a blocking computation - forcing tryLock() to fail");
				Thread.sleep(3000);
			} finally {
				reentrantLock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		var executor = Executors.newCachedThreadPool();
		var self = new UsingExplicitReentrantLocks();
		for (int i = 0; i < 10; i++) {
			executor.execute(() -> self.lockMyHearth());
		}

		for (int i = 0; i < 40; i++) {
			executor.execute(() -> {
				try {
					self.lockMyHearthWithTiming();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		executor.shutdown();
	}

}
