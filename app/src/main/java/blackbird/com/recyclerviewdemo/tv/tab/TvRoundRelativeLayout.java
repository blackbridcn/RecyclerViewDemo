package blackbird.com.recyclerviewdemo.tv.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import blackbird.com.recyclerviewdemo.R;


public class TvRoundRelativeLayout extends RelativeLayout {

    private final Context mContext;

    private int mStrokeColor;
    private float mStrokeWidth;
    private float mRadius;
    private boolean mNeedShadow;
    private int mShadowColor;

    private final Path mPath;
    private final Paint mPaint;
    private final RectF mRectF;
    private final RectF mStrokeRectF;

    private int mWidth;
    private int mHeight;

    private final float[] mStrokeRadii;
    private final float[] mRadii;

    public TvRoundRelativeLayout(Context context) {
        this(context, null);
    }

    public TvRoundRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TvRoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mStrokeRadii = new float[8];
        mRadii = new float[8];

        mPaint = new Paint();
        mPath = new Path();
        mRectF = new RectF();
        mStrokeRectF = new RectF();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCorner);
        if (array == null) {
            return;
        }
        mRadius = array.getDimension(R.styleable.RoundCorner_radius, 0);
        mStrokeWidth = array.getDimension(R.styleable.RoundCorner_strokeWidth, 0);
        mStrokeColor = array.getColor(R.styleable.RoundCorner_strokeColor, Color.WHITE);
        mNeedShadow = array.getBoolean(R.styleable.RoundCorner_needShadow,false);
        mShadowColor = array.getColor(R.styleable.RoundCorner_shadowColor,Color.TRANSPARENT);
        array.recycle();

        initRound();
    }


    public void setStrokeWidth(int width) {
        mStrokeWidth = ScreenUtil.dp2pxFloat(mContext,width);
        onChange(mWidth,mHeight);
        invalidate();
    }

    public void setRadius(int radius) {
        mRadius = ScreenUtil.dp2pxFloat(mContext,radius);
        initRound();
        onChange(mWidth,mHeight);
    }

    public void setStrokeColor(int color) {
        mStrokeColor = color;
        invalidate();
    }

    public void setShadow(boolean needShadow,int shadowColor) {
        mNeedShadow = needShadow;
        mShadowColor = shadowColor;
        onChange(mWidth,mHeight);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        onChange(width,height);
    }

    private void onChange(int width, int height) {
        mWidth = width;
        mHeight = height;

        mRadii[0] = mRadii[1] = mRadius - mStrokeWidth;
        mRadii[2] = mRadii[3] = mRadius - mStrokeWidth;
        mRadii[4] = mRadii[5] = mRadius - mStrokeWidth;
        mRadii[6] = mRadii[7] = mRadius - mStrokeWidth;

        mStrokeRadii[0] = mStrokeRadii[1] = mRadius - mStrokeWidth / 2;
        mStrokeRadii[2] = mStrokeRadii[3] = mRadius - mStrokeWidth / 2;
        mStrokeRadii[4] = mStrokeRadii[5] = mRadius - mStrokeWidth / 2;
        mStrokeRadii[6] = mStrokeRadii[7] = mRadius - mStrokeWidth / 2;

        if (mRectF != null) {
            mRectF.set(mStrokeWidth, mStrokeWidth, width - mStrokeWidth, height - mStrokeWidth);
        }

        if (mStrokeRectF != null) {
            mStrokeRectF.set((mStrokeWidth / 2), (mStrokeWidth / 2), width - mStrokeWidth / 2, height - mStrokeWidth / 2);
        }
    }




    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        if (mStrokeWidth > 0) {
            mPaint.reset();
            mPath.reset();
            mPaint.setAntiAlias(true);
            canvas.restore();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(mStrokeColor);
            mPath.addRoundRect(mStrokeRectF, mStrokeRadii, Path.Direction.CCW);
            canvas.drawPath(mPath, mPaint);
        }

        if (mNeedShadow) {
            mPaint.reset();
            mPath.reset();
            mPaint.setAntiAlias(true);
            //canvas.restore();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mShadowColor);
            mPath.addRoundRect(mRectF, mRadii, Path.Direction.CCW);
            canvas.drawPath(mPath,mPaint);
        }
    }

    /**
     * 初始化圆角
     */
    private void initRound() {
        RoundViewOutlineProvider outlineProvider = new RoundViewOutlineProvider(mRadius);
        setOutlineProvider(outlineProvider);
        setClipToOutline(true);
    }

    /**
     * 圆角ViewOutlineProvider
     */
    private static class RoundViewOutlineProvider extends ViewOutlineProvider {
        private final float roundSize;

        public RoundViewOutlineProvider(float roundSize) {
            this.roundSize = roundSize;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), roundSize);
        }
    }
}
