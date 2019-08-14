package com.baisc.animatorprocessor;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.VariableElement;

/**
 * view动画集.
 */

public class Animations extends Annotation {

    private List<Annotation> mChilds = new ArrayList<>();

    Animations(VariableElement mirror) {
        super(mirror);
    }

    private void add(Annotation annotation) {
        if (annotation != null) {
            mChilds.add(annotation);
        }
    }

    public static Animations merge(List<Annotation> childs) {
        Animations animations = new Animations(null);
        if (childs != null && !childs.isEmpty()) {
            Annotation one = childs.get(0);
            animations.mInterpolatorClass = one.mInterpolatorClass;
            for (Annotation child : childs) {
                child.mInterpolatorClass = null;
                child.mEvaluatorClass = null;
                animations.add(child);
            }
            //通过注解方式，animationSet会共享interpolator
        }
        return animations;
    }

    public List<Annotation> getChilds() {
        return mChilds;
    }
}
