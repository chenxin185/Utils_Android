package com.example.chenxin.utils_android.utils.animation.Path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by momo on 2018/4/8.
 */

public class PathAnimView extends View {
    protected Path mSourcePath;//需要做动画的源Path
    protected Path mAnimPath;//用于绘制动画的Path
    protected Paint mPaint;
    protected int mColorBg = Color.GRAY;//背景色
    protected int mColorFg = Color.WHITE;//前景色 填充色
    protected PathAnimHelper mPathAnimHelper;//Path动画工具类

    protected int mPaddingLeft, mPaddingTop;
    public PathAnimView(Context context) {
        super(context);
    }

    public PathAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PathAnimView setSourcePath(Path sourcePath){
        this.mSourcePath = sourcePath;
        initAnimHelper();
        return this;
    }
    public Paint getPaint(){
        return mPaint;
    }
    public PathAnimView setPaint(Paint paint){
        this.mPaint = paint;
        return this;
    }
    public int ColorBg(){
        return mColorBg;
    }

    public PathAnimView setColorBg(int colorBg) {
        mColorBg = colorBg;
        return this;
    }

    public int getColorFg() {
        return mColorFg;
    }

    public PathAnimView setColorFg(int colorFg) {
        mColorFg = colorFg;
        return this;
    }

    public PathAnimHelper getPathAnimHelper() {
        return mPathAnimHelper;
    }

    public PathAnimView setPathAnimHelper(PathAnimHelper pathAnimHelper) {
        mPathAnimHelper = pathAnimHelper;
        return this;
    }

    public Path getAnimPath() {
        return mAnimPath;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
    }


    private void init() {
        //Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        //动画路径只要初始化即可
        mAnimPath = new Path();

        //初始化动画帮助类
        initAnimHelper();

    }

    private void initAnimHelper() {
        mPathAnimHelper = getInitAnimHeper();
    }
    protected PathAnimHelper getInitAnimHeper() {
        return new PathAnimHelper(this, mSourcePath, mAnimPath);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mPaddingLeft, mPaddingTop);


        mPaint.setColor(mColorBg);
        canvas.drawPath(mSourcePath, mPaint);

        mPaint.setColor(mColorFg);
        canvas.drawPath(mAnimPath,mPaint);
    }

    public PathAnimView setAnimInfinite(boolean infinite) {
        mPathAnimHelper.setRepeat(infinite);
        return this;
    }
    public PathAnimView setAnimTime(long animTime) {
        mPathAnimHelper.setAnimTime(animTime);
        return this;
    }

    public void startAnim() {
        mPathAnimHelper.startAnim();
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        mPathAnimHelper.stopAnim();
    }

    public void clearAnim() {
        stopAnim();
        mAnimPath.reset();
        mAnimPath.lineTo(0, 0);
        invalidate();
    }
}
