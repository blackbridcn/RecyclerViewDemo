package blackbird.com.recyclerviewdemo.tv.tab.app;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.tv.tab.AppInfo;
import blackbird.com.recyclerviewdemo.tv.tab.CustomerRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppViewPageAdapter extends RecyclerView.Adapter<AppViewPageAdapter.FavViewHolder> {
    private static final String TAG = AppViewPageAdapter.class.getSimpleName();

    private static final int PAGE_SPAN_COUNT = 6;
    private static final int PAGE_ROW_COUNT = 2;

    private static final int ITEMS_PER_PAGE = PAGE_SPAN_COUNT * PAGE_ROW_COUNT; // 每页 12 个 item

    static interface RequestShowViewPageListener {
        void onScrollViewPageTask(int pageIndex,int focusIndex);
    }

    RequestShowViewPageListener showViewPageListener;
    List<AppInfo> appInfos;
    List<List<AppInfo>> data;
    Map<Integer,RecyclerView> itemChild =new HashMap<>();
    int pageSize;
    Context mContext;

    void onRecyclerViewRequestFocus(int pageIndex,int focusIndex){
        RecyclerView child= itemChild.get(pageIndex);
        if(child!=null){
            int  count =child.getChildCount();
            if(count < focusIndex){
                focusIndex=0; //下一页时，从第二行最后一个item右键触发下一页，且下一页item少于7个时
            }
            child.getChildAt(focusIndex).requestFocus();
        }
    }

    public AppViewPageAdapter(List<AppInfo> mAppInfos, RequestShowViewPageListener showViewPageListener) {
        this.appInfos = mAppInfos;
        this.showViewPageListener = showViewPageListener;
        this.data = splitItemsIntoPages(mAppInfos);
        this.pageSize = data.size();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_tab_fav_vp2_item_layuout, parent, false);
        return new FavViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        List<AppInfo> pageData = data.get(position);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, PAGE_SPAN_COUNT, RecyclerView.VERTICAL, false));
        holder.recyclerView.setAdapter(new AppPageItemAdapter(pageData));
        holder.recyclerView.setKeyEventListener(event -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                View currentFocusedView = holder.recyclerView.findFocus();
                int focusPosition = holder.recyclerView.getChildAdapterPosition(currentFocusedView);
                boolean next = canNextPagePosition(focusPosition, position, pageSize);
                if (next) {
                    int nextPageFocusIndex = (focusPosition == PAGE_SPAN_COUNT - 1)? 0: PAGE_SPAN_COUNT;
                    showViewPageListener.onScrollViewPageTask(position + 1,nextPageFocusIndex);
                    return true;
                }
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                View currentFocusedView = holder.recyclerView.findFocus();
                int focusPosition = holder.recyclerView.getChildAdapterPosition(currentFocusedView);
                boolean pre = canPrePagePosition(focusPosition, position, pageSize);
                if(pre){
                    int prePageFocusIndex = (focusPosition == 0)?PAGE_SPAN_COUNT-1:ITEMS_PER_PAGE-1 ;
                    showViewPageListener.onScrollViewPageTask(position - 1,prePageFocusIndex);
                    return true;
                }
            }
            /*View currentFocusedView = holder.recyclerView.findFocus();
            int focusedPosition = (Objects.requireNonNull(holder.recyclerView.getLayoutManager()).getPosition(currentFocusedView));
            int adapterPosition = holder.recyclerView.getChildAdapterPosition(currentFocusedView);*/
            return false;
        });
        itemChild.putIfAbsent(position,holder.recyclerView);
    }

    boolean canNextPagePosition(int focusPos, int pageIndex, int pageCount) {
        if (pageIndex >= pageCount - 1) {
            //已最后一页了
            return false;
        }
        if (focusPos == PAGE_SPAN_COUNT - 1 || focusPos == ITEMS_PER_PAGE - 1) {
            return true;
        }
        return false;
    }

    boolean canPrePagePosition(int focusPos, int pageIndex, int pageCount) {
        if (pageIndex ==0) {
            //已第一页了
            return false;
        }
        if (focusPos == 0 || focusPos == PAGE_SPAN_COUNT) {
            return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    static class FavViewHolder extends RecyclerView.ViewHolder {
        CustomerRecyclerView recyclerView;
        FavViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.vp_rv);
        }
    }


    /**
     * 将 item 列表按每页指定数量分割成多个列表
     *
     * @param items 原始 item 列表
     * @return 分页后的 item 列表
     */
    private List<List<AppInfo>> splitItemsIntoPages(List<AppInfo> items) {
        List<List<AppInfo>> pagedItems = new ArrayList<>();
        int itemSize = items.size();
        for (int i = 0; i < itemSize; i += ITEMS_PER_PAGE) {
            int end = Math.min(i + ITEMS_PER_PAGE, itemSize);
            pagedItems.add(items.subList(i, end));
        }
        return pagedItems;
    }


}
