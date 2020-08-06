package com.cmiot.gateway.luo;
/**
 * 
 * @Description 位反转
 * @author luowz
 * @date 2020/7/22 16:12
 * @version 1.0
 *
 **/
public class BytesReverser {
    public static int reverseBits(int n) {
        n = ((n&0xaaaaaaaa)>>>1)|((n&0x55555555)<<1);
        n = ((n&0xcccccccc)>>>2)|((n&0x33333333)<<2);
        n = ((n&0xf0f0f0f0)>>>4)|((n&0x0f0f0f0f)<<4);
        n = (n<<24)|((n&0xff00)<<8)|(n>>>24)|((n>>>8)&0xff00);
        return n;
    }
    public static long reverseBits(long n) {
        return (reverseBits((int)(n>>>32))&0xffffffffL)|(((long) reverseBits((int)n))<<32);
    }


    public static void main(String[] args) {
        System.out.println(BytesReverser.reverseBits(0xaaaaaaaa));
        System.out.println(BytesReverser.reverseBits(0x55555555));
        System.out.println(BytesReverser.reverseBits(0x80000000));
        System.out.println(BytesReverser.reverseBits(0x80000001));
        System.out.println(BytesReverser.reverseBits(0xaaaaaaaaaaaaaaaaL));
        System.out.println(BytesReverser.reverseBits(0x5555555555555555L));
        System.out.println(BytesReverser.reverseBits(0x8000000000000000L));
        System.out.println(BytesReverser.reverseBits(0x8000000000000001L));
        System.out.println(BytesReverser.reverseBits(0b1011101010011101010101011010101010101001111001010110001101110110L));
        System.out.println(Long.toHexString(0b1011101010011101010101011010101010101001111001010110001101110110L));
        System.out.println((int)7982251651537484125L);
    }
}