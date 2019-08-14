package com.baisc.animationcore.animation;

import android.animation.Animator;
import android.support.annotation.NonNull;

import com.baisc.animationcore.BaseAnimation;
import com.baisc.animationcore.QuickAnimation;
import com.baisc.animationcore.animation.BaseObjectAnimator.Builder;

/**
 * base object animator.
 */

public abstract class BaseObjectAnimator<T extends Builder> extends BaseAnimation {

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
        if (mAnimator.isRunning()){
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
