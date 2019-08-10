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


    public Scale(Builder builder, float fromX, float toX, float fromY, float toY,
            int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        super(builder);
        mAnimation =  new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
    }

    public Scale(Builder builder, float fromX, float toX, float fromY, float toY){
       this(builder, fromX, toX, fromY, toY, 0f, 0f);
    }

    public Scale(Builder builder, float fromX, float toX, float fromY, float toY, float pivotX, float pivotY){
         this(builder, fromX, toX, fromY, toY, Animation.ABSOLUTE, pivotX, Animation.ABSOLUTE, pivotY);
    }


}
