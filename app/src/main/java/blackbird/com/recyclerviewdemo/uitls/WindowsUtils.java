package blackbird.com.recyclerviewdemo.uitls;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by yuzhu on 2017/3/3.
 */

public class WindowsUtils {

    public static void setTitleBar(final Activity mActivity, final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {  //解决状态栏高度为warp_content或match_parent问题
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        lp.height = view.getHeight() + getStatusBarHeight(mActivity);
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(mActivity),
                                view.getPaddingRight(), view.getPaddingBottom());
                    }
                });
            } else {
                lp.height += getStatusBarHeight(mActivity);
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(mActivity),
                        view.getPaddingRight(), view.getPaddingBottom());
            }
            fullScreen(mActivity);
           //setTranslucentStatus(mActivity,true);
        }
    }

    /**
     * 获取当前设备状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    /**
     * 设置状态栏透明
     *
     * @param on
     */
    @TargetApi(19)
    public static void setTranslucentStatus(Activity mActivity, boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

}
