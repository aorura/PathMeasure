package com.iigo.pathmeasure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SpeedometerView extends View {
    private static final String TAG = "CarTrackView";
    private final static float SPEED_RATIO = 0.006f; //控制速度

    private Bitmap carBitmap; //小车bitmap
    private Paint contourPaint; //轮廓画笔
    private float distanceRatio = 0; //距离比例
    private Paint carPaint; //画小车的画笔
    private Paint followingLinePaint;
    private Paint needlePaint;
    private int[] ProgressColors = {0xFF5E92E9, 0xFFFBD9E4, 0xFFE6DFE7};
    private float[] ProgressGradientPos = {0.0f, 0.4f, 0.6f};
//    private int[] needleColor = {0x0FFFFFFF,0x32FFFFFF, 0x32FFFFFF, 0x0FFFFFFF};
//    private float[] needlePos = {0.0f, 0.1f, 0.1f, 0.1f};
    private int[] colors = {Color.RED, Color.BLUE};
    private float[] poss = {0.5f, 0.5f};

    private float[] pos = new float[2];
    private float[] tan = new float[2];

    public SpeedometerView(Context context) {
        super(context);

        init();
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init(){
        carBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_car);

        contourPaint = new Paint();
        contourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contourPaint.setStyle(Paint.Style.STROKE);
        contourPaint.setStrokeWidth(5);
        contourPaint.setStrokeJoin(Paint.Join.ROUND);

        followingLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        followingLinePaint.setStyle(Paint.Style.STROKE);
        followingLinePaint.setColor(0xFFFF0000);
        followingLinePaint.setStrokeWidth(10);
        followingLinePaint.setStrokeJoin(Paint.Join.ROUND);

        needlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        needlePaint.setStyle(Paint.Style.STROKE);
        needlePaint.setStrokeWidth(5);
        needlePaint.setColor(0xFFFFFFFF);

        carPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int defaultX = width/8;
        int defaultY = height/2;

        canvas.translate(defaultX, defaultY); //移动canvas坐标系

        Path path = new Path();  //第一段为直线，第二段为曲线，第三段为直线

        path.moveTo(- defaultX,  defaultX);
        path.lineTo(0, 0);
        path.cubicTo(0, 0, 18, -18, +50, -20); //画条三阶贝塞尔曲线
        path.lineTo(width-defaultX, -20);

        distanceRatio += SPEED_RATIO;
        if(distanceRatio >=1){
            distanceRatio = 0;
        }

        PathMeasure pathMeasure = new PathMeasure(path, false);
        float distance = pathMeasure.getLength() * distanceRatio;

        Matrix carMatrix = new Matrix();
        pathMeasure.getMatrix(distance, carMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG); //获取距离的坐标和旋转角度
        carMatrix.preTranslate(-carBitmap.getWidth() / 2, -carBitmap.getHeight() / 2);

        canvas.drawPath(path, contourPaint);


        Path dst = new Path();
        dst.moveTo(-defaultX,defaultX);
        pathMeasure.getSegment(0, distance, dst, true);
        followingLinePaint.setShader(new LinearGradient(-defaultX,0, width,0, ProgressColors, ProgressGradientPos, Shader.TileMode.CLAMP));
        canvas.drawPath(dst, followingLinePaint);

        pathMeasure.getPosTan(distance, pos, tan);
        int degree = (int) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
        needlePaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.NORMAL));
        canvas.drawLine(pos[0] - 20, pos[1] + 20, pos[0] + 20 + degree, pos[1] - 20, needlePaint);
        //needlePaint.setShader(new LinearGradient(-defaultX,0,0,defaultX, colors, poss, Shader.TileMode.CLAMP));
        needlePaint.setMaskFilter(null);
        needlePaint.setColor(0x32FFFFFF);
        canvas.drawLine(pos[0] - 30, pos[1] + 30, pos[0] + 100 + degree, pos[1] - 100, needlePaint);

        invalidate();
    }
}
