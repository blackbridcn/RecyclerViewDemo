package blackbird.com.recyclerviewdemo.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

public class ScrollerTest  extends View{

    private Scroller scroller;

    public ScrollerTest(Context context) {
        super(context);
    }

    public ScrollerTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollerTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(Context context){
        scroller = new Scroller(context);

    }

    private void smoothScrollTo(int destX,int destY){
         int scrollX = getScrollX();
         int deltax=destX-scrollX;
        scroller.startScroll(scrollX,0,deltax,0,1000);
        invalidate();
    }
    //scroller.compuScrollOffset()来判断View的滚动是否在继续，返回true表示还在继续，false
    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}
