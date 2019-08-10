package com.baisc.basicanimator;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;

import java.util.Map;

/**
 * Created by basic on 2019/7/17.
 */

public class TestAnimationHepler<T extends TestActivity> {

    private SparseArray<Animation> mSparseArray = new SparseArray<>();

    public static TestAnimationHepler get() {
        return new TestAnimationHepler();
    }

//    public void playLoginViewAnimation(T object) {
//        View view = object.mTextView;
//        Animation animation = mSparseArray.get(view.hashCode());
//        if (animation == null) {
//            animation = new TranslateAnimation(1, 1, 1, 1);
//            animation.setRepeatCount(10);
//            animation.setDuration(1000);
//            mSparseArray.put(view.hashCode(), animation);
//        }
//        if (animation.hasStarted()) {
//            animation.cancel();
//        }
//        object.mTextView.startAnimation(animation);
//    }

    public void release(){
        for (int index=0;index < mSparseArray.size();index++){
            Animation animation = mSparseArray.valueAt(index);
            if (animation != null){
                animation.cancel();
                animation = null;
            }
        }
        mSparseArray.clear();
        mSparseArray = null;
    }
}
