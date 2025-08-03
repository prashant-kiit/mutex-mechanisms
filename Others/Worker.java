package Others;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {
    private final CyclicBarrier barrier;
    private final int id;

    public Worker(CyclicBarrier barrier, int id) {
        this.barrier = barrier;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int phase = 1; phase <= 3; phase++) {
                System.out.println("Thread " + id + " - Phase " + phase + " work");
                Thread.sleep(500); // simulate work
                barrier.await();   // wait for others
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
