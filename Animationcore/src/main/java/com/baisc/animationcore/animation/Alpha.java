package com.baisc.animationcore.animation;

import android.view.animation.AlphaAnimation;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * alpha.
 */

public class Alpha extends ViewAnimation {

    public Alpha(Builder builder, float fromAlpha, float toAlpha) {
        super(builder);
        mAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        create();
    }
}
