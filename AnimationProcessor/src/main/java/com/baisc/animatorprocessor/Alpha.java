package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * alpha数据封装.
 */
public class Alpha extends Annotation {

    public float fromAlpha;

    public float toAlpha;

    public static String ANNOTATION_NAME = com.baisc.animationannotation.Alpha.class.getName();


    @Override
    public byte viewType() {
        return Type.TYPE_VIEW;
    }

    public Alpha(VariableElement mirror) {
        super(mirror);
        com.baisc.animationannotation.Alpha alpha = mirror.getAnnotation(com.baisc.animationannotation.Alpha.class);
        fromAlpha = alpha.fromAlpha();
        toAlpha = alpha.toAlpha();
    }


}
