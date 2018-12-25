package blackbird.com.recyclerviewdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        ButterKnife.bind(this);


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
            startActivity(intent);
        }


    }

    @OnClick({R.id.imageView2, R.id.textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                Toast.makeText(this,"FUCK YOU --->",Toast.LENGTH_LONG).show();
                break;
            case R.id.textView:
                Toast.makeText(this,"FUCK YOU --->",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
