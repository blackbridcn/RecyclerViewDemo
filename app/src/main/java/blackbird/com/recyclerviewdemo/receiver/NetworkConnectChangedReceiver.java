package blackbird.com.recyclerviewdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import blackbird.com.recyclerviewdemo.uitls.NetworkUtils;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
  /*  @Override
    public void onReceive(Context context, Intent intent) {
        //**判断当前的网络连接状态是否可用
        boolean isConnected = isConnected(context);
        Toast.makeText(context, "当前网络 " + isConnected, Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onReceive: 当前网络 " + isConnected);
      //  EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
    }
    */

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION) {
            /*判断当前网络时候可用以及网络类型*/
            boolean isConnected = NetworkUtils.isConnected(context);
            NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType(context);
            Toast.makeText(context,"isConnected :" + isConnected + "  NetworkType:" + networkType,Toast.LENGTH_LONG).show();
            Log.e("TAG", "onReceive: -------------->isConnected :" + isConnected + "  NetworkType:" + networkType);
            //EventBus.getDefault().post(new NetworkChangeEvent(isConnected, networkType));
        }


        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.e("TAG", "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //获取联网状态的NetworkInfo对象
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        Toast.makeText(context, getConnectionType(info.getType()) + "连上", Toast.LENGTH_SHORT).show();
                        Log.i("TAG", getConnectionType(info.getType()) + "连上");
                    }
                } else {
                    Toast.makeText(context, getConnectionType(info.getType()) + "断开", Toast.LENGTH_SHORT).show();

                    Log.i("TAG", getConnectionType(info.getType()) + "断开");
                }
            }
        }

    }

    /**
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }
}
