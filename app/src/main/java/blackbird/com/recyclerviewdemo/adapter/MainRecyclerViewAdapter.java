package blackbird.com.recyclerviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.org.appconstant.HomeButtonTypeContentKotlin;

import java.util.List;

import blackbird.com.recyclerviewdemo.MainActivity;
import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;


/**
 * Created by yzzhang on 2017/2/9.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private MainActivity mContext;
    private List<MenuResourceData> list;

    public MainRecyclerViewAdapter(MainActivity mainActivity) {
        this.mContext = mainActivity;
    }

    @Override
    public MainRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        MenuResourceData resourceData = MainActivity.mMainPageDefaultButtonData.get(position);
        if (resourceData.getBtnType().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_SKIP_APP()) || resourceData.getBtnType().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_J_WEBVIEW_H5())) {
           // Glide.with(mContext).load(resourceData.getIconUrl()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(holder.iv_item);
            Glide.with(mContext).load(resourceData.getIconUrl()).into(holder.iv_item);
        } else {
            holder.iv_item.setImageResource(resourceData.getIconRes());
        }
        holder.tv_item.setText(resourceData.getTitle());
        String tips = resourceData.getTips();
        if(tips!=null&&tips.length()>0){
            holder.unread_number.setText(tips);
            holder.unread_number.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return MainActivity.mMainPageDefaultButtonData.size();
    }


    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.OnItemClick(view, (Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.OnItemLongClick(view, (Integer) view.getTag());
        }
        return true;
    }

    class MainRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item, unread_number;
        ImageView iv_item;
        RelativeLayout item_bg;

        public MainRecyclerViewHolder(View itemView) {
            super(itemView);
            iv_item = (ImageView) itemView.findViewById(R.id.iv_item);
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);
            unread_number = (TextView) itemView.findViewById(R.id.unread_number);
            item_bg = (RelativeLayout) itemView.findViewById(R.id.item_bg);
        }
    }

    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

}
