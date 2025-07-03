package blackbird.com.recyclerviewdemo.tv.tab.favorites;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.tv.tab.AppInfo;
import blackbird.com.recyclerviewdemo.tv.tab.TvRoundRelativeLayout;

import java.util.List;

public class FavPageItemAdapter extends RecyclerView.Adapter<FavPageItemAdapter.PageViewHolder> {

   private List<AppInfo> data;
    private Context mContext;

    public FavPageItemAdapter(List<AppInfo> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_grid, parent, false);
        return  new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        AppInfo info=data.get(position);
      //  holder.itemBg.setShadow(true, mContext.getColor(R.color.item_bg_shadow));
        if (info.getBanner(mContext) != null) {
         //   holder.itemBg.setBackground(info.getBanner(mContext).getDrawable());
            holder.itemIcon.setBackgroundColor(Color.TRANSPARENT);
        } else {
            holder.itemBg.setBackgroundColor(Color.WHITE);
            holder.itemIcon.setBackgroundColor(Color.TRANSPARENT);
        }
      //  boolean isRect = SpUtils.getInt(mContext, SpUtils.FILE_CUSTOM, SpUtils.KEY_ITEM_CORNER) == 0;
        holder.itemBg.setRadius(11);
        holder.root.post(new Runnable() {
            @Override
            public void run() {
                if (holder.root.isFocused()) {
                    holder.itemLabel.setText("");
                }
            }
        });

        holder.itemBg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        holder.root.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //holder.itemBg.setShadow(!hasFocus,hasFocus ? Color.TRANSPARENT : mContext.getColor(R.color.item_bg_shadow));
                holder.itemLabel.setText(hasFocus ? info.getLabel(mContext) : "");
                try {
                    PackageManager pm = mContext.getPackageManager();
                    ApplicationInfo applicationInfo = pm.getApplicationInfo(info.packageName, 0);
                    CharSequence charSequence = applicationInfo.loadLabel(pm);
                    holder.itemLabel.setText(hasFocus ? charSequence : "");
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, hgv.getChildAdapterPosition(v));
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data==null){
            return 0;
        }
        return data.size();
    }

    static class PageViewHolder extends RecyclerView.ViewHolder{

        ImageView itemIcon;
        TextView itemLabel;
        LinearLayout root;
        TvRoundRelativeLayout itemBg;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.item_app_icon);
            itemLabel = itemView.findViewById(R.id.item_app_label);
            root = itemView.findViewById(R.id.item_root);
            itemBg = itemView.findViewById(R.id.item_app_bg);
        }
    }
}
