package com.baisc.animationannotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RotateAnimation.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Rotate {

    float fromDegree();

    float toDegree();

    /**
     * Animation.ABSOLUTE default.
     *
     * @return
     */
    int pivotXType() default 0;

    /**
     * Animation.ABSOLUTE default.
     * @return
     */
    int pivotYType() default 0;

    float pivotXValue() default 0f;

    float pivotYValue() default 0f;
}
