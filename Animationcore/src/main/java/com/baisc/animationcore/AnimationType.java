package com.baisc.animationcore;

/**
 * 动画枚举类.
 */

public enum AnimationType {


    Translate(QuickAnimation.TRANSLATE),

    Rotate(QuickAnimation.ROTATE),

    Scale(QuickAnimation.SCALE),

    Alpha(QuickAnimation.ALPHA),

    Resource(QuickAnimation.RESOURCE);


    private int mCode;

    public static final int CODE = 1;

    private AnimationType(int code) {
        mCode = code;
    }

    int getCode() {
        return mCode;
    }


}
