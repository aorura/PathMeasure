package com.lge.vs.gaugebarlibrary.gaugebar.gmc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressBgPainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class GaugeBarProgressBgPainterImp implements GaugeBarProgressBgPainter {
    private Paint mPaint;
    private final Context mContext;
    private int mWidth;
    private int mHeight;
    private int mLineThick;
    private final int mMargin;
    private int mLineTickWidth;
    private int mLongLineTickWidth;
    private int mLineTickSpace;
    private int mColor;
    private int YPosition;

    public GaugeBarProgressBgPainterImp(int color, int margin, Context context) {
        this.mMargin = margin;
        this.mContext = context;
        this.mColor = color;
        initSize();
        initPainter();
    }

    private void initSize() {
        this.mLineTickWidth = EtcUtils.dpToPx(1, mContext);
        this.mLineTickSpace = EtcUtils.dpToPx(1, mContext);
        this.mLineThick = EtcUtils.dpToPx(5, mContext);
        this.mLongLineTickWidth = EtcUtils.dpToPx(56, mContext);
        this.YPosition = EtcUtils.dpToPx(66, mContext) + EtcUtils.dpToPx(9, mContext);
    }

    private void initPainter() {
        float[] pathEff = new float[]{mLineTickWidth, 2 * mLineTickSpace, mLongLineTickWidth, 2 * mLineTickSpace};
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mLineThick);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(new DashPathEffect(pathEff, 0));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(0, YPosition, mWidth, YPosition, mPaint);
    }

    @Override
    public int getColor() {
        return mColor;
    }

    @Override
    public void setColor(int color) {
        this.mColor = color;
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.mWidth = width;
        this.mHeight = height;
    }
}
