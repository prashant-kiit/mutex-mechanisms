package Locks;

public class Counter {
    private int counter = 0;
    private final MyLock lock = new MyLock();

    public void increment() {
        lock.lock();
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " => " + counter);
        } finally {
            lock.unlock();
        }
    }
}
