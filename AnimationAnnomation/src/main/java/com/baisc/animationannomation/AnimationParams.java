package com.baisc.animationannomation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动画定义参数.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface AnimationParams {

    /**
     * duration.
     *
     * @return
     */
    long duration();

    /**
     * delayTime.
     *
     * @return
     */
    long delayTime() default 0;

    /**
     * startTime.
     *
     * @return
     */
    long startTime() default -1;

    /**
     * repeatCount.
     *
     * @return
     */
    int repeatCount() default 0;

    /**
     * repeatMode.
     *
     * default 1
     * #Animation.RESTART
     *
     *
     * @return repeatMode
     */
    int repeatMode() default 1;

    /**
     * fillAfter.
     *
     * @return
     */
    boolean fillAfter() default false;

    /**
     * fillBefore.
     *
     * @return
     */
    boolean fillBefore() default  false;
}
