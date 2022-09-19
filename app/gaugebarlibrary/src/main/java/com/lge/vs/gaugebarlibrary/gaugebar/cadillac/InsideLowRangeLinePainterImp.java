package com.lge.vs.gaugebarlibrary.gaugebar.cadillac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.lge.vs.gaugebarlibrary.gaugebar.common.InsideLowRangeLinePainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class InsideLowRangeLinePainterImp implements InsideLowRangeLinePainter {

  private Context context;
  private Paint paint;
  private Paint paintRed;
  private RectF circle;
  private int width;
  private int height;
  private float startAngle = 150;
  private float endAngle = 240;
  private int lowRangeColor = Color.parseColor("#ED2D30");
  private int color;
  private int strokeWidth;
  private int externalStrokeWidth;
  private int extraMargin;
  private int margin;

  public InsideLowRangeLinePainterImp(int color, Context context) {
    this.context = context;
    this.color = color;
    initSize();
    initPainter();
  }

  private void initSize() {
    this.extraMargin = EtcUtils.dpToPx(15, context);
    this.externalStrokeWidth = EtcUtils.dpToPx(20, context);
    this.strokeWidth = EtcUtils.dpToPx(2, context);
    this.margin = EtcUtils.dpToPx(0, context);
  }

  private void initPainter() {
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStrokeWidth(strokeWidth);
    paint.setColor(color);
    paint.setStyle(Paint.Style.STROKE);
    paintRed = new Paint();
    paintRed.setAntiAlias(true);
    paintRed.setStrokeWidth(strokeWidth);
    paintRed.setColor(lowRangeColor);
    paintRed.setStyle(Paint.Style.STROKE);
  }

  private void initCircle() {
    int padding = externalStrokeWidth + margin + extraMargin;
    circle = new RectF();
    circle.set(padding, padding, width - padding, height - padding);
  }

  @Override public void draw(Canvas canvas) {
    //canvas.drawArc(circle, startAngle, endAngle, false, paint);
    //canvas.drawArc(circle, startAngle, endAngle *0.1f, false, paintRed);
  }

  @Override public void setColor(int color) {
    this.color = color;
  }

  @Override public int getColor() {
    return color;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.width = width;
    this.height = height;
    initCircle();
  }
}
