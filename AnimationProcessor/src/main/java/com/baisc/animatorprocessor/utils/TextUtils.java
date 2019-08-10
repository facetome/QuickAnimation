package com.baisc.animatorprocessor.utils;

import java.util.List;

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

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Object[] tokens, String suffix) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token).append(suffix);
        }
        return sb.toString();
    }

    public static <T> boolean isAll(List<T> list, T target, Compare<T> compare) {
        if (target != null && list != null && !list.isEmpty()) {
            for (T v : list) {
                if (!compare.compare(v, target)) {
                    return false;
                }
            }
        }
        return true;
    }


    public interface Compare<T> {
        boolean compare(T source, T target);
    }

}
