package com.baisc.animationcore.animation;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * translate.
 */

public class Translate extends ViewAnimation {

    public Translate(Builder builder, int fromXType, float fromX, int toXType, float toX,
            int fromYType, float fromY, int toYType, float toY) {
        super(builder);
        mAnimation = new TranslateAnimation(fromXType, fromX, toXType, toX, fromYType, fromY, toYType, toY);
        create();
    }

    public Translate(Builder builder, float fromX, float toX, float fromY, float toY) {
        this(builder, Animation.ABSOLUTE, fromX, Animation.ABSOLUTE, toX, Animation.ABSOLUTE, fromY, Animation.ABSOLUTE, toY);
    }

}
