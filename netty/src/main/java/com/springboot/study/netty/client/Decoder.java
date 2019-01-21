package com.springboot.study.netty.client;

import com.springboot.study.netty.SmartTools;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author XieShuang
 * @version v1.0
 * @since 2019-01-14
 */
public class Decoder extends ByteToMessageDecoder {

    /**
     * 一条消息的最大长度
     */
    private int	lineLength;

    public Decoder(int lineLength){
        this.lineLength = lineLength;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i = byteBuf.readableBytes();
        if (i<lineLength){
            return;
        }
        byte[] bytes2 = new byte[24];
        byteBuf.readBytes(bytes2);
        byte[] startCode = new byte[]{bytes2[0],bytes2[1],bytes2[2],bytes2[3],bytes2[4]};
        byte[] deviceIdBytes = new byte[]{bytes2[5],bytes2[6],bytes2[7],bytes2[8],bytes2[9],bytes2[10]};
        byte success = bytes2[12];//是否成功
        byte[] body = new byte[]{bytes2[14],bytes2[15],bytes2[16],bytes2[17],bytes2[18],bytes2[19],bytes2[20],bytes2[21]};
        String deviceId = getDeviceId(deviceIdBytes);
        //deviceId = (String) session.getAttribute("deviceId");
        //BigDecimal currentValue = getCurrentValue(body);
        list.add(new BigDecimal("127.12"));
    }

    private String getDeviceId(byte[] deviceIdBytes) {
        return SmartTools.bytesIDToStringID(deviceIdBytes);
    }

    private BigDecimal getCurrentValue(byte[] body) {
        byte[] valueBytes = new byte[]{(byte) (body[4]-0x33), (byte) (body[5]-0x33), (byte) (body[6]-0x33), (byte) (body[7]-0x33)};//需要减去  33 33 33 33 ，减去后，小端规则需倒置
        valueBytes = SmartTools.endianness(valueBytes);
        //前三位为整数，后1位为小数
        byte[] intValue = new byte[]{valueBytes[0], valueBytes[1], valueBytes[2]};
        int value = SmartTools.bytesToInt(intValue);
        String s = Integer.toHexString(value);
        int floatValue = SmartTools.byteToUnsignedInt(valueBytes[3]);
        String s1 = Integer.toHexString(floatValue);
        return new BigDecimal(s);
    }
}
