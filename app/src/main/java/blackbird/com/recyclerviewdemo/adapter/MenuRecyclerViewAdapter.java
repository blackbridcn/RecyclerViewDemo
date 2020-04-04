package blackbird.com.recyclerviewdemo.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import blackbird.com.recyclerviewdemo.HomeButtonTypeContent;
import blackbird.com.recyclerviewdemo.MenuManagerActivity;
import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.menueven.MenuClickEventContext;
import blackbird.com.recyclerviewdemo.uitls.StringUtils;


/**
 * Created by yzzhang on 2017/12/9.
 */

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.AllButtonChildViewHold> {
    private MenuManagerActivity mActvity;
    private List<MenuResourceData> resourceDataList;
    private boolean isEdit = false;

    public MenuRecyclerViewAdapter(MenuManagerActivity activity, List<MenuResourceData> resourceDataList, boolean isEdit) {
        this.mActvity = activity;
        this.resourceDataList = resourceDataList;
        this.isEdit = isEdit;
    }

    @Override
    public AllButtonChildViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActvity).inflate(R.layout.item_menubtn_rv_childs, null, false);
        return new AllButtonChildViewHold(view);
    }

    @Override
    public void onBindViewHolder(final AllButtonChildViewHold holder, int position) {
        if (position >= resourceDataList.size()) {
            holder.item_btn_name_tv.setText("");
            return;
        }
        final MenuResourceData resourceData = resourceDataList.get(position);
        String btnType = resourceData.getBtnType();
        if (btnType.equals(HomeButtonTypeContent.TYPE_URL_CLASS_J_ACTIVITY) || btnType.equals(HomeButtonTypeContent.TYPE_URL_SKIP_APP)
                || btnType.equals(HomeButtonTypeContent.TYPE_URL_J_WEBVIEW_H5)) {
            Glide.with(mActvity).load(resourceData.getIconUrl()).into(holder.item_btn_bg_img);
        } else
            holder.item_btn_bg_img.setImageResource(resourceData.getIconRes());
        holder.item_btn_name_tv.setText(resourceData.getTitle());
        setEditState(holder,resourceData);
        setLinster(holder,resourceData);
    }
    private void setEditState(AllButtonChildViewHold holder,MenuResourceData resourceData){
        String tips = resourceData.getTips();
        if (!isEdit) {
            if (!StringUtils.isEmpty(tips)) {
                holder.unread_number.setVisibility(View.VISIBLE);
                holder.unread_number.setText(tips);
            } else
                holder.unread_number.setVisibility(View.GONE);
            holder.item_add_img.setVisibility(View.GONE);
            holder.item_container.setBackgroundColor(Color.WHITE);
        } else {
            holder.item_add_img.setVisibility(View.VISIBLE);
            holder.unread_number.setVisibility(View.GONE);
            if (resourceData.isSelect())
                holder.item_add_img.setImageResource(R.mipmap.ic_block_selected);//menu_select_28
            else
                holder.item_add_img.setBackgroundResource(R.mipmap.ic_block_add);//menu_add_28
            holder.item_container.setBackgroundColor(mActvity.getResources().getColor(R.color.button_selesct_bg));
        }
    }
    private void setLinster(AllButtonChildViewHold holder,MenuResourceData resourceData){
        holder.item_add_img.setOnClickListener((view) -> {
            if (!resourceData.isSelect()) if (mActvity.mainList.size() > 10) {
                Toast.makeText(mActvity, mActvity.getResources().getString(R.string.toast_most_buttons), Toast.LENGTH_SHORT).show();
                return;
            } else {
                mActvity.addMenuItem(resourceData);
                holder.item_add_img.setBackgroundResource(R.mipmap.ic_block_selected);//menu_select_28
            }
        });
        holder.item_container.setOnClickListener((view) -> {
            if (isEdit) {
                if (!resourceData.isSelect()) if (mActvity.mainList.size() > 10) {
                    Toast.makeText(mActvity,  mActvity.getResources().getString(R.string.toast_most_buttons), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mActvity.addMenuItem(resourceData);
                    holder.item_add_img.setBackgroundResource(R.mipmap.ic_block_selected);//menu_select_28
                }
            } else{
                MenuClickEventContext.getInstance().onClick(mActvity,resourceData);
                //AppMainButtonDataUtils.getInstance().handleButtonData(mActvity, resourceData);
            }
        });
        holder.item_container.setOnLongClickListener((view) -> {
            if (!isEdit)
                mActvity.setItemOnLongClick();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        // return resourceDataList.size() % 4 > 0 ? resourceDataList.size() + (4 - resourceDataList.size() % 4) : resourceDataList.size();
        return resourceDataList.size();
    }

    class AllButtonChildViewHold extends RecyclerView.ViewHolder {
        ImageView item_btn_bg_img, item_add_img;
        TextView item_btn_name_tv, unread_number;
        FrameLayout item_container;
        ViewStub under_viewstube;
        View itemView;

        public AllButtonChildViewHold(View itemView) {
            super(itemView);
            this.itemView = itemView;
            item_btn_bg_img = (ImageView) itemView.findViewById(R.id.item_btn_bg_img);
            item_btn_name_tv = (TextView) itemView.findViewById(R.id.item_btn_name_tv);
            item_add_img = (ImageView) itemView.findViewById(R.id.item_add_img);
            item_container = (FrameLayout) itemView.findViewById(R.id.item_container);
            unread_number = (TextView) itemView.findViewById(R.id.under_number_);
        }
    }
}
