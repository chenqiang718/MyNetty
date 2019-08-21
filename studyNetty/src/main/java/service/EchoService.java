package service;

import handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author: Chen Qiang
 * @Date: 2019-08-20 14:39
 * @description
 */
public class EchoService implements MyService{
    private final static int THREAD_HANDLER_NUMBER=2;
    private int port;

    public EchoService(int port){
        this.port=port;
    }

    public void start() throws Exception{
        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup worker=new NioEventLoopGroup(THREAD_HANDLER_NUMBER);
        try{
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture channelFuture=bootstrap.bind().sync();
            System.out.println("EchoService start success...");
            channelFuture.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully().sync();
            boss.shutdownGracefully().sync();
        }
    }
}
