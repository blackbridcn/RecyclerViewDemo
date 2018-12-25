package blackbird.com.recyclerviewdemo.test;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GestureDetectorTest extends View implements GestureDetector.OnGestureListener {
    private static final String TAG = "GestureDetector";
    public List<Float> data ;

    Paint mPaint ;
    private GestureDetector detector;

    public GestureDetectorTest(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectorTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectorTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        Log.e(TAG, "init: --------------------》" );
        mPaint = new Paint();
        data = new ArrayList<Float>();
        detector = new GestureDetector(context,this);
        //解决长按屏幕后无法拖动的现象
        detector.setIsLongpressEnabled(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;//super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.e(TAG, "onDown: -----------------> " );
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

        //拖动
        Log.e(TAG, "onShowPress: -----------------> " );

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //单击屏幕
        Log.e(TAG, "onSingleTapUp: -----------------> " );
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e(TAG, "onScroll: -----------------> distanceX ："+distanceX+"   distanceY:"+distanceY );
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长按
        Log.e(TAG, "onLongPress: -----------------> " );
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e(TAG, "onFling: ----------------->velocityX： "+velocityX+"   velocityY:"+velocityY );
        return false;
    }

}
