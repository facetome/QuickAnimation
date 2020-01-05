package com.baisc.basicanimator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by basic on 2019/12/8.
 */

public class AnnotationTestFragment extends Fragment implements OnClickListener {

    public static final String TAG = "AnnotationTestFragment";


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
    @Scale(fromX = 1, toX = 2, fromY = 1, toY = 2, pivotXType = 1, pivotXValue = 0.5f, pivotYType = 1, pivotYValue = 0.5f)
    TextView scale;

    @ResourceAnimation(animationId = R.anim.my_animation)
    TextView resource;

    @AnimationParams(duration = 1000, delayTime = 100)
    @Animator(property = "TranslationX", value = {0, 100})
    TextView animator;

    @AnimationParams(duration = 1000, delayTime = 100)
    @Interpolator(DecelerateInterpolator.class)
    @Animators(value = {@Animator(property = "TranslationX", value = {0, 100}, autoCancel = true),
            @Animator(property = "TranslationY", value = {0, 100}, autoCancel = true)}, playTogether = true)
    TextView animators;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test_fragment_layout, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        translate = view.findViewById(R.id.translate_fragment);
        rotate = view.findViewById(R.id.rotate_fragment);
        alpha = view.findViewById(R.id.alpha_fragment);
        scale = view.findViewById(R.id.scale_fragment);
        resource = view.findViewById(R.id.resource_fragment);
        animator = view.findViewById(R.id.animator_fragment);
        animators = view.findViewById(R.id.animators_fragment);
        translate.setOnClickListener(this);
        rotate.setOnClickListener(this);
        alpha.setOnClickListener(this);
        scale.setOnClickListener(this);
        resource.setOnClickListener(this);
        animator.setOnClickListener(this);
        animators.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translate_fragment:
                AnnotationTestFragment_Generator.playTranslateAnimations(this);
                break;
            case R.id.rotate_fragment:
                AnnotationTestFragment_Generator.playRotateRotate(this);
                break;
            case R.id.alpha_fragment:
                AnnotationTestFragment_Generator.playAlphaAlpha(this);
                break;
            case R.id.scale_fragment:
                AnnotationTestFragment_Generator.playScaleScale(this);
                break;
            case R.id.resource_fragment:
                AnnotationTestFragment_Generator.playResourceResource(this);
                break;
            case R.id.animator_fragment:
                AnnotationTestFragment_Generator.playAnimatorAnimator(this);
                break;
            case R.id.animators_fragment:
                AnnotationTestFragment_Generator.playAnimatorsAnimators(this);
                break;

            default:
                break;

        }
    }
}
