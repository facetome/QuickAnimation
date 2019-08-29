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

    @Override
    public byte viewType() {
        return Type.TYPE_VIEW;
    }

    public Rotate(VariableElement mirror) {
        super(mirror);
        com.baisc.animationannomation.Rotate rotate = mirror.getAnnotation(com.baisc.animationannomation.Rotate.class);
        fromDegree = rotate.fromDegree();
        toDegree = rotate.toDegree();
        pivotXType = rotate.pivotXType();
        pivotYType = rotate.pivotYType();
        pivotXValue = rotate.pivotXValue();
        pivotYValue = rotate.pivotYValue();
    }
}
