package blackbird.com.recyclerviewdemo.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ScrollTest extends View {

    private int dx;
    private int dy;
    private Paint paint;

    public ScrollTest(Context context) {
        super(context);
        init();
    }

    public ScrollTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextSize(20);
    }

    /**
     * scrollTo(int x, int y);
     * 　x: 当x为正值时，表示View向左滚动了x距离
     * <p>
     * 　　　　　 当x为负值时，表示View向右移动了x距离
     * <p>
     * 　　　　y:当y为正值时，表示View向上滚动了y距离
     * <p>
     * 　　　　　 当y为负值时，表示View向下移动了y距离
     *
     * @param event
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "onTouchEvent: -------------- > Action :" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = (int) event.getX();
                dy = (int) event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE:
                int mx = (int) event.getX();
                int my = (int) event.getY();

                ((View) getParent()).scrollBy(-(mx - dx), -(my - dy));
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;//super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int w, h;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    public String text = "SCROLL_TEXT";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, 0, h / 2, paint);
    }
}
