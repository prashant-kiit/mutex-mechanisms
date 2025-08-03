package OptimisticLock;

public class OptimisticLock {
    @SuppressWarnings("unused")
    private String entity;
    private int version;

    public OptimisticLock(String entity) {
        this.entity = entity;
    }

    public int updateVerison() {
        this.version++;
        return version;
    }

    public int getVerion() {
        return this.version;
    }

}
