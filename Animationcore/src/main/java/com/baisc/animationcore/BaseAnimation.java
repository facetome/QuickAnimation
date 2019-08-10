package com.baisc.animationcore;

import android.view.View;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * 基础动画.
 */

public abstract class BaseAnimation implements AnimationState {

    protected Builder mBuilder;

    public BaseAnimation(Builder builder){
       mBuilder = builder;
    }

    protected abstract void create();

    public abstract AnimationState play(View view);
}
