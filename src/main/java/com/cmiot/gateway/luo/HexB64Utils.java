package com.cmiot.gateway.luo;

/**
 * 
 * @Description hex string和base64相互转换的算法实现
 * hex=>base64：从低位开始，每3位hex字符（3字节，二位hex为1字节），转为2位base64，hex高位不足3位则补'0'，每补1个零，hex后加1个'='
 * base64=>hex：从高位开始，每2位base64字符转为3位hex
 * @author luowz
 * @date 2020/6/8 11:25
 * @version 1.0
 *
 **/
public class HexB64Utils {
    private static final char[] INT_TO_HEX_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] INT_TO_HEX_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static char[] INT_TO_HEX = INT_TO_HEX_UPPER;
    private static final char INT_TO_BASE_64[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/' };
    private static final int BASE_64_TO_INT[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,-1,-1,63,52,53,54,55,56,57,58,59,60,61,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,-1,-1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51};
    private static final int HEX_TO_INT[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,4,5,6,7,8,9,-1,-1,-1,-1,-1,-1,-1,10,11,12,13,14,15,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,10,11,12,13,14,15};
    public static void main(String[] args) {
        System.out.println(base64ToHex("p9hLzd2bkBSe6FGbgMTMgIXZ29GIzBXb1pGI49mZg42dvJnYgs2YpVXcgUGaUB="));
        System.out.println(hexToBase64("54686520717569636B2062726F776E20666F78206A756D7073206F766572203133206C617A7920646F67732E1F69"));
        System.out.println(base64ToHex("2NW5pqaVdqL=="));
        System.out.println(hexToBase64("ba9d55aaa9e56376"));

    }

    /**
     * 从高位开始，每2位base64字符转为3位hex
     * @param base64
     * @return
     */
    public static String base64ToHex(String base64){
        int len = base64.length();
        int eqCounter = 0;
        for (int i = len-1; i >= 0; i--) {
            if(base64.charAt(i)=='='){
                eqCounter++;
            }else{
                break;
            }
        }
        int end = len - eqCounter-1;
        StringBuilder builder = new StringBuilder(end*2);
        for( int i=0;i<end;i+=2){
            int b64_hi = BASE_64_TO_INT[base64.charAt(i)];
            int b64_low = BASE_64_TO_INT[base64.charAt(i+1)];
            int hex_low = b64_hi&0x0f;
            int hex_mid = (b64_hi>>4)|((b64_low&0x03)<<2);
            int hex_hi = b64_low>>2;
            builder.insert(0, INT_TO_HEX[hex_low]);
            builder.insert(0, INT_TO_HEX[hex_mid]);
            builder.insert(0, INT_TO_HEX[hex_hi]);
        }
        if((end&0x01)==1){
            //base64偶数位，去除补的0
            for(int j = 0;j<eqCounter;j++){
                if(builder.charAt(0)=='0'){
                    builder.deleteCharAt(0);
                }else{
                    break;
                }
            }
        }else{
            //base64奇数位，单独处理
            int end_b64 = BASE_64_TO_INT[base64.charAt(end)];
            builder.insert(0, INT_TO_HEX[end_b64&0x0f]);
            int hex_mid= end_b64>>4;
            if(hex_mid==0){
                //hex补两位0的情况
                if(eqCounter==1){
                    //hex自带一位高位0加补一位0的情况
                    builder.insert(0, INT_TO_HEX[0]);
                }
            }else{
                //格式不满足规定，将base64剩余部分当成一位hex
                builder.insert(0, INT_TO_HEX[hex_mid]);
            }
        }
        return builder.toString();
    }
    public static void setHexUpperCase(boolean flag){
        INT_TO_HEX = flag? INT_TO_HEX_UPPER : INT_TO_HEX_LOWER;
    }

    /**
     * 从低位开始，每3位hex字符（3字节，二位hex为1字节），转为2位base64，hex高位不足3位则补'0'，每补1个零，hex后加1个'='
     * @param hex
     * @return
     */
    public static String hexToBase64(String hex){
        int len = hex.length();
        StringBuffer sb = new StringBuffer(len*2);
        //由低位到高位
        int i;
        for(i=len-1;i>1;i-=3){
            int hex_low = HEX_TO_INT[hex.charAt(i)];
            int hex_mid = HEX_TO_INT[hex.charAt(i-1)];
            int hex_hi = HEX_TO_INT[hex.charAt(i-2)];
            int b64_hi = ((hex_mid&0x03)<<4)|hex_low;//<=64
            int b64_low = ((hex_mid>>2)&0x03)|(hex_hi<<2);//<=64
            if(b64_hi>=64||b64_low>=64){
                throw new IllegalArgumentException("illegal hex string:"+hex);
            }
            sb.append(INT_TO_BASE_64[b64_hi]).append(INT_TO_BASE_64[b64_low]);
        }
        switch (i){
            case 0:
                sb.append(INT_TO_BASE_64[HEX_TO_INT[hex.charAt(0)]]).append("==");
                break;
            case 1:
                int ch_mid = HEX_TO_INT[hex.charAt(0)];
                int code_b64_hi = HEX_TO_INT[hex.charAt(1)]+((ch_mid&0x03)<<4);
                int code_b64_low = (ch_mid>>2)&0x03;
                sb.append(INT_TO_BASE_64[code_b64_hi]).append(INT_TO_BASE_64[code_b64_low]).append("=");
                break;
            default:
        }
        return sb.toString();
    }
}
