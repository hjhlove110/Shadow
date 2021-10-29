package com.dresses.library.utils;

import android.text.TextUtils;

/**
 * 类型转换
 *
 * @author: zzs
 * @date: 2018/2/9
 */

public class TypeConversionUtil {

    /**
     * 字符串转化为int
     *
     * @param data 数据
     * @return 数据
     */
    public static int stringToInt(String data) {
        int dataInt = 0;
        try {
            if (!TextUtils.isEmpty(data))
                dataInt = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dataInt;
    }

    /**
     * int 转字符串
     *
     * @param data 数据
     * @return 数据
     */
    public static String intToString(int data) {
        String dataString = "";
        try {
            dataString = String.valueOf(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataString;
    }

    /**
     * 字符串转化为long
     *
     * @param data 数据
     * @return 数据
     */
    public static long stringToLong(String data) {
        long dataLong = 0;
        try {
            if (!TextUtils.isEmpty(data))
                dataLong = Long.parseLong(data);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return dataLong;
    }

    /**
     * 字符串转Double
     *
     * @param data 数据
     * @return 数据
     */
    public static double stringToDouble(String data) {

        double dataDouble = 0;


        if(!TextUtils.isEmpty(data)){
            try {
                dataDouble = Double.parseDouble(data);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        return dataDouble;
    }

    /**
     * 字符串转Float
     *
     * @param data 数据
     * @return 数据
     */
    public static float stringToFloat(String data) {

        float dataFloat = 0;
        try {
            dataFloat = Float.parseFloat(data);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dataFloat;
    }

    /**
     * DoubleTo String
     *
     * @param data 数据
     * @return String
     */
    public static String doubleToString(double data) {
        String dataString;
        try {
            dataString = Double.toString(data);
        } catch (Exception e) {
            e.printStackTrace();
            dataString = "";
        }
        return dataString;
    }

    public static String longToString(long data) {
        String dataString;
        try {
            dataString = Long.toString(data);
        } catch (Exception e) {
            e.printStackTrace();
            dataString = "";
        }
        return dataString;
    }
}
