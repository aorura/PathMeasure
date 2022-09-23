package com.lge.vs.gaugebarlibrary.gaugebar.cadillac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressPainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class GaugeBarProgressPainterImp implements GaugeBarProgressPainter {
    protected Paint mPaint;
    private int mColor;
    private int mWidth;
    private int mHeight;
    private float mMax;
    private final int mMargin;
    private final Context mContext;
    private int mLeftMargin;
    private Path mPath;
    private float mRatio = 1f;
    private boolean mEnableGradient = true;

    public GaugeBarProgressPainterImp(int color, float max, int margin, Context context) {
        this.mColor = color;
        this.mMax = max;
        this.mMargin = margin;
        this.mContext = context;
        initSize();
        init();
    }

    private void initSize() {
        this.mLeftMargin = EtcUtils.dpToPx(8.06f, mContext);
    }

    private void init() {
        initPainter();
    }

    private void initPainter() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void updateProgressPath() {
        mPath = new Path();
        mPath.moveTo(mLeftMargin,mHeight);
        mPath.lineTo(mWidth,mHeight*mRatio);
        mPath.lineTo(mLeftMargin,mHeight*mRatio);
        mPath.close();
        if (mEnableGradient != true) return;
        mPaint.setShader(new LinearGradient(0,0, mWidth, mHeight,mColor, 0x008fA3AE, Shader.TileMode.CLAMP));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public int getColor() {
        return mColor;
    }

    @Override
    public void setColor(int color) {
        this.mColor = color;
        mPaint.setColor(color);
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.mWidth = width;
        this.mHeight = height;

        updateProgressPath();
    }

    public void setValue(float value) {
        this.mRatio = 1 - value / mMax;
        updateProgressPath();
    }

    public void setGradient(boolean val) {
        mEnableGradient = val;
    }

    public float getMax() {
        return mMax;
    }

    public void setMax(float max) {
        this.mMax = max;
    }
}
