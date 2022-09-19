package com.lge.vs.gaugebarlibrary.gaugebar.cadillac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

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
    private int lineOneWidth;
    private int lineSpace;
    private int color;

    private Paint lineOnePaint, lineTwoPaint;
    private Path lineTwoPath;
    private int lineTwoWidth;
    private int lineOneTwoInterval;
    private int lineTickWidth;
    private int lineTwoX;
    private Path progressPath;

    public GaugeBarProgressBgPainterImp(int color, int margin, Context context) {
        this.extraMargin = margin;
        this.context = context;
        this.color = color;
        initSize();
        initPainter();
    }

    private void initSize() {
        this.lineOneWidth = EtcUtils.dpToPx(1.15f, context);
        this.lineTwoWidth = EtcUtils.dpToPx(2.3f,context);
        this.lineOneTwoInterval = EtcUtils.dpToPx(5.76f, context);
        this.lineTickWidth = EtcUtils.dpToPx(5.76f, context);
        this.lineSpace = EtcUtils.dpToPx(3, context);
        this.strokeWidth = EtcUtils.dpToPx(12, context);
    }

    private void initPainter() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //paint.setAntiAlias(true);
        //paint.setStrokeWidth(strokeWidth);
        //paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);


        lineOnePaint = new Paint();
        lineOnePaint.setAntiAlias(true);
        lineOnePaint.setColor(0xFF8FA2AE);
        lineOnePaint.setStrokeWidth(lineOneWidth);
        lineOnePaint.setStyle(Paint.Style.STROKE);

        lineTwoPaint = new Paint();
        lineTwoPaint.setAntiAlias(true);
        lineTwoPaint.setColor(0xFFFFFFFF);
        lineTwoPaint.setStrokeWidth(lineOneWidth);
        lineTwoPaint.setStyle(Paint.Style.STROKE);
    }

    private void initCircle() {
        int padding = strokeWidth / 2 + extraMargin;
        circle = new RectF();
        circle.set(padding, padding, mWidth - padding, mHeight - padding);

        lineTwoX = lineOneWidth/2+lineOneTwoInterval;
        lineTwoPath = new Path();
        lineTwoPath.moveTo(lineTwoX,0);
        lineTwoPath.lineTo(lineTwoX, mHeight);
        lineTwoPath.moveTo(lineTwoX, mHeight*0.25f);
        lineTwoPath.lineTo(lineTwoX+lineTickWidth, mHeight*0.25f);
        lineTwoPath.moveTo(lineTwoX, mHeight*0.5f);
        lineTwoPath.lineTo(lineTwoX+lineTickWidth, mHeight*0.5f);
        lineTwoPath.moveTo(lineTwoX, mHeight*0.75f);
        lineTwoPath.lineTo(lineTwoX+lineTickWidth, mHeight*0.75f);

        float ratiog = mHeight/mWidth;
        paint.setShader(new LinearGradient(0,0, mWidth*0.5f, mHeight*0.5f,0xFF8FA3AE, 0x008fA3AE, Shader.TileMode.CLAMP));

        float ratio = 0.9f;
        progressPath = new Path();
        progressPath.moveTo(lineTwoX,mHeight);
        progressPath.lineTo(mWidth,mHeight*ratio);
        progressPath.lineTo(lineTwoX,mHeight*ratio);
        progressPath.close();

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(lineOneWidth /2,0, lineOneWidth /2, mHeight, lineOnePaint);
        canvas.drawPath(lineTwoPath, lineTwoPaint);
        canvas.drawPath(progressPath,paint);
        //canvas.drawRect(lineTwoX,mHeight, mWidth,mHeight*0.1f, paint);
        
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
