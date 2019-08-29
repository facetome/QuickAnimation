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

    /**
     * 是否是view动画.
     *
     * @return
     */
    public abstract byte viewType();


    public Annotation(VariableElement mirror) {
        mVariableElement = mirror;
    }

    public static class Type{

        public static final byte TYPE_RESOURCE = 1;  // 01

        public static final byte TYPE_VIEW = 2; // 10

        public static final int TYPE_ANIMATOR = 4; // 100
    }


}
