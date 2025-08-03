package OptimisticLock;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Runnable task1 = () -> {
            try {
                int lastVersion = counter.getVersion();
                // Thread.sleep(1000); // some random delay
                int currentcount = counter.update(10, lastVersion);
                System.out.println("New Count 1 " + currentcount);
            } catch (Exception e) {
                System.out.println(e);
            }
        };

        Runnable task2 = () -> {
            try {
                // Thread.sleep(1000); // some random delay
                int lastVersion = counter.getVersion();
                int currentcount = counter.update(20, lastVersion);
                System.out.println("New Count 2 " + currentcount);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        };

        Thread t1 = new Thread(task1, "T1");
        Thread t2 = new Thread(task2, "T2");
        t1.start();
        t2.start();
    }
}
