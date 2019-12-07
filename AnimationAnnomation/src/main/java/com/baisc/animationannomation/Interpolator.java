package com.baisc.animationannomation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加速器.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Interpolator {

    /**
     * 用于标记插值器名称，可以是系统插值器也可以是自定义插值器.
     *
     * @return 插值器
     */
    Class<?> value();
}
