package blackbird.com.recyclerviewdemo.timeline;

import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import blackbird.com.recyclerviewdemo.R;

public class IndicatorViewHolder extends RecyclerView.ViewHolder {

    View topLineIndicator,dotIndicator,bottomLineIndicator;
    FrameLayout container;

    public IndicatorViewHolder(@NonNull View itemView) {
        super(itemView);
         topLineIndicator = itemView.findViewById(R.id.line_indicator_top);
         dotIndicator = itemView.findViewById(R.id.dot_indicator);
         bottomLineIndicator = itemView.findViewById(R.id.line_indicator_bottom);
         container = itemView.findViewById(R.id.content);
    }

}
