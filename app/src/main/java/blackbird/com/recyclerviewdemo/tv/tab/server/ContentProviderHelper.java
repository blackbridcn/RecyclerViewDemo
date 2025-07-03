package blackbird.com.recyclerviewdemo.tv.tab.server;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ContentProviderHelper {

    private static final String CONFIG_LAUNCHER_URI_AUTHORITY = "com.oem.config_launcher";

    private static final String IS_PREVIEW = "isPreview";

    //background
    private static final String CONFIG_LAUNCHER_BACKGROUND = "background";
    public static final Uri LAUNCHER_BACKGROUND_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath(CONFIG_LAUNCHER_BACKGROUND)
            .build();

    public static final Uri LAUNCHER_BACKGROUND_REQUEST_DELETE_PREVIEW_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath("delete_background_preview")
            .build();
    //background

    //logo
    private static final String CONFIG_LAUNCHER_LOGO = "logo";
    public static final Uri LAUNCHER_LOGO_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath(CONFIG_LAUNCHER_LOGO)
            .build();
    public static final Uri LAUNCHER_LOGO_REQUEST_DELETE_PREVIEW_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath("delete_logo_preview")
            .build();
    //logo

    //app list
    private static final String CONFIG_LAUNCHER_APPS = "apps";
    public static final Uri LAUNCHER_APPS_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath(CONFIG_LAUNCHER_APPS)
            .build();
    public static final Uri LAUNCHER_APPS_REQUEST_DELETE_PREVIEW_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath("delete_apps_preview")
            .build();
    //app list

    //favorite list
    private static final String CONFIG_LAUNCHER_FAV_APPS = "fav_apps";
    public static final Uri LAUNCHER_FAV_APPS_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath(CONFIG_LAUNCHER_FAV_APPS)
            .build();
    public static final Uri LAUNCHER_FAV_APPS_REQUEST_DELETE_PREVIEW_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONFIG_LAUNCHER_URI_AUTHORITY)
            .appendPath("delete_fav_apps_preview")
            .build();
    //favorite list

    public static ServerResult getLauncherLogo(Context context) {
        ParcelFileDescriptor logoFd = null;
        Drawable drawable = null;
        // 获取launcher logo
        try {
            logoFd = context.getContentResolver().openFile(LAUNCHER_LOGO_URI, "r", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (logoFd != null) {
            // 获取logo,可以在本地保存一份
            Bitmap logo = BitmapFactory.decodeFileDescriptor(logoFd.getFileDescriptor());
            // todo 看launcher如何使用了
            drawable = new BitmapDrawable(context.getResources(), logo);
//            binding.launcherLogo.setImageDrawable(drawable);

        }
        ServerResult serverResult = isPreviewLogo(context);
        serverResult.logoDrawable = drawable;
        return serverResult;
    }



    public static ServerResult isPreviewLogo(Context context) {
        ServerResult result = new ServerResult();
        Cursor cursor = context.getContentResolver().
                query(LAUNCHER_LOGO_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // 查询下是不是预览logo, 预览logo显示一次后就立即删除
            int columnIndex = cursor.getColumnIndex(IS_PREVIEW);
            int cornerIndex = cursor.getColumnIndex("launcher_app_logo_type");

            if (columnIndex > -1) {
                result.isPreviewLogo = "true".equals(cursor.getString(columnIndex));
            }
            if (cornerIndex > -1) {
                boolean isCorner = "true".equals(cursor.getString(cornerIndex));
                if (isCorner) {
                    int appLogoIndex = cursor.getColumnIndex("app_logo_value");
                    if (appLogoIndex > -1) {
                        int cornerVal = cursor.getInt(appLogoIndex);
//                        if (SpUtils.getInt(context,SpUtils.FILE_CUSTOM,SpUtils.KEY_ITEM_CORNER) == cornerVal) {
//                            result.cornerVal = -1;  //相同的值不处理，设置为-1
//                        } else {
                            result.cornerVal = cornerVal;
                         //   SpUtils.putInt(context,SpUtils.FILE_CUSTOM,SpUtils.KEY_ITEM_CORNER,result.cornerVal);
//                        }
                    }
                }
            }

            cursor.close();
        }
        return result;
    }

    public static Drawable getLauncherBackground(Context context) {
        ParcelFileDescriptor backgroundFd = null;
        Drawable drawable = null;
        // 获取launcher background
        try {
            backgroundFd = context.getContentResolver().openFile(LAUNCHER_BACKGROUND_URI, "r", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (backgroundFd != null) {
            // 获取background,可以在本地保存一份
            Bitmap background = BitmapFactory.decodeFileDescriptor(backgroundFd.getFileDescriptor());
            drawable = new BitmapDrawable(context.getResources(), background);
        }
        isPreviewBackground(context);
        return drawable;
    }

    public static boolean isPreviewBackground(Context context) {
        boolean isPreviewBackground = false;
        Cursor cursor = context.getContentResolver().
                query(LAUNCHER_BACKGROUND_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // 查询下是不是预览logo, 预览logo显示一次后就立即删除
            int columnIndex = cursor.getColumnIndex(IS_PREVIEW);
            if (columnIndex > -1) {
                isPreviewBackground = "true".equals(cursor.getString(columnIndex));
            }
            cursor.close();
        }
        return isPreviewBackground;
    }

    public static String getLauncherAppsList(Context context) {
        ParcelFileDescriptor appsFd = null;
        // 获取APP列表配置文件
        try {
            appsFd = context.getContentResolver().openFile(LAUNCHER_APPS_URI, "r", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Cursor cursor = context.getContentResolver().query(LAUNCHER_APPS_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // 查询下是不是预览文件, 预览文件显示一次后就立即删除
            int columnIndex = cursor.getColumnIndex(IS_PREVIEW);
            if (columnIndex > -1) {
                //isPreviewApps = "true".equals(cursor.getString(columnIndex));
            }
            cursor.close();
        }

        if (appsFd != null) {
            try {
                StringBuilder s = new StringBuilder();
                String line = null;
                BufferedReader reader = new BufferedReader(new FileReader(appsFd.getFileDescriptor()));
                while ((line = reader.readLine()) != null) {
                    s.append(line);
                }
                reader.close();
                return s.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getLauncherFavList(Context context) {
        ParcelFileDescriptor favAppsFd = null;
        try {
            favAppsFd = context.getContentResolver().openFile(LAUNCHER_FAV_APPS_URI, "r", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (favAppsFd != null) {
            try {
                StringBuilder s = new StringBuilder();
                String line = null;
                BufferedReader reader = new BufferedReader(new FileReader(favAppsFd.getFileDescriptor()));
                while ((line = reader.readLine()) != null) {
                    s.append(line);
                }
                reader.close();
                return s.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
