package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * Rotate.
 */

public class Rotate extends Annotation {

    public static String ANNOTATION_NAME = com.baisc.animationannomation.Rotate.class.getName();

    public float fromDegree;

    public float toDegree;

    public int pivotXType;

    public int pivotYType;

    public float pivotXValue;

    public float pivotYValue;

    public Rotate(VariableElement mirror) {
        super(mirror);
    }
}
