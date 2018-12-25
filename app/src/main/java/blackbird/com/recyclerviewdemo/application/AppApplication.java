package blackbird.com.recyclerviewdemo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.x5webview.utils.WebViewsUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by yzzhang on 2017/12/8.
 */

public class AppApplication extends Application {
    public static AppApplication instance;
  /*  @Inject*/
    public Context mContext;
    private long downloadFerence;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        initApplicationConpent();
        WebViewsUtils.initTBSX5(this);
        //初识化网络框架
        //RxRetrofitApp.init(this, true);
        //GodEye.instance().installAll(this, new CrashFileProvider(this));
    }



    private void initApplicationConpent() {
      // mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }
    public static Context getApplicationContexts() {
        return instance.getApplicationContext();
    }

    public synchronized static AppApplication getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        registerModules();
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Log.e("TAG", "onActivityCreated:--------- > "+activity.getClass().getSimpleName() );

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.e("TAG", "onActivityDestroyed:--------- > "+activity.getClass().getSimpleName() );
            }
        });
    }
    /**
     * 初始化模块
     */
    private void registerModules() {

    }
    public void setDownloadFerence(long downloadFerence) {
        this.downloadFerence = downloadFerence;
    }

    public long getDownloadFerence() {
        return downloadFerence;
    }


    /**
     * 判断缓存数据是否可读
     *
     * @param cachefile
     * @return
     */
    private boolean isReadDataCache(String cachefile) {
        return readObject(cachefile) != null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    private boolean isExistDataCache(String cachefile) {
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }


    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }


    /**
     * 保存磁盘缓存
     *
     * @param key
     * @param value
     * @throws IOException
     */
    public void setDiskCache(String key, String value) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("cache_" + key + ".data", Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.flush();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取磁盘缓存数据
     *
     * @param key
     * @return
     * @throws IOException
     */
    public String getDiskCache(String key) throws IOException {
        FileInputStream fis = null;
        try {
            fis = openFileInput("cache_" + key + ".data");
            byte[] datas = new byte[fis.available()];
            fis.read(datas);
            return new String(datas);
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 保存对象
     * path/data/data/
     *
     * @param ser  Serializable序列化后数据
     * @param file File Name
     * @throws IOException Exception
     */
    public void saveObject(final Serializable ser, final String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "saveObject:  Exception ERROR : " + e.toString());
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
                Log.e("TAG", "saveObject:  Exception ERROR : " + e.toString());
            }
            try {
                fos.close();
            } catch (Exception e) {
                Log.e("TAG", "saveObject:  Exception ERROR : " + e.toString());
            }
        }
    }

    /**
     * 删除数据
     *
     * @param file File Name
     */
    public void delFileData(String file) {
        File data = getFileStreamPath(file);
        data.delete();
    }

    /**
     * 读取对象
     *
     * @param file File Name
     * @return data
     * @throws IOException FileNotFoundException
     */
    public Serializable readObject(final String file) {
      /*  if (!isExistDataCache(file)){
            Log.e("TAG", "readObject: ------------- >>>>>>>>>>"+file );
            return null;}*/

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            Log.e("TAG", "readObject: ObjectInputStream----------- >>>>>>>>>>"+file );

            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG", "readObject: FileNotFoundException----------- >>>>>>>>>>"+e.getMessage() );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "readObject: Exception----------- >>>>>>>>>>"+e.getMessage() );
            ;
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = getFileStreamPath(file);
                data.delete();
            }

        } finally {
            try {
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
