package com.baisc.animationcore.animation;

import android.animation.Animator;
import android.support.annotation.NonNull;
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
    private List<Animator> animators = new ArrayList<>();


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
        android.animation.AnimatorSet animator = (android.animation.AnimatorSet) mAnimator;
        if (animator != null) {
            if (animator.isRunning()) {
                animator.cancel();
            }
            animator.setTarget(view);
            if (isPlayTogether) {
                animator.playTogether(animators);
            } else {
                animator.playSequentially(animators);
            }
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
        android.animation.AnimatorSet set = new android.animation.AnimatorSet();
        Builder newBuilder =  builder;
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
            isPlayTogether = true;
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
