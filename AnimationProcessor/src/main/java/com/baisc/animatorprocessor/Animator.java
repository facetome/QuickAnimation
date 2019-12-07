package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * 属性动画.
 */

public class Animator extends Annotation {

    public static final String ANNOTATION_NAME = com.baisc.animationannotation.Animator.class.getName();

    public String property;

    public float[] values;

    public boolean autoCancel;

    @Override
    public byte viewType() {
        return Type.TYPE_ANIMATOR;
    }

    public Animator(VariableElement mirror) {
        this(mirror, mirror.getAnnotation(com.baisc.animationannotation.Animator.class));

    }

    public Animator(VariableElement element, com.baisc.animationannotation.Animator mirror) {
        super(element);
        property = mirror.property();
        values = mirror.value();
        autoCancel = mirror.autoCancel();
    }
}
