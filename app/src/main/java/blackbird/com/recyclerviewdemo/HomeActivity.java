package blackbird.com.recyclerviewdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.modelmbean.InvalidTargetObjectTypeException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    // tv1用来显示时间，tv2是用来做个背景的
    private TextView tv1, tv2;
    // 想调用字体需要使用这个Typeface
    private Typeface typeface;
    // 设置一个常量，这里就是我们的数码管字体文件
    private static final String FONT_DIGITAL_7 = "fonts" + File.separator
            + "digital-7.ttf";
    // Handler里用
    private static final int SHOW_TIME = 1;

    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            // 调用控件不能在子线程，所以这里需要在handler中调用
            handler.sendEmptyMessage(SHOW_TIME);
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_TIME:
                    Calendar calendar = Calendar.getInstance();
                    // 显示时间
                    tv1.setText(String.format("%02d:%02d:%02d",
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            calendar.get(Calendar.SECOND)));
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setTypeface(getResources().getFont(R.font.digital_7));
        }*/
      /*  PackageManager packageManager
                =   this.getApplicationContext().getPackageManager();
        List<ApplicationInfo> lists
                = packageManager.getInstalledApplications(0);
        for(ApplicationInfo appInfo : lists) {
            if(appInfo != null) {
                Log.i("TAG", "packageName:" + appInfo.packageName);
            }
        }*/


        try {
            PackageManager packageManager
                    = this.getApplicationContext().getPackageManager();
            //Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
            //com.eg.android.AlipayGphone.AlipayLogin
            Intent intent = new Intent();
            intent.setClassName("com.eg.android.AlipayGphone", "com.eg.android.AlipayGphone.AlipayLogin");
            startActivity(intent);
        }catch (Exception e) {
            String url = "https://ds.alipay.com/?from=mobileweb";
            //https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
           //startActivity(intent);
        }

        tv1 = (TextView) findViewById(R.id.ledview_clock_time);
        tv2 = (TextView) findViewById(R.id.ledview_clock_bg);
        // 准备字体
        typeface = Typeface.createFromAsset(getAssets(), FONT_DIGITAL_7);
        // 设置字体
        tv1.setTypeface(typeface);
        tv2.setTypeface(typeface);
        tv2.setText("88:88:88");
        // 0毫秒后执行timerTask，并且以后每隔1000毫秒执行一次timerTask
        timer.schedule(timerTask, 0, 1000);
    }

}
