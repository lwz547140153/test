package com.cmiot.gateway.luo;


/**
 * @author luowz
 * @version 1.0
 * @Description TODO
 * @date 2020/7/23 15:44
 **/
class Solution3 {

    public static float invSqrt(float x){
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);          // get bits for floating value
        i =  0x5f3759df - (i>>1);    // gives initial guess
        //i =  0x5f375a86 - (i>>1);    // gives initial guess
        x = Float.intBitsToFloat(i);            // convert bits back to float
        x = x * (1.5f - xhalf*x*x); // Newton step
        return x;
    }
    public static double newton(double a){
        a = a*(1.5d-2.5*a*a);
        return a;
    }
    public static void main(String[] args) {
        double a = 0.1d;
        for (int i = 0; i < 100; i++) {
            a = newton(a);
            System.out.println(a);
        }
        System.out.println(invSqrt(3));
        System.out.println(1/Math.sqrt(3));
        System.out.println(invSqrt(800));
        System.out.println(1/Math.sqrt(800));
        System.out.println(Float.floatToIntBits(3.150000000000001f));
        System.out.println(Float.intBitsToFloat(1078565274));

        System.out.println(Float.intBitsToFloat(2139095040));
        System.out.println(Float.intBitsToFloat(-8388608));

        System.out.println(Float.intBitsToFloat(2143289344));
        System.out.println(Float.intBitsToFloat(-4194304));
        System.out.println(Float.isNaN(Float.intBitsToFloat(2143289345)));
        System.out.println(Float.isNaN(Float.intBitsToFloat(-4194303)));
        System.out.println(Float.floatToIntBits(Float.intBitsToFloat(-4194303)));
        System.out.println(Float.floatToRawIntBits(Float.intBitsToFloat(-4194303)));
    }
}
