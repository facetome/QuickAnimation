package com.baisc.basicanimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.baisc.animationcore.BaseAnimation;
import com.baisc.animationcore.QuickAnimation;
import com.baisc.animationcore.QuickAnimation.Builder;
import com.baisc.animationcore.QuickAnimation.OnAnimationCallback;
import com.baisc.animationcore.animation.BaseObjectAnimator;
import com.baisc.animationcore.animation.Rotate;
import com.baisc.animationcore.animation.Scale;

/**
 * Created by basic on 2019/7/7.
 */

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

    }

    public void processor(View view) {
        Intent intent = new Intent(this, AnnotationTestActivity1.class);
        startActivity(intent);
    }

    public void translate(View view) {
        QuickAnimation.with(this)
                .fillAfter(false)
                .repeatCount(1)
                .repeatMode(QuickAnimation.REVERSE)
                .interpolator(new AccelerateDecelerateInterpolator())
                .delay(0)
                .duration(1000)
                .astTranslate(0, 0, 500, 500).play(view);
    }

    public void rotate(View view) {
        QuickAnimation.with(this)
                .fillAfter(true)
                .repeatCount(2)
                .repeatMode(QuickAnimation.REVERSE)
                .interpolator(new AccelerateDecelerateInterpolator())
                .delay(1000)
                .duration(1000)
                .callback(new OnAnimationCallback<Rotate>() {
                    @Override
                    public void onStart(Rotate animation) {
                        Log.d("basic", "onstart");
                    }

                    @Override
                    public void onEnd(Rotate animation) {
                        Log.d("basic", "onEnd");
                    }

                    @Override
                    public void onCancel(Rotate animation) {
                        Log.d("basic", "onCancel");
                    }

                    @Override
                    public void onRepeat(Rotate animaton) {
                        Log.d("basic", "onRepeat");
                    }
                })
                .asRotate(0, 360, QuickAnimation.ABOSOLUTE, view.getWidth() / 2, QuickAnimation.ABOSOLUTE, view.getHeight() / 2)
                .play(view);
    }

    public void alpha(View view) {
        QuickAnimation.with(this)
                .repeatMode(QuickAnimation.RESTART)
                .repeatCount(1)
                .duration(1000)
                .delay(100).interpolator(new AccelerateInterpolator())
                .fillBefore(true)
                .asAlpha(1, 0)
                .play(view);
    }

    public void scale(View view) {
        QuickAnimation.with(this)
                .repeatMode(QuickAnimation.REVERSE)
                .repeatCount(1).
                duration(1000)
                .delay(100)
                .interpolator(new AccelerateInterpolator())
                .asScale(1, 2, 1, 2, QuickAnimation.ABOSOLUTE, view.getWidth() / 2f, QuickAnimation.ABOSOLUTE, view.getHeight() / 2f)
                .play(view);
    }

    public void resource(View view) {
        QuickAnimation.with(this).asResourceAnimation(R.anim.my_animation).play(view);
    }

    public void animator(View view) {
        QuickAnimation.with(this)
                .repeatMode(QuickAnimation.REVERSE)
                .repeatCount(1)
                .duration(1000)
                .delay(500).interpolator(new AccelerateInterpolator())
                .callback(new OnAnimationCallback<BaseAnimation>() {
            @Override
            public void onStart(BaseAnimation animation) {
                Log.d("basic", "onstart");
            }

            @Override
            public void onEnd(BaseAnimation animation) {
                Log.d("basic", "onEnd");
            }

            @Override
            public void onCancel(BaseAnimation animation) {
                Log.d("basic", "onCancel");
            }

            @Override
            public void onRepeat(BaseAnimation animation) {
                Log.d("basic", "onRepeat");
            }
        })
                .asObjectAnimator("TranslationX").setFloatValues(0, 100, 200)
                .create()
                .play(view);
    }

    public void animators(View view) {
        Builder builder = QuickAnimation.with(this);
        builder.duration(1000)
                .delay(100)
                .fillBefore(true)
                .repeatMode(QuickAnimation.RESTART);

        BaseObjectAnimator translateX = builder.asObjectAnimator("TranslationX").setFloatValues(0, 100).create();
        BaseObjectAnimator translatey = builder.asObjectAnimator("TranslationY").setFloatValues(0, 100).create();
        BaseObjectAnimator aplha = builder.asObjectAnimator("Alpha").setFloatValues(1, 0.5f)
                .create();
        BaseObjectAnimator ScaleX = builder.asObjectAnimator("ScaleX").setFloatValues(1, 2).create();
        BaseObjectAnimator ScaleY = builder.asObjectAnimator("ScaleY").setFloatValues(1, 2).create();
        builder.asObjectAnimators().addAnimators(translateX).addAnimators(translatey)
                .addAnimators(ScaleX)
                .addAnimators(ScaleY)
                .addAnimators(aplha)
                .create()
                .play(view);

//        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "TranslationX", 0f, 100f);
//        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "TranslationY", 0f,100f);
//        AnimatorSet set = new AnimatorSet();
//        set.setDuration(1000);
//        set.setTarget(view);
//        set.setupStartValues();
//        set.playTogether(animatorX, animatorY);
//        set.start();
    }

    public void animations(View view) {
        Builder builder = QuickAnimation.with(this).fillBefore(true).repeatMode(QuickAnimation
                .RESTART).delay(100)
                .duration(1000);
        com.baisc.animationcore.animation.Translate translate = builder.astTranslate(0, 0, 100, 100);
        Scale scale = builder.asScale(1, 2, 1, 2, QuickAnimation.ABOSOLUTE, view.getWidth() / 2, QuickAnimation.ABOSOLUTE, view.getHeight() / 2);

        Rotate rotate = builder.asRotate(0, 360, QuickAnimation.ABOSOLUTE, view.getWidth() / 2f, QuickAnimation.ABOSOLUTE, view.getHeight() / 2f);
        builder.asViewAnimation(true)
                .addAnimations(translate)
                .addAnimations(rotate)
                .addAnimations(scale)
                .play(view);
    }

    public void fragment(View view){

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
