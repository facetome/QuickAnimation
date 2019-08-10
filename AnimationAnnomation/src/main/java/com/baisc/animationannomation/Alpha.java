package com.baisc.animationannomation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AlphaAnimation.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Alpha {

    /**
     * fromAlpha.
     *
     * @return
     */
    float fromAlpha();


    /**
     * toAlpha.
     *
     * @return
     */
    float toAlpha();

}
