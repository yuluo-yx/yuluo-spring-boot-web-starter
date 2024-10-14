package indi.yuluo.core.web.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SnowFlakeIdWorker {

    private final static Logger log = LoggerFactory.getLogger(SnowFlakeIdWorker.class);

    /**
     * Start timestamp, in milliseconds; This is 2021-06-01
     */
    private static final long TW_EPOCH = 1622476800000L;

    /**
     * The number of bits occupied by the machine ID
     */
    private static final long WORKER_ID_BITS = 4L;

    /**
     * Maximum machine ID supported, 0-15
     * <p>
     * The source code of PS.Twitter is -1L ^ (-1L << workerIdBits); Here the final xor operation with -1,
     * because of the particularity of -1's binary complement, it is equivalent to taking the inverse.
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * The number of bits the sequence occupies in the ID
     */
    private static final long SEQUENCE_BITS = 8L;

    /**
     * Number of machine ID shifts left
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * Time truncated left shift number
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * The maximum mask of the generated sequence, 256
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * Working machine ID(0~15)
     */
    private final long workerId;

    /**
     * Millisecond sequence (0~256)
     */
    private long sequence = 0L;

    /**
     * Timestamp of the last ID generated
     */
    private long lastTimestamp = -1L;

    /**
     * How to create an ID generator: Use the serial number range of the working machine [0, 15]
     *
     * @param workerId Working machine ID
     */
    public SnowFlakeIdWorker(long workerId) {
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            Random random = new Random(workerId);
            workerId = random.nextInt((int) MAX_WORKER_ID);
            log.warn("Worker ID can't be greater than {} or less than 0, use random: {}.", MAX_WORKER_ID, workerId);
        }
        this.workerId = workerId;
    }

    /**
     * How to create an ID generator: Create the generator using the local IP as the machine ID
     */
    public SnowFlakeIdWorker() {
        int workerId = 0;
        String host = IpDomainUtil.getLocalhostIp();
        if (host == null) {
            Random random = new Random(workerId);
            workerId = random.nextInt((int) MAX_WORKER_ID);
        } else {
            workerId = host.hashCode() % (int) MAX_WORKER_ID;
            workerId = Math.abs(workerId);
        }
        this.workerId = workerId;
    }

    /**
     * get next id
     * thread safe
     * @return id with 15 length
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (lastTimestamp == timestamp) {
            // Generated at the same time, then the sequence number +1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // Sequence overflow in milliseconds: The maximum value is exceeded
            if (sequence == 0) {
                // Block to the next millisecond to get a new timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // The timestamp changes and the sequence resets in milliseconds
            sequence = 0L;
        }
        // Timestamp of the last ID generated
        lastTimestamp = timestamp;

        // Shift it and put it together with the OR operation
        return ((timestamp - TW_EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}
