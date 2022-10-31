package com.dj.boot.test;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

/**
 * 描述信息
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-10-31-15-33
 */
@Slf4j
public class CpuTest {

    public static void main(String[] args) throws IOException {
        //获取CPU的核数
        log.error("CPU的核数:{}", Runtime.getRuntime().availableProcessors());

        getMemory();

    }


    /**
     * 获取内存使用情况
     */
    public static void getMemory() throws IOException {
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 获取内存总容量
        long totalMemorySize = mem.getTotalPhysicalMemorySize();
        // 获取可用内存容量(剩余物理内存）
        long freeMemorySize = mem.getFreePhysicalMemorySize();
        // 空闲的交换容量
        long freeSwapSpaceSize = mem.getFreeSwapSpaceSize();

        float usedRAM = (float)(((totalMemorySize - freeMemorySize) * 1.0 / totalMemorySize) * 100);

        System.out.println("物理内存总容量totalMemorySize：" + transformation(totalMemorySize) );
        System.out.println("剩余物理内存可用容量freeMemorySize：" + transformation(freeMemorySize));
        System.out.println("usedRAM：" + usedRAM);
        System.out.println("空闲的交换容量:" + transformation(freeSwapSpaceSize));

        Runtime runtime = Runtime.getRuntime();
        // java虚拟机中的内存总量，可用内存空间 单位为byte，默认为系统的1/64
        long totalMemory = runtime.totalMemory();
        // java虚拟机试图使用的最大内存量 最大可用内存空间 单位byte，默认为系统的1/4
        long maxMemory = runtime.maxMemory();
        // java 虚拟机中的空闲内存量 空闲空间 单位byte， 默认为系统的1/4
        long freeMemory = runtime.freeMemory();
        float usedRAMJava = (float)(((totalMemory - freeMemory) * 1.0 / totalMemory) * 100);
        System.out.println("java虚拟机中的内存总量:" + totalMemory / 1024 / 1024 + "MB" );
        System.out.println("java虚拟机试图使用的最大内存量:" + maxMemory / 1024 / 1024 + "MB" );
        System.out.println("java虚拟机中的空闲内存量:" + freeMemory / 1024 / 1024 + "MB" );
        System.out.println("java虚拟机中的剩余内存占总量:" + usedRAMJava + "%" );

        DecimalFormat df = new DecimalFormat("#0.00");
        File[] disks = File.listRoots();
        for (File file : disks) {
            // 获取盘符
            System.out.print(file.getCanonicalPath() + "   ");
            // 获取总容量
            long totalSpace = file.getTotalSpace();
            // 获取剩余容量
            long usableSpace = file.getUsableSpace();
            // 获取已经使用的容量
            long freeSpace = totalSpace - usableSpace;
            // 获取使用率
            float useRate = (float)((freeSpace * 1.0 / totalSpace) * 100);
            System.out.print("总容量： " + transformation(totalSpace));
            System.out.print("已经使用： " + transformation(freeSpace));
            System.out.print("剩余容量： " + transformation(usableSpace));
            System.out.println("使用率： " + Double.parseDouble(df.format(useRate)) + "%   ");
        }
    }
    /**
     * 将字节容量转化为GB
     */
    public static String transformation(long size){
        return (float) size / 1024 / 1024 / 1024 + "GB"+"   ";
    }



}
