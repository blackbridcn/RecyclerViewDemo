package blackbird.com.recyclerviewdemo.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.org.appconstant.HomeButtonTypeContentKotlin;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import blackbird.com.recyclerviewdemo.MenuManagerActivity;
import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.application.AppApplication;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.uitls.drag_recycleview.RecyclerViewItemTouchCallback;


/**
 * Created by yzzhang on 2017/12/9.
 */

public class DragRecyclerViewAdapter extends RecyclerView.Adapter<DragRecyclerViewAdapter.DragViewHodler>
        implements RecyclerViewItemTouchCallback.ItemTouchAdapter {
    private MenuManagerActivity mContext;
    private List<MenuResourceData> mainList;
    private boolean isEdit;

    public DragRecyclerViewAdapter(MenuManagerActivity menuBtnManagerActivity, List<MenuResourceData> mainList) {
        this.mainList = mainList;
        this.mContext = menuBtnManagerActivity;
    }

    @Override
    public DragViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu_drag_rv, null, false);
        return new DragViewHodler(view);
    }

    @Override
    public void onBindViewHolder(DragViewHodler holder, final int position) {
        holder.itemView.setTag(position);
        final MenuResourceData resourceData = mainList.get(position);
        holder.name_tv.setText(resourceData.getTitle());
        if (resourceData.getBtnType().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_J_WEBVIEW_H5()) || resourceData.getBtnType().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_SKIP_APP())
                || resourceData.getBtnType().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_CLASS_J_ACTIVITY())) {
            Glide.with(mContext)
                    .load(resourceData.getIconUrl())
                    .into(holder.icon_bg);
        } else {
            holder.icon_bg.setImageResource(resourceData.getIconRes());
        }
        if (isEdit) {
            holder.delete_Img.setVisibility(View.VISIBLE);
            holder.container.setBackgroundColor(mContext.getResources().getColor(R.color.button_selesct_bg));
            holder.delete_Img.setBackgroundResource(R.mipmap.ic_block_delete);
        } else {
            holder.delete_Img.setVisibility(View.GONE);
            holder.container.setBackgroundColor(Color.WHITE);
        }
        holder.delete_Img.setOnClickListener((view)->{
                mContext.deletMeunItem(resourceData);
                mainList.remove(position);
                AppApplication.getInstance().saveObject((Serializable) mainList, HomeButtonTypeContentKotlin.INSTANCE.getTYPE_MAIN_BUTTON_DATA());
        });
        holder.container.setOnClickListener((view)->{
                if (isEdit) {
                    mContext.deletMeunItem(resourceData);
                    mainList.remove(position);
                    AppApplication.getInstance().saveObject((Serializable) mainList, HomeButtonTypeContentKotlin.INSTANCE.getTYPE_MAIN_BUTTON_DATA());
                }
        });
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mainList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mainList, i, i - 1);
            }
        }
        AppApplication.getInstance().saveObject((Serializable) mainList, HomeButtonTypeContentKotlin.INSTANCE.getTYPE_MAIN_BUTTON_DATA());
        this.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {

    }

    class DragViewHodler extends RecyclerView.ViewHolder {
        ImageView delete_Img, icon_bg;
        TextView name_tv;
        View container;

        public DragViewHodler(View itemView) {
            super(itemView);
            delete_Img = (ImageView) itemView.findViewById(R.id.delete_img);
            icon_bg = (ImageView) itemView.findViewById(R.id.icon_img);
            name_tv = (TextView) itemView.findViewById(R.id.name_tv);
            container = itemView.findViewById(R.id.item_container);
        }
    }

    public void startEdit() {
        isEdit = true;
        this.notifyDataSetChanged();
    }

    public void stopEdit() {
        isEdit = false;
        this.notifyDataSetChanged();
    }

    public boolean getEditStatue() {
        return this.isEdit;
    }
}
