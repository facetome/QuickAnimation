package com.baisc.animationcore.animation;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 *  animator set.
 */

public class AnimationSet extends ViewAnimation {


    public AnimationSet(Builder builder, boolean shareInterpolator) {
        super(builder);
        mAnimation = new android.view.animation.AnimationSet(shareInterpolator);
        create();
    }

    /**
     * 添加动画.
     *
     * @param animation
     */
    public AnimationSet addAnimations(ViewAnimation animation){
        android.view.animation.AnimationSet set = (android.view.animation.AnimationSet) mAnimation;
        if (set != null){
            set.addAnimation(animation.mAnimation);
        }
        return this;
    }
}
