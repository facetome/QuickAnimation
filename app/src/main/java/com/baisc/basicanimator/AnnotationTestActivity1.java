package com.baisc.basicanimator;

import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.baisc.animationannotation.Alpha;
import com.baisc.animationannotation.AnimationParams;
import com.baisc.animationannotation.Animator;
import com.baisc.animationannotation.Animators;
import com.baisc.animationannotation.Interpolator;
import com.baisc.animationannotation.ResourceAnimation;
import com.baisc.animationannotation.Rotate;
import com.baisc.animationannotation.Scale;
import com.baisc.animationannotation.Translate;
import com.baisc.animationannotation.TypeEvaluator;

import androidx.annotation.Nullable;

/**
 * Created by basic on 2019/7/14.
 */

public class AnnotationTestActivity1 extends TestActivity {

    @Interpolator(DecelerateInterpolator.class)
    @AnimationParams(duration = 1000, delayTime = 100)
    @Translate(fromX = 0, toX = 100, fromY = 0, toY = 100)
    @Alpha(fromAlpha = 1f, toAlpha = 0.5f)
    TextView translate;

    @Rotate(fromDegree = 0, toDegree = 360f, pivotXType = 1, pivotYType = 1, pivotXValue = 0.5f, pivotYValue = 0.5f)
    @AnimationParams(duration = 1000, delayTime = 100)
    TextView rotate;

    @AnimationParams(duration = 1000, delayTime = 100)
    @Alpha(fromAlpha = 1f, toAlpha = 0.5f)
    TextView alpha;

    @AnimationParams(duration = 1000, delayTime = 100)
    @Scale(fromX = 1, toX = 2, fromY = 1, toY = 2, pivotXType = 1, pivotXValue = 0.5f, pivotYType = 1,pivotYValue = 0.5f)
    TextView scale;

    @ResourceAnimation(animationId = R.anim.my_animation)
    TextView resource;

    @AnimationParams(duration = 1000, delayTime = 100)
    @Animator(property = "TranslationX", value = {0, 100})
    TextView animator;

    @AnimationParams(duration = 1000, delayTime = 100)
    @TypeEvaluator(TestEvaluator.class)
    @Interpolator(DecelerateInterpolator.class)
    @Animators(value = {@Animator(property = "TranslationX", value = {0, 100}, autoCancel = true),
            @Animator(property = "TranslationY", value = {0, 100}, autoCancel = true)}, playTogether = true)
    TextView animators;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1_layout);
        translate = findViewById(R.id.translate);
        rotate = findViewById(R.id.rotate);
        alpha = findViewById(R.id.alpha);
        scale = findViewById(R.id.scale);
        resource = findViewById(R.id.resource);
        animator = findViewById(R.id.animator);
        animators = findViewById(R.id.animators);
    }

    public void translate(View view) {
        AnnotationTestActivity1_Generator.playTranslateAnimations(this);
    }

    public void rotate(View view) {
        AnnotationTestActivity1_Generator.playRotateRotate(this);
    }

    public void alpha(View view) {
        AnnotationTestActivity1_Generator.playAlphaAlpha(this);
    }

    public void scale_click(View view) {
        AnnotationTestActivity1_Generator.playScaleScale(this);
    }

    public void resource(View view){
        AnnotationTestActivity1_Generator.playResourceResource(this);
    }

    public void animator(View view){
        AnnotationTestActivity1_Generator.playAnimatorAnimator(this);
    }

    public void animators(View view){
        AnnotationTestActivity1_Generator.playAnimatorsAnimators(this);
    }

}
