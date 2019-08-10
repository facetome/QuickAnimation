package com.baisc.basicanimator;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.baisc.animationannomation.Alpha;
import com.baisc.animationannomation.Interpolators;
import com.baisc.animationannomation.Translate;
import com.baisc.animationannomation.TypeEvaluators;
import com.baisc.animationcore.QuickAnimation;
import com.baisc.animationcore.QuickAnimation.Builder;

/**
 * Created by basic on 2019/7/7.
 */

public class TestActivity extends AppCompatActivity {


//    @Interpolators(DecelerateInterpolator.class)
//    @Translate(fromY = 0f, fromX = 1f, toX = 0f, toY = 1f)
//    protected TextView mTextView;
//
//    @Alpha(fromAlpha = 1.0f, toAlpha = 0f)
//    protected TextView mTextView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TestAnimationHepler.get().playLoginViewAnimation(this);
        Builder builder = QuickAnimation.with(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
