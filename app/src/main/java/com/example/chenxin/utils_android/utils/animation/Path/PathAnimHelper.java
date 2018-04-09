package com.example.chenxin.utils_android.utils.animation.Path;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * Created by momo on 2018/4/8.
 */

public class PathAnimHelper {
    public static final long mDefultAnimTime = 1500;//默认总时间
    private View mView;
    private Path mSourcePath;
    private Path mAnimPath;
    private long mAnimTime;
    private boolean mIsRepeat;
    private ValueAnimator mAnimator;
    private Boolean mContinous =false;
    public PathAnimHelper(View view, Path sourcePath, Path animPath){
        this(view,sourcePath,animPath,mDefultAnimTime,true);

    }

    public PathAnimHelper(View view, Path sourcePath, Path animPath, long animTime, boolean isRepeat) {
        if (view ==null||sourcePath ==null||animPath ==null){
            return;
        }
        this.mView = view;
        this.mSourcePath = sourcePath;
        this.mAnimPath = animPath;
        this.mAnimTime = animTime;
        this.mIsRepeat = isRepeat;
    }

    public View getView() {
        return mView;
    }

    public PathAnimHelper setView(View view) {
        mView = view;
        return this;
    }

    public Path getSourcePath() {
        return mSourcePath;
    }

    public PathAnimHelper setSourcePath(Path sourcePath) {
        mSourcePath = sourcePath;
        return this;
    }

    public Path getAnimPath() {
        return mAnimPath;
    }

    public PathAnimHelper setAnimPath(Path animPath) {
        mAnimPath = animPath;
        return this;
    }

    public long getAnimTime() {
        return mAnimTime;
    }

    public void setAnimTime(long animTime) {
        mAnimTime = animTime;
    }

    public boolean isRepeat() {
        return mIsRepeat;
    }

    public PathAnimHelper setRepeat(boolean repeat) {
        mIsRepeat = repeat;
        return this;
    }

    public ValueAnimator getAnimator() {
        return mAnimator;
    }

    public PathAnimHelper setAnimator(ValueAnimator animator) {
        mAnimator = animator;
        return this;
    }

    public void startAnim(){
        if (mView ==null||mSourcePath==null||mAnimPath ==null){
            return;
        }
        PathMeasure pathMeasure = new PathMeasure();
        mAnimPath.reset();
        mAnimPath.lineTo(0,0);
        pathMeasure.setPath(mSourcePath,false);
        int count =0;
        while (pathMeasure.getLength()!=0){
            pathMeasure.nextContour();
            count++;
        }
        pathMeasure = new PathMeasure();
        loopAnim(mView, mSourcePath, mAnimPath, mAnimTime, pathMeasure, mAnimTime / count, mIsRepeat);
    }
    public Boolean getContinous(){
        return  mContinous;
    }
    public PathAnimHelper setContinous(Boolean continous){
        this.mContinous = continous;
        return this;
    }

    private void loopAnim(final View view, final Path sourcePath, final Path animPath, long animTime, final PathMeasure pathMeasure, long l, final boolean isRepeat) {
        stopAnim();

        mAnimator = ValueAnimator.ofFloat(0.0f,1.0f);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(l);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!mContinous){
                    mAnimPath.reset();
                    mAnimPath.lineTo(0,0);
                }
                onPathAnimCallback(view,sourcePath,animPath,pathMeasure,animation);
                mView.invalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                pathMeasure.getSegment(0, pathMeasure.getLength(), animPath, true);
                pathMeasure.nextContour();
                if (pathMeasure.getLength() ==0){
                    if (isRepeat){
                        animPath.reset();
                        animPath.lineTo(0,0);
                        pathMeasure.setPath(sourcePath,false);
                    }else {
                        mAnimator.end();
                    }
                }
            }
        });
        mAnimator.start();
    }

    public void onPathAnimCallback(View view, Path sourcePath, Path animPath, PathMeasure pathMeasure, ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        //获取一个段落
        pathMeasure.getSegment(0, pathMeasure.getLength() * value, animPath, true);
    }
    public void stopAnim() {
        if (mAnimator!=null&&mAnimator.isRunning()){
            mAnimator.end();
        }
    }
}
