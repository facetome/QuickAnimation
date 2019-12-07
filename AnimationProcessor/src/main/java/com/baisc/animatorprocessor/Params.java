package com.baisc.animatorprocessor;

import com.baisc.animationannotation.AnimationParams;

/**
 * Created by basic on 2019/7/14.
 */

public class Params {

    public long duration;

    public long delayTime;

    public long startTime;

    public int repeatCount;

    public int repeatMode;

    public boolean fillAfter;

    public boolean fillBefore;

    public static Params convert(AnimationParams params) {
        if (params != null) {
            Params param = new Params();
            param.duration = params.duration();
            param.delayTime = params.delayTime();
            param.fillAfter = params.fillAfter();
            param.fillBefore = params.fillBefore();
            param.repeatCount = params.repeatCount();
            param.repeatMode = params.repeatMode();
            param.startTime = params.startTime();
            return param;
        }
        return null;
    }
}
