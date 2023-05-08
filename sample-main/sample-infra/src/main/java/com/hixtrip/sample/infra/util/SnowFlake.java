package com.hixtrip.sample.infra.util;

public class SnowFlake {

    /**
     * 起始时间戳，从2021-12-01开始生成
     */
    private final static long START_STAMP = 1638288000000L;
    /**
     * 序列号占用的位数 12
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 10;
    /**
     * 机器数量最大值
     */
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    /**
     * 序列号最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastStamp = -1L;

    /**
     * 构造方法
     *
     * @param machineId 机器ID
     */
    public SnowFlake(long machineId) {
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new RuntimeException("机器超过最大数量");
        }
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("时钟后移，拒绝生成ID！");
        }
        if (currStamp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStamp = currStamp;
        return (currStamp - START_STAMP) << TIMESTAMP_LEFT // 时间戳部分
                | machineId << MACHINE_LEFT             // 机器标识部分
                | sequence;                             // 序列号部分
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }
}
