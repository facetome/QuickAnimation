package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * Translate的数据封装
 */

public class Translate extends Annotation {

    public float fromX;

    public float fromY;

    public float toX;

    public float toY;

    public int xType;

    public int yType;

    public static String ANNOTATION_NAME = com.baisc.animationannotation.Translate.class.getName();

    @Override
    public byte viewType() {
        return Type.TYPE_VIEW;
    }

    public Translate(VariableElement mirror) {
        super(mirror);
        com.baisc.animationannotation.Translate translate = mirror.getAnnotation(com.baisc.animationannotation.Translate.class);
        fromX = translate.fromX();
        fromY = translate.fromY();
        toX = translate.toX();
        toY = translate.toY();
        xType = translate.xType();
        yType = translate.yType();
    }
}
