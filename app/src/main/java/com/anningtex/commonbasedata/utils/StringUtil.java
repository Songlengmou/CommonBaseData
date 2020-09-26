package com.anningtex.commonbasedata.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @Author: Song
 */
public class StringUtil {

    public static boolean isNumeric(String str) {
        Pattern pattern = compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String addComma(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        boolean neg = false;
        //处理负数
        if (str.startsWith("-")) {
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        //处理小数点
        if (str.indexOf('.') != -1) {
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }

    public static String formatFloatNumber(double value) {
        if (value != 0) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("########");
            return df.format(value);
        } else {
            return "0";
        }
    }

    public static String formatFloatNumberTwo(double value) {
        if (value != 0.00) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
            String format = df.format(value);
            if (format.indexOf(".") == 0) {
                format = "0" + format;
            }
            return format;
        } else {
            return "0.00";
        }
    }

    public static String formatFloatNumberThree(double value) {
        if (value != 0.000) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.000");
            String format = df.format(value);
            if (format.indexOf(".") == 0) {
                format = "0" + format;
            }
            return format;
        } else {
            return "0.000";
        }
    }
}
