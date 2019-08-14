package com.baisc.animationcore.animation;

import android.animation.*;
import android.view.View;
import android.view.animation.Animation;

import com.baisc.animationcore.QuickAnimation;
import com.baisc.animationcore.AnimationState;
import com.baisc.animationcore.animation.BaseObjectAnimator.Builder;


/**
 * Object animator.
 */

public class ObjectAnimator extends BaseObjectAnimator<ObjectAnimator.Builder> {

    ObjectAnimator(Builder builder) {
        super(builder);
    }

    @Override
    protected Animator createAnimator(Builder builder) {
        android.animation.ObjectAnimator animator = new android.animation.ObjectAnimator();
        animator.setRepeatCount(builder.mBuilder.getRepeatCount());
        animator.setRepeatMode(builder.mBuilder.getRepeatMode());
        animator.setPropertyName(builder.mPropertyName);
        if (builder.mBuilder.getEvaluator() != null) {
            animator.setEvaluator(builder.mBuilder.getEvaluator());
        }
        float[] values = builder.values;
        if (values != null && values.length > 0) {
            animator.setFloatValues(values);
        }
        return animator;
    }

    @Override
    public AnimationState play(View view) {

        android.animation.ObjectAnimator animator = (android.animation.ObjectAnimator) mAnimator;
        Animation animation = view.getAnimation();
        if (animation != null) {
            animation.cancel();
        }
        view.clearAnimation();
        if (animator.isRunning()) {
            animator.cancel();
        }
        animator.setTarget(view);
        animator.start();
        return null;
    }

    public static class Builder extends BaseObjectAnimator.Builder {

        float[] values;

        String mPropertyName;

        boolean mAutoCancel;

        public Builder(QuickAnimation.Builder builder) {
            super(builder);
        }

        @Override
        protected void clear() {

        }

        public Builder setAutoCancel(boolean autoCancel) {
            mAutoCancel = autoCancel;
            return this;
        }

        public Builder setPropertyName(String propertyName) {
            mPropertyName = propertyName;
            return this;
        }

        public Builder setFloatValues(float... values) {
            this.values = values;
            return this;
        }


        @Override
        public BaseObjectAnimator create() {
            return new ObjectAnimator(this);
        }

    }

}
