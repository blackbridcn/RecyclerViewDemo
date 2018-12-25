package blackbird.com.recyclerviewdemo.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class VelocityTrackerTest extends View {

    private static final String TAG = "VelocityTracker";
    public List<Float> data = new ArrayList<Float>();

    Paint mPaint = new Paint();
    private VelocityTracker tracker;


    public VelocityTrackerTest(Context context) {
        super(context);
        init();
    }

    public VelocityTrackerTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VelocityTrackerTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(0xffed1941);
        mPaint.setTextSize(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float[] pts = new float[data.size()];
        for (int i = 0; i < pts.length; i++) {
            pts[i] = data.get(i);
        }
        canvas.drawLines(pts, mPaint);
        // super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tracker = VelocityTracker.obtain();
                break;
            case MotionEvent.ACTION_MOVE:
                tracker.addMovement(event);
                tracker.computeCurrentVelocity(1000);
                float vx = tracker.getXVelocity();
                float vy = tracker.getYVelocity();
                data.add(event.getX());
                data.add(event.getY());

                Log.e(TAG, "onTouchEvent: -------------> vx:" + vx + "  vy:" + vy);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                tracker.clear();
                tracker.recycle();
                break;
        }


        return true;//super.onTouchEvent(event);
    }

}
