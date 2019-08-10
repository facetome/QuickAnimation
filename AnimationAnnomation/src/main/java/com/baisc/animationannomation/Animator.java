package com.baisc.animationannomation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ObjectAnimator.ofFloat.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Animator {

    String property();

    float[] value();

    boolean autoCancel() default false;
}
