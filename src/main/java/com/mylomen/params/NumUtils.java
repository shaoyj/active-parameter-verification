package com.mylomen.params;


/**
 * @author: Shaoyongjun
 * @date: 2020/3/3
 * @time: 6:25 PM
 * @copyright by  上海鱼泡泡信息技术有限公司
 */
public class NumUtils {


    /**
     * 校验是否是正整数
     */
    public static boolean isPositiveNum(Long num) {
        return num != null && num > 0;
    }


    /**
     * 校验是否是正整数
     */
    public static boolean isPositiveNum(Integer num) {
        return num != null && num > 0;
    }


    /**
     * 校验是否是正整数
     */
    public static boolean isPositiveNum(String num) {
        return DefaultUtils.isNotEmpty(num) && DefaultUtils.toLong(num) > 0;
    }


    public static boolean noNullAndZero(Long num) {
        return num != null && num != 0;
    }

    public static boolean isNullOrZero(Long num) {
        return num == null || num == 0;
    }


    /**
     * 不为null 且 不为0？
     */
    public static boolean noNullAndZero(String num) {
        return DefaultUtils.isNotEmpty(num) && DefaultUtils.toLong(num) != 0;
    }


    /**
     * 是空或者0？
     */
    public static boolean isNullOrZero(String num) {
        return DefaultUtils.isEmpty(num) || DefaultUtils.toLong(num) == 0;
    }


    public static boolean noNullAndZero(Integer num) {
        return num != null && num != 0;
    }

    public static boolean isNullOrZero(Integer num) {
        return num == null || num == 0;
    }


    public static boolean isNullOrZero(Object num) {
        if (num == null) {
            return true;
        }

        return DefaultUtils.toLong(num.toString()) == 0;
    }
}
