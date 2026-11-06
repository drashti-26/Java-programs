public class ProducerConsumer {
    static class Buffer {
        private int item;
        private boolean available = false;

        synchronized void produce(int value) throws InterruptedException {
            while (available) {
                wait();
            }
            item = value;
            available = true;
            System.out.println("Produced: " + value);
            notify();
        }

        synchronized int consume() throws InterruptedException {
            while (!available) {
                wait();
            }
            int value = item;
            available = false;
            System.out.println("Consumed: " + value);
            notify();
            return value;
        }
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                }
                buffer.produce(-1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    int value = buffer.consume();
                    if (value == -1) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
