package com.baisc.animationcore.animation;

import android.view.animation.RotateAnimation;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * Rotate.
 */

public class Rotate extends ViewAnimation {

    public Rotate(Builder builder, float fromDegree, float toDegree, float pivotXValue, float pivotYValue) {
        super(builder);
        mAnimation = new RotateAnimation(fromDegree, toDegree, pivotXValue, pivotYValue);
        create();
    }
}
