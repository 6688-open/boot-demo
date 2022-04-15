package com.dj.boot.test.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务端
 *  EventLoopGroup 继承 EventExecutorGroup ScheduledExecutorService  处理io事件 普通任务 定时任务
 *  分配一个 EventLoop 线程执行器（selector多路复用器） 可与多个ServerSocketChannel进行绑定 监听事件 处理io操作
 *  后续的读写都是由同一个EventLoop线程执行
 * @ProjectName: demo
 * @PackageName: com.dj.boot.test.netty
 * @Author: wangJia
 * @Date: 2021-12-20-09-54
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        //某些耗时的handler 用特定的group 分配EventLoop去执行
        EventLoopGroup group = new DefaultEventLoopGroup();
        //启动类 负责组装netty组件 启动服务器
        new ServerBootstrap()
                //boss  worker
                //boss只负责ServerSocketChannel的accept事件 worker 负责SocketChannel的读写事件
                .group(new NioEventLoopGroup()) //accept read 事件
                //选择服务器 ServerSocketChannel 的实现
                .channel(NioServerSocketChannel.class)
                //boss 负责处理链接  worker(child) 负责处理读写
                .childHandler(
                        //chanel    代表和客户端进行数据读写的通道  Initializer初始化所有的处理器
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override //链接建立后 调用初始化方法
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //添加handler处理器
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter(){
                            @Override //读事件 监听read方法
                            public void channelRead(ChannelHandlerContext ctx, Object msg){
                                log.debug(msg.toString());
                                ctx.fireChannelRead(msg); //消息传递给pipeline的下一个handler处理器
                            }
                        }).addLast(group, "handler2", new ChannelInboundHandlerAdapter(){
                            @Override //读事件 监听read方法
                            public void channelRead(ChannelHandlerContext ctx, Object msg){
                                log.debug(msg.toString());
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
