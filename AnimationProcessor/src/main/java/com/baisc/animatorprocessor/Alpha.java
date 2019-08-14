package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * alpha数据封装.
 */
public class Alpha extends Annotation {

    public float fromAlpha;

    public float toAlpha;

    public static String ANNOTATION_NAME = com.baisc.animationannomation.Alpha.class.getName();


    public Alpha(VariableElement mirror) {
        super(mirror);
        com.baisc.animationannomation.Alpha alpha = mirror.getAnnotation(com.baisc.animationannomation.Alpha.class);
        fromAlpha = alpha.fromAlpha();
        toAlpha = alpha.toAlpha();
    }


}
