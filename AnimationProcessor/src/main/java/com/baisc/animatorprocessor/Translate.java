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

    public static String ANNOTATION_NAME = com.baisc.animationannomation.Translate.class.getName();

    public Translate(VariableElement mirror) {
        super(mirror);
    }
}
