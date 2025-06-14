package blackbird.com.recyclerviewdemo.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import org.x5webview.utils.WebViewsUtils
import java.io.*


/**
 * File: RcecyclerApplication.java
 * Author: yuzhuzhang
 * Create: 2021/2/13 3:02 PM
 *
 * -----------------------------------------------------------------
 * Description:
 *
 *
 * -----------------------------------------------------------------
 */
class RcecyclerApplication : Application()  {


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        closeAndroidPDialog()
        registerActivityLifecycleCallbacks(callback)
        WebViewsUtils.initTBSX5(this)

    }


    var callback = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }

    private fun closeAndroidPDialog() {
        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.setAccessible(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 判断缓存数据是否可读
     *
     * @param cachefile
     * @return
     */
    private fun isReadDataCache(cachefile: String): Boolean {
        return readObject(cachefile) != null
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    private fun isExistDataCache(cachefile: String): Boolean {
        var exist = false
        val data = getFileStreamPath(cachefile)
        if (data.exists()) exist = true
        return exist
    }


    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    private fun clearCacheFolder(dir: File?, curTime: Long): Int {
        var deletedFiles = 0
        if (dir != null && dir.isDirectory) {
            try {
                for (child in dir.listFiles()) {
                    if (child.isDirectory) {
                        deletedFiles += clearCacheFolder(child, curTime)
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++
                        }
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return deletedFiles
    }


    /**
     * 保存磁盘缓存
     *
     * @param key
     * @param value
     * @throws IOException
     */
    @Throws(IOException::class)
     fun setDiskCache(key: String, value: String) {
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput("cache_$key.data", MODE_PRIVATE)
            fos.write(value.toByteArray())
            fos.flush()
        } finally {
            try {
                fos!!.close()
            } catch (e: java.lang.Exception) {
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
    @Throws(IOException::class)
     fun getDiskCache(key: String): String? {
        var fis: FileInputStream? = null
        return try {
            fis = openFileInput("cache_$key.data")
            val datas = ByteArray(fis.available())
            fis.read(datas)
            String(datas)
        } finally {
            try {
                fis!!.close()
            } catch (e: java.lang.Exception) {
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
     fun saveObject(ser: Serializable?, file: String?) {
        var fos: FileOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            fos = openFileOutput(file, MODE_PRIVATE)
            oos = ObjectOutputStream(fos)
            oos.writeObject(ser)
            oos.flush()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                oos!!.close()
            } catch (e: java.lang.Exception) {
            }
            try {
                fos!!.close()
            } catch (e: java.lang.Exception) {
            }
        }
    }

    /**
     * 删除数据
     *
     * @param file File Name
     */
     fun delFileData(file: String?) {
        val data = getFileStreamPath(file)
        data.delete()
    }

    /**
     * 读取对象
     *
     * @param file File Name
     * @return data
     * @throws IOException FileNotFoundException
     */
     fun readObject(file: String?): Serializable? {
        var fis: FileInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            fis = openFileInput(file)
            ois = ObjectInputStream(fis)
            return ois.readObject() as Serializable
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            // 反序列化失败 - 删除缓存文件
            if (e is InvalidClassException) {
                val data = getFileStreamPath(file)
                data.delete()
            }
        } finally {
            try {
                ois!!.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            try {
                fis!!.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    companion object {
        @JvmStatic
        var application: RcecyclerApplication? = null
            get



        var downloadFerence :Long? =0
            set

        fun getInstance(): RcecyclerApplication? {
            return application;
        }
    }
}