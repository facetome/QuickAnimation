package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * ResourceAnimation.
 */

public class ResourceAnimation extends Annotation {

    public static final String ANNOTATION_NAME = com.baisc.animationannomation.ResourceAnimation.class.getName();

    public int resourceId;

    public ResourceAnimation(VariableElement mirror) {
        super(mirror);
        com.baisc.animationannomation.ResourceAnimation resourceAnimation = mirror.getAnnotation(com.baisc.animationannomation.ResourceAnimation.class);
        resourceId = resourceAnimation.animationId();
    }
}
