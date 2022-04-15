package com.dj.boot.juc.lock8;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**1 标准访问 先打印邮件还是短信    public synchronized void sendMSM()
 * 2 暂停4秒 邮件方法   先打印邮件还是短信
 * 3 新增普通方法 sayHellow  先打印邮件还是sayHellow
 * 4 两部手机   先打印邮件还是短信
 * 5 两个静态同步方法 同一部手机 先打印邮件还是短信
 * 6 两个静态同步方法 2 部手机 先打印邮件还是短信
 * 7 1个静态同步方法 1个普通同步方法 同一部手机 先打印邮件还是短信
 * 8 1个静态同步方法 1个普通同步方法 2部手机 先打印邮件还是短信
 *
 */

/**  Thread.sleep(100);  所以线程A 先于 线程B 100毫秒
 *
 *
 * 答案   1  一个对象 （资源类） 有多个synchronized方法   synchronized锁的是对象 （this） 当一个线程访问一个方法时 另一个线程等待  非synchronized方法不互斥
 *        2   synchronized锁的是实例对象 线程A先访问 其他线程阻塞 直到线程A执行完  B线程等待  4秒后   在访问线程B
 *        3 synchronized锁的是实例对象 普通方法和同步锁无关    同时执行  不互斥    线程A 4秒
 *        4  synchronized锁的是实例对象 两个对象锁  不影响 不互斥
 *        5 和 6  synchronized 锁的是类对象     static  synchronized 锁的是class 全局锁 只要是Phone   不管phone1 phone2 ... 有一个线程进来获取锁 其他等待等待 直到锁被释放
 *        7 和 8  静态同步方法 锁的是Class对象   普通方法/同步方法 线程A获取锁 线程B正常访问普通方法 没有锁的竞争  同时执行   线程A 2秒后执行 然后释放锁
 */

public class Test {

    @SneakyThrows
    public static void main(String[] args) {

        Phone phone1 = new Phone();
        Phone phone2 = new Phone();


        new Thread( () -> {
            phone1.sendEmail();
        } ,"A").start();

        //不加时   sendEmail  sendMSM 不确定哪个先执行      ----- 先执行线程A  然后 线程B
        Thread.sleep(100);

        new Thread( () -> {
            //phone2.sayHellow();
            phone2.sendMSM();
            //phone1.sendMSM();
        } ,"B").start();
    }
}



class Phone  {

    @SneakyThrows
    public static   synchronized void sendEmail() {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("*****sendEmail");
    }

    public   synchronized void sendMSM() {
        System.out.println("*****sendMSM");
    }

    public  void sayHellow() {
        System.out.println("*****sayHellow");
    }



}
