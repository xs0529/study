package com.springboot.study.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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
public class EchoClientHandler extends SimpleChannelInboundHandler<BigDecimal> {
    private ByteBuf[] byteBufs;
    private int index = 0;

    public EchoClientHandler(ByteBuf[] byteBufs){
        this.byteBufs = byteBufs;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(byteBufs[index].copy());
        index++;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, BigDecimal in) {
        System.out.println(in);
        System.out.println("第"+ index + "次");
        if (index>=byteBufs.length){
            index = 0;
        }
        ctx.writeAndFlush(byteBufs[index].copy());
        index++;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("66666666");
        ctx.fireUserEventTriggered(evt);
    }
}
