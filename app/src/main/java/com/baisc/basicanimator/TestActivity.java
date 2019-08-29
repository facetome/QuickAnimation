package com.baisc.basicanimator;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.baisc.animationannomation.Alpha;
import com.baisc.animationannomation.AnimationParams;
import com.baisc.animationannomation.Interpolators;
import com.baisc.animationannomation.Translate;
import com.baisc.animationannomation.TypeEvaluators;
import com.baisc.animationcore.BaseAnimation;
import com.baisc.animationcore.QuickAnimation;
import com.baisc.animationcore.QuickAnimation.Builder;
import com.baisc.animationcore.QuickAnimation.OnAnimationCallback;
import com.baisc.animationcore.animation.Rotate;

/**
 * Created by basic on 2019/7/7.
 */

public class TestActivity extends AppCompatActivity {


//    @TypeEvaluators(TestEvaluator.class)
//    @AnimationParams(duration = 1000, delayTime = 100, repeatCount = 10, repeatMode = 0)
//    @Alpha(fromAlpha = 1.0f, toAlpha = 0f)
//    @Interpolators(DecelerateInterpolator.class)
//    @Translate(fromY = 0f, fromX = 1f, toX = 0f, toY = 1f)
//    protected TextView mTextView;
//
//    @TypeEvaluators(TestEvaluator.class)
//    @Interpolators(DecelerateInterpolator.class)
//    @Alpha(fromAlpha = 1.0f, toAlpha = 0f)
//    protected TextView mTextView2;
//
//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

    }

    public void translate(View view) {
        QuickAnimation.with(this).fillAfter(true).repeatCount(2).repeatMode(QuickAnimation.REVERSE)
                .interpolator(new AccelerateDecelerateInterpolator())
                .delay(1000)
                .duration(1000)
                .astTranslate(0, 0, 500, 500).play(view);
    }

    public void rotate(View view) {
        QuickAnimation.with(this).fillAfter(true).repeatCount(2).repeatMode(QuickAnimation.REVERSE)
                .interpolator(new AccelerateDecelerateInterpolator())
                .delay(1000)
                .duration(1000)
                .callback(new OnAnimationCallback<Rotate>() {
                    @Override
                    public void onStart(Rotate animation) {

                    }

                    @Override
                    public void onEnd(Rotate animation) {

                    }

                    @Override
                    public void onCancel(Rotate animation) {

                    }

                    @Override
                    public void onRepeat(Rotate animaton) {

                    }
                })
                .asRotate(0, 360, view.getWidth()/2, view.getHeight() / 2).play(view);
    }

    public void alpha(View view) {

    }

    public void scale(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
