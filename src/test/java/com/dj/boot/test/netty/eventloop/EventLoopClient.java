package com.dj.boot.test.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * 客户端
 * @ProjectName: demo
 * @PackageName: com.dj.boot.test.netty
 * @Author: wangJia
 * @Date: 2021-12-20-10-01
 */
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        //启动类
        Channel channel = new Bootstrap()
                //添加EventLoop
                .group(new NioEventLoopGroup())
                //选择客户端chanel的实现
                .channel(NioSocketChannel.class)
                //添加处理器handler
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080))
                .sync() //阻塞方法 知道建立连接
                .channel();//代表链接对象
        System.out.println(channel);
        System.out.println("main");
    }

}
