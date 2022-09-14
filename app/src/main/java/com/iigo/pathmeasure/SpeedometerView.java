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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

public class SpeedometerView extends View {
    private static final String TAG = "CarTrackView";
    private final static float SPEED_RATIO = 0.001f; //控制速度
    private Bitmap activeBar, group;
    private Bitmap carBitmap; //小车bitmap
    private Bitmap diagnalBitmap;

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
    Context mContext;

    public SpeedometerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SpeedometerView(Context context,  AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SpeedometerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }
    private static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(context.getResources(), drawableId);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private void init(){
        carBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_car);
        diagnalBitmap = getBitmap(mContext, R.drawable.ic_active_bar_tick_lrg_70);
        activeBar = BitmapFactory.decodeResource(getResources(), R.drawable.active_bar_tick_lrg_70_dp);
        group = BitmapFactory.decodeResource(getResources(), R.drawable.group);

//        Drawable drawable = getResources().getDrawable(R.drawable.ic_active_bar_tick_lrg_70);
//        drawable.setBounds(0,0,100,100);
//        diagnalBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        //diagnalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher_background);
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

        canvas.drawColor(Color.RED);

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
        //distanceRatio=0.5f;

        PathMeasure pathMeasure = new PathMeasure(path, false);
        float distance = pathMeasure.getLength() * distanceRatio;

//        Matrix carMatrix = new Matrix();
//        pathMeasure.getMatrix(distance, carMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG); //获取距离的坐标和旋转角度
//        carMatrix.preTranslate(-carBitmap.getWidth() / 2, -carBitmap.getHeight() / 2);
//        canvas.drawBitmap(carBitmap,carMatrix,carPaint);

        canvas.drawPath(path, contourPaint);

        Path dst = new Path();
        dst.moveTo(-defaultX,defaultX);
        pathMeasure.getSegment(0, distance, dst, true);
        followingLinePaint.setShader(new LinearGradient(-defaultX,0, width,0, ProgressColors, ProgressGradientPos, Shader.TileMode.CLAMP));
        canvas.drawPath(dst, followingLinePaint);

        pathMeasure.getPosTan(distance, pos, tan);
        int degree = (int) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);

        Log.d("aorura", "Degree: " + degree);
        canvas.drawBitmap(group, pos[0]-group.getWidth()/2, pos[1]-group.getHeight()/2,carPaint);

        Matrix diagnalMatrix = new Matrix();
        pathMeasure.getMatrix(distance, diagnalMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        if (degree == -45) {
            diagnalMatrix.preRotate( 5);
        }
        diagnalMatrix.preTranslate(-activeBar.getWidth()*0.3f, -activeBar.getHeight()*0.7f);
        canvas.drawBitmap(activeBar, diagnalMatrix, carPaint);

        invalidate();
    }
}
