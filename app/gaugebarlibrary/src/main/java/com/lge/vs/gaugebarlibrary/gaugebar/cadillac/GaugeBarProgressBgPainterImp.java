package com.lge.vs.gaugebarlibrary.gaugebar.cadillac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressBgPainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class GaugeBarProgressBgPainterImp implements GaugeBarProgressBgPainter {
    private final Context context;
    private int mWidth;
    private int mHeight;
    private final int mExtraMargin;
    private int lineOneWidth;
    private int mColor;
    private Paint lineOnePaint, lineTwoPaint;
    private Path lineTwoPath;
    private int lineTwoWidth;
    private int startX;
    private int lineOneTwoInterval;
    private int lineTickWidth;
    private int lineTwoX;
    private int lineOneColor = 0xFF8FA2AE;
    private int lineTwoColor = 0xFFFFFFFF;

    public GaugeBarProgressBgPainterImp(int color, int margin, Context context) {
        this.mExtraMargin = margin;
        this.context = context;
        this.mColor = color;
        initSize();
        initPainter();
    }

    private void initSize() {
        this.lineOneWidth = EtcUtils.dpToPx(1.15f, context);
        this.lineTwoWidth = EtcUtils.dpToPx(2.3f,context);
        this.lineOneTwoInterval = EtcUtils.dpToPx(5.76f, context);
        this.lineTickWidth = EtcUtils.dpToPx(5.76f, context);
    }

    private void initPainter() {
        lineOnePaint = new Paint();
        lineOnePaint.setAntiAlias(true);
        lineOnePaint.setColor(lineOneColor);
        lineOnePaint.setStrokeWidth(lineOneWidth);
        lineOnePaint.setStyle(Paint.Style.STROKE);

        lineTwoPaint = new Paint();
        lineTwoPaint.setAntiAlias(true);
        lineTwoPaint.setColor(lineTwoColor);
        lineTwoPaint.setStrokeWidth(lineTwoWidth);
        lineTwoPaint.setStyle(Paint.Style.STROKE);
    }

    private void initLinePath() {
        startX = lineOneWidth/2;
        lineTwoX = startX + lineOneTwoInterval;
        lineTwoPath = new Path();
        lineTwoPath.moveTo(lineTwoX,0);
        lineTwoPath.lineTo(lineTwoX, mHeight);
        lineTwoPath.moveTo(lineTwoX, mHeight*0.25f);
        lineTwoPath.lineTo(lineTwoX+lineTickWidth, mHeight*0.25f);
        lineTwoPath.moveTo(lineTwoX, mHeight*0.5f);
        lineTwoPath.lineTo(lineTwoX+lineTickWidth, mHeight*0.5f);
        lineTwoPath.moveTo(lineTwoX, mHeight*0.75f);
        lineTwoPath.lineTo(lineTwoX+lineTickWidth, mHeight*0.75f);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX,0, startX, mHeight, lineOnePaint);
        canvas.drawPath(lineTwoPath, lineTwoPaint);
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public void setColor(int color) {
        this.mColor = color;
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.mWidth = width;
        this.mHeight = height;

        initLinePath();
    }
}
