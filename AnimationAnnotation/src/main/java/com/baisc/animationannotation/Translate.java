package com.baisc.animationannotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * translate动画.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Translate {

    float fromX();

    float fromY();

    float toX();

    float toY();

    /**
     * Specifies how fromXValue should be interpreted. One of
     * ResourceAnimation.ABSOLUTE, ResourceAnimation.RELATIVE_TO_SELF, or
     * ResourceAnimation.RELATIVE_TO_PARENT.
     *
     * @return
     */
    int xType() default 0;

    /**
     * Specifies how fromXValue should be interpreted. One of
     * ResourceAnimation.ABSOLUTE, ResourceAnimation.RELATIVE_TO_SELF, or
     * ResourceAnimation.RELATIVE_TO_PARENT.
     *
     * @return
     */
    int yType() default 0;

}
