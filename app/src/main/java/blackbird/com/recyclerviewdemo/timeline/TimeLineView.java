package blackbird.com.recyclerviewdemo.timeline;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import blackbird.com.recyclerviewdemo.R;

public class TimeLineView  extends RelativeLayout {

    RecyclerView recyclerView;


    public TimeLineView(Context context) {
        super(context);
        init(context);
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.timeline_view, this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));// = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
