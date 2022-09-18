package com.lge.vs.gaugebarlibrary.speedometer.cadillac;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;

import com.lge.vs.gaugebarlibrary.R;
import com.lge.vs.gaugebarlibrary.gaugebar.common.GaugeBarProgressPainter;
import com.lge.vs.gaugebarlibrary.utils.EtcUtils;

public class GaugeBarProgressPainterImp implements GaugeBarProgressPainter {
    private Paint mContourPaint;
    private float mDistanceRatio = 0;
    private Paint mFollowingLinePaint;
    private int[] mProgressColors = {0xFF5E92E9, 0xFFFBD9E4, 0xFFE6DFE7};
    private float[] mProgressGradientPos = {0.0f, 0.4f, 0.6f};
    private int mWidth, mHeight;
    private int mColor;
    private float mProgressLength=0.0f;
    private float mMax = 512f;
    private int mMargine=0;
    private Context mContext;
    private int mPadding;
    private float[] mPos = new float[2];
    private float[] mTan = new float[2];
    private Bitmap mActiveBar, mGroup;
    private Paint diagonalPaint;

    public GaugeBarProgressPainterImp(int color, float max, int margin, Context context) {
        mMax = max;
        mMargine = margin;
        mContext = context;
        initSize();
        init();
    }

    private void initSize() {
        this.mPadding = EtcUtils.dpToPx(40, mContext);
    }

    private void init() {
        mActiveBar = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.active_bar_tick_lrg_70_dp);
        mGroup = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.group_60);

        mContourPaint = new Paint();
        mContourPaint.setAntiAlias(true);
        mContourPaint.setStyle(Paint.Style.STROKE);
        mContourPaint.setColor(0xFF969696);
        mContourPaint.setStrokeWidth(10);
        mContourPaint.setStrokeJoin(Paint.Join.ROUND);

        mFollowingLinePaint = new Paint();
        mFollowingLinePaint.setAntiAlias(true);
        mFollowingLinePaint.setStyle(Paint.Style.STROKE);
        mFollowingLinePaint.setColor(0xFFFF0000);
        mFollowingLinePaint.setStrokeWidth(10);
        mFollowingLinePaint.setStrokeJoin(Paint.Join.ROUND);

        diagonalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int defaultX = (int) (width*0.12f);
        int defaultY = (int) (height*0.6f);

        canvas.translate(defaultX, defaultY);

        Path path = new Path();

        path.moveTo(- defaultX,  height-defaultY*0.9f);
        path.lineTo(0, 0);
        path.cubicTo(0, 0, 18, -18, +50, -20);
        path.lineTo(width-defaultX-mPadding, -20);

        PathMeasure pathMeasure = new PathMeasure(path, false);
        float distance = pathMeasure.getLength() * mDistanceRatio;
        canvas.drawPath(path, mContourPaint);

        Path dst = new Path();
        dst.moveTo(-defaultX,defaultX);
        pathMeasure.getSegment(0, distance, dst, true);
        mFollowingLinePaint.setShader(new LinearGradient(-defaultX,0, width,0, mProgressColors, mProgressGradientPos, Shader.TileMode.CLAMP));
        canvas.drawPath(dst, mFollowingLinePaint);

        pathMeasure.getPosTan(distance, mPos, mTan);
        int degree = (int) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);

        Matrix groupMatrix = new Matrix();
        pathMeasure.getMatrix(distance, groupMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        groupMatrix.preTranslate(-mGroup.getWidth()*0.48f, -mGroup.getHeight()*0.5f);
        if (((int)mProgressLength) > 5) {
            canvas.drawBitmap(mGroup, groupMatrix, diagonalPaint);
        }
        Matrix diagonalMatrix = new Matrix();
        pathMeasure.getMatrix(distance, diagonalMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        if (degree == -45) {
            diagonalMatrix.preRotate( 5);
        }
        diagonalMatrix.preTranslate(-mActiveBar.getWidth()*0.3f, -mActiveBar.getHeight()*0.7f);
        if (((int)mProgressLength) > 15) {
            canvas.drawBitmap(mActiveBar, diagonalMatrix, diagonalPaint);
        }
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
    public void setValue(float value) {
        this.mProgressLength = value;
        this.mDistanceRatio = value / mMax;
    }

    public float getMax() {
        return mMax;
    }

    public void setMax(float max) {
        this.mMax = max;
    }
}
