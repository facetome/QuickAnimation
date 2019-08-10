package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * Scale.
 */

public class Scale extends Annotation {

    public static String ANNOTATION_NAME = com.baisc.animationannomation.Scale.class.getName();

    public float fromX;

    public float fromY;

    public float toX;

    public float toY;

    public int pivotXType;

    public int pivotYType;

    public float pivotXValue;

    public float pivotYValue;

    public Scale(VariableElement mirror) {
        super(mirror);
    }
}
