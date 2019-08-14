package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * 拍平后的动画参数.
 */

public abstract class Annotation {
    public Class<?> mAnnotationClass;

    public String mInterpolatorClass;

    public String mEvaluatorClass;

    public Params mParams;

    private VariableElement mVariableElement;


    public Annotation(VariableElement mirror) {
        mVariableElement = mirror;
    }

}
