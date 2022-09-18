package com.lge.vs.gaugebarlibrary.gaugebar.cadillac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressBgPainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class GaugeBarProgressBgPainterImp implements GaugeBarProgressBgPainter {
    private Paint paint;
    private RectF circle;
    private final Context context;
    private int mWidth;
    private int mHeight;
    private final float startAngle = 150;
    private final float endAngle = 240;
    private int strokeWidth;
    private final int extraMargin;
    private int lineWidth;
    private int lineSpace;
    private int color;

    private Paint lineOnePaint, lineTwoPaint;

    public GaugeBarProgressBgPainterImp(int color, int margin, Context context) {
        this.extraMargin = margin;
        this.context = context;
        this.color = color;
        initSize();
        initPainter();
    }

    private void initSize() {
        this.lineWidth = EtcUtils.dpToPx(2, context);
        this.lineSpace = EtcUtils.dpToPx(3, context);
        this.strokeWidth = EtcUtils.dpToPx(12, context);
    }

    private void initPainter() {
        float[] pathEff = new float[]{lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineSpace, lineWidth, lineWidth + 2 * lineSpace};
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(pathEff, 0));

        lineOnePaint = new Paint();
        lineOnePaint.setAntiAlias(true);
        lineOnePaint.setColor(0xFF8FA2AE);
        lineOnePaint.setStrokeWidth(lineWidth);
        lineOnePaint.setStyle(Paint.Style.STROKE);
    }

    private void initCircle() {
        int padding = strokeWidth / 2 + extraMargin;
        circle = new RectF();
        circle.set(padding, padding, mWidth - padding, mHeight - padding);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(mWidth /2,0, mWidth /2, mHeight, lineOnePaint);
        canvas.drawArc(circle, startAngle, endAngle, false, paint);
        canvas.drawLine(lineWidth/2,0,lineWidth/2, mHeight, lineOnePaint);
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.mWidth = width;
        this.mHeight = height;
        initCircle();
    }
}
