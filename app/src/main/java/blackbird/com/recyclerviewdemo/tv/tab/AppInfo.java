package blackbird.com.recyclerviewdemo.tv.tab;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;



import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class AppInfo implements Comparable<AppInfo>, Serializable {

    private static final long serialVersionUID = 9984631225347L;

    private transient SeriDrawable mIcon;
    private transient SeriDrawable mBanner;
    private transient CharSequence mLabel;
    public final String packageName;
    public long mPriority;
    public final static long ADD_PRIORITY = -10086L;


    public AppInfo(long priority) {
        mIcon = null;
        mBanner = null;
        mLabel = null;
        packageName = null;
        mPriority = priority;
    }

    public AppInfo(ResolveInfo info, long priority) {
        packageName = info.activityInfo.packageName;
        mPriority = priority;
    }

    @Override
    public int compareTo(AppInfo info) {
        long priorityDiff = info.mPriority - mPriority;
        if (priorityDiff != 0) {
            return priorityDiff < 0L ? -1 : 1;
        } else {
            if (mLabel == null && info.mLabel != null) {
                return mLabel.toString().compareTo(info.mLabel.toString());
            }
        }
        return 0;
    }

    public class SeriDrawable implements Serializable {
        private static final long serialVersionUID = 190744L;
        private byte[] mBitmapBytes = null;

        public SeriDrawable(byte[] bitmapBytes) {
            this.mBitmapBytes = bitmapBytes;
        }

        public SeriDrawable(Drawable drawable) {
            mBitmapBytes = drawableToBytes(drawable);
        }

        public Drawable getDrawable() {
            return bytesToDrawable(mBitmapBytes);
        }

        public byte[] getBitmapBytes() {
            return this.mBitmapBytes;
        }

        public byte[] drawableToBytes(Drawable d) {
            Bitmap bitmap = this.drawableToBitmap(d);
            return this.bitmapToBytes(bitmap);
        }

        public Bitmap drawableToBitmap(Drawable drawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        public byte[] bitmapToBytes(Bitmap bm) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        public Bitmap bytesToBitmap(byte[] b) {
            if (b.length != 0) {
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            }
            return null;
        }

        public Drawable bytesToDrawable(byte[] b) {
            Bitmap bitmap = this.bytesToBitmap(b);
            return this.bitmapToDrawable(bitmap);
        }

        public Drawable bitmapToDrawable(Bitmap bitmap) {
            BitmapDrawable bd = new BitmapDrawable(bitmap);
            Drawable d = bd;
            return d;
        }
    }

    public SeriDrawable getIcon(@NonNull Context context) {
        if (mIcon == null) {
            mIcon = new SeriDrawable(AppUtils.getAppIcon(context, packageName));
        }
        return mIcon;
    }

    public SeriDrawable getBanner(@NonNull Context context) {
        if (mBanner == null) {
            Drawable drawable = FileUtils.initAppIcon(context, packageName);
            if (drawable != null) {
                mBanner = new SeriDrawable(drawable); //优先从本地获取banner图标
            } else {
                drawable = AppUtils.getAppBanner(context, packageName);
                if (drawable != null) {
                    mBanner = new SeriDrawable(drawable);
                }
            }
        }
        return mBanner;
    }

    public CharSequence getLabel(@NonNull Context context) {
        if (mLabel == null) {
            mLabel = AppUtils.getAppLabelByPackageName(context, packageName);
        }
        return mLabel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppInfo appInfo = (AppInfo) o;
        return mPriority == appInfo.mPriority && Objects.equals(packageName, appInfo.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, mPriority);
    }

    public static ArrayList<AppInfo> getInstalledList(Context context, ArrayList<AppInfo> appInfos) {
        ArrayList<AppInfo> installList = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < appInfos.size(); i++) {
            AppInfo appInfo = appInfos.get(i);
            if (AppUtils.isPkgInstalled(context, appInfo.packageName) || appInfo.mPriority == ADD_PRIORITY) {
                long end = System.currentTimeMillis();
                Log.d("InfoTest", "getInstallList cost->" + (end - start) + "ms");
                start = end;
                installList.add(appInfo);
            }
        }
        long end = System.currentTimeMillis();
        Log.d("InfoTest", "getInstallList total cost->" + (end - start) + "ms");
        return installList;
    }
}
