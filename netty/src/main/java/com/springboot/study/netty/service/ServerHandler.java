package com.springboot.study.netty.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author XieShuang
 * @version v1.0
 * @since 2019-01-11
 */
public class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 读客户端数据
     * @param ctx
     * @param in
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        byte[] bytes = new byte[2];
        in.readBytes(bytes);
        int random = (int) (Math.random()*100);
        bytes[1] = (byte) random;
        System.out.println(new BigDecimal(bytes[1]));
        ByteBuf byteBuf;
        byteBuf = Unpooled.buffer(bytes.length);
        byteBuf.writeBytes(bytes);
        ctx.writeAndFlush(byteBuf);
    }

    /**
     * 客户端接入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端接入："+ctx.name());
    }

    /**
     * 客户端断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端断开："+ctx.name());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        //关闭通道
        ctx.channel().close();
        //打印异常
        cause.printStackTrace();
    }
}
