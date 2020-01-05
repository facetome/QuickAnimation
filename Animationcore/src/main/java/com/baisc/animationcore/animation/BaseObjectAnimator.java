package com.baisc.animationcore.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;

import com.baisc.animationcore.BaseAnimation;
import com.baisc.animationcore.QuickAnimation;
import com.baisc.animationcore.animation.BaseObjectAnimator.Builder;

import androidx.annotation.NonNull;

/**
 * base object animator.
 */

public abstract class BaseObjectAnimator<T extends Builder> extends BaseAnimation implements AnimatorListener {

    protected Animator mAnimator;

    BaseObjectAnimator(Builder builder) {
        super(builder.mBuilder);
        T newBuilder = (T) builder;
        mAnimator = createAnimator(newBuilder);
        if (newBuilder.mBuilder.getInterpolator() != null) {
            mAnimator.setInterpolator(newBuilder.mBuilder.getInterpolator());
        }
        mAnimator.setDuration(builder.mBuilder.getDuration());
        mAnimator.setStartDelay(builder.mBuilder.getDelay());
        mAnimator.addListener(this);
    }


    @Override
    public void onAnimationStart(Animator animation) {
        if (mBuilder.getAnimationCallback() != null) {
            mBuilder.getAnimationCallback().onStart(this);
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (mBuilder.getAnimationCallback() != null) {
            mBuilder.getAnimationCallback().onRepeat(this);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        if (mBuilder.getAnimationCallback() != null) {
            mBuilder.getAnimationCallback().onCancel(this);
        }
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (mBuilder.getAnimationCallback() != null) {
            mBuilder.getAnimationCallback().onEnd(this);
        }
    }

    @NonNull
    protected abstract Animator createAnimator(T builder);

    @Deprecated
    @Override
    protected void create() {
        // do nothing
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    @Override
    public void reset() {
        // do nothing
    }

    @Override
    public void destroy() {
        cancel();
        mAnimator = null;
    }

    @Override
    public void cancel() {
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    public static abstract class Builder {
        QuickAnimation.Builder mBuilder;

        public Builder(QuickAnimation.Builder builder) {
            this.mBuilder = builder;
        }

        protected abstract void clear();

        public abstract BaseObjectAnimator create();
    }
}
