package blackbird.com.recyclerviewdemo.uitls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.org.appconstant.HomeButtonTypeContentKotlin;

import org.x5webview.utils.WebViewsUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import blackbird.com.recyclerviewdemo.HomeActivity;
import blackbird.com.recyclerviewdemo.Main2Activity;
import blackbird.com.recyclerviewdemo.MainActivity;
import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.application.AppApplication;
import blackbird.com.recyclerviewdemo.bean.MenuCustomContent;
import blackbird.com.recyclerviewdemo.bean.MenuGroupResourceData;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;


/**
 * Created by yzzhang on 2017/12/8.
 */

public class AppMainButtonDataUtils {
    private static class AppMainButtonDataUtilsHolder {
        private static final AppMainButtonDataUtils INSTANCE = new AppMainButtonDataUtils();
    }

    public static AppMainButtonDataUtils getInstance() {
        return AppMainButtonDataUtilsHolder.INSTANCE;
    }

    /**
     * 获取全部按钮数据【本地数据+网络数据】
     */
    public List<MenuGroupResourceData> getAllFunctionButton() {
        List<MenuGroupResourceData> list = (List<MenuGroupResourceData>) AppApplication.getInstance().readObject(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_ALL_BUTTON_DATA());
        if (list == null || list.size() < 1) {
            list = getLoacalAllButtonData();
        }
        return list;
    }

    /**
     * 点击按钮操作
     * @param mContext
     * @param mMenuResourceData
     */
    public void handleButtonData(Activity mContext,MenuResourceData mMenuResourceData){
        String type = mMenuResourceData.getBtnType();
        Intent intent = null;
        if (type.equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_CLASS_J_ACTIVITY())) {
            intent = new Intent();
            intent.setClass(mContext, mMenuResourceData.getClazz());
            mContext.startActivityForResult(intent, MainActivity.BTN_MENU_REQUEST_CODE);
        } else if (type.equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_CLASS_J_ACTIVITY())) {
            intent = new Intent();
            intent.setClassName(mContext.getPackageName(), mMenuResourceData.getActyName());
            mContext.startActivity(intent);
        } else if (type.equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_STRING_J_ACTIVITY())) {
            intent = new Intent(mMenuResourceData.getDescription());
            mContext.startActivityForResult(intent, MainActivity.BTN_MENU_RESULT_CODE);
        } else if (type.equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_J_WEBVIEW_H5()) || type.equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_J_WEBVIEW_H5())) {
            WebViewsUtils.startWebView(mContext, mMenuResourceData.getContentUrl());
        } else if (type.equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_SKIP_APP()) || type.equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_SKIP_APP())) {
            skipThridApp(mContext, mMenuResourceData);
        } else {
            //Other Type  TO DO
        }
    }

    /**
     * 跳转到第三方App,如果未安装则提示下载apk
     * @param mContext
     * @param mMenuResourceData
     */
    private void skipThridApp(Activity mContext,MenuResourceData mMenuResourceData){
        Log.e("TAG", "skipThridApp: ------------------> "+mMenuResourceData );
        List<MenuCustomContent> customContent = mMenuResourceData.getCustomContent();
        String downPath = null,actyNames = null,packageNames = null;
        for (MenuCustomContent mMenuCustomContent : customContent)
            if (mMenuCustomContent.getCustomKey().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_START_ACTIVITY())) {
                actyNames = mMenuCustomContent.getCustomValue();
            } else if (mMenuCustomContent.getCustomKey().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_PACKAGE_NAME())) {
                packageNames = mMenuCustomContent.getCustomValue();
            } else if (mMenuCustomContent.getCustomKey().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_DOWN_LOAD_PATH()))
                downPath = mMenuCustomContent.getCustomValue();
        try {
            Intent intent=null;
            if(!StringUtils.isEmpty(packageNames)){
                intent= mContext.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageNames);
            }
            /*if(!StringUtils.isEmpty(actyNames)&&!StringUtils.isEmpty(packageNames)){
                intent = new Intent();
                intent.setClassName(packageNames, actyNames);
            }else */
            mContext.startActivity(intent);
        } catch (Exception e) {
            final String title = mMenuResourceData.getTitle();
            if (!StringUtils.isEmpty(downPath)) {
                final String finalDownPath = downPath;
                new AlertDialog.Builder(mContext).setTitle(mContext.getResources().getString(R.string.install_tip))
                        .setMessage(mContext.getResources().getString(R.string.no_install_tip) + title +mContext.getResources().getString(R.string.need_install_tip))
                        .setNegativeButton(mContext.getResources().getString(R.string.quet), (dialogInterface,i)->{
                        })
                        .setPositiveButton(mContext.getResources().getString(R.string.installs), (dialogInterface,i)->
                                DownloadAppUtils.downloadApp(mContext, finalDownPath, mContext.getResources().getString(R.string.downing_app) + title)
                        )
                        .show();
            } else {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
            }
    }}

    /**
     * 获取主要按钮数据【主页按钮数据】
     */
    public List<MenuResourceData> getMainButtonRes() {
        List<MenuResourceData> allList = (List<MenuResourceData>) AppApplication.getInstance().readObject(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_MAIN_BUTTON_DATA());
        if (allList == null || allList.size() < 1)
            allList = getMainPageDefaultButtonData();
        return allList;
    }

    /**
     * 获取主要按钮数据【主页按钮数据(更多)】
     */
    public List<MenuResourceData> getMainButtonData() {
        List<MenuResourceData> allList = null;
        try {
            allList = (List<MenuResourceData>) AppApplication.getInstance().readObject(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_MAIN_BUTTON_DATA());
        } catch (Exception e) {
        }
        if (allList == null || allList.size() < 1)
            allList = getMainPageDefaultButtonData();
        allList.add(new MenuResourceData("更多", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_CLASS_J_ACTIVITY(), R.mipmap.function, Main2Activity.class, 0));
        return allList;
    }

    public void saveMainFunctionButtonData(List<MenuResourceData> main) {
        AppApplication.getInstance().saveObject((Serializable) main, HomeButtonTypeContentKotlin.INSTANCE.getTYPE_MAIN_BUTTON_DATA());
    }

    public void saveAllFunctionButtonData(List<MenuGroupResourceData> all) {
        AppApplication.getInstance().saveObject((Serializable) all, HomeButtonTypeContentKotlin.INSTANCE.getTYPE_ALL_BUTTON_DATA());
    }

    public void clearNetButtonResource() {
        AppApplication.getInstance().delFileData(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_ALL_BUTTON_DATA());
    }

    public HomePageMianButtomChangeLintener listener;

    public interface HomePageMianButtomChangeLintener {
        void HomePageMianButtomChange(List<MenuResourceData> mainBtnRes);
    }

    public void setHomePageMianButtomChangeLintener(HomePageMianButtomChangeLintener listener) {
        this.listener = listener;
    }

    /**
     * 主页中默认是按钮
     */
    public List<MenuResourceData> getMainPageDefaultButtonData() {
        MenuCustomContent wechatAty = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_START_ACTIVITY(), "com.tencent.mm.ui.LauncherUI");
        MenuCustomContent wechatPkg = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_PACKAGE_NAME(), "com.tencent.mm");
        MenuCustomContent wechatDownPath = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_DOWN_LOAD_PATH(), "http://imtt.dd.qq.com/16891/2C721C91F73FC503FAD6A503D941645D.apk?fsname=com.tencent.mm_6.5.23_1180.apk&amp");
        List<MenuCustomContent> wechat = creatArrayList(wechatAty, wechatPkg, wechatDownPath);
        MenuResourceData wechats = new MenuResourceData("微信", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_SKIP_APP(), wechat, R.mipmap.icon_wechat, "10", HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        MenuResourceData setting = new MenuResourceData("系统设置", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_STRING_J_ACTIVITY(), R.mipmap.filechooser, Settings.ACTION_SETTINGS, HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        MenuResourceData activty = new MenuResourceData("Activty", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_CLASS_J_ACTIVITY(), R.mipmap.ic_launcher_round, HomeActivity.class, HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        MenuResourceData baidu = new MenuResourceData("百度一下", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_J_WEBVIEW_H5(), "https://www.baidu.com/", R.mipmap.tbsvideo, HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        MenuCustomContent smartActy = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_START_ACTIVITY(), "com.alk.activity.StartActivity");
        MenuCustomContent smartPckg = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_PACKAGE_NAME(), "com.alk.smart");
        MenuCustomContent smartDownPath = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_DOWN_LOAD_PATH(), "http://39.108.105.41:8080/download/smarthome20171025.apk");
        MenuResourceData smart = new MenuResourceData("智能家居", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_SKIP_APP(), creatArrayList(smartActy, smartPckg, smartDownPath), R.mipmap.tbsweb, HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        return creatArrayList(wechats, setting, activty, baidu, smart);
    }

    /**
     * 本地全部按钮数据
     */
    public List<MenuGroupResourceData> getLoacalAllButtonData() {
        MenuGroupResourceData thirdApp = new MenuGroupResourceData(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_THRID(), HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_NAME_THRID(), getLocalThirdAppList());
        MenuGroupResourceData webView = new MenuGroupResourceData(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_WEB(), HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_NAME_WEB(), getLocalWebViewList());
        MenuGroupResourceData other = new MenuGroupResourceData(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_OTHER(), HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_NAME_OTHER(), getLocaloOtherList());
        MenuGroupResourceData setting = new MenuGroupResourceData(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_SET(), HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_NAME_SET(), getLocaloSettingList());
        return creatArrayList(thirdApp, webView, other,setting);
    }

    /**
     * 本地按钮中
     * 跳转第三方APP按钮数据
     *
     * @return 跳转第三方APP
     */
    public List<MenuResourceData> getLocalThirdAppList() {
        MenuCustomContent wechatAty = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_START_ACTIVITY(), "com.tencent.mm.ui.LauncherUI");
        MenuCustomContent wechatPkg = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_PACKAGE_NAME(), "com.tencent.mm");
        MenuCustomContent wechatDownPath = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_DOWN_LOAD_PATH(), "http://imtt.dd.qq.com/16891/2C721C91F73FC503FAD6A503D941645D.apk?fsname=com.tencent.mm_6.5.23_1180.apk&amp");
        List<MenuCustomContent> wechat = creatArrayList(wechatAty, wechatPkg, wechatDownPath);
        MenuResourceData wechats = new MenuResourceData("微信", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_SKIP_APP(), wechat, R.mipmap.icon_wechat, "有好东西奥", HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        MenuCustomContent smartActy = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_START_ACTIVITY(), "com.alk.activity.StartActivity");
        MenuCustomContent smartPckg = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_PACKAGE_NAME(), "com.alk.smart");
        MenuCustomContent smartDownPath = new MenuCustomContent(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_DOWN_LOAD_PATH(), "http://39.108.105.41:8080/download/smarthome20171025.apk");
        MenuResourceData smart = new MenuResourceData("智能家居", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_SKIP_APP(), creatArrayList(smartActy, smartPckg, smartDownPath), R.mipmap.tbsweb, HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        return creatArrayList(wechats, smart);
    }

    /**
     * 本地按钮中
     * WEBVIEW按钮数据
     *
     * @return ButtonResourceDataInfo
     */
    public List<MenuResourceData> getLocalWebViewList() {
        MenuResourceData baidu = new MenuResourceData("百度一下", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_J_WEBVIEW_H5(), "https://www.baidu.com/", R.mipmap.tbsvideo, "google", HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        MenuResourceData soho = new MenuResourceData("搜狐", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_J_WEBVIEW_H5(), "http://www.sohu.com/", R.mipmap.filechooser, HomeButtonTypeContentKotlin.INSTANCE.getLOCAL_TYPE());
        return creatArrayList(baidu, soho);
    }

    /**
     * 本地按钮中
     * ther按钮数据
     *
     * @return ButtonResourceDataInfo
     */
    public List<MenuResourceData> getLocaloOtherList() {
        MenuResourceData activty = new MenuResourceData("Activty", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_CLASS_J_ACTIVITY(), R.mipmap.ic_launcher_round, HomeActivity.class, 0);
        return creatArrayList(activty);
    }

    public List<MenuResourceData> getLocaloSettingList(){
        MenuResourceData setting = new MenuResourceData("系统设置", HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_STRING_J_ACTIVITY(), R.mipmap.filechooser, Settings.ACTION_SETTINGS, 0);
        return creatArrayList(setting);
    }


    private final <T> List<T> creatArrayList(T... element) {
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, element);
        return list;
    }
}
