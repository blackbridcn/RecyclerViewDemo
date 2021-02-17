package blackbird.com.recyclerviewdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.aop.annotation.Trace;

import java.util.List;

import blackbird.com.recyclerviewdemo.adapter.MainRecyclerViewAdapter;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.menueven.MenuClickEventContext;
import blackbird.com.recyclerviewdemo.uitls.AppMainButtonDataUtils;
import blackbird.com.recyclerviewdemo.uitls.DataCleanManager;
import blackbird.com.recyclerviewdemo.uitls.ThreadPool;
import blackbird.com.simple.acvm.ViewModelActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_mian_btn)
    RecyclerView rvMianBtn;
    @BindView(R.id.load_mian_btn)
    Button loadMianBtn;
    @BindView(R.id.clear_cache_btn)
    Button clearCacheBtn;
    @BindView(R.id.miv)
    ImageView miv;

    private MainRecyclerViewAdapter mMainRecyclerViewAdapter;
    public static int BTN_MENU_RESULT_CODE = 5;
    public static int BTN_MENU_REQUEST_CODE = 6;
    public static List<MenuResourceData> mMainPageDefaultButtonData;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }


    @OnClick({R.id.load_mian_btn, R.id.clear_cache_btn, R.id.miv, R.id.vmda})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.load_mian_btn:
                NetDataTask netDataTask = new NetDataTask(MainActivity.this, HomeButtonTypeContent.netData);
                ThreadPool.getInstance().getThreadPoolExecutor().execute(netDataTask);
                break;
            case R.id.clear_cache_btn:
                DataCleanManager.cleanFiles(this);
                try {
                    PackageManager packageManager
                            = this.getApplicationContext().getPackageManager();
                    Intent intent = packageManager.
                            getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                    startActivity(intent);
                } catch (Exception e) {
                    String url = "https://ds.alipay.com/?from=mobileweb";
                    Log.e("TAG", "onViewClicked: ----------------> " + e.getMessage());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }

                break;

            case R.id.miv:
                startActivity(new Intent(this, ViewModelActivity.class));

                break;

            case R.id.vmda:
                startActivity(new Intent(this, ViewModelActivity.class));
                break;

            case R.id.vmdrl:

                break;
        }
    }

    @Trace
    private void initData() {
        mMainPageDefaultButtonData = AppMainButtonDataUtils.getInstance().getMainButtonData();
        mMainRecyclerViewAdapter = new MainRecyclerViewAdapter(this);
        rvMianBtn.setLayoutManager(new GridLayoutManager(this, 4));
        rvMianBtn.setAdapter(mMainRecyclerViewAdapter);
        setListener();
        mMainRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setListener() {
        mMainRecyclerViewAdapter.setOnItemClickListener((view, position) ->
                        MenuClickEventContext.getInstance().onClick(this, mMainPageDefaultButtonData.get(position))
                /*AppMainButtonDataUtils.getInstance().handleButtonData(this, mMainPageDefaultButtonData.get(position))*/
        );
        mMainRecyclerViewAdapter.setOnItemLongClickListener((view, position) ->
                Toast.makeText(MainActivity.this, "OnItemLongClick  position :" + position, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.BTN_MENU_REQUEST_CODE && resultCode == MainActivity.BTN_MENU_RESULT_CODE) {
            mMainPageDefaultButtonData.clear();
            mMainPageDefaultButtonData = AppMainButtonDataUtils.getInstance().getMainButtonData();
            mMainRecyclerViewAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
  /*  public void testRxinterval(){
        int  countTime=60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return countTime-aLong;
                    }
                })
                .take(30)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        // LogUtils.e("================doOnSubscribe");
                        //  timeText.setVisibility(View.VISIBLE);
                        // failLayout.setVisibility(View.GONE);
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //  LogUtils.e("================doOnUnsubscribe");
                        stopSubScribe();
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        //  timeText.setVisibility(View.GONE);
                        // failLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        //  LogUtils.e("================"+integer);
                        // timeText.setText(Html.fromHtml(String.format(getResources().getString(R.string.verify_code_time), "<font color=\"#3d9bf5\">" + integer + "秒</font>")));
                    }
                });


    }
    private void stopSubScribe() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }*/
}