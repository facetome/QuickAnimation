package com.baisc.animatorprocessor.utils;


/**
 * class utils.
 */

public class ClassUtils {

    /**
     * 获取一个类的package.
     *
     * @param className class.getName();
     * @return packageName
     */
    public static String getPackageName(String className){
        if (!TextUtils.isEmpty(className)){
            int lastSeparator = className.lastIndexOf(".");

            String packageName = className.substring(0, lastSeparator);
            return packageName;
        }
        return null;
    }

    /**
     * 获取类名.
     *
     * @param className class.getName
     * @return simpleName
     */
    public static String getClassSimpleName(String className){
        if (!TextUtils.isEmpty(className)) {
            int lastSeparator = className.lastIndexOf(".");
            return className.substring(lastSeparator + 1);
        }
        return null;
    }
}
