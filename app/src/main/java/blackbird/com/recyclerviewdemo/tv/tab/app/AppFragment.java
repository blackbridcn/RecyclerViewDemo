package blackbird.com.recyclerviewdemo.tv.tab.app;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.tv.tab.AppInfo;
import blackbird.com.recyclerviewdemo.tv.tab.FileUtils;
import blackbird.com.recyclerviewdemo.tv.tab.ItemService;
import blackbird.com.recyclerviewdemo.uitls.InstallApkUtils;

import java.util.ArrayList;
import java.util.List;

public class AppFragment extends Fragment {

    public static final String ACTION_APPS_UPDATE_LIST = "FavRowPresenter.APPS_UPDATE_LIST";
    public static final String ACTION_EXTRA_APPS_LIST = "FavRowPresenter.extra_APPS_LIST";
    private  List<AppInfo> allApps = new ArrayList<AppInfo>();
    private ServiceConnection mConnection;
    private ItemService mService;
    private AppViewPageAdapter pageAdapter;
    ViewPager2 viewPager2;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_app_layuout, container, false);
        onRegisterAppUpdateReceiverTask();
        initViewPageWidget(view);
        return view;
    }

    private void initViewPageWidget(View rootView) {
        viewPager2 = rootView.findViewById(R.id.app_vp2);

        ArrayList<AppInfo> apps = FileUtils.getFromApps(getContext());
        if (apps != null) {
            apps = AppInfo.getInstalledList(getContext(), apps);
        } else {
            apps = new ArrayList<>();
        }
        allApps.clear();
        allApps.addAll(apps);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL); // 默认
        pageAdapter = new AppViewPageAdapter(allApps, this::setViewPageIndex);
        viewPager2.setAdapter(pageAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 当页面被选中时调用
               // LogUtils.e("---------------->  onPageSelected : "+position);
            }
        });

    }

    void setViewPageIndex(int pageIndex,int nextPageFocusIndex){
        viewPager2.setCurrentItem(pageIndex);
        viewPager2.postDelayed(() -> pageAdapter.onRecyclerViewRequestFocus(pageIndex,nextPageFocusIndex),100);
    }

    @SuppressLint("NotifyDataSetChanged")
    private synchronized void updateAllAppListTask(List<AppInfo> allApp) {
        // allApps.clear();
        //allApps.addAll(allApp);
        if (pageAdapter!=null) {
          //  pageAdapter.notifyDataSetChanged();
        }
    }

    protected void onRegisterAppUpdateReceiverTask() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_APPS_UPDATE_LIST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mAppsReceiver, filter);
        if (mConnection == null) {
            mConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Log.d("mopp", "ItemService onServiceConnected");
                    mService = ((ItemService.LocalBinder) service).getItemService();
                    mService.updateAppList(null);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    mService = null;
                }
            };
            getContext().bindService(new Intent(getContext(), ItemService.class), mConnection, Context.BIND_AUTO_CREATE);
        }
    }
    private final BroadcastReceiver mAppsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mService != null) {
                String action = intent.getAction();
                if (ACTION_APPS_UPDATE_LIST.equals(action)) {
                    ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) intent.getSerializableExtra(ACTION_EXTRA_APPS_LIST);
                    if (appInfos != null) {
                        appInfos = AppInfo.getInstalledList(getContext(), appInfos);
                    }

                }
            } else {
                Log.d("mopp", "ItemService is null,do not update ui");
            }
        }
    };

}

