package blackbird.com.recyclerviewdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.org.appconstant.HomeButtonTypeContentKotlin;

import java.io.Serializable;
import java.util.List;

import blackbird.com.recyclerviewdemo.adapter.DragRecyclerViewAdapter;
import blackbird.com.recyclerviewdemo.adapter.MenuGroupRecyclerViewAdapter;
import blackbird.com.recyclerviewdemo.application.AppApplication;
import blackbird.com.recyclerviewdemo.bean.MenuGroupResourceData;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.uitls.AppMainButtonDataUtils;
import blackbird.com.recyclerviewdemo.uitls.CollectionUtls;
import blackbird.com.recyclerviewdemo.uitls.StringUtils;
import blackbird.com.recyclerviewdemo.uitls.drag_recycleview.OnRecyclerItemClickListener;
import blackbird.com.recyclerviewdemo.uitls.drag_recycleview.RecyclerViewItemTouchCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuManagerActivity extends AppCompatActivity {

    @BindView(R.id.left_image)
    ImageView leftImage;
    @BindView(R.id.left_textview)
    TextView leftTextview;
    @BindView(R.id.left_button)
    RelativeLayout leftButton;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.right_imge)
    ImageView rightImge;
    @BindView(R.id.right_textview)
    TextView rightTextview;
    @BindView(R.id.right_button)
    RelativeLayout rightButton;
    @BindView(R.id.top_actionbar)
    RelativeLayout topActionbar;
    @BindView(R.id.recyclerview_drage)
    RecyclerView recyclerviewDrage;
    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    public List<MenuResourceData> mainList;
   // @BindView(R.id.app_bar)
  //  AppBarLayout appBar;
    private List<MenuGroupResourceData> allList;
    private DragRecyclerViewAdapter mDragRecyclerViewAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private MenuGroupRecyclerViewAdapter mAllMenuButtonParentAdapter;
    private boolean isScroll;
    private RecyclerView.SmoothScroller mSmoothScroller;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
        ButterKnife.bind(this);
        initUI();
        initData();
        initDrageRecyclerView();
        setAllRecyclerViewAdapter();
        initTabLayout();
        //new ViewGroup()
        //new View()
        //new PhoneWindow();
        //new Activity()

        //https://github.com/ywanhzy/MenuManage-Imitate-Alipay
        //https://github.com/Cornflower1991/RecyclerViewTabLayout
        //https://blog.csdn.net/xu_coding/article/details/80870334

        //https://www.jianshu.com/p/3bf26722c489
        //https://github.com/sunfusheng/StickyHeaderListView.git

       // https://github.com/oldbirdy/recyclerdemo
        //https://blog.csdn.net/xu_coding/article/details/80870334

    }



    private void initUI() {
        titleText.setText(this.getResources().getString(R.string.all_appliction));
        rightImge.setVisibility(View.GONE);
        leftImage.setImageResource(R.mipmap.icon_back);
        rightTextview.setText(this.getResources().getString(R.string.edit));
        leftTextview.setText(this.getResources().getString(R.string.back));
        leftTextview.setOnClickListener((v) ->
                MenuManagerActivity.this.finish()
        );
        rightTextview.setOnClickListener((v)->{
            setItemOnLongClick();
        });

    }



    private void initData() {
        mainList = AppMainButtonDataUtils.getInstance().getMainButtonRes();
        allList = AppMainButtonDataUtils.getInstance().getAllFunctionButton();
        List<MenuResourceData> child;
        for (MenuResourceData mMenuResourceData : mainList)
            for (MenuGroupResourceData mMenuGroupResourceData : allList) {
                child = mMenuGroupResourceData.getChild();
                if (CollectionUtls.notNull(child))
                    for (MenuResourceData childs : child)
                        if (StringUtils.equals(mMenuResourceData.getTitle(), childs.getTitle())) {
                            childs.setSelect(true);
                            break;
                        }
            }

    }

    private void initTabLayout() {
        mSmoothScroller = new LinearSmoothScroller(this) {
            @Override
            protected int getVerticalSnapPreference() {
                //顶部对齐
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return linearLayoutManager.computeScrollVectorForPosition(targetPosition);
            }
        };
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));
        tabLayout.setTabTextColors(Color.BLACK, ContextCompat.getColor(this, R.color.colorAccent));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击tab的时候，RecyclerView自动滑到该tab对应的item位置
                int position = tab.getPosition();
                if (!isScroll) {
                    // 有动画且滚动到顶部
                    mSmoothScroller.setTargetPosition(position);
                    linearLayoutManager.startSmoothScroll(mSmoothScroller);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

        }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (MenuGroupResourceData data : allList) {
            tabLayout.addTab(tabLayout.newTab().setText(data.getCategoryName()));
        }
    }

    private void initDrageRecyclerView() {
        mDragRecyclerViewAdapter = new DragRecyclerViewAdapter(this, mainList);
        recyclerviewDrage.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerViewItemTouchCallback mTouchCallback = new RecyclerViewItemTouchCallback(mDragRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(mTouchCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerviewDrage);
        recyclerviewDrage.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerviewDrage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                super.onItemClick(vh);
                if (!mDragRecyclerViewAdapter.getEditStatue())
                    AppMainButtonDataUtils.getInstance().handleButtonData(MenuManagerActivity.this, mainList.get(vh.getLayoutPosition()));
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                super.onLongClick(vh);
                if (!mDragRecyclerViewAdapter.getEditStatue())
                    setItemOnLongClick();
                mItemTouchHelper.startDrag(vh);
            }
        });
        recyclerviewDrage.setAdapter(mDragRecyclerViewAdapter);
    }

    private void setAllRecyclerViewAdapter() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAllMenuButtonParentAdapter = new MenuGroupRecyclerViewAdapter(this, recyclerView, allList);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScroll = false;
                } else {
                    isScroll = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //滑动RecyclerView list的时候，根据最上面一个Item的position来切换tab
                 int position = layoutManager.findFirstVisibleItemPosition();
                Log.e("TAG", "onScrolled: ------------------> position :"+position );
                TabLayout.Tab tabAt = tabLayout.getTabAt(layoutManager.findFirstVisibleItemPosition());
                if (tabAt != null && !tabAt.isSelected()) {
                    tabAt.select();
                }
            }
        });
        recyclerView.setAdapter(mAllMenuButtonParentAdapter);
    }

    public void deletMeunItem(MenuResourceData resourceData) {
        outloop:
        for (int j = 0; j < allList.size(); j++) {
            List<MenuResourceData> child = allList.get(j).getChild();
            for (int k = 0; k < child.size(); k++)
                if (child.get(k).getTitle().equals(resourceData.getTitle())) {
                    child.get(k).setSelect(false);
                    break outloop;
                }
        }
        if (mDragRecyclerViewAdapter != null)
            mDragRecyclerViewAdapter.notifyDataSetChanged();
        mAllMenuButtonParentAdapter.notifyDataSetChanged();
    }

    public void addMenuItem(MenuResourceData resourceData) {
        mainList.add(resourceData);
        AppApplication.getInstance().saveObject((Serializable) mainList, HomeButtonTypeContentKotlin.INSTANCE.getTYPE_MAIN_BUTTON_DATA());
        outterLoop:
        for (int i = 0; i < allList.size(); i++) {
            List<MenuResourceData> child = allList.get(i).getChild();
            for (int k = 0; k < child.size(); k++)
                if (child.get(k).getTitle().equals(resourceData.getTitle())) {
                    child.get(k).setSelect(true);
                    break outterLoop;
                }
        }
        mDragRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void setItemOnLongClick() {
        String text = rightTextview.getText().toString();
        if (TextUtils.equals(getResources().getString(R.string.edit), text)) {
            if (mDragRecyclerViewAdapter != null)
                mDragRecyclerViewAdapter.startEdit();
            if (mAllMenuButtonParentAdapter != null)
                mAllMenuButtonParentAdapter.startEdit();
            rightTextview.setText(getResources().getString(R.string.finish));
        } else {
            rightTextview.setText(getResources().getString(R.string.edit));
            if (mDragRecyclerViewAdapter != null)
                mDragRecyclerViewAdapter.stopEdit();
            if (mAllMenuButtonParentAdapter != null)
                mAllMenuButtonParentAdapter.stopEdit();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                Intent intent = new Intent(this, MainActivity.class);
                setResult(MainActivity.BTN_MENU_RESULT_CODE, intent);
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
