package com.example.chenxin.utils_android.utils.animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.animation.Interpolator;

/**
 * Created by momo on 2018/4/9.
 */

public class ValueAnimation {

    private long mDuration;
    private UpdateAnimationCallback mCallback;
    private ValueAnimator mAnimator;
    private Interpolator mInterpolator;
    private TypeEvaluator mEvaluator;
    private long mPauseTime;

    public ValueAnimation setAnimator(ValueAnimator valueAnimator){
        mAnimator = valueAnimator;
        return this;
    }
    public ValueAnimator setAnimator(){
        return mAnimator;
    }

    public void setCallback(UpdateAnimationCallback callback){
        this.mCallback = callback;
    }

    public ValueAnimation setDuration( long duration){
        mAnimator.setDuration(mDuration);
        this.mDuration = duration;
        return this;
    }
    public ValueAnimation setInterpolator(Interpolator interpolator){
        this.mInterpolator = interpolator;
        return this;
    }
    public ValueAnimation setTypeEvaluator(TypeEvaluator typeEvaluator){
        this.mEvaluator = typeEvaluator;
        return this;
    }



    public void startAnimation(){
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCallback.update((float)animation.getAnimatedValue());
            }
        });
    }
    public void pause(){
        mPauseTime = mAnimator.getCurrentPlayTime();
        mAnimator.pause();
    }
    public void continous(){
        mAnimator.start();
        mAnimator.setCurrentPlayTime(mPauseTime);
    }
    public void destroy(){
        mAnimator.end();
        if (mAnimator!=null){
            mAnimator.removeAllUpdateListeners();
            mAnimator =null;
        }
    }






}
