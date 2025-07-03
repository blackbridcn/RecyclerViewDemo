package blackbird.com.recyclerviewdemo.tv.tab.favorites;


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

import java.util.ArrayList;
import java.util.List;

import blackbird.com.recyclerviewdemo.tv.tab.AppInfo;
import blackbird.com.recyclerviewdemo.tv.tab.FileUtils;
import blackbird.com.recyclerviewdemo.tv.tab.ItemService;

public class FavoritesFragment extends Fragment {

    private ServiceConnection mConnection;
    private ItemService mService;
    public static final String ACTION_APPS_UPDATE_LIST = "FavRowPresenter.APPS_UPDATE_LIST";
    public static final String ACTION_EXTRA_APPS_LIST = "FavRowPresenter.extra_APPS_LIST";

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_tab_layuout, container, false);
        initView();

        ViewPager2 viewPager2 = view.findViewById(R.id.fav_vp2);
        List<AppInfo> favAppInfos = AppInfo.getInstalledList(getContext(), FileUtils.getFromFav(getContext()));
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL); // 默认
        FavViewPageAdapter pageAdapter = new FavViewPageAdapter(favAppInfos);
        viewPager2.setAdapter(pageAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 当页面被选中时调用
            }
        });

        return view;
    }


    private final BroadcastReceiver mAppsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mService != null) {
                String action = intent.getAction();
                if (ACTION_APPS_UPDATE_LIST.equals(action)) {
                    ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) intent.getSerializableExtra(ACTION_EXTRA_APPS_LIST);
                    if (appInfos != null) {
                     //   appInfos = AppInfo.getInstalledList(getContext(), appInfos);
                    }
                }

            } else {
                Log.d("mopp", "ItemService is null,do not update ui");
            }
        }
    };

    protected void initView() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_APPS_UPDATE_LIST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mAppsReceiver, filter);
        if (mConnection == null) {
            mConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Log.d("mopp", "ItemService onServiceConnected");
                    mService = ((ItemService.LocalBinder) service).getItemService();
                    //updateList(mService.getAppItems(null));
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

}

