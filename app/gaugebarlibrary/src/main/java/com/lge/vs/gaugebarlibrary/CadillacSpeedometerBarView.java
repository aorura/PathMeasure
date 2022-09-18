package com.lge.vs.gaugebarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.lge.vs.gaugebarlibrary.speedometer.cadillac.GaugeBarProgressPainterImp;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class CadillacSpeedometerBarView extends View {
    private GaugeBarProgressPainterImp mGaugeBarProgressPainter;
    private int mMin = 0;
    private int mMax = 100;
    private float mValue;
    private int mMargin = 15;
    private int mGaugeBarProgressColor = 0x9cfa1d;


    public CadillacSpeedometerBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CadillacSpeedometerBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mGaugeBarProgressPainter.onSizeChanged(h, w);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray attributes = context.obtainStyledAttributes(attributeSet, R.styleable.EnergyRangeGaugeBarView);
        initAttributes(attributes);

        int marginPixels = EtcUtils.dpToPx(mMargin, getContext());
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mGaugeBarProgressPainter = new GaugeBarProgressPainterImp(mGaugeBarProgressColor, mMax, marginPixels, getContext());
        attributes.recycle();
    }

    private void initAttributes(TypedArray attributes) {
        mGaugeBarProgressColor = attributes.getColor(R.styleable.EnergyRangeGaugeBarView_gaugebar_progress_color, mGaugeBarProgressColor);

        mMax = attributes.getInt(R.styleable.EnergyRangeGaugeBarView_max, mMax);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mGaugeBarProgressPainter.draw(canvas);

        invalidate();
    }

    public void setProgress(float value) {
        this.mValue = value;
        if (value <= mMax && value >= mMin) {
            updateValueProgress(this.mValue);
        }
    }

    public float getMax() {
        return mMax;
    }

    public void setMax(int max) {
        this.mMax = max;
    }

    private void updateValueProgress(float value) {
        mGaugeBarProgressPainter.setValue(value);
    }
}
