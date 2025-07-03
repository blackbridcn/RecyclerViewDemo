package blackbird.com.recyclerviewdemo.tv.tab;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static String APPS = "/apps";
    public static String FAV = "/favorites";
    public static String GALLERY = "/gallery";
    public static String BROWSER_CUSTOM = "browser_custom";
    private final static String CUSTOMER_BACKGROUND = "system/etc/launcher_bg.png";
    private final static String CUSTOMER_LOGO = "system/etc/launcher_logo.png";

    public static void saveToFav(Context context, Object object) {
        writeToFile(context.getDataDir() + FAV, Object2String(object));
    }

    public static ArrayList<AppInfo> getFromFav(Context context) {
        ArrayList<AppInfo> mFavItems = (ArrayList<AppInfo>) String2Object(
                readFromFile(context.getDataDir() + FAV));
        if (mFavItems != null && !mFavItems.isEmpty()) {
            return mFavItems;
        }
        mFavItems = new ArrayList<>();
        mFavItems.add(new AppInfo(AppInfo.ADD_PRIORITY));
        return mFavItems;
        //return (ArrayList<AppInfo>) String2Object(readFromFile(context.getDataDir() + FAV));
    }

    public static void saveToApps(Context context, Object object) {
        writeToFile(context.getDataDir() + APPS, Object2String(object));
    }

    public static ArrayList<AppInfo> getFromApps(Context context) {
        return (ArrayList<AppInfo>) String2Object(readFromFile(context.getDataDir() + APPS));
    }





    public static void saveToGallery(Context context, ArrayList<Integer> list) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(context.getDataDir() + GALLERY))) {
            for (Integer i : list) {
                writer.write(i + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> getFromGallery(Context context) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(context.getDataDir() + GALLERY))) {
            ArrayList<Integer> myList = new ArrayList<>();
            String line;
            while (!TextUtils.isEmpty(line = reader.readLine())) {
                myList.add(Integer.parseInt(line));
            }
            return myList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object String2Object(String objectString) {
        if (objectString == null) {
            return null;
        }

        ObjectInputStream objectInputStream = null;
        try {
            byte[] bytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String Object2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToFile(String fPath, String inputString) {
        File file = new File(fPath);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(inputString.getBytes());
            fos.write("\r\n".getBytes());
            fos.getFD().sync();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                StringBuilder s = new StringBuilder();
                String line = null;
                BufferedReader reader = new BufferedReader(new FileReader(file));
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

    public static String readFromRes(Context context, int id) {
        //获取过滤列表
        InputStreamReader inputStream = new InputStreamReader(context.getResources().openRawResource(id));
        BufferedReader bufReader = new BufferedReader(inputStream);
        String line = null;
        StringBuilder result = new StringBuilder();
        try {
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //过滤结束
        return result.toString();
    }

    public static Drawable initAppIcon(Context context, String pkgName) {
        AssetManager assets = context.getAssets();
        try {
            InputStream open = assets.open(pkgName + ".png");
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(), open);
            return drawable;
        } catch (IOException e) {
            return null;
        }
    }

    public static Drawable getSmallIconFromAssert(Context context, String index) {
        AssetManager assets = context.getAssets();
        try {
            InputStream open = assets.open(index + ".png");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeStream(open, null, options);
            return new BitmapDrawable(context.getResources(), bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getLauncherBackground(Context context) {
        BitmapDrawable drawable = null;
        try {
            FileInputStream fis = new FileInputStream(CUSTOMER_BACKGROUND);
            drawable = new BitmapDrawable(context.getResources(), fis);
            return drawable;
        } catch (FileNotFoundException e) {
            //LogUtils.d("system/etc/launcher_bg.png no found");
            //e.printStackTrace();
        }
        return null;
    }

    public static Drawable getCustomAppIcon(Context context) {
        BitmapDrawable drawable = null;
        try {
            FileInputStream fis = new FileInputStream(CUSTOMER_LOGO);
            drawable = new BitmapDrawable(context.getResources(), fis);
            return drawable;
        } catch (FileNotFoundException e) {
          //  LogUtils.d("system/etc/launcher_logo.png no found");
            //e.printStackTrace();
        }
        return null;
    }

    public static boolean copyFile(File source, File target) {
        boolean copyFinish = false;
        //LogUtils.d(String.format("copy %s file to %s", source.getPath(), target.getPath()));
        try {
            if (!source.exists()) {
             //   LogUtils.d(String.format("%s do not exist", source.getPath()));
                return copyFinish;
            }

            if (target.exists()) {
                target.delete();
            }
            FileInputStream fileInputStream = new FileInputStream(source);
            FileOutputStream fileOutputStream = new FileOutputStream(target);
            byte[] bArr = new byte[8192];
            int read = 0;
            while ((read = fileInputStream.read(bArr)) != -1) {
                fileOutputStream.write(bArr, 0, read);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            fileInputStream.close();
            copyFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copyFinish;
    }

    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return options.outWidth != -1;
    }
}
