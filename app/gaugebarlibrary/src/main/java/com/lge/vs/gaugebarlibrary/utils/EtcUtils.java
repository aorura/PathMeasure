package com.lge.vs.gaugebarlibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

public class EtcUtils {

  private EtcUtils() {
  }

  public static int dpToPx(float dp, Context context) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    float px = dm.density * dp;
    return (int) (px + 0.5f);
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
}
