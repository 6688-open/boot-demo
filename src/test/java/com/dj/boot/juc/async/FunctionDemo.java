package com.dj.boot.juc.async;

import io.swagger.models.auth.In;
import org.junit.Test;

import javax.sound.midi.SoundbankResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionDemo {

    public static void main(String[] args) {
        // 函数式接口

        /**
         * 函数型接口 Function <T, R>   有参数  有返回  apply()
         */
        Function<String, Integer> function = s -> 33;
        System.out.println( function.apply("sss"));


        /**
         *  断定型接口 Predicate<T>  有参数  返回值 boolean  test()
         */
        Predicate<String> predicate = s -> false;
        System.out.println(predicate.test("222"));
        /**
         * 消费型接口  Consumer<T>     有参数   无返回      accept()
         */
        Consumer<String> consumer = s -> System.out.println(s.length());
        consumer.accept("ss");


        /**
         * 供给型接口 Supplier<T>    无参数  有返回     get()
         */
        Supplier<String> supplier = () -> "222";
        System.out.println(supplier.get());


    }

    /**
     * Java 8在java.util,function包下预定义了大量的函数式接口供我们使用
     *
     *         Supplier接口
     *         Consumer接口
     *         Predicate接口
     *         Function接口
     */

//=======================================================================================================================
//=======================================================================================================================
    /**
     * Supplier接口
     * Supplier< T >：包含一个无参的方法    无参数  有返回     get()
     *
     * T get()：获得结果
     * 该方法不需要参数，它会按照某种实现逻辑（由Lambda表达式实现）返回一个数据
     * Supplier接口也被称为生产型接口，如果指定了接口的泛型是什么类型，那么接口中的get方法就会生产什么类型的数据供我们使用
     */
    @Test
    public void supplierTest(){
        //使用Lambda表达式
        Integer i = getInteger(() -> 1);
        String s = getString(() -> "2222");
    }

    private Integer getInteger (Supplier<Integer> supplier){
        return supplier.get();
    }
    private String getString (Supplier<String> supplier){
        return supplier.get();
    }

//=======================================================================================================================
//=======================================================================================================================

    /**
     * Consumer接口
     * Consumer< T t >:包含两个方法  有参数   无返回      accept()
     *
     * void accept(T t):对给定的参数执行此操作
     * default Consumer< T > andThen(Consumer after):返回一个组合的Consumer，依次执行此操作，然后执行after操作
     * Consumer< T >接口也被称为消费型接口，它消费的数据的数据类型由泛型
     */
    @Test
    public void consumerTest(){
        //使用Lambda表达式
        operateString("张三", s -> System.out.println(s));
        operateString("张三", s -> System.out.println(new StringBuilder(s).reverse().toString()));
        operateString("张三", s -> System.out.println(s), s -> System.out.println(new StringBuilder(s).reverse().toString()));

    }

    private void operateString(String name, Consumer<String> consumer){
        consumer.accept(name);
    }
    private void operateString(String name, Consumer<String> consumer1, Consumer<String> consumer2){
        consumer1.andThen(consumer2).accept(name);
    }

//=======================================================================================================================
//=======================================================================================================================

    /**
     * Predicate接口
     * Predicate< T >:常用的四个方法   有参数  返回值 boolean  test()
     *
     * boolean test(T t):对给定的参数进行判断（判断逻辑由Lambda表达式实现），返回一个布尔值
     * default Predicate< T > negate():返回一个逻辑的否定，对应逻辑非
     * default Predicate< T > and(Predicate other):返回一个组合判断，对应短路与
     * default Predicate< T > or(Predicate other):返回一个组合判断，对应短路或
     * Predicate< T >接口通常用于判断参数是否满足指定的条件
     */
    @Test
    public void predicateTest(){

        boolean b = checkString("hello", s -> s.length() < 8);
        System.out.println(b);

        boolean b1 = checkString("hello", s -> s.length() < 8, s -> s.startsWith("he"));
        System.out.println(b1);
    }

    private boolean checkString(String str, Predicate<String> predicate){
        return predicate.test(str);
        //return predicate.negate().test(str);//逻辑非 相当于  !predicate.test(str)
    }

    private boolean checkString(String str, Predicate<String> predicate1, Predicate<String> predicate2){
        return predicate1.or(predicate2).test(str); //相当于 ||
        //return predicate1.and(predicate2).test(str);//相当于 &&
    }

//=======================================================================================================================
//=======================================================================================================================

    /**
     * Function接口
     * Function<T,R>:常用的两个方法   有参数  有返回  apply()
     *
     * R apply(T,t):将此函数应用于给定的参数
     * default < V > Function andThen(Function after):返回一个组合函数，首先将该函数应用于输入，然后将after函数应用于结果
     * Function<T,R>接口通常用于对参数进行处理，转换（处理逻辑由Lambda表达式实现），然后返回一个新的值
     */
    @Test
    public void functionTest(){
        Integer i1 = convert("123", s -> Integer.parseInt(s));
        String s1 = convert(1, s -> String.valueOf(s + 1));
        String s2 = convert("123", s -> Integer.parseInt(s), i -> String.valueOf(i + 1));
    }

    private Integer convert(String s, Function<String, Integer> function){
        return function.apply(s);
    }

    private String convert(Integer s, Function<Integer, String> function){
        return function.apply(s);
    }
    //字符串转int  int+1转字符串
    private String convert(String s, Function<String, Integer> function1, Function<Integer, String> function2){
        return function1.andThen(function2).apply(s);
    }





}

interface MyInterface {

    public Integer myInt (Integer x);

    public  boolean isOk(String str);

    public void consumer (String str);

    public String sup ();


}




