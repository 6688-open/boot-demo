package com.dj.boot.service.lock;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.lock
 * @Author: wangJia
 * @Date: 2020-08-17-10-46
 */
public interface RedissonService {
    /**
     * 无指定时间 加锁
     */
    void lock(String name);

    /**
     * 指定时间 加锁
     *
     * @param leaseTime
     * @param unit
     */
    void lock(String name, long leaseTime, TimeUnit unit);


    boolean tryLock(String name);

    boolean tryLock(String name, long time, TimeUnit unit) throws InterruptedException;

    boolean isLocked(String name);

    void unlock(String name);
}
