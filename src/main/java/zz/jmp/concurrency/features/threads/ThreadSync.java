package zz.jmp.concurrency.features.threads;

public class ThreadSync {
    private Integer counter = 0;

    public static void main(String[] args) throws InterruptedException {
        ThreadSync threadSync = new ThreadSync();
        Runnable task1 = () -> {
            for (int i = 0; i < 500; i++) {
                threadSync.increment();
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(threadSync.get());
    }

    private void increment() {
        synchronized(counter) {
            increment();
        }
    }


    private Integer get() {
        synchronized(counter) {
            return counter;
        }
    }
}
