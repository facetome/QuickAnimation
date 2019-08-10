package com.baisc.animatorprocessor;

import com.baisc.animationannomation.AnimationParams;
import com.baisc.animationannomation.Interpolators;
import com.baisc.animationannomation.TypeEvaluators;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;

/**
 * 拍平后的动画参数.
 */

public class Annotation {
    public Class<?> mAnnotationClass;

    public String mInterpolatorClass;

    public String mEvaluatorClass;

    public Params mParams;

    private VariableElement mVariableElement;


    public Annotation(VariableElement mirror) {
        mVariableElement = mirror;
    }


    public static List<Annotation> convert(Messager messager, VariableElement element) {

        List<Annotation> annotations = new ArrayList<>();
        List<AnnotationMirror> mirrors = (List<AnnotationMirror>) element.getAnnotationMirrors();
        if (mirrors != null && !mirrors.isEmpty()) {
            AnimationParams animationParams = element.getAnnotation(AnimationParams.class);
            Params params = Params.convert(animationParams);
            Interpolators interpolators = element.getAnnotation(Interpolators.class);

            String interpolatorClass = null;
            if (interpolators != null) {
                try {
                    interpolators.value();
                } catch (MirroredTypeException e) {
                    interpolatorClass = e.getTypeMirror().toString();
                }
            }
            TypeEvaluators evaluators = element.getAnnotation(TypeEvaluators.class);
            String evalutorClass = null;
            if (evaluators != null) {
                try {
                    evaluators.value();
                } catch (MirroredTypeException e) {
                    evalutorClass = e.getTypeMirror().toString();
                }
            }
            for (AnnotationMirror mirror : mirrors) {
//                 messager.printMessage(Kind.NOTE, "element: "+ element.getSimpleName().toString
//                         () + "    annomation: " + mirror.getAnnotationType().toString());
                Annotation annotation = null;
                String annotationType = mirror.getAnnotationType().toString();

                if (Alpha.ANNOTATION_NAME.equals(annotationType)) {
                    Alpha alphaValue = new Alpha(element);
                    com.baisc.animationannomation.Alpha alpha = element.getAnnotation(com.baisc.animationannomation.Alpha.class);
                    alphaValue.fromAlpha = alpha.fromAlpha();
                    alphaValue.toAlpha = alpha.toAlpha();
                    annotation = alphaValue;
                } else if (Translate.ANNOTATION_NAME.equals(annotationType)) {
                    com.baisc.animationannomation.Translate translate = element.getAnnotation(com.baisc.animationannomation.Translate.class);
                    Translate translateValue = new Translate(element);
                    translateValue.fromX = translate.fromX();
                    translateValue.fromY = translate.fromY();
                    translateValue.toX = translate.toX();
                    translateValue.toY = translate.toY();
                    translateValue.xType = translate.xType();
                    translateValue.yType = translate.yType();
                    annotation = translateValue;
                } else if (Scale.ANNOTATION_NAME.equals(annotationType)) {
                    com.baisc.animationannomation.Scale scale = element.getAnnotation(com.baisc.animationannomation.Scale.class);
                    Scale scaleValue = new Scale(element);
                    scaleValue.fromX = scale.fromX();
                    scaleValue.fromY = scale.fromY();
                    scaleValue.toX = scale.toX();
                    scaleValue.toY = scale.toY();
                    scaleValue.pivotXType = scale.pivotXType();
                    scaleValue.pivotYType = scale.pivotYType();
                    scaleValue.pivotXValue = scale.pivotXValue();
                    scaleValue.pivotYValue = scale.pivotYValue();
                    annotation = scaleValue;
                } else if (Rotate.ANNOTATION_NAME.equals(annotationType)) {
                    com.baisc.animationannomation.Rotate rotate = element.getAnnotation(com.baisc.animationannomation.Rotate.class);
                    Rotate rotateValue = new Rotate(element);
                    rotateValue.fromDegree = rotate.fromDegree();
                    rotateValue.toDegree = rotate.toDegree();
                    rotateValue.pivotXType = rotate.pivotXType();
                    rotateValue.pivotYType = rotate.pivotYType();
                    rotateValue.pivotXValue = rotate.pivotXValue();
                    rotateValue.pivotYValue = rotate.pivotYValue();
                    annotation = rotateValue;
                } else if (ResourceAnimation.ANNOTATION_NAME.equals(annotationType)) {
                    com.baisc.animationannomation.ResourceAnimation resourceAnimation = element.getAnnotation(com.baisc.animationannomation.ResourceAnimation.class);
                    ResourceAnimation resourceValue = new ResourceAnimation(element);
                    resourceValue.resourceId = resourceAnimation.animationId();
                    annotation = resourceValue;
                } else if (Animator.ANNOTATION_NAME.equals(annotationType)) {
                    com.baisc.animationannomation.Animator animator = element.getAnnotation(com.baisc.animationannomation.Animator.class);
                    annotation = bindAnimator(animator, element);
                } else if (Animators.ANNOTATION_NAME.equals(annotationType)) {
                    com.baisc.animationannomation.Animators animatorsAnnotation = element.getAnnotation(com.baisc.animationannomation.Animators.class);
                    com.baisc.animationannomation.Animator[] animatorArray = animatorsAnnotation.value();
                    com.baisc.animatorprocessor.Animators  animators = new com.baisc.animatorprocessor.Animators(element);
                    animators.setPlayTogether(animatorsAnnotation.playTogether());
                    for (com.baisc.animationannomation.Animator source : animatorArray) {
                        Animator animator = bindAnimator(source, element);
                        animators.addAnimator(animator);
                    }
                    annotation = animators;
                }
                if (annotation != null) {
                    annotations.add(packageExtra(annotation, params, annotationType, interpolatorClass, evalutorClass));
                }

            }
        }


        return annotations;
    }

    private static Annotation packageExtra(Annotation annotation, Params params, String annotationType, String interpolatorClass, String evalutorClass) {
        annotation.mParams = params;
        try {
            annotation.mAnnotationClass = Class.forName(annotationType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        annotation.mInterpolatorClass = interpolatorClass;
        annotation.mEvaluatorClass = evalutorClass;
        return annotation;
    }

    private static Animator bindAnimator(com.baisc.animationannomation.Animator source,
            VariableElement element) {
        Animator animatorValue = new Animator(element);
        animatorValue.property = source.property();
        animatorValue.values = source.value();
        animatorValue.autoCancel = source.autoCancel();
        return animatorValue;
    }

}
