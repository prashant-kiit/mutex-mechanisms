package Locks;

import java.util.concurrent.atomic.AtomicBoolean;

class MyLock {
    private final AtomicBoolean isLocked = new AtomicBoolean(false);

    public void lock() {
        while (!isLocked.compareAndSet(false, true)) {
            Thread.yield(); // yield to avoid tight CPU spinning
        }
    }

    public void unlock() {
        isLocked.set(false);
    }
}
