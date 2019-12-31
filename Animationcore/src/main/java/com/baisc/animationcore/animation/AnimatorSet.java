package com.baisc.animationcore.animation;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Animation;

import com.baisc.animationcore.QuickAnimation;
import com.baisc.animationcore.AnimationState;
import com.baisc.animationcore.animation.AnimatorSet.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性动画集.
 */

public class AnimatorSet extends BaseObjectAnimator<Builder> {

    private boolean isPlayTogether = true;
    private List<Animator> animators;


    AnimatorSet(Builder builder) {
        super(builder);
    }

    @Override
    public AnimationState play(View view) {
        Animation animation = view.getAnimation();
        if (animation != null && animation.hasStarted()) {
            animation.cancel();
        }
        view.clearAnimation();
        android.animation.AnimatorSet set = (android.animation.AnimatorSet) mAnimator;
        if (set != null) {
            if (set.isRunning()) {
                set.cancel();
            }
            set.setTarget(view);
            for (Animator animator: animators){
                animator.setTarget(view);
            }
            if (isPlayTogether) {
                set.playTogether(animators);
            } else {
                set.playSequentially(animators);
            }
            set.start();
        }
        return this;
    }

    @Override
    public void destroy() {
        super.destroy();
        animators.clear();
        animators = null;
    }

    @Override
    protected Animator createAnimator(Builder builder) {
        if (animators == null) {
            animators = new ArrayList<>();
        }
        animators.clear();
        android.animation.AnimatorSet set = new android.animation.AnimatorSet();
        Builder newBuilder = builder;
        List<BaseObjectAnimator> animatorWraps = newBuilder.animators;
        if (animatorWraps != null && !animatorWraps.isEmpty()) {
            for (BaseObjectAnimator animator : animatorWraps) {
                animators.add(animator.mAnimator);
            }
        }
        isPlayTogether = newBuilder.isPlayTogether;
        newBuilder.clear();
        return set;
    }

    public static class Builder extends BaseObjectAnimator.Builder {

        boolean isPlayTogether = true;

        List<BaseObjectAnimator> animators = new ArrayList<>();

        float[] values;


        public Builder(QuickAnimation.Builder builder) {
            super(builder);
        }


        public Builder playTogether(boolean together) {
            isPlayTogether = together;
            return this;
        }

        public Builder addAnimators(BaseObjectAnimator animator) {
            animators.add(animator);
            return this;
        }

        protected void clear() {
            animators.clear();
            animators = null;
        }

        @Override
        public BaseObjectAnimator create() {
            return new AnimatorSet(this);
        }
    }

}
