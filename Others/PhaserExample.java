package Others;

import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1); // register main thread

        for (int i = 0; i < 3; i++) {
            int threadNum = i;
            phaser.register(); // register each worker
            new Thread(() -> {
                for (int phase = 0; phase < 3; phase++) {
                    System.out.println("Thread " + threadNum + " - Phase " + phase);
                    phaser.arriveAndAwaitAdvance(); // wait for others
                    System.out.println("Thread " + threadNum + " - Complete " + phase);
                }
                phaser.arriveAndDeregister(); // done
            }).start();
        }

        // main thread participates
        for (int phase = 0; phase < 3; phase++) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("---- Phase " + phase + " complete ----");
        }

        phaser.arriveAndDeregister(); // main thread done
    }
}
