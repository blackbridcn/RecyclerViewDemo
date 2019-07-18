package blackbird.com.recyclerviewdemo;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.Nullable;

import com.trello.rxlifecycle.components.RxActivity;

import blackbird.com.recyclerviewdemo.receiver.NetworkConnectChangedReceiver;

public class BaseActivity extends RxActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver();
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
           // netBroadcastReceiver = new NetBroadcastReceiver();
            //注册广播接收
           // registerReceiver(netBroadcastReceiver, filter);
        }
    }

    NetworkConnectChangedReceiver netWorkChangReceiver;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(netWorkChangReceiver!=null){
                unRegisterReceiver();
            }
        }
    }

    /**
     * 注册广播
     */
    protected void registerReceiver() {
        //注册网络状态监听广播 7.0之后系统网络状态改为动态注册
        netWorkChangReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangReceiver, filter);
    }

    protected void unRegisterReceiver() {
        if (netWorkChangReceiver != null)
            unregisterReceiver(netWorkChangReceiver);
    }
}
