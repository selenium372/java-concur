package zz.jmp.concurrency.features.forkjoin;

import java.util.Date;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSum {
    static long magicNumber = 25_000_000_000L;

    static int cores = Runtime.getRuntime().availableProcessors();


    public static void main(String[] args) {
        System.out.println(new Date());
        ForkJoinPool pool = new ForkJoinPool(cores);
        System.out.println(pool.invoke(new MyFork(0, magicNumber)));
        System.out.println(new Date());
        System.out.println("-----------");
        long sum = 0L;
        System.out.println(new Date());
        for (long i = 0; i < magicNumber; i++) {
          sum += i;
        }
        System.out.println(sum);
        System.out.println(new Date());

    }

    static class MyFork extends RecursiveTask<Long> {
        long start, finish;

        public MyFork(long start, long finish) {
            this.start = start;
            this.finish = finish;
        }

        @Override
        protected Long compute() {
            if(finish - start <= magicNumber/cores) {
                long sum = 0L;
                for (long i = start; i < finish; i++) {
                    sum += i;
                }
                return sum;
            } else {
                long midl = (finish + start) / 2;
                MyFork firstHalf = new MyFork(start, midl);
                firstHalf.fork();
                MyFork secondHalf = new MyFork(midl +1, finish);
                long secondValue = secondHalf.compute();
                return firstHalf.join() + secondValue;
            }
        }
    }
}
