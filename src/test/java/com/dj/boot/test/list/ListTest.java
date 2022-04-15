package com.dj.boot.test.list;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

public class ListTest {

    public static void main(String[] args) {
        /**
         * ArrayList<>(0) 初始化空的Object数组 第一次add时 扩容1
         *  this.elementData = EMPTY_ELEMENTDATA;
         * ArrayList<>() 初始化默认的空的Object数组 第一次add时 扩容默认10
         *  this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
         * ArrayList<>(4) 初始化elementData的长度
         *  this.elementData = new Object[initialCapacity];
         */

        List<String> list1 = new ArrayList<>(5);
        List<String> list2 = new Vector<>(1,2);
        list1.add("33333");

        listToArray();


        //String[] arr={"AAA","BBB","CCC"};

       List lists= Arrays.asList("AAA", "BBB", "CCC");

        List<?> list = new ArrayList<>(); // 通配符泛型  当做入参 和 返回值



        String a = null;
        if (StringUtils.isNoneBlank(a) && !a.contains("null")) {
            System.out.println(111);
        } else {
            System.out.println(222);
        }
        test();
    }


    @Scheduled(cron = "*/5 * * * * ?")
    public static void test() {
        List<String> l = new ArrayList<>();
        l.add("a");
        l.add("b");
        l.add("c");
        l.add("d");
        l.forEach(System.out::println);
        Integer a = 0;
        for (String s : l) {
            a++;
            System.out.println(a+":"+ s);
        }
    }



    private static void listToArray(){
        List<String> linkList = new LinkedList<>();
        linkList.add("555555");
        linkList.add(0, "0000");
        linkList.add(1, "1111");
        linkList.add(2, "2222");
        linkList.add(3, "33333");
        linkList.add(4, "4444");
        linkList.add(5, "44444");
        String s = linkList.get(2);
        List<String> deptNoList = Lists.newArrayList();
        deptNoList.add("DP000001");
        deptNoList.add(null);
        deptNoList.add("DP000002");
        deptNoList.add(null);
        deptNoList.add("DP000003");
        deptNoList.add("DP000006");
        deptNoList.add("DP000003");

        deptNoList.remove(1);//根据下标删除 实际就是获取下标位置 以后的所有元素左移一位  覆盖即删除
        deptNoList.remove(null);//根据对象删除 遍历数组 删除第一个满足条件的 实际就是获取下标位置 以后的所有元素左移一位  覆盖即删除
        deptNoList.remove("DP000003");


        deptNoList.removeAll(deptNoList);

        deptNoList.get(3);
        //集合转数组
        String[] deptNos = deptNoList.toArray(new String[deptNoList.size()]);
        //数组转集合
        List<String> asList = Arrays.asList(deptNos);
    }
}
