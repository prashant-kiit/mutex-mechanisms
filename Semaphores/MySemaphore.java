package Semaphores;

class MySemaphore {
    private int permits;

    public MySemaphore(int initialPermits) {
        if (initialPermits < 0) throw new IllegalArgumentException("Negative permits");
        this.permits = initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notify();
    }

    public synchronized int availablePermits() {
        return permits;
    }
}
