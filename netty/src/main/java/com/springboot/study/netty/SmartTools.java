package com.springboot.study.netty;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;

/***
 * 常用功能基类。
 * @author wang
 *
 */
public class SmartTools {

    //断开连接的时候 把连接信息清除
    static public void disCon(String host_ID) {
    }

    /******
     * 转换byte[3]的hostID或DevID为String类型
     * @param bytes：长度为3的byte数组
     * @return 对应的16进制string，不足6个字符补零
     */
    static public String bytesIDToStringID(byte[] bytes) {
        bytes = endianness(bytes);
      byte[] dev1 = {bytes[0], bytes[1], bytes[2]};
      byte[] dev2 = {bytes[3], bytes[4], bytes[5]};
        int temp1 = new BigInteger(dev1).intValue();
        int temp2 = new BigInteger(dev2).intValue();

        return addZero(Integer.toHexString(temp1))+addZero(Integer.toHexString(temp2));
    }

    /*******
     * 将string类型的devID或hostID转换为byte[3]
     * @param ID :strig的ID
     * @return byte[3]的ID
     */
    static public byte[] StringIDTobytesID(String ID) {
        byte[] intID = intToByte(Integer.parseInt(ID, 16));

        return new byte[]{intID[1], intID[2], intID[3]};

    }

    /*******
     * 将string类型的devID或hostID转换为byte[6]
     * @param ID :strig的ID
     * @return byte[3]的ID
     */
    static public byte[] StringIDTobytesID6(String ID) {
        byte[] bytes = new byte[6];
        bytes[5] = (byte) Integer.parseInt(ID.substring(0,2),16);
        bytes[4] = (byte) Integer.parseInt(ID.substring(2,4),16);
        bytes[3] = (byte) Integer.parseInt(ID.substring(4,6),16);
        bytes[2] = (byte) Integer.parseInt(ID.substring(6,8),16);
        bytes[1] = (byte) Integer.parseInt(ID.substring(8,10),16);
        bytes[0] = (byte) Integer.parseInt(ID.substring(10,12),16);
        return bytes;

    }

    /***
     * 整数转换为byte[4]
     * @param i
     * @return
     */
    static private byte[] intToByte(int i) {

        byte[] abyte0 = new byte[4];

        abyte0[3] = (byte) (0xff & i);

        abyte0[2] = (byte) ((0xff00 & i) >> 8);

        abyte0[1] = (byte) ((0xff0000 & i) >> 16);

        abyte0[0] = (byte) ((0xff000000 & i) >> 24);

        return abyte0;
    }

    //补齐6位
    static private String addZero(String string) {
        int countZero = 6 - string.length();
        switch (countZero) {
            case 0:
                break;
            case 1:
                string = "0" + string;
                break;
            case 2:
                string = "00" + string;
                break;
            case 3:
                string = "000" + string;
                break;
            case 4:
                string = "0000" + string;
                break;
            case 5:
                string = "00000" + string;
                break;

            default:
                break;
        }
        if (string.length() > 6)    //ID只有6个字符
            string = string.substring(2);

        return string;
    }

    /**
     * 截取byte数据
     *
     * @param b        是byte数组
     * @param endIndex 包含
     * @return
     */
    public static byte[] cutOutByte(byte[] b, int StartIndex, int endIndex) {
        if (b.length == 0 || endIndex == 0 || b.length <= StartIndex || b.length <= endIndex) {
            return null;
        }
        byte[] bjq = new byte[endIndex - StartIndex + 1];
        for (int i = StartIndex; i <= endIndex; i++) {
            bjq[i - StartIndex] = b[i];
        }
        return bjq;
    }

    //根据key读取value
    public static String readValue(String key) throws IOException {
        try {
            Properties props = new Properties();

            String proFilePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                    + "SmartHome.properties";
            InputStream in = new BufferedInputStream(new FileInputStream(
                    proFilePath));
            props.load(in);
            return props.getProperty(key);
        } catch (Exception e) {
            throw new IOException("读取配置参数错误！请检查根目录下是否正确放置SmartHome.properties文件！");
        }
    }

    public static byte[] endianness(byte[] deviceIdBytes) {
        int length = deviceIdBytes.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length-1; i++) {
            result[length-i-1] = deviceIdBytes[i];
        }
        return result;
    }

    public static int bytesToInt(byte[] intValue) {
        return new BigInteger(intValue).intValue();
    }

    public static Integer byteToUnsignedInt(byte data) {
        return data & 0xff;
    }

}
