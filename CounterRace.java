public class CounterRace {
    static class Counter {
        int count = 0;

        void increment() {
            count++;
        }
    }

    static class SafeCounter {
        int count = 0;

        synchronized void increment() {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 10;
        int incrementsPerThread = 10000;

        Counter counter = new Counter();
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

        System.out.println("Expected count: " + (numberOfThreads * incrementsPerThread));
        System.out.println("Count without synchronization: " + counter.count);

        SafeCounter safeCounter = new SafeCounter();
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    safeCounter.increment();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

        System.out.println("Count with synchronization: " + safeCounter.count);
    }
}
