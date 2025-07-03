package blackbird.com.recyclerviewdemo.tv.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;



public class CustomerRecyclerView extends RecyclerView {

    private static final String TAG = CustomerRecyclerView.class.getSimpleName();

    public CustomerRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface onKeyEventListener {
        boolean onKeyEven(KeyEvent event);
    }

    private onKeyEventListener listener;

    public void removeKeyEventListener() {
        listener=null;
    }

    public void setKeyEventListener(onKeyEventListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
       // LogUtils.e("-------> dispatchKeyEvent  :" + event );
        if (listener != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT ||
                        event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT)) {
            if (listener.onKeyEven(event)) {
                return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG,"------------------>onKeyDown : "+event);
        return super.onKeyDown(keyCode, event);
    }
}
