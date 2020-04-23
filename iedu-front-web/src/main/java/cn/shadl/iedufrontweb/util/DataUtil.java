package cn.shadl.iedufrontweb.util;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    //解决数组到了thymeleaf中作为对象输出的问题
    public static String parseArrayToString(Object[] array) {
        if (array==null || array.length==0) {
            return null;
        }
        else {
            String str = "[";
            for (Object obj : array) {
                str += obj;
                str += ",";
            }
            str = str.substring(0, str.length()-1);
            str += "]";
            return str;
        }
    }

    //给日期字符串集合中的元素加上单引号以避免被前端js识别为数字
    public static List<String> stringArrayToString(List<String> array) {
        if (array==null || array.isEmpty()) {
            return null;
        }
        List<String> transformedString = new ArrayList<>();
        for (String str : array) {
            transformedString.add("\'"+str+"\'");
        }
        return transformedString;
    }
}
