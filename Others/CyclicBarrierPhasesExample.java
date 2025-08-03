package Others;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierPhasesExample {
    public static void main(String[] args) {
        int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties, () -> {
            System.out.println("== All threads reached the barrier ==");
        });

        for (int i = 0; i < parties; i++) {
            new Thread(new Worker(barrier, i)).start();
        }
    }
}

class Worker implements Runnable {
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

