package blackbird.com.recyclerviewdemo.tv.tab;


import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.tv.tab.app.AppFragment;
import blackbird.com.recyclerviewdemo.tv.tab.favorites.FavoritesFragment;
import blackbird.com.recyclerviewdemo.tv.tab.presenter.TabTitlePresenter;
import blackbird.com.recyclerviewdemo.uitls.InstallApkUtils;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabViewPagerRecyclerViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        });
        initViewTask();
    }


    private void initViewTask(){
        initTabTitleLayoutTask();
    }
    HorizontalGridView horGrideView;
    private void initTabTitleLayoutTask(){
        horGrideView= findViewById(R.id.hgv);
        ArrayObjectAdapter arrayObjectAdapter =new ArrayObjectAdapter(new TabTitlePresenter());
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(arrayObjectAdapter);
        horGrideView.setAdapter(itemBridgeAdapter);
        List<TabTitleModel> tabTitle=new ArrayList<TabTitleModel>();
        tabTitle.add(new TabTitleModel("Favorites",0));
        tabTitle.add(new TabTitleModel("App",1));
        arrayObjectAdapter.addAll(0, tabTitle);
        InstallApkUtils.loadAllApk(this);
        tabFragment.add(new FavoritesFragment());
        tabFragment.add(new AppFragment());
        horGrideView.setOnChildSelectedListener((parent, view, position, id) -> {
            //initTabFragmentTask(position);
            //   Log.e("TAG","-------------------------> position : "+position);
            onSelFragmentTask(position);
        });
    }
    private List<Fragment> tabFragment= new ArrayList<Fragment>();
    private void onSelFragmentTask(int index){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_host, tabFragment.get(index))
                .commit();
    }


}
