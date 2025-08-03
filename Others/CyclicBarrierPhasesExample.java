package Others;

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

