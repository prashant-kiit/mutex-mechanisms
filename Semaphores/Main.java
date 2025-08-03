package Semaphores;

public class Main {
    public static void main(String[] args) {
        MySemaphore semaphore = new MySemaphore(2);

        Runnable task = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + " trying to acquire...");
                semaphore.acquire();
                System.out.println(name + " acquired permit!");
                Thread.sleep(1000);
                System.out.println(name + " releasing...");
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 1; i <= 5; i++) {
            new Thread(task, "Thread-" + i).start();
        }
    }
}