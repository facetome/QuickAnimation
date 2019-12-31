package com.baisc.animatorprocessor.core;

import com.baisc.animatorprocessor.Alpha;
import com.baisc.animatorprocessor.Animations;
import com.baisc.animatorprocessor.Animator;
import com.baisc.animatorprocessor.Animators;
import com.baisc.animatorprocessor.Annotation;
import com.baisc.animatorprocessor.Params;
import com.baisc.animatorprocessor.ResourceAnimation;
import com.baisc.animatorprocessor.Rotate;
import com.baisc.animatorprocessor.Scale;
import com.baisc.animatorprocessor.Translate;
import com.baisc.animatorprocessor.exception.AnnotationTypeException;
import com.baisc.animatorprocessor.utils.ClassUtils;
import com.baisc.animatorprocessor.utils.TextUtils;
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
    private static final String BASE_PACKAGE_PATH = "com.baisc.animationcore.";
    private static final String BASE_ANIMATION_PATH = BASE_PACKAGE_PATH + "animation.";


    @Override
    public boolean generate(String clazz, Map<String, List<Annotation>> annotations,
            ProcessingEnvironment environment) {
        TypeSpec.Builder classSpecBuilder = createClass(clazz);
        Iterator<Entry<String, List<Annotation>>> iterator = annotations.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, List<Annotation>> entry = iterator.next();
            MethodSpec method = convertAnnotationMethod(clazz, entry.getKey(), entry.getValue());
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
        return TypeSpec.classBuilder(simpleClassName + GENERATOR_SUFFIX)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }


    private MethodSpec convertAnnotationMethod(String clazz, String viewName, List<Annotation> annotations) {
        if (annotations != null && !annotations.isEmpty()) {
            Annotation annotation;
            MethodCreator creator;
            if (annotations.size() > 1) { // 同一个view上面注解了多个动画注解器.
                if (AnnotationChecker.checkAnimation(annotations)) {
                    annotation = Animations.merge(annotations);
                    creator = new AnimationSet();
                } else {
                    //属性动画和view动画不能一起使用.
                    throw new AnnotationTypeException("a view element can be only bind one animation type");
                }
            } else {
                annotation = annotations.get(0);
                creator = createMethodCreator(annotation);
            }
            if (creator != null) {
                return new MethodCreatorProxy(creator).createMethod(clazz, viewName, annotation);

            }
        }
        return null;
    }

    private static MethodCreator createMethodCreator(Annotation annotation) {
        if (annotation != null) {
            MethodCreator creator = null;
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
            } else if (Animators.ANNOTATION_NAME.equals(animationClass)) {
                creator = new AnimatorSet();
            }
            return creator;
        }
        return null;
    }


    interface MethodCreator {
        MethodSpec createMethod(String clazzName, String viewName, Annotation annotation);
    }

    private class MethodCreatorProxy implements MethodCreator {

        MethodCreator mMethodCreator;

        MethodCreatorProxy(MethodCreator creator) {
            this.mMethodCreator = creator;
        }

        @Override
        public MethodSpec createMethod(String clazz, String viewName, Annotation annotation) {
            return mMethodCreator.createMethod(clazz, viewName, annotation);
        }

    }

    private static String formatMethodName(String viewName, String suffix) {
        viewName = viewName.substring(0, 1).toUpperCase() + viewName.substring(1);
        return String.format("play%1$s%2$s", viewName, suffix);
    }

    private static abstract class AnimationMethodCreator implements MethodCreator {
        @Override
        public MethodSpec createMethod(String clazz, String viewName, Annotation annotation) {
            Builder builder = createMethodModifier(clazz, formatMethodName(viewName, animationType()));
            createMethodContentEnter(builder);
            createMethodContentWithAnimationParams(builder, annotation);
            createMethodContentExit(builder, viewName);
            return builder.build();
        }

        protected Builder createMethodModifier(String clazzName, String methodName) {
            TypeVariableName targetVariable = TypeVariableName.get("T");
            String returnTypeClazz = BASE_PACKAGE_PATH + "AnimationState";
            ClassName parent = ClassName.get(ClassUtils.getPackageName(clazzName), ClassUtils.getClassSimpleName(clazzName));
            TypeVariableName clazzTypeVariable = TypeVariableName.get("T", parent);
            return MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC, Modifier.STATIC)
                    .returns(ClassName.get(ClassUtils.getPackageName(returnTypeClazz), ClassUtils.getClassSimpleName(returnTypeClazz)))
                    .addParameter(targetVariable, "target")
                    .addTypeVariable(clazzTypeVariable);
        }

        protected abstract String animationType();

        protected void createMethodContentEnter(Builder builder) {
            String animationName = BASE_PACKAGE_PATH + "QuickAnimation";
            String builderName = animationName + ".Builder";
            builder.addStatement("$T builder = $T.with($L)",
                    ClassName.get(ClassUtils.getPackageName(builderName), ClassUtils.getClassSimpleName(builderName)),
                    ClassName.get(ClassUtils.getPackageName(animationName), ClassUtils.getClassSimpleName(animationName)),
                    "target");
        }

        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            Params params = annotation.mParams;
            if (params != null) {
                builder.addStatement("builder.duration($L)\n.delay($L)\n.repeatCount($L)\n" +
                                ".repeatMode($L)\n.fillAfter($L)\n.fillBefore($L)",
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
                builder.addStatement("builder.interpolator(new $T())", interpolator);
            }
            if (!TextUtils.isEmpty(annotation.mEvaluatorClass)) {
                ClassName evaluator = ClassName.get(ClassUtils.getPackageName(annotation.mEvaluatorClass),
                        ClassUtils.getClassSimpleName(annotation.mEvaluatorClass));
                builder.addStatement("builder.evaluator(new $T())", evaluator);
            }
            return builder;
        }

        protected void createMethodContentExit(Builder builder, String targetView) {
            builder.addStatement("return $L.play(target.$L)", animationType().toLowerCase(), targetView);
        }
    }

    private static class TranslateCreator extends AnimationMethodCreator {


        @Override
        protected String animationType() {
            return "Translate";
        }

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, annotation);
            Translate translate = (Translate) annotation;

            String translatePath = BASE_ANIMATION_PATH + "Translate";
            builder.addStatement("$T $L = builder.asTranslate($Lf, $Lf,  $Lf,$Lf)",
                    ClassName.get(ClassUtils.getPackageName(translatePath),
                            ClassUtils.getClassSimpleName(translatePath)),
                    animationType().toLowerCase(),
                    translate.fromX, translate.fromY, translate.toX, translate.toY);
            return builder;
        }
    }


    private static class AlphaCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, annotation);
            Alpha alpha = (Alpha) annotation;
            String alphaPath = BASE_ANIMATION_PATH + "Alpha";
            builder.addStatement("$T $L = builder.asAlpha($Lf, $Lf)",
                    ClassName.get(ClassUtils.getPackageName(alphaPath), ClassUtils
                            .getClassSimpleName(alphaPath)),
                    animationType().toLowerCase(),
                    alpha.fromAlpha, alpha.toAlpha);
            return builder;
        }

        @Override
        protected String animationType() {
            return "Alpha";
        }
    }

    private static class RotateCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, annotation);
            String rotatePath = BASE_ANIMATION_PATH + "Rotate";
            Rotate rotate = (Rotate) annotation;
            builder.addStatement("$T $L = builder.asRotate($Lf, $Lf, $L,$Lf, $L, $Lf)",
                    ClassName.get(ClassUtils.getPackageName(rotatePath), ClassUtils
                            .getClassSimpleName(rotatePath)),
                    animationType().toLowerCase(),
                    rotate.fromDegree, rotate.toDegree, rotate.pivotYType, rotate.pivotXValue, rotate.pivotYType, rotate.pivotYValue);
            return builder;
        }

        @Override
        protected String animationType() {
            return "Rotate";
        }
    }

    private static class ScaleCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, annotation);
            Scale scale = (Scale) annotation;
            String scalePath = BASE_ANIMATION_PATH + "Scale";
            builder.addStatement("$T $L = builder.asScale($Lf, $Lf, $Lf, $Lf, $L, $Lf, $L,$Lf)",
                    ClassName.get(ClassUtils.getPackageName(scalePath),
                            ClassUtils.getClassSimpleName(scalePath)),
                    animationType().toLowerCase(),
                    scale.fromX, scale.toX, scale.fromY, scale.toX, scale.xType, scale.pivotXValue, scale.yType, scale.pivotYValue);
            return builder;
        }


        @Override
        protected String animationType() {
            return "Scale";
        }
    }

    private static class ResourceAnimationCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, annotation);
            ResourceAnimation animation = (ResourceAnimation) annotation;
            String resourcePath = BASE_ANIMATION_PATH + "ResourceAnimation";
            builder.addStatement("$T $L = builder.asResourceAnimation($L)",
                    ClassName.get(ClassUtils.getPackageName(resourcePath),
                            ClassUtils.getClassSimpleName(resourcePath)),
                    animationType().toLowerCase(),
                    animation.resourceId);
            return builder;
        }

        @Override
        protected String animationType() {
            return "Resource";
        }
    }

    private static class AnimatorCreator extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, annotation);
            Animator animator = (Animator) annotation;
            float[] values = animator.values;
            List<Float> formatValue = new ArrayList<>();
            for (float value : values) {
                formatValue.add(value);
            }
            String animatorPath = BASE_ANIMATION_PATH + "BaseObjectAnimator";
            builder.addStatement("$T $L = builder.asObjectAnimator($S).setAutoCancel($L).setFloatValues($L).create()",
                    ClassName.get(ClassUtils.getPackageName(animatorPath),
                            ClassUtils.getClassSimpleName(animatorPath)),
                    animationType().toLowerCase(),
                    animator.property,
                    animator.autoCancel,
                    TextUtils.join(",", formatValue.toArray(new Float[formatValue.size()]), "f"));
            return builder;
        }


        @Override
        protected String animationType() {
            return "Animator";
        }
    }

    private static class AnimatorSet extends AnimationMethodCreator {


        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            super.createMethodContentWithAnimationParams(builder, annotation);
            String objectPath = BASE_ANIMATION_PATH + "BaseObjectAnimator";
            ClassName receiverAnimator = ClassName.get(ClassUtils.getPackageName(objectPath),
                    ClassUtils.getClassSimpleName(objectPath));
            Animators animators = (Animators) annotation;
            int currentIndex = 0;
            for (Animator animator : animators.getAnimators()) {
                float[] values = animator.values;
                List<Float> formatValue = new ArrayList<>();
                for (float value : values) {
                    formatValue.add(value);
                }
                builder.addStatement("$T animator$L = builder.asObjectAnimator($S).setAutoCancel($L).setFloatValues($L).create()",
                        receiverAnimator,
                        currentIndex,
                        animator.property,
                        animator.autoCancel,
                        TextUtils.join(",", formatValue.toArray(new Float[formatValue.size()]), "f"));
                currentIndex++;
            }
            String animatorSetPath = BASE_ANIMATION_PATH + "AnimatorSet";
            String animatorSetBuilderPath = animatorSetPath + ".Builder";
            builder.addStatement("$T animatorBuilder = builder.asObjectAnimators()",
                    ClassName.get(ClassUtils.getPackageName(animatorSetBuilderPath), ClassUtils.getClassSimpleName(animatorSetBuilderPath)));
            for (int i = 0; i < animators.getAnimators().size(); i++) {
                builder.addStatement("animatorBuilder.addAnimators(animator$L)", i);
            }
            builder.addStatement("$T $L = (AnimatorSet)animatorBuilder.playTogether($L).create()",
                    ClassName.get(ClassUtils.getPackageName(animatorSetPath), ClassUtils.getClassSimpleName(animatorSetPath)),
                    animationType().toLowerCase(),
                    animators.isPlayTogether());
            return builder;
        }

        @Override
        protected String animationType() {
            return "Animators";
        }
    }

    private static class AnimationSet extends AnimationMethodCreator {

        @Override
        protected Builder createMethodContentWithAnimationParams(Builder builder, Annotation annotation) {
            Animations animations = (Animations) annotation;
            Annotation child = animations.getChild(0);
            if (child != null) {
                animations.mParams = child.mParams;
            }
            super.createMethodContentWithAnimationParams(builder, annotation);
            String animationsPath = BASE_ANIMATION_PATH + "AnimationSet";
            ClassName setReceiver = ClassName.get(ClassUtils.getPackageName(animationsPath),
                    ClassUtils.getClassSimpleName(animationsPath));
            builder.addStatement("$T $L = builder.asViewAnimation(true)", setReceiver, animationType().toLowerCase());

            for (Annotation animation : animations.getChilds()) {
                animation.mParams = null; //使用父类
                AnimationMethodCreator creator = (AnimationMethodCreator) createMethodCreator(animation);
                if (creator != null) {
                    creator.createMethodContentWithAnimationParams(builder, animation);
                    builder.addStatement("$L.addAnimations($L)", animationType().toLowerCase(),
                            creator.animationType().toLowerCase());
                }
            }
            return builder;
        }

        @Override
        protected String animationType() {
            return "Animations";
        }
    }


}
