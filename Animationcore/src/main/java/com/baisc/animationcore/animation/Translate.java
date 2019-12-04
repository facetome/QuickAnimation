package com.baisc.animationcore.animation;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * translate.
 */

public class Translate extends ViewAnimation {


    public Translate(Builder builder, float fromXDela, float toXDela, float fromYDela, float toYDela) {
        super(builder);
        mAnimation = new TranslateAnimation(fromXDela, toXDela, fromYDela, toYDela);
        create();
    }

}
