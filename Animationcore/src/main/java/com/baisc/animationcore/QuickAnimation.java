package com.baisc.animationcore;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.ColorSpace.Model;

import com.baisc.animationcore.animation.Alpha;
import com.baisc.animationcore.animation.AnimationSet;
import com.baisc.animationcore.animation.AnimatorSet;
import com.baisc.animationcore.animation.ObjectAnimator;
import com.baisc.animationcore.animation.ResourceAnimation;
import com.baisc.animationcore.animation.Rotate;
import com.baisc.animationcore.animation.Scale;
import com.baisc.animationcore.animation.Translate;

import java.lang.ref.WeakReference;

/**
 * 动画类.
 */

public class QuickAnimation {

    static final int TRANSLATE = 1;

    static final int ROTATE = 2;

    static final int SCALE = 3;

    static final int ALPHA = 4;

    //xml动画
    static final int RESOURCE = 5;

    public static final int INFINITE = -1;

    public static final int RESTART = 1;

    public static final int REVERSE = 2;


    public static Builder with(Activity context) {
        return new Builder(context);
    }

    public static Builder with(Fragment fragment) {
        return new Builder(fragment);
    }

    public static Builder with(android.support.v4.app.Fragment fragment) {
        return new Builder(fragment);
    }

    public static class Builder {

        private AnimationType animationType;

        private TimeInterpolator interpolator;

        private WeakReference<Activity> mActivityReference;

        private WeakReference<Fragment> mFragmentReference;

        private WeakReference<android.support.v4.app.Fragment> mSupportFragmentReference;

        private TypeEvaluator evaluator;

        private long duration;

        private long delay;

        private boolean fillAfter = false;

        private boolean fillBefore = true;

        private int repeatCount = 0;

        private int repeatMode = RESTART;

        private Builder(Activity activity) {
            if (activity == null) {
                throw new IllegalArgumentException("activity is not be allowed null");
            }
            mActivityReference = new WeakReference<>(activity);
        }

        private Builder(Fragment fragment) {
            if (fragment == null) {
                throw new IllegalArgumentException("fragment is not be allowed null");
            }
            mFragmentReference = new WeakReference<>(fragment);
        }

        private Builder(android.support.v4.app.Fragment fragment) {
            if (fragment == null) {
                throw new IllegalArgumentException("fragment is not be allowed null");
            }
            mSupportFragmentReference = new WeakReference<>(fragment);
        }

        public Builder interpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public Builder evaluator(TypeEvaluator evaluator) {
            this.evaluator = evaluator;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder delay(long delay) {
            this.delay = delay;
            return this;
        }

        public Builder fillBefore(boolean fillBefore) {
            this.fillBefore = fillBefore;
            return this;
        }

        public Builder fillAfter(boolean fillAfter) {
            this.fillAfter = fillAfter;
            return this;
        }

        public Builder repeatCount(int repeatCount){
                this.repeatCount = repeatCount;
                return this;
        }

        public Builder repeatMode(int mode){
            this.repeatMode = mode;
            return this;
        }

        public Translate astTranslate(float fromX, float fromY, float toX, float toY) {
            return new Translate(this, fromX, fromY, toX, toY);
        }

        public Alpha asAlpha(float fromAlpha, float toAlpha) {
            return new Alpha(this, fromAlpha, toAlpha);
        }

        public Scale asScale(float fromX, float toX, float fromY, float toY) {
            return new Scale(this, fromX, toX, fromY, toY);
        }

        public ResourceAnimation asResourceAnimation(int id) {
            Context context = mActivityReference.get();
            if (context == null && mFragmentReference.get() != null) {
                context = mFragmentReference.get().getActivity();
            }
            if (context == null && mSupportFragmentReference.get() != null) {
                context = mSupportFragmentReference.get().getActivity();
            }
            return new ResourceAnimation(this, context, id);
        }

        public Rotate asRotate(float fromDegree, float toDegree, float pivotXValue, float pivotYValue) {
            return new Rotate(this, fromDegree, toDegree, pivotXValue, pivotYValue);
        }

        public ObjectAnimator.Builder asObjectAnimator(String propertyName) {
            ObjectAnimator.Builder builder = new ObjectAnimator.Builder(this);
            builder.setPropertyName(propertyName);
            return builder;
        }

        public AnimatorSet.Builder asObjectAnimators() {
            return new AnimatorSet.Builder(this);
        }

        public AnimationSet asViewAnimation(boolean shareInterpolator) {
            return new AnimationSet(this, shareInterpolator);
        }

        public TypeEvaluator getEvaluator() {
            return evaluator;
        }

        public TimeInterpolator getInterpolator() {
            return interpolator;
        }

        public long getDuration() {
            return duration;
        }

        public long getDelay() {
            return delay;
        }

        public int getRepeatCount() {
            return repeatCount;
        }

        public int getRepeatMode() {
            return repeatMode;
        }

        public boolean isFillAfter() {
            return fillAfter;
        }

        public boolean isFillBefore() {
            return fillBefore;
        }

        private boolean checkActivity() {
            if (mActivityReference != null) {
                Activity activity = mActivityReference.get();
                return activity != null && !activity.isFinishing();
            }
            return false;
        }

        private boolean checkFragment() {
            if (mFragmentReference != null) {
                Fragment fragment = mFragmentReference.get();
                return fragment != null && fragment.isAdded();
            }
            return false;
        }

        private boolean checkSupportFragment() {
            if (mSupportFragmentReference != null) {
                android.support.v4.app.Fragment fragment = mSupportFragmentReference.get();
                return fragment != null && fragment.isAdded();
            }
            return false;
        }

    }
}
