package com.cmiot.gateway.luo;


/**
 * @author luowz
 * @version 1.0
 * @Description TODO
 * @date 2020/7/23 15:44
 **/
class Solution {
    public int splitArray(int[] nums) {
        int len = nums.length;
        if (len == 1 || maxCommonDivisor(nums[0], nums[len - 1]) > 1) {
            return 1;
        }
        //填充公约标识表
        boolean[][] table = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            table[i][i] = true;
        }
        for (int i = 1; i < len; i++) {
            for (int j = i; j < len; j++) {
                table[j][i]=table[i][j] = maxCommonDivisor(nums[i], nums[j]) > 1;
                /*if (i != j && table[i][j]) {
                    System.out.println("i,j,v:" + i + "," + j + "," + table[i][j]);
                }*/
            }
        }
        //插针法
        len--;
        for (int i = 1; i < len; i++) {
            if (content(table, i, 0)) {
                return i + 1;
            }
        }
        return nums.length;
    }

    /**
     * 插针贪心算法求最小值n
     *
     * @param table
     * @param n     剩余插针数
     * @return
     */
    public boolean content(boolean[][] table, int n, int lastIndex) {
        int end = table.length - 1;
        if (lastIndex >= end) {
            return true;
        }
        if (n == 1) {
            for (int i = lastIndex; i < end; i++) {
                if (table[lastIndex][i] && table[i + 1][end]) {
                    return true;
                }
            }
            return false;
        }
        for (int i = lastIndex; i < end; i++) {
            if (table[lastIndex][i] && content(table, n - 1, i + 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 余数法求最大公约数
     *
     * @param x
     * @param y
     * @return
     */
    public static int maxCommonDivisor(int x, int y) {
        while (y > 0) {
            int temp = x % y;
            x = y;
            y = temp;
        }
        return x;
    }

    public static void main(String[] args) {
        String str = "123123";
        String[]strs = str.split(",");
        int len = strs.length;
        System.out.println(len);
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = Integer.parseInt(strs[i]);
        }
        System.out.println(new Solution().splitArray(nums));
    }
}
