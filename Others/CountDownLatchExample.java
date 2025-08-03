package Others;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3); // Wait for 3 tasks

        Runnable worker = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " working...");
                Thread.sleep(1000); // simulate work
                System.out.println(Thread.currentThread().getName() + " done.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown(); // decrement count
            }
        };

        // Start 3 threads
        for (int i = 1; i <= 3; i++) {
            new Thread(worker, "Worker-" + i).start();
        }

        latch.await(); // wait for all workers to finish
        System.out.println("All workers finished. Main thread proceeding.");
    }
}

