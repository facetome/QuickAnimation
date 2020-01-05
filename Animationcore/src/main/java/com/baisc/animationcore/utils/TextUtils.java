package com.baisc.animationcore.utils;


/**
 * 文本工具类.
 */

public class TextUtils {

    /**
     * 文本是否为空.
     *
     * @param text origin
     * @return true is empty,include null and ""
     */
    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }


    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Object[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }


}
