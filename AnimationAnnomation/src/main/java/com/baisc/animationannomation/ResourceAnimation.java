package com.baisc.animationannomation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动画注解.
 * 用于标注是什么类型的动画.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ResourceAnimation {

    /**
     * 动画资源id.
     *
     * @return resourceId.
     */
    int animationId();

}
