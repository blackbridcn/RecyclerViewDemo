package blackbird.com.recyclerviewdemo.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import blackbird.com.recyclerviewdemo.MenuManagerActivity;
import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.bean.MenuGroupResourceData;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;


/**
 * Created by yzzhang on 2017/12/9.
 */

public class MenuGroupRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MenuManagerActivity mActivity;
    private List<MenuGroupResourceData> allList;
    private boolean isEdit = false;
    private final RecyclerView.RecycledViewPool viewPool;
    private RecyclerView parRecyclerView;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_FOOTER = 2;
    private int parRVHeight;
    private int itemHeight;

    public MenuGroupRecyclerViewAdapter(MenuManagerActivity menuBtnManagerActivity, RecyclerView parRecyclerView, List<MenuGroupResourceData> allList) {
        this.mActivity = menuBtnManagerActivity;
        this.allList = allList;
        this.parRecyclerView = parRecyclerView;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_menubtn_rv_parent, null, false);
            return new AllButtonParentViewHolder(view);
        } else {
            View view = new View(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parRVHeight - itemHeight));
            return new defailtViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if(viewHolder.getItemViewType()==VIEW_TYPE_ITEM){
            final AllButtonParentViewHolder holder = (AllButtonParentViewHolder) viewHolder;
            MenuGroupResourceData resourceDataInfo = allList.get(position);
            holder.item_tv_title.setText(resourceDataInfo.getCategoryName());
            List<MenuResourceData> resourceDataList = resourceDataInfo.getChild();
            if (resourceDataList != null && resourceDataList.size() > 0) {
                holder.item_recyclerview_childs.setRecycledViewPool(viewPool);
                holder.item_recyclerview_childs.setHasFixedSize(false);
                holder.item_recyclerview_childs.setNestedScrollingEnabled(false);
                holder.item_recyclerview_childs.setLayoutManager(new GridLayoutManager(mActivity, 4) {
                    @Override
                    public void onLayoutCompleted(RecyclerView.State state) {
                        super.onLayoutCompleted(state);
                        parRVHeight = parRecyclerView.getHeight();
                        itemHeight = holder.item_recyclerview_childs.getHeight();
                    }
                });
                MenuRecyclerViewAdapter childAdapter = new MenuRecyclerViewAdapter(mActivity, resourceDataList, isEdit);
                holder.item_recyclerview_childs.setAdapter(childAdapter);
        }
        }
    }

    @Override
    public int getItemCount() {
        return allList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == allList.size()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    class defailtViewHolder extends RecyclerView.ViewHolder {

        public defailtViewHolder(View itemView) {
            super(itemView);
        }
    }

    class AllButtonParentViewHolder extends RecyclerView.ViewHolder {
        TextView item_tv_title;
        RecyclerView item_recyclerview_childs;
        View itemView;

        public AllButtonParentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            item_tv_title = (TextView) itemView.findViewById(R.id.item_tv_title);
            item_recyclerview_childs = (RecyclerView) itemView.findViewById(R.id.item_recyclerview_childs);
        }
    }

    public void startEdit() {
        this.isEdit = true;
        this.notifyDataSetChanged();
    }

    public void stopEdit() {
        this.isEdit = false;
        this.notifyDataSetChanged();
    }
}
