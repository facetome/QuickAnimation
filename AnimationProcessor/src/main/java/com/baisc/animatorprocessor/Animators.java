package com.baisc.animatorprocessor;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.VariableElement;

/**
 * 属性动画集.
 */

public class Animators extends Annotation {

    public static final String ANNOTATION_NAME = com.baisc.animationannotation.Animators.class.getName();

    private List<Animator> mAnimators = new ArrayList<>();

    private boolean mPlayTogether;

    @Override
    public byte viewType() {
        return Type.TYPE_ANIMATOR;
    }

    public Animators(VariableElement mirror) {
        super(mirror);
        com.baisc.animationannotation.Animators animatorsAnnotation = mirror.getAnnotation(com.baisc.animationannotation.Animators.class);
        com.baisc.animationannotation.Animator[] animatorArray = animatorsAnnotation.value();
        mPlayTogether = animatorsAnnotation.playTogether();
        for (com.baisc.animationannotation.Animator source : animatorArray) {
            addAnimator(new Animator(mirror, source));
        }
    }

    private void addAnimator(Animator animator) {
        mAnimators.add(animator);
    }

    public boolean isPlayTogether() {
        return mPlayTogether;
    }

    public List<Animator> getAnimators() {
        return mAnimators;
    }
}
