package io.github.grooters.idles.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class ResourcEr {

    public static float dp2px(float dp){

        return (int) (0.5f + dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float px2dp(float px){

        return (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenWidth(Activity activity){
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static int getScreenHeight(Activity activity){
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.y;
    }

}
