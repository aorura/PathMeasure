package com.lge.vs.gaugebarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.lge.vs.gaugebarlibrary.gaugebar.chevy.InsideLowRangeLinePainterImp;
import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressPainter;
import com.lge.vs.gaugebarlibrary.gaugebar.chevy.GaugeBarProgressPainterImp;
import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressBgPainter;
import com.lge.vs.gaugebarlibrary.gaugebar.chevy.GaugeBarProgressBgPainterImp;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class ChevyEnergyRangeGaugeBarView extends View {
    private GaugeBarProgressBgPainter gaugeBarProgressBgPainter;
    private GaugeBarProgressPainter gaugeBarProgressPainter;
    private InsideLowRangeLinePainterImp insideLowRangeLinePainter;
    private int min = 0;
    private int max = 100;
    private float value;
    private int margin = 15;
    private int insideLowRangeProgressColor = 0x094e35;
    private int gaugeBarProgressColor = 0x9cfa1d;
    private int gaugeBarProgressBgColor = 0x2E2E3B;

    public ChevyEnergyRangeGaugeBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChevyEnergyRangeGaugeBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width > height) {
            size = height;
        } else {
            size = width;
        }
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        gaugeBarProgressBgPainter.onSizeChanged(h, w);
        gaugeBarProgressPainter.onSizeChanged(h, w);
        insideLowRangeLinePainter.onSizeChanged(h, w);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray attributes = context.obtainStyledAttributes(attributeSet, R.styleable.EnergyRangeGaugeBarView);
        initAttributes(attributes);

        int marginPixels = EtcUtils.dpToPx(margin, getContext());
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        gaugeBarProgressBgPainter = new GaugeBarProgressBgPainterImp(gaugeBarProgressBgColor, marginPixels, getContext());
        gaugeBarProgressPainter = new GaugeBarProgressPainterImp(gaugeBarProgressColor, max, marginPixels, getContext());
        insideLowRangeLinePainter = new InsideLowRangeLinePainterImp(insideLowRangeProgressColor, getContext());
        attributes.recycle();
    }

    private void initAttributes(TypedArray attributes) {
        insideLowRangeProgressColor = attributes.getColor(R.styleable.EnergyRangeGaugeBarView_inside_low_line_color, insideLowRangeProgressColor);
        gaugeBarProgressColor = attributes.getColor(R.styleable.EnergyRangeGaugeBarView_gaugebar_progress_color, gaugeBarProgressColor);
        gaugeBarProgressBgColor = attributes.getColor(R.styleable.EnergyRangeGaugeBarView_gaugebar_bg_color, gaugeBarProgressBgColor);

        max = attributes.getInt(R.styleable.EnergyRangeGaugeBarView_max, max);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gaugeBarProgressBgPainter.draw(canvas); // dash-progress
        gaugeBarProgressPainter.draw(canvas);
        insideLowRangeLinePainter.draw(canvas); // inside-line progress

        invalidate();
    }

    public void setProgress(float value) {
        this.value = value;
        if (value <= max && value >= min) {
                updateValueProgress(value);
        }
    }

    public float getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    private void updateValueProgress(float value) {
        gaugeBarProgressPainter.setValue(value);
    }
}
