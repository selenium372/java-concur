package zz.jmp.concurrency.features.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {


    ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private Double value = 0.0;

    private Double readValue() {
        ReentrantReadWriteLock.ReadLock readLock = rwlock.readLock();
        Double result;
        readLock.lock();
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        result = this.value;
        System.out.println("read from:" + Thread.currentThread().getName());
        readLock.unlock();
        return result;

    }

    private void writeRndValue(Double value) {
        ReentrantReadWriteLock.WriteLock writeLock = rwlock.writeLock();
        writeLock.lock();
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            System.out.println(e.getCause());;
        }
        this.value = value;
        System.out.println("" + value +" write from:" + Thread.currentThread().getName());
        writeLock.unlock();
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(4);
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        for (int i=0; i<100; i++) {
            try {
                Thread.sleep(5L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Future<Double> fv = exec.submit(() -> {
                return readWriteLockTest.readValue();
            });
        }

        for (int i=0; i<10; i++) {
            try {
                Thread.sleep(5L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            exec.execute(() -> {
                readWriteLockTest.writeRndValue(Math.random());
            });
        }
        for (int i=0; i<1000; i++) {
            Future<Double> fv = exec.submit(() -> {
                return readWriteLockTest.readValue();
            });
        }
        exec.shutdown();
    }

}
