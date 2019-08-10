package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * 属性动画.
 */

public class Animator extends Annotation {

    public static final String ANNOTATION_NAME = com.baisc.animationannomation.Animator.class.getName();

    public String property;

    public float[] values;

    public boolean autoCancel;

    public Animator(VariableElement mirror) {
        super(mirror);
    }
}
