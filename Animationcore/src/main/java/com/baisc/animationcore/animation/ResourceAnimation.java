package com.baisc.animationcore.animation;

import android.content.Context;
import android.view.animation.AnimationUtils;

import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * xml animation.
 */

public class ResourceAnimation extends ViewAnimation {

    public ResourceAnimation(Builder builder, Context context, int id) {
        super(builder);
        if (context != null && id > 0) {
            mAnimation = AnimationUtils.loadAnimation(context, id);
        }
    }

}
