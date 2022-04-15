package com.dj.boot.common.util.map.lrumap;

import com.dj.boot.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述:使用Lru算法实现的cache
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-06-08-15-55
 */
public class LruCache {
    /**
     * 真正缓存数据的容器
     */
    private Map<String, DLinkedNode> cache = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static final Logger logger = LoggerFactory.getLogger(LruCache.class);


    /**
     * 当前缓存中的数据数量
     */
    private volatile int count;

    /**
     * 缓存的容量（最多能存多少个数据KV）
     */
    private volatile int capacity;

    /**
     * 双向链表的头尾节点（数据域key和value都为null）
     */
    private volatile DLinkedNode head, tail;

    /**
     * 唯一构造器，进行初始化
     *
     * @param capacity 最多能保存的缓存项数量
     */
    public LruCache(int capacity) {
        this.count = 0;
        this.capacity = capacity;

        this.head = new DLinkedNode();
        this.tail = new DLinkedNode();

        this.head.pre = null;
        this.head.next = this.tail;

        this.tail.pre = this.head;
        this.tail.next = null;
    }

    /**
     * 从缓存中获取数据
     *
     * @param key 缓存中的key
     * @return 缓存的value
     */
    public String get(String key) {
        readWriteLock.readLock().lock();
        try {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                return null;
            }
            moveToFirst(node); // 每次访问后，就需要将访问的key对应的节点移到第一个位置（最近访问）
            return node.value;
        } catch (Exception e) {
            logger.error("获取缓存异常:{}", e.getMessage());
        } finally {
            readWriteLock.readLock().unlock();
        }
        return null;
    }

    /**
     * 向缓存中添加数据
     *
     * @param key   元素key
     * @param value 元素value
     */
    public void set(String key, String value) {
        readWriteLock.writeLock().lock();
        try {
            DLinkedNode existNode = cache.get(key);// 先尝试从缓存中获取key对应缓存项（node）
            if (null == existNode) { // key对应的数据不存在，则加入缓存
                DLinkedNode newNode = new DLinkedNode();
                newNode.key = key;
                newNode.value = value;
                cache.put(key, newNode);// 放入缓存
                addNodeToFirst(newNode); // 将新加入的节点存入双链表，且放到第一个位置
                count++;
                if (count > capacity) { // 如果加入新的数据后，超过缓存容量，则要进行淘汰
                    DLinkedNode delNode = delLastNode();
                    cache.remove(delNode.key);
                    --count; // 淘汰后，数量建议
                }
            } else {
                existNode.value = value; // key对应的数据已存在，则进行覆盖
                moveToFirst(existNode); // 将访问的节点移动到第一个位置（最近访问）
            }
        } catch (Exception e) {
            logger.error("添加缓存异常:{}", e.getMessage());
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 添加新节点到双向链表（新加入的节点位于第一个位置）
     *
     * @param newNode 新加入的节点
     */
    private void addNodeToFirst(DLinkedNode newNode) {
        newNode.next = head.next;
        newNode.pre = head;

        head.next.pre = newNode;
        head.next = newNode;
    }

    /**
     * 删除双向链表的尾节点（淘汰节点）
     *
     * @return 被删除的节点
     */
    private DLinkedNode delLastNode() {
        DLinkedNode last = tail.pre;
        delNode(last);
        return last;
    }

    /**
     * 将节点移动到双向链表的第一个位置
     *
     * @param node 需要移动的节点
     */
    private void moveToFirst(DLinkedNode node) {
        delNode(node); // 将节点移动到头部，有两种方式：
        addNodeToFirst(node);
    }

    /**
     * 删除双链表的节点（直接连接前后节点)
     *
     * @param node 要删除的节点
     */
    private void delNode(DLinkedNode node) {
        DLinkedNode pre = node.pre;
        DLinkedNode post = node.next;

        pre.next = post;
        post.pre = pre;
    }
}
