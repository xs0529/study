package com.springboot.study.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author XieShuang
 * @version v1.0
 * @since 2019-01-11
 */
public class EchoClient {

    private String host;
    private int port;
    private ByteBuf[] byteBufs;

    public EchoClient(String host, int port, ByteBuf[] byteBufs) {
        this.host = host;
        this.port = port;
        this.byteBufs = byteBufs;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS))
                                    .addLast(new Decoder(24))
                                    .addLast(new EchoClientHandler(byteBufs));
                        }
                    });

            // 发起异步连接操作
            ChannelFuture f = b.connect().sync();
            // 当代客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        ByteBuf firstMessage;
        byte[] req1 = new byte[]{(byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, 0x25, 0x57, 0x07, 0x00, 0x17, 0x00, 0x68, 0x11, 0x04, 0x33, 0x33, 0x34, 0x33, 0x4C, 0x16};
        firstMessage = Unpooled.buffer(req1.length);
        firstMessage.writeBytes(req1);

        ByteBuf firstMessage1;
        byte[] req2 = new byte[]{(byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, 0x25, 0x57, 0x07, 0x00, 0x17, 0x00, 0x68, 0x11, 0x04, 0x33, 0x33, 0x34, 0x33, 0x4C, 0x16};
        firstMessage1 = Unpooled.buffer(req2.length);
        firstMessage1.writeBytes(req2);

        ByteBuf firstMessage2;
        byte[] req3 = new byte[]{(byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, 0x25, 0x57, 0x07, 0x00, 0x17, 0x00, 0x68, 0x11, 0x04, 0x33, 0x33, 0x34, 0x33, 0x4C, 0x16};
        firstMessage2 = Unpooled.buffer(req3.length);
        firstMessage2.writeBytes(req3);
        ByteBuf[] byteBufs = new ByteBuf[]{firstMessage,firstMessage1,firstMessage2};
        new EchoClient("127.0.0.1", 8000, byteBufs).start();
    }
}