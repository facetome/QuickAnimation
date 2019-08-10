package com.baisc.animationcore.animation;

import android.animation.TimeInterpolator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import com.baisc.animationcore.BaseAnimation;
import com.baisc.animationcore.QuickAnimation.Builder;
import com.baisc.animationcore.AnimationState;

/**
 * view动画基类.
 */

public abstract class ViewAnimation extends BaseAnimation {

    @Nullable
    protected Animation mAnimation;

    public ViewAnimation(Builder builder) {
        super(builder);
    }

    @Override
    protected void create() {
        TimeInterpolator interpolator = mBuilder.getInterpolator();
        if (interpolator != null && !(interpolator instanceof Interpolator)) {
            throw new IllegalArgumentException("the translate only can user Interpolator");
        }
        if (interpolator != null) {
            mAnimation.setInterpolator((Interpolator) interpolator);
        }
        mAnimation.setDuration(mBuilder.getDuration());
        mAnimation.setFillAfter(mBuilder.isFillAfter());
        mAnimation.setFillBefore(mBuilder.isFillBefore());
        mAnimation.setRepeatCount(mBuilder.getRepeatCount());
        mAnimation.setRepeatMode(mBuilder.getRepeatMode());
        mAnimation.setStartOffset(mBuilder.getDelay());
    }

    @Override
    public AnimationState play(View view) {
        Animation animation = view.getAnimation();
        if (animation != null && animation.hasStarted()) {
            animation.cancel();
        }
        view.clearAnimation();
        if (mAnimation != null) {
            if (mAnimation.hasStarted()) {
                mAnimation.cancel();
            }
            view.startAnimation(mAnimation);
        }
        return this;
    }

    @Override
    public boolean isRunning() {
        if (mAnimation == null) {
            return false;
        }
        return mAnimation.hasStarted() && !mAnimation.hasEnded();
    }

    @Override
    public void reset() {
        if (mAnimation != null) {
            mAnimation.reset();
        }
    }

    @Override
    public void cancel() {
        if (mAnimation != null && mAnimation.hasStarted()) {
            mAnimation.cancel();
        }
    }

    @Override
    public void destroy() {
        cancel();
        mAnimation = null;
    }
}
