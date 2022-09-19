package com.lge.vs.gaugebarlibrary.gaugebar.cadillac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressPainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class GaugeBarProgressPainterImp implements GaugeBarProgressPainter {

    protected Paint mProgressBarPaint, mArcPaint;
    private RectF mCircle, mInsideCircle;
    private int mColor;
    private final int mStartAngle = 150;
    private float mArcStartAngle, mArcCurrentAngle;
    private int mWidth;
    private int mHeight;
    private float mCurrentAngle = 0;
    private float mMax;
    private int mProgressBarTickHeight;
    private final int mMargin;
    private int mProgressBarTickWidth;
    private int mProgressBarTickSpace;
    private final Context mContext;
    private float mArcSizefactor = 0.9f;

    public GaugeBarProgressPainterImp(int color, float max, int margin, Context context) {
        this.mColor = color;
        this.mMax = max;
        this.mMargin = margin;
        this.mContext = context;
        initSize();
        init();
    }

    private void initSize() {
        this.mProgressBarTickWidth = EtcUtils.dpToPx(2, mContext);
        this.mProgressBarTickSpace = EtcUtils.dpToPx(3, mContext);
        this.mProgressBarTickHeight = EtcUtils.dpToPx(12, mContext);
    }

    private void init() {
        initPainter();
        initArcPainter();
    }

    private void initPainter() {
        float[] pathEff = new float[]{mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickWidth + 2 * mProgressBarTickSpace};
        mProgressBarPaint = new Paint();
        mProgressBarPaint.setAntiAlias(true);
        mProgressBarPaint.setStrokeWidth(mProgressBarTickHeight);
        mProgressBarPaint.setColor(mColor);
        mProgressBarPaint.setStyle(Paint.Style.STROKE);
        mProgressBarPaint.setPathEffect(new DashPathEffect(pathEff, 0));
    }

    private void initArcPainter() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.FILL);
    }

    private void initExternalCircle() {
        int padding = mProgressBarTickHeight / 2 + mMargin;
        int insidePadding = mProgressBarTickHeight * 2 + mMargin;
        mCircle = new RectF();
        mCircle.set(padding, padding, mWidth - padding, mHeight - padding);

        mInsideCircle = new RectF();
        mInsideCircle.set(insidePadding, insidePadding, mWidth - insidePadding, mHeight - insidePadding);
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawArc(mCircle, mStartAngle, mCurrentAngle, false, mProgressBarPaint);
        mArcStartAngle = (mStartAngle + mCurrentAngle) * mArcSizefactor;
        if (mArcStartAngle < mStartAngle) mArcStartAngle = mStartAngle;
        mArcCurrentAngle = mCurrentAngle - (mArcStartAngle - mStartAngle);
        //canvas.drawArc(mInsideCircle, mArcStartAngle, mArcCurrentAngle, true, mArcPaint);
    }

    @Override
    public int getColor() {
        return mColor;
    }

    @Override
    public void setColor(int color) {
        this.mColor = color;
        mProgressBarPaint.setColor(color);
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.mWidth = width;
        this.mHeight = height;
        mArcPaint.setShader(new RadialGradient(width/2,height/2,Math.min(width,height)/2.0f,0x00000000, 0x087294c2,Shader.TileMode.CLAMP));
        mProgressBarPaint.setShader(new LinearGradient(0f, 0f, width, 0f, 0xff7294c2, 0xffffffff, Shader.TileMode.CLAMP));
        initExternalCircle();
    }

    public void setValue(float value) {
        this.mCurrentAngle = (240f * value) / mMax;
    }

    public float getMax() {
        return mMax;
    }

    public void setMax(float max) {
        this.mMax = max;
    }
}
