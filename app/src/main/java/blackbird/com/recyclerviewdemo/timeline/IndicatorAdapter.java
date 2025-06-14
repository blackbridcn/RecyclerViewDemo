package blackbird.com.recyclerviewdemo.timeline;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import blackbird.com.recyclerviewdemo.R;
import blackbird.com.recyclerviewdemo.timeline.model.Status;
import blackbird.com.recyclerviewdemo.timeline.model.TimeLine;

public class IndicatorAdapter extends RecyclerView.Adapter<IndicatorViewHolder> {

    List<TimeLine> timeLines;
    Context context;

    @NonNull
    @Override
    public IndicatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_time_line_recycler_view_item_layout, parent, false);

        return new IndicatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndicatorViewHolder holder, int position) {

        TimeLine prevTimeline = (position > 0) ? timeLines.get(position - 1) : null;
        if (prevTimeline != null) {
            if (prevTimeline.getStatus() == Status.COMPLETED) {
                holder.topLineIndicator.setBackground(ContextCompat.getDrawable(context, R.drawable.box_enabled));
            } else {
                holder.topLineIndicator.setBackground(ContextCompat.getDrawable(context, R.drawable.box_disabled));
            }
        }
        TimeLine timeLine = timeLines.get(position);
        if (timeLine.getStatus() != Status.COMPLETED) {
            holder.dotIndicator.setBackground(ContextCompat.getDrawable(context, R.drawable.dot_disabled));
            holder.bottomLineIndicator.setBackground(ContextCompat.getDrawable(context, R.drawable.box_disabled));
        }


        holder.topLineIndicator.setVisibility((position == 0) ? View.INVISIBLE : View.VISIBLE);
        holder.bottomLineIndicator.setVisibility((position == timeLines.size() - 1) ? View.INVISIBLE : View.VISIBLE);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View child = loadView(context, holder.container, timeLine);//_callback ?.onBindView(timeLine, holder.container, position) ?:
        // LayoutInflater.from(holder.itemView.context).inflate(R.layout.sample_time_line, holder.container, false)
        child.setLayoutParams(params); //= ;
        //clean the container
        if (holder.container.getChildCount() > 0) {
            holder.container.removeAllViews();
        }

        //start adding new views
        holder.container.addView(child);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
    }

    View loadView(Context context, FrameLayout container, TimeLine timeLine) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_time_line,
                container, false);
        TextView title = view.findViewById(R.id.tv_title);
        title.setText(timeLine.getTitle());// model.title
        TextView content = view.findViewById(R.id.tv_content);
        content.setText(timeLine.getContent());
              //  (view.findViewById < TextView > (R.id.tv_content)).text = model.content
        return view;
    }

    @Override
    public int getItemCount() {
        return timeLines.size();
    }
}
