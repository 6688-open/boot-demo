package com.dj.boot.common.util.map.lrumap;

import com.dj.boot.controller.base.BaseController;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;
import com.googlecode.concurrentlinkedhashmap.Weighers;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.util.map.lrumap
 * @Author: wangJia
 * @Date: 2021-06-08-15-56
 */
public class LruCacheTest extends BaseController {

    /**
     * 自定义 基于hashMap + 双向链表实现 lru算法缓存
     *
     * 不能防止并发 (加了ReentrantReadWriteLock 可以防止并发)
     *
     * 不支持并发修改，因为底层使用了HashMap，如果需要支持并发，可以修改为ConcurrentHashMap，同时对count、head、tail、capacity等属性增加volatile关键字，各种修改接口（比如set、remove）增加锁，以此来实现并发安全
     */
    @Test
    public void testLruCache() {
        LruCache cache = new LruCache(3);

        cache.set("one", "111");
        System.out.println(cache.get("one"));   // 111

        cache.set("two", "222");
        System.out.println(cache.get("two"));   // 222

        cache.set("three", "333");
        System.out.println(cache.get("three")); // 333

        cache.set("four", "444");
        System.out.println(cache.get("four"));  // 444

        System.out.println(cache.get("one"));   //null
    }


    /**
     * WeakHashMap
     *   防止 内存溢出  防止并发
     *   但是性能不高 没有缓存策略
     */
    @Test
    public void testWeakHashMap () {
        //如果存放在WeakHashMap中的key都存在强引用，那么WeakHashMap就会退化为HashMap。
        // -Xmx5M java.lang.OutOfMemoryError: Java heap space
        // at cn.intsmaze.collection.MapCase.testWeakHash(MapCase.java:119)
        WeakHashMap<String, String> weakHashMap = new WeakHashMap<>();
        Map<String, String> synchronizedMap = Collections.synchronizedMap(weakHashMap);
        for (int i = 0; i < 100; i++) {
            Integer integer = i;
            synchronizedMap.put(integer+"", i+"");
            //list.add(integer);// list对 key 有强引用 导致WeakHashMap就会退化为HashMap。
        }
    }


    /**
     * JDK 实现LRU算法 是LinkedHashMap实现的
     * ConcurrentLinkedHashMap 是google团队ConcurrentHashMap的封装
     * 防并发 + 实现一个基于LRU策略的缓存
     *
     ConcurrentLinkedHashMap 是google团队提供的一个容器。它有什么用呢？其实它本身是对
     ConcurrentHashMap的封装，可以用来实现一个基于LRU策略的缓存。详细介绍可以参见
     http://code.google.com/p/concurrentlinkedhashmap
     */
    @Test
    public void testConcurrentLinkedHashMap () {

        //ConcurrentLinkedHashMap 在把一个最近最少使用的值丢失时，是没有任何提示的，每丢弃一个值时的提示
        EvictionListener<String, String> listener = new EvictionListener<String, String>() {
            @Override
            public void onEviction(String key, String value) {
                logger.error("LRU丢弃最近未使用的数据---->key:{}, value:{}", key, value);
            }
        };

        ConcurrentLinkedHashMap<String, String> map = new ConcurrentLinkedHashMap.Builder<String, String>()
                .maximumWeightedCapacity(2).listener(listener).weigher(Weighers.singleton()).build();

        map.put( "1", "1");
        map.put( "2", "2");
        map.put( "3", "3");
        map.put( "4", "4");
        System.out.println(map.get( "1")); // null 已经失效了
        System.out.println(map.get( "2"));
    }
}
