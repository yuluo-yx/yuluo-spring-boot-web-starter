package indi.yuluo.core.web.util;

public class SnowFlakeIdGenerator {

    private static final SnowFlakeIdWorker ID_WORKER;

    static {
        ID_WORKER = new SnowFlakeIdWorker();
    }

    public static long generateId() {
        return ID_WORKER.nextId();
    }
}
