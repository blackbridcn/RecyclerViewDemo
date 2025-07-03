package blackbird.com.recyclerviewdemo.tv.tab.presenter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;



import java.util.HashMap;
import java.util.Map;

import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.tv.tab.TabTitleModel;
import blackbird.com.recyclerviewdemo.tv.tab.TitleTextView;

public class TabTitlePresenter extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_title_layout, parent, false);
        return new ViewHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof TabTitleModel) {
            TabTitleModel itemModel = (TabTitleModel) item;
            TitleTextView titleTextView=  viewHolder.view .findViewById(R.id.tv_title);
            titleTextView.setText(itemModel.getTitle());
            titleTextView.setOnFocusChangeListener(onFocusChangeListener);
            titleTextView.setTag(itemModel);
        }

    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    // 在类中声明这些成员变量
    private final Map<Boolean, String> map = new HashMap<>();
    private View lastSelectedView = null;

    private final View.OnFocusChangeListener onFocusChangeListener = (view, hasFocus) -> {
        String tag = view.getTag() != null ? view.getTag().toString() : "";
        map.put(hasFocus, tag);

        if (map.get(true) != null && map.get(true).equals(map.get(false))) {
            // 获得焦点和失去焦点的是同一个item
            view.setSelected(true);
            lastSelectedView = view;
        } else {
            // 清除之前选中的视图状态
            if (lastSelectedView != null) {
                lastSelectedView.setSelected(false);
            }
            lastSelectedView = null;
        }
    };

}
