package com.dj.boot.common.util.map.lrumap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-06-08-15-54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DLinkedNode {
    public String key;
    public String value;
    public DLinkedNode pre;
    public DLinkedNode next;
}
