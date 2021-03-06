package com.baisc.animationannotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Animator setEvaluator.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface TypeEvaluator {

    Class<?> value();
}
