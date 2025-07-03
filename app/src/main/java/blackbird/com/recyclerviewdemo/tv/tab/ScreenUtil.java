package blackbird.com.recyclerviewdemo.tv.tab;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtil {

    public static int dp2pxInt(Context context, int dpValue) {
        float densityScale = context.getResources().getDisplayMetrics().density;
        // 四舍五入取整
        return (int) (dpValue * densityScale);
    }

    public static float dp2pxFloat(Context context, float dpValue) {
        float densityScale = context.getResources().getDisplayMetrics().density;
        // 四舍五入取整
        return dpValue * densityScale;
    }

    public static int[] getRealPixels(Context context) {
        int[] pixels = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        pixels[0] = widthPixels;
        pixels[1] = heightPixels;
        return pixels;
    }

    public static float px2dp(Context context,int px) {
        float densityScale = context.getResources().getDisplayMetrics().density;
        return px/densityScale;
    }

}
