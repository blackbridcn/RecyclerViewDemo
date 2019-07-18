package blackbird.com.recyclerviewdemo.menueven.iml;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.widget.Toast;

import com.org.appconstant.HomeButtonTypeContentKotlin;

import java.util.List;

import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.bean.MenuCustomContent;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.menueven.MenuClickStrategy;
import blackbird.com.recyclerviewdemo.uitls.DownloadAppUtils;
import blackbird.com.recyclerviewdemo.uitls.IntentUtils;
import blackbird.com.recyclerviewdemo.uitls.StringUtils;

/**
 * 本地按钮 点击跳转到第三方App类型
 */
public class SkipThridApp implements MenuClickStrategy {

    /**
     * 跳转到第三方App,如果未安装则提示下载apk
     *
     * @param mContext
     * @param mMenuResourceData
     */
    @Override
    public void onMenuClick(Activity mContext, MenuResourceData mMenuResourceData) {
        List<MenuCustomContent> customContent = mMenuResourceData.getCustomContent();
        String downPath = null, actyNames = null, packageNames = null;
        for (MenuCustomContent mMenuCustomContent : customContent)
            if (mMenuCustomContent.getCustomKey().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_START_ACTIVITY())) {
                actyNames = mMenuCustomContent.getCustomValue();
            } else if (mMenuCustomContent.getCustomKey().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_PACKAGE_NAME())) {
                packageNames = mMenuCustomContent.getCustomValue();
            } else if (mMenuCustomContent.getCustomKey().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_SKIP_APP_DOWN_LOAD_PATH()))
                downPath = mMenuCustomContent.getCustomValue();
        boolean skipApk = IntentUtils.skipThridApkActivity(mContext, packageNames, actyNames);
        if (!skipApk) {
            downApkDialog(mContext, mMenuResourceData.getTitle(), downPath);
        }
       // Toast.makeText(mContext, mContext.getResources().getString(R.string.error_toast), Toast.LENGTH_SHORT).show();

    }

    /**
     * 没有安装apk时[没有找到activity]，dialog 提示是否下载
     *
     * @param mContext
     * @param title
     * @param downPath
     */
    private void downApkDialog(Activity mContext, String title, String downPath) {
        if (!StringUtils.isEmpty(downPath)) {
            final String finalDownPath = downPath;
            new DialogFragment();

            new AlertDialog.Builder(mContext).setTitle(mContext.getResources().getString(R.string.install_tip))
                    .setMessage(mContext.getResources().getString(R.string.down_install_tip,title))
                    .setNegativeButton(mContext.getResources().getString(R.string.quet), (dialogInterface, i) -> {
                    })
                    .setPositiveButton(mContext.getResources().getString(R.string.installs), (dialogInterface, i) ->
                            DownloadAppUtils.downloadApp(mContext, finalDownPath, mContext.getResources().getString(R.string.downing_app) + title)
                    )
                    .show();
        } else
            Toast.makeText(mContext, mContext.getResources().getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
    }

}
