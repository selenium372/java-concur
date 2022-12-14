package zz.jmp.concurrency.features.threads;


public class UsingThreads {

	public static void main(String[] args) throws InterruptedException {
		// Creating
		var created = new Thread();
		created.start();


		
		// Assigning a task for running on a thread - we pass a Runnable instance
		var threadWithTask = new Thread(() -> {
			System.out.println("Inside thread" + Thread.currentThread().getName() + "Created thread: ");
			created.setDaemon(true);

		});
		threadWithTask.start();

		
//		// Interrupting a thread
//		Runnable interrupatblyTask = () -> {
//			while (!Thread.currentThread().isInterrupted()) {
//				System.out.println("File downloading!");
//			}
//			System.out.println("Read from file");
//		};
//		var interruptable = new Thread(interrupatblyTask);
//		interruptable.start();
//		System.out.println("Starting upload");
//		Thread.sleep(2_000L);
//		System.out.println("Finished");
//		interruptable.interrupt();
		
	}
	
	
}
