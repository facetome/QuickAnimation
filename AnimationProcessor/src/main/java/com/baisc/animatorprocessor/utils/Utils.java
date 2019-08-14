package com.baisc.animatorprocessor.utils;

import com.baisc.animatorprocessor.Annotation;

import java.util.List;

/**
 * Created by basic on 2019/8/4.
 */

public class Utils {

    public static boolean hasTargetAnimation(List<Annotation> annotations, String target) {
        if (annotations != null && !annotations.isEmpty() && !TextUtils.isEmpty(target)) {
            for (Annotation annotation : annotations) {
                if (!target.equals(annotation.mAnnotationClass.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isExitAnimation(List<Annotation> annotations, String target) {
        if (annotations != null && !annotations.isEmpty() && !TextUtils.isEmpty(target)) {
            for (Annotation annotation : annotations) {
                if (target.equals(annotation)) {
                    return true;
                }
            }
        }
        return false;
    }
}
