package com.baisc.animatorprocessor.core;

import com.baisc.animationannomation.AnimationParams;
import com.baisc.animationannomation.Interpolators;
import com.baisc.animationannomation.TypeEvaluators;
import com.baisc.animatorprocessor.Alpha;
import com.baisc.animatorprocessor.Animator;
import com.baisc.animatorprocessor.Animators;
import com.baisc.animatorprocessor.Annotation;
import com.baisc.animatorprocessor.Params;
import com.baisc.animatorprocessor.ResourceAnimation;
import com.baisc.animatorprocessor.Rotate;
import com.baisc.animatorprocessor.Scale;
import com.baisc.animatorprocessor.Translate;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;

/**
 * 将注解值转化为数据对象.
 */

public final class AnnotationConverter {

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
                    annotation = new Alpha(element);
                } else if (Translate.ANNOTATION_NAME.equals(annotationType)) {
                    annotation = new Translate(element);
                } else if (Scale.ANNOTATION_NAME.equals(annotationType)) {
                    annotation = new Scale(element);
                } else if (Rotate.ANNOTATION_NAME.equals(annotationType)) {
                    annotation = new Rotate(element);
                } else if (ResourceAnimation.ANNOTATION_NAME.equals(annotationType)) {
                    annotation = new ResourceAnimation(element);
                } else if (Animator.ANNOTATION_NAME.equals(annotationType)) {
                    annotation = new Animator(element);
                } else if (Animators.ANNOTATION_NAME.equals(annotationType)) {
                    annotation = new Animators(element);
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


}


