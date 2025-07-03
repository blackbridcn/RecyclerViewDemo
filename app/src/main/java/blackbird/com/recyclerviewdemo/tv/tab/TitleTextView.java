package blackbird.com.recyclerviewdemo.tv.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class TitleTextView extends AppCompatTextView {

    private final RectF backgroundRect = new RectF();
    private final RectF bottomLineRect = new RectF();
    private final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TitleTextView(Context context) {
        this(context, null);
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private final Paint underlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int underlineColor = 0xFF00C767; // 默认高亮绿色
    private float underlineHeight = 4f; // 下划线高度
    private float underlinePadding = 4f; // 下划线与文本间距


    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setDefaultFocusHighlightEnabled(false);
        }

        // 初始化阴影画笔
        shadowPaint.setStyle(Paint.Style.FILL);
       // shadowPaint.setColor(0xFFFFFFFF);
        shadowPaint.setShadowLayer(shadowRadius, 0, 0, shadowColor);

        // 初始化边框画笔
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);

        // 设置内边距，确保阴影不会被裁剪
        int padding = (int) (shadowRadius + borderWidth);
        setPadding(padding, padding, padding, padding);



    }

    private final Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path clipPath = new Path();
    private final RectF rectF = new RectF();
    private int shadowColor = 0xFF000000;
    private float shadowRadius = 16f;
    private float cornerRadius = 24f;
    private float borderWidth = 3f;
    //private int borderColor = 0xFFFF5722;
    private int borderColor = 0xFF03DAC5;



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float offset = w * 0.05f;
        backgroundRect.top = offset;
        backgroundRect.left = offset;
        backgroundRect.right = w - offset;
        backgroundRect.bottom = h - offset;

        bottomLineRect.top = h - (h * 0.2f) - offset;
        bottomLineRect.left = w * 0.3f;
        bottomLineRect.right = w * 0.7f;
        bottomLineRect.bottom = h - (h * 0.1f) - offset;


        // 更新圆角矩形区域
        rectF.set(shadowRadius, shadowRadius,
                w - shadowRadius, h - shadowRadius);
        // 创建剪裁路径
        clipPath.reset();
        clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 绘制阴影
     //  canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, shadowPaint);
        // 剪裁画布为圆角矩形
        //canvas.clipPath(clipPath);
        // 绘制子视图
        super.dispatchDraw(canvas);
        // 绘制边框
      //  canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, borderPaint);
    }

    int selTextColor =0xFF03DAC5;
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) return;
        if (isFocused()) {
            setTextColor(selTextColor);
            backgroundPaint.setColor(selTextColor);

        } else if (isSelected()) {
            setTextColor(selTextColor);
            onDrawUnderLine(canvas);
        }else {
            setTextColor(Color.WHITE);
        }

        super.onDraw(canvas);
    }

    void onDrawUnderLine(Canvas canvas){
        // 计算下划线位置
        float startY = getBaseline() + underlinePadding;
        float endY = startY + underlineHeight;

        // 计算文本宽度
        float textWidth = getPaint().measureText(getText().toString());

        // 计算下划线起点和终点（居中显示）
        float centerX = getWidth() / 2f;
        float startX = centerX - textWidth / 2f;
        float endX = centerX + textWidth / 2f;
        // 创建渐变着色器
        LinearGradient gradient = new LinearGradient(
                startX, 0, endX, 0,
                new int[]{0x00000000, underlineColor, underlineColor, 0x00000000},
                new float[]{0f, 0.3f, 0.7f, 1f},
                Shader.TileMode.CLAMP
        );

        // 初始化画笔
        underlinePaint.setStyle(Paint.Style.FILL);

        underlinePaint.setShader(gradient);
        // 绘制渐变下划线
        canvas.drawRect(startX, startY, endX, endY, underlinePaint);
    }
}
