package com.baisc.basicanimator;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.baisc.animationannomation.Animator;
import com.baisc.animationannomation.Animators;
import com.baisc.animationannomation.Interpolators;
import com.baisc.animationannomation.TypeEvaluators;
import com.baisc.animationcore.QuickAnimation;

/**
 * Created by basic on 2019/7/14.
 */

public class TestActivity1 extends TestActivity {

//    @AnimationParams(duration = 1000, fillAfter = true)
//    @Interpolators(DecelerateInterpolator.class)
//    @Translate(fromX = 10f, fromY = 20f, toY = 30f,toX = 40f)
//    TextView mTextView3;
//
//    @Rotate(fromDegree = 0, toDegree = 360)
//    @AnimationParams(duration = 1000, fillAfter = true)
//    ImageView mRotateImage;
//
//    @ResourceAnimation(animationId = R.anim.my_animation)
//    ImageView resourceImage;

    @TypeEvaluators(TestEvaluator.class)
    @Interpolators(DecelerateInterpolator.class)
    @Animators(value = {@Animator(property = "TranslateX", value = {1,3,3}, autoCancel = true),
            @Animator(property = "TranslateY", value = {1,3,3}, autoCancel = true)},playTogether = false)
//    @Animator(property = "Translate", value = {1,3,3}, autoCancel = true)
    TextView mAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnimatorSet set = new AnimatorSet();
//        QuickAnimation.with(this).fillAfter(true).asRotate();
//        QuickAnimation.with(this).asViewAnimation(true).addAnimations().play(mAnimator)

    }
}
