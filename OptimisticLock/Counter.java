package OptimisticLock;

public class Counter {
    private int counter = 0;
    private final OptimisticLock lock = new OptimisticLock("Counter");

    public int update(int count, int lastVersion) throws Exception {
        int currentVersion = lock.getVerion();
        if (currentVersion != lastVersion) {
            throw new Exception("Version Mismatch");
        }
        this.counter = count;
        lock.updateVerison();
        return counter;
    }

    public int getVersion() {
        return lock.getVerion();
    }
}
