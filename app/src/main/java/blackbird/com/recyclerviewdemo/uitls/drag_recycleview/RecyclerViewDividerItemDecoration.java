package blackbird.com.recyclerviewdemo.uitls.drag_recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView 分割线[可以左右缩进]
 * <p>
 * Created by yzzhang on 2017/9/4.
 */

public class RecyclerViewDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private Drawable mDivider;

    /**
     * 分割线方向. HORIZONTAL or  #VERTICAL
     */
    private int mOrientation;
    /**
     * decoration indent left and right
     * 分割线 左右缩进距离int 值
     */
    private int inset = 0;

    private final Rect mBounds = new Rect();

    /**
     * 构造方法
     *
     * @param mContext        加载resources用到的Context
     * @param dividerDrawable 传入一个自定义的drawable作为分割线：
     * @param inset           分割线 左右缩进距离d
     * @param orientation     分割线方向 Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL
     */
    public RecyclerViewDividerItemDecoration(Context mContext, int dividerDrawable, int inset, int orientation) {
        mDivider = mContext.getResources().getDrawable(dividerDrawable);
        setInset(inset);
        setOrientation(orientation);
    }

    /***
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with this
     * @param mContext Current context, it will be used to access resources.
     * @param dividerDrawable  divider line should be R.id.drawable;
     */
    public RecyclerViewDividerItemDecoration(Context mContext, int dividerDrawable, int inset) {
        mDivider = mContext.getResources().getDrawable(dividerDrawable);
        setInset(inset);
    }

    public RecyclerViewDividerItemDecoration(Context mContext, int dividerDrawable) {
        mDivider = mContext.getResources().getDrawable(dividerDrawable);
    }

    /**
     * Set decoration indent left and right values@{Int }}
     *
     * @param inset ident left and right values
     */
    public void setInset(int inset) {
        this.inset = inset;
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @SuppressLint("NewApi")
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        //最后一个Item下不需要添加分割线
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - mDivider.getIntrinsicHeight();
            if (inset > 0) {
                mDivider.setBounds(left + inset, top, right - inset, bottom);
            } else {
                mDivider.setBounds(left, top, right, bottom);
            }
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @SuppressLint("NewApi")
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        //最后一个Item下不需要添加分割线
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
            final int left = right - mDivider.getIntrinsicWidth();
            if (inset > 0) {
                mDivider.setBounds(left + inset, top, right - inset, bottom);
            } else {
                mDivider.setBounds(left, top, right, bottom);
            }
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    //由于分割线引起的Item偏移
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}