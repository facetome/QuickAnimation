package com.baisc.animatorprocessor;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.VariableElement;

/**
 * 属性动画集.
 */

public class Animators extends Annotation {

    public static final String ANNOTATION_NAME = com.baisc.animationannomation.Animators.class.getName();

    private List<Animator> mAnimators = new ArrayList<>();

    private boolean mPlayTogether;

    public Animators(VariableElement mirror) {
        super(mirror);
    }

    public void addAnimator(Animator animator) {
        mAnimators.add(animator);
    }

    public void setPlayTogether(boolean together) {
        mPlayTogether = together;
    }

    public boolean isPlayTogether() {
        return mPlayTogether;
    }

    public List<Animator> getAnimators() {
        return mAnimators;
    }
}
