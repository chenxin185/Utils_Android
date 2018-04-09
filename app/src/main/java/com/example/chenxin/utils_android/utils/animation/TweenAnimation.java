package com.example.chenxin.utils_android.utils.animation;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2018/4/8.
 */

public class TweenAnimation {
    private long mDuration;
    private Boolean mIsFillAfter =false;
    private Context mContext;
    private List<Animation> mAnimationList;
    private AnimationSet mAnimationSet;
    private Interpolator mInterpolator;

    public TweenAnimation(Context context){
        this.mContext = context;
        this.mAnimationList = new ArrayList<>();
        this.mAnimationSet = new AnimationSet(false);
    }

    public TweenAnimation setDuration(long duration){
        this.mDuration = duration;
        return this;

    }
    public TweenAnimation setInterpolator(Interpolator interpolator){
        this.mInterpolator = interpolator;
        return this;
    }
    public Interpolator getInterpolator(){
        return mInterpolator;
    }

    public TweenAnimation setFillAfter( Boolean fillAfter){
        this.mIsFillAfter = fillAfter;
        return this;
    }

    public TweenAnimation addAnimation(Animation animation){
        mAnimationList.add(animation);
        mAnimationSet.addAnimation(animation);
        return this;
    }

    public TweenAnimation addTranslateAnimation(int fromXType, float fromXValue, int toXType,
                                                float toXValue, int fromYType, float fromYValue,
                                                int toYType, float toYValue){
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXType,fromXValue,toXType,
                toXValue,fromYType,fromYValue,toYType,toYValue);
        mAnimationList.add(translateAnimation);
        mAnimationSet.addAnimation(translateAnimation);
        return this;
    }
    public TweenAnimation addScaleAnimation(float fromX, float toX, float fromY, float toY,
                                            float pivotX, float pivotY){
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY, pivotX, pivotY);
        mAnimationList.add(scaleAnimation);
        mAnimationSet.addAnimation(scaleAnimation);
        return this;
    }
    public TweenAnimation addAlphaAnimation(float fromAlpha, float toAlpha){
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha,toAlpha);
        mAnimationList.add(alphaAnimation);
        mAnimationSet.addAnimation(alphaAnimation);
        return this;
    }
    public TweenAnimation addRotateAnimation(float fromDegrees, float toDegrees, int pivotXType,
                                             float pivotXValue, int pivotYType, float pivotYValue){
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees,toDegrees,
                pivotXType,pivotXValue,pivotYType,pivotYValue);
        mAnimationList.add(rotateAnimation);
        mAnimationSet.addAnimation(rotateAnimation);
        return this;
    }
    public AnimationSet getAnimationSet(){
        return mAnimationSet;
    }
    public TweenAnimation setAnimationSet(AnimationSet animationSet){
        this.mAnimationSet = animationSet;
        return this;
    }
    public void startAnimator(){
        mAnimationSet.start();
    }
    public void destory(){
        if (mAnimationSet!=null){
            mAnimationSet =null;
        }
    }



}
