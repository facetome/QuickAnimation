package com.baisc.animationcore.animation;

import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * Scale.
 */

public class Scale extends ViewAnimation {

    public Scale(Builder builder, float fromX, float toX, float fromY, float toY, int xType, float pivotX, int yType, float pivotY) {
        super(builder);
        mAnimation = new ScaleAnimation(fromX, toX, fromY, toY, xType, pivotX, yType, pivotY);
        create();
    }


}
