package com.baisc.animatorprocessor.core;

import com.baisc.animatorprocessor.Annotation;

import java.util.List;

/**
 * 注解检测.
 */

public final class AnnotationChecker {

    /**
     * 检测列表里面的注解是否是同种类型.
     *
     * @param annotations annotations
     * @return check type
     */
    public static boolean checkAnimation(List<Annotation> annotations) {
        if (annotations == null || annotations.isEmpty()) {
            return true;
        }

        byte mask = annotations.get(0).viewType();
        for (Annotation annotation : annotations) {
            mask = (byte) (mask & annotation.viewType());
            if (mask == 0) {
                return false;
            }
        }
        return true;
    }
}
