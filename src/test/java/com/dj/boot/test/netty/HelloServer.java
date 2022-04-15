package com.dj.boot.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 *  服务端
 * @ProjectName: demo
 * @PackageName: com.dj.boot.test.netty
 * @Author: wangJia
 * @Date: 2021-12-20-09-54
 */
public class HelloServer {
    public static void main(String[] args) {
        //启动类 负责组装netty组件 启动服务器
        new ServerBootstrap()
                //BossEventLoop WorkerEventLoop (Selector thread)
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
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override //读事件 监听read方法
                            public void channelRead(ChannelHandlerContext ctx, Object msg){
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
