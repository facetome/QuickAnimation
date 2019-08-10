package com.baisc.animatorprocessor.core;

import com.baisc.animatorprocessor.Alpha;
import com.baisc.animatorprocessor.Animator;
import com.baisc.animatorprocessor.Animators;
import com.baisc.animatorprocessor.Annotation;
import com.baisc.animatorprocessor.Params;
import com.baisc.animatorprocessor.ResourceAnimation;
import com.baisc.animatorprocessor.Rotate;
import com.baisc.animatorprocessor.Scale;
import com.baisc.animatorprocessor.Translate;
import com.baisc.animatorprocessor.utils.ClassUtils;
import com.baisc.animatorprocessor.utils.TextUtils;
import com.baisc.animatorprocessor.utils.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

/**
 * java代码生成器.
 */

public class JavaPoetGenerator implements GenerateCode {

    private static final String GENERATOR_SUFFIX = "_Generator";


    @Override
    public boolean generate(String clazz, Map<String, List<Annotation>> annotations,
            ProcessingEnvironment environment) {
        TypeSpec.Builder classSpecBuilder = createClass(clazz);
        Iterator<Entry<String, List<Annotation>>> iterator = annotations.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, List<Annotation>> entry = iterator.next();
            MethodSpec method = createMethod(entry.getKey(), entry.getValue());
            if (method != null) {
                classSpecBuilder.addMethod(method);
            }
        }
        String packName = ClassUtils.getPackageName(clazz);
        JavaFile javaFile = JavaFile.builder(packName, classSpecBuilder.build()).build();
        try {
            javaFile.writeTo(environment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private TypeSpec.Builder createClass(String className) {
        if (TextUtils.isEmpty(className)) {
            throw new IllegalArgumentException("className is not allowed null or empty");
        }
        String simpleClassName = ClassUtils.getClassSimpleName(className);
        ClassName parent = ClassName.get(ClassUtils.getPackageName(className), simpleClassName);
        TypeVariableName t = TypeVariableName.get("T", parent);
        return TypeSpec.classBuilder(simpleClassName + GENERATOR_SUFFIX)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addTypeVariable(t);
    }


    private MethodSpec createMethod(String viewName, List<Annotation> annotations) {
        if (annotations != null && !annotations.isEmpty()) {
            MethodCreator creator = null;
            if (annotations.size() > 1) { //代表这个是AnimationSet 或者AnimatorSet
                if (Utils.isTargetAnimation(annotations, Animator.ANNOTATION_NAME)
                        || !Utils.isExitAnimation(annotations, Animator.ANNOTATION_NAME)) {
                    if (Utils.isTargetAnimation(annotations, Animator.ANNOTATION_NAME)) {
                        creator = new AnimatorSet();
                    } else {
                        creator = new AnimationSet();
                    }
                } else {
                    throw new IllegalStateException("@Animator can't be used with animation " + "together");
                }

            } else {
                Annotation annotation = annotations.get(0);
                if (annotation != null) {
                    String animationClass = annotation.mAnnotationClass.getName();
                    if (Translate.ANNOTATION_NAME.equals(animationClass)) {
                        creator = new TranslateCreator();
                    } else if (Alpha.ANNOTATION_NAME.equals(animationClass)) {
                        creator = new AlphaCreator();
                    } else if (Rotate.ANNOTATION_NAME.equals(animationClass)) {
                        creator = new RotateCreator();
                    } else if (Scale.ANNOTATION_NAME.equals(animationClass)) {
                        creator = new ScaleCreator();
                    } else if (ResourceAnimation.ANNOTATION_NAME.equals(animationClass)) {
                        creator = new ResourceAnimationCreator();
                    } else if (Animator.ANNOTATION_NAME.equals(animationClass)) {
                        creator = new AnimatorCreator();
                    } else if (Animators.ANNOTATION_NAME.equals(animationClass)){
                        creator = new AnimatorSet();
                    }
                }
            }
            if (creator != null) {
                return new MethodCreatorProxy(creator).createMethod(viewName, annotations);

            }
        }
        return null;
    }


    interface MethodCreator {
        MethodSpec createMethod(String viewName, List<Annotation> annotations);
    }

    private class MethodCreatorProxy implements MethodCreator {

        MethodCreator mMethodCreator;

        MethodCreatorProxy(MethodCreator creator) {
            this.mMethodCreator = creator;
        }

        @Override
        public MethodSpec createMethod(String viewName, List<Annotation> annotations) {
            return mMethodCreator.createMethod(viewName, annotations);
        }

    }

    private String formatMethodName(String viewName, String suffix) {
        viewName = viewName.substring(0, 1).toUpperCase() + viewName.substring(1);
        return String.format("play%1$s%2$s", viewName, suffix);
    }

    private abstract class AnimationMethodCreator implements MethodCreator {
        @Override
        public MethodSpec createMethod(String viewName, List<Annotation> annotations) {
            Builder builder = createMethodModifier(formatMethodName(viewName, animationType()));
            beforeMethodCreate(builder, viewName, annotations);
            int index = 0;
            for (Annotation annotation : annotations) {
                createMethodContent(builder, index);
                createMethodContentWithAnimationParams(builder, index, viewName, annotation);
                index++;
            }
            afterMethodCreate(builder, viewName, annotations);
            return builder.build();
        }

        protected void afterMethodCreate(Builder builder, String targetView, List<Annotation> annotations) {

        }

        protected void beforeMethodCreate(Builder builder, String targetView, List<Annotation> annotations) {

        }

        protected Builder createMethodModifier(String methodName) {
            TypeVariableName t = TypeVariableName.get("T");
            return MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(t, "target");
        }

        protected abstract String animationType();

        protected Builder createMethodContent(Builder builder, int index) {
            String animationName = "com.baisc.animationcore.QuickAnimation";
            String builderName = animationName + ".Builder";
            builder.addStatement("$T builder$L = $T.with($L)",
                    ClassName.get(ClassUtils.getPackageName(builderName), ClassUtils.getClassSimpleName(builderName)),
                    index,
                    ClassName.get(ClassUtils.getPackageName(animationName), ClassUtils.getClassSimpleName(animationName)),
                    "target");
            return builder;
        }

        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String targetView, Annotation annotation) {
            Params params = annotation.mParams;
            if (params != null) {
                builder.addStatement("builder$L.duration($L)\n.delay($L)\n.repeatCount($L)\n.repeatMode($L)\n.fillAfter($L)\n.fillBefore($L)",
                        index,
                        params.duration,
                        params.delayTime,
                        params.repeatCount,
                        params.repeatMode,
                        params.fillAfter,
                        params.fillBefore);
            }
            if (!TextUtils.isEmpty(annotation.mInterpolatorClass)) {
                ClassName interpolator = ClassName.get(ClassUtils.getPackageName(annotation.mInterpolatorClass),
                        ClassUtils.getClassSimpleName(annotation.mInterpolatorClass));
                builder.addStatement("builder$L.interpolator(new $T())", index, interpolator);
            }
            return builder;
        }


    }

    private class TranslateCreator extends AnimationMethodCreator {


        @Override
        protected String animationType() {
            return "Translate";
        }

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String targetView, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, index, targetView, annotation);
            Translate translate = (Translate) annotation;
            builder.addStatement("builder$L.astTranslate($Lf, $Lf,  $Lf,$Lf).play(target.$L)",
                    index, translate.fromX, translate.fromY, translate.toX, translate.toY, targetView);
            return builder;
        }
    }


    private class AlphaCreator extends AnimationMethodCreator {


        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String targetView, Annotation annotation) {
            Alpha alpha = (Alpha) annotation;
            builder.addStatement("builder$L.asAlpha($Lf, $Lf).play(target.$L)", index, alpha.fromAlpha, alpha.toAlpha, targetView);
            return builder;
        }

        @Override
        protected String animationType() {
            return "Alpha";
        }
    }

    private class RotateCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String targetView, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, index, targetView, annotation);
            Rotate rotate = (Rotate) annotation;
            builder.addStatement("builder$L.asRotate($Lf, $Lf, $Lf, $Lf).play(target.$L)",
                    index, rotate.fromDegree, rotate.toDegree, rotate.pivotXValue, rotate.pivotYValue, targetView);
            return builder;
        }

        @Override
        protected String animationType() {
            return "Rotate";
        }
    }

    private class ScaleCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String targetView, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, index, targetView, annotation);
            Scale scale = (Scale) annotation;
            builder.addStatement("builder$L.asScale($Lf, $Lf, $Lf, $Lf).play(target.$L)",
                    index, scale.fromX, scale.toX, scale.fromY, scale.toX);
            return builder;
        }

        @Override
        protected String animationType() {
            return "Scale";
        }
    }

    private class ResourceAnimationCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String
                targetView, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, index, targetView, annotation);
            ResourceAnimation animation = (ResourceAnimation) annotation;
            builder.addStatement("builder$L.asResourceAnimation($L).play(target.$L)",
                    index, animation.resourceId, targetView);
            return builder;
        }

        @Override
        protected String animationType() {
            return "ResourceAnimation";
        }
    }

    private class AnimatorCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String targetView, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, index, targetView, annotation);
            Animator animator = (Animator) annotation;
            float[] values = animator.values;
            List<Float> formatValue = new ArrayList<>();
            for (float value : values) {
                formatValue.add(value);
            }
            builder.addStatement("builder$L.asObjectAnimator($S).setAutoCancel($L).setFloatValues($L).create().play(target.$L)",
                    index,
                    animator.property,
                    animator.autoCancel,
                    TextUtils.join(",", formatValue.toArray(new Float[formatValue.size()]), "f"), targetView);
            return builder;
        }


        @Override
        protected String animationType() {
            return "Animator";
        }
    }

    private class AnimatorSet extends AnimationMethodCreator {


        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, int index, String targetView, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, index, targetView, annotation);
            ClassName receiverAnimator = ClassName.get(ClassUtils.getPackageName("com.baisc.animationcore.animation.BaseObjectAnimator"),
                    ClassUtils.getClassSimpleName("com.baisc.animationcore.animation.BaseObjectAnimator"));
            Animators animators = (Animators) annotation;
            int currentIndex=0;
            for (Animator animator : animators.getAnimators()){
                float[] values = animator.values;
                List<Float> formatValue = new ArrayList<>();
                for (float value : values) {
                    formatValue.add(value);
                }
                builder.addStatement("$T animator$L = builder$L.asObjectAnimator($S).setAutoCancel($L).setFloatValues($L).create()",
                        receiverAnimator,
                        currentIndex,
                        index,
                        animator.property,
                        animator.autoCancel,
                        TextUtils.join(",", formatValue.toArray(new Float[formatValue.size()]), "f"));
                currentIndex++;
            }
            String quickName = "com.baisc.animationcore.QuickAnimation";
            String animatorName = "com.baisc.animationcore.animation.AnimatorSet.Builder";
            builder.addStatement("$T animatorBuilder = $T.with($L).asObjectAnimators()",
                    ClassName.get(ClassUtils.getPackageName(animatorName), ClassUtils.getClassSimpleName(animatorName)),
                    ClassName.get(ClassUtils.getPackageName(quickName), ClassUtils.getClassSimpleName(quickName)), "target");
            for (int i = 0; i < animators.getAnimators().size(); i++) {
                builder.addStatement("animatorBuilder.addAnimators(animator$L)", i);
            }
            builder.addStatement("animatorBuilder.playTogether($L).create().play(target.$L)", animators.isPlayTogether(), targetView);
            return builder;
        }

        @Override
        protected void afterMethodCreate(Builder builder, String targetView, List<Annotation> annotations) {
            super.afterMethodCreate(builder, targetView, annotations);

        }

        @Override
        public MethodSpec createMethod(String viewName, List<Annotation> annotations) {
            return super.createMethod(viewName, annotations);
        }

        @Override
        protected String animationType() {
            return "Animators";
        }
    }

    private class AnimationSet extends AnimationMethodCreator {


        @Override
        protected String animationType() {
            return "Animation";
        }
    }


}
