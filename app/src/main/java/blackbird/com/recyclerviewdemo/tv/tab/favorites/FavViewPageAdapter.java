package blackbird.com.recyclerviewdemo.tv.tab.favorites;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import blackbird.com.recyclerviewdemo.R;
import java.util.ArrayList;
import java.util.List;

import blackbird.com.recyclerviewdemo.tv.tab.AppInfo;

public class FavViewPageAdapter extends RecyclerView.Adapter<FavViewPageAdapter.FavViewHolder> {
    private static final String TAG = FavViewPageAdapter.class.getSimpleName();
    private static final int ITEMS_PER_PAGE = 12; // 每页 12 个 item
    private static final int PAGE_SPAN_COUNT = 6;
    List<AppInfo> favAppInfos;
    List<List<AppInfo>> data;
    Context mContext;

    public FavViewPageAdapter(List<AppInfo> favAppInfos) {
        this.favAppInfos = favAppInfos;
        data = splitItemsIntoPages(favAppInfos);
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
        //List<AppInfo> pageData = data.get(position);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext,PAGE_SPAN_COUNT,RecyclerView.VERTICAL,false));
        holder.recyclerView.setAdapter(new FavPageItemAdapter(data.get(position)));

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 3;
    }

    static class FavViewHolder extends RecyclerView.ViewHolder {
        //TextView textView;
        RecyclerView recyclerView;

        FavViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.vp_rv);
            //textView = itemView.findViewById(R.id.tv_name);
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
