package com.lge.vs.gaugebarlibrary.gaugebar.gmc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressPainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class GaugeBarProgressPainterImp implements GaugeBarProgressPainter {

    protected Paint mProgressPaint, mGradientPaint, mMarkerLinePainter, mMarkerDotPainter;
    private int mColor;
    private int mWidth;
    private int mHeight;
    private float mProgressLength = 0;
    private float mMax;
    private int mProgressBarTickHeigth;
    private final int mMargin;
    private int mProgressBarTickWidth;
    private int mStartProgressBarTickWidth;
    private int mProgressBarTickSpace;
    private final Context context;
    private int mProgressBarGradientHeight;
    private int mDefaultWidth;
    private int mProgressBarMarkerTickWidth;

    public GaugeBarProgressPainterImp(int color, float max, int margin, Context context) {
        this.mColor = color;
        this.mMax = max;
        this.mMargin = margin;
        this.context = context;
        initSize();
        init();
    }

    private void initSize() {
        this.mStartProgressBarTickWidth = EtcUtils.dpToPx(1, context);
        this.mProgressBarTickWidth = EtcUtils.dpToPx(1, context);
        this.mProgressBarTickSpace = EtcUtils.dpToPx(1, context);
        this.mProgressBarTickHeigth = EtcUtils.dpToPx(66, context);
        this.mProgressBarGradientHeight = EtcUtils.dpToPx(58, context);
        this.mDefaultWidth = EtcUtils.dpToPx(243, context);
        this.mProgressBarMarkerTickWidth = EtcUtils.dpToPx(2, context);
    }

    private void init() {
        initProgressBarPainter();
        initGradientPainter();
    }

    private void initProgressBarPainter() {
        float[] pathEff = new float[]{mStartProgressBarTickWidth, 2* mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, mProgressBarTickSpace, mProgressBarTickWidth, 2* mProgressBarTickSpace};
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(mProgressBarTickHeigth);
        mProgressPaint.setColor(mColor);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setPathEffect(new DashPathEffect(pathEff, 0));

        mMarkerLinePainter = new Paint();
        mMarkerLinePainter.setAntiAlias(true);
        mMarkerLinePainter.setStrokeWidth(mProgressBarMarkerTickWidth);
        mMarkerLinePainter.setColor(0xFFB5B5B5);
        mMarkerLinePainter.setStyle(Paint.Style.STROKE);

        mMarkerDotPainter = new Paint();
        mMarkerDotPainter.setAntiAlias(true);
        mMarkerDotPainter.setStrokeWidth(mProgressBarMarkerTickWidth);
        mMarkerDotPainter.setColor(0xFFFFFFFF);
        mMarkerDotPainter.setStyle(Paint.Style.STROKE);
    }

    private void initGradientPainter() {
        mGradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(0, mProgressBarTickHeigth /2, mProgressLength, mProgressBarTickHeigth /2 , mProgressPaint); // progress-bar
        canvas.drawLine(mProgressLength - mProgressBarMarkerTickWidth,0, mProgressLength - mProgressBarMarkerTickWidth, mProgressBarTickHeigth, mMarkerLinePainter); // marker-line
        canvas.drawLine(mProgressLength - mProgressBarMarkerTickWidth, mProgressBarTickHeigth + mProgressBarMarkerTickWidth, mProgressLength - mProgressBarMarkerTickWidth, mProgressBarTickHeigth +2* mProgressBarMarkerTickWidth, mMarkerDotPainter); // marker-dot
        canvas.drawRect(0,0, mWidth, mProgressBarGradientHeight, mGradientPaint); // gradient
    }

    @Override
    public int getColor() {
        return mColor;
    }

    @Override
    public void setColor(int color) {
        this.mColor = color;
        mProgressPaint.setColor(mColor);
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.mWidth = width;
        this.mHeight = height;
        mGradientPaint.setShader(new LinearGradient(0f, 0f, 0, mProgressBarGradientHeight, 0xFF181A1E, 0x000C0C0D, Shader.TileMode.CLAMP));
        mProgressPaint.setShader(new LinearGradient(0f, 0f, 0f, mProgressBarTickHeigth, 0x0fffffff, 0xff3E3E3E, Shader.TileMode.CLAMP));
    }

    public void setValue(float value) {
        if (mWidth == 0) mWidth = mDefaultWidth;
        this.mProgressLength = (mWidth * value) / mMax;
    }

    public float getMax() {
        return mMax;
    }

    public void setMax(float max) {
        this.mMax = max;
    }
}
