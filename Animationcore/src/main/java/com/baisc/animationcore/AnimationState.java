package com.baisc.animationcore;

/**
 * Created by basic on 2019/7/23.
 */

public interface AnimationState {

    boolean isRunning();

    void reset();

    void cancel();

    void destroy();
}
