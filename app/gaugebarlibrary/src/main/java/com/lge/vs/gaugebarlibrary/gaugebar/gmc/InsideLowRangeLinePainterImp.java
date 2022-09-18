package com.lge.vs.gaugebarlibrary.gaugebar.gmc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.lge.vs.gaugebarlibrary.gaugebar.common.InsideLowRangeLinePainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class InsideLowRangeLinePainterImp implements InsideLowRangeLinePainter {

  private Context mContext;
  private Paint mRedPaint;
  private int mWidth, mHeight;
  private int mLowRangeColor = 0xFFED2D30;
  private int mColor;
  private int mLineThick;
  private int mYPosition;
  private int mLineLength;

  public InsideLowRangeLinePainterImp(int color, Context context) {
    this.mContext = context;
    this.mColor = color;
    initSize();
    initPainter();
  }

  private void initSize() {
    this.mLineThick = EtcUtils.dpToPx(5, mContext);
    this.mYPosition = EtcUtils.dpToPx(66, mContext) + EtcUtils.dpToPx(20, mContext);
    this.mLineLength = EtcUtils.dpToPx(31, mContext);
  }

  private void initPainter() {
    mRedPaint = new Paint();
    mRedPaint.setAntiAlias(true);
    mRedPaint.setStrokeWidth(mLineThick);
    mRedPaint.setColor(mLowRangeColor);
    mRedPaint.setStyle(Paint.Style.STROKE);
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawLine(0, mYPosition, mLineLength, mYPosition, mRedPaint);
  }

  @Override public void setColor(int color) {
    this.mColor = color;
  }

  @Override public int getColor() {
    return mColor;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.mWidth = width;
    this.mHeight = height;
  }
}
