package com.baisc.animatorprocessor;

import javax.lang.model.element.VariableElement;

/**
 * ResourceAnimation.
 */

public class ResourceAnimation extends Annotation {

    public static final String ANNOTATION_NAME = com.baisc.animationannotation.ResourceAnimation.class.getName();

    public int resourceId;

    @Override
    public byte viewType() {
        return Type.TYPE_RESOURCE;
    }

    public ResourceAnimation(VariableElement mirror) {
        super(mirror);
        com.baisc.animationannotation.ResourceAnimation resourceAnimation = mirror.getAnnotation(com.baisc.animationannotation.ResourceAnimation.class);
        resourceId = resourceAnimation.animationId();
    }
}
