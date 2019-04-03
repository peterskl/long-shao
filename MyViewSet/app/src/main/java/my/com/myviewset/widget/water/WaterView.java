package my.com.myviewset.widget.water;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import my.com.myviewset.R;


/**
 * Created by Administrator on 2017/5/22.
 */

public class WaterView extends View  {

    public static final int BORDER_SQUARE = 0;
    public static final int BORDER_CIRCLE = 1;
    private final int START_ANIMA = 999;
    private int DEFAULT_BORDER_COLOR = 0x33ff0000;//默认颜色
    private int DEFAULT_BELOW_COLOR = 0x99ff0000;
    private float DEFAULT_BORDER_SIZE = 10;//默认外框大小
    // 水波位移
    private float mWaveTranslateRatio = 1.0f;
    // 水波幅度
    private float DEFAULT_RANGE = 0.05f;
    private float range = DEFAULT_RANGE;
    // 水波平移速度
    private int time = 2000;

    private int borderColor;//外框颜色
    private float borderSize;//外框大小
    private int aboveColor;//前面水波颜色
    private int belowColor;//底下水波颜色
    private int borderStyle;
    private float tempProgress = 0.5f;//临时进度-为0时，正弦函数下峰超过控件，导致平移时丢失图像
    private float progress;//实时进度

    private float max = 0;//正弦曲线最大值
    private float min = 0;//正弦曲线最小值

    private BitmapShader bitmapShader;
    private Matrix mShaderMatrix;
    private Paint mPaint;
    private Paint mBorderPaint;

    public WaterView(Context context) {
        this(context, null);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterView);
        borderColor = typedArray.getColor(R.styleable.WaterView_borderColor, DEFAULT_BORDER_COLOR);
        borderSize = typedArray.getDimension(R.styleable.WaterView_borderSize, DEFAULT_BORDER_SIZE);
        aboveColor = typedArray.getColor(R.styleable.WaterView_aboveColor, DEFAULT_BORDER_COLOR);
        belowColor = typedArray.getColor(R.styleable.WaterView_belowColor, DEFAULT_BELOW_COLOR);
        borderStyle = typedArray.getInteger(R.styleable.WaterView_borderStyle, BORDER_CIRCLE);
        progress = typedArray.getFloat(R.styleable.WaterView_progress, 0);
        //初始化
        mShaderMatrix = new Matrix();
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(borderSize);
        mBorderPaint.setColor(borderColor);
        //开启动画
        //值动画
        ValueAnimator va = ValueAnimator.ofFloat(0, 1f);//平滑过渡0-1
        va.setDuration(time);//设置一次动画时长
//                    va.setStartDelay(0);
        va.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        va.setRepeatMode(ValueAnimator.RESTART);//正序循环
        va.setInterpolator(new LinearInterpolator());//设置动画加速器，LinearInterpolator()-以常量速率改变(不加速)
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setWaveTranslateRatio((Float) animation.getAnimatedValue());
            }
        });
    }

    /**
     * 设置外框颜色
     */
    public void setBorderColor(int color) {
        mBorderPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置外框风格
     */
    public void setBorderStyle(BorderStyle value) {
        borderStyle = value.getValue();
        invalidate();
    }

    /**
     * 设置水波颜色
     */
    public void setAllWaterColor(int color) {
        this.aboveColor = color;
        invalidate();
    }

    /**
     * 设置水波幅度
     */
    public void setRange(int range) {
        this.range = range / 100f;
        createShader();
        invalidate();
    }

    //设置水波幅度为默认值
    public void setDefaultRange() {
        this.range = DEFAULT_RANGE;
        invalidate();
    }

    //设置水波进度
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    //设置水波平移
    private void setWaveTranslateRatio(float value) {
        mWaveTranslateRatio = value;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("WaterView", "onSizeChanged()");
        createShader();
//        invalidate();
    }

    private void createShader() {
        int height = getHeight();
        int width = getWidth();
        Log.i("WaterView", "width=" + width + "，height=" + height);
        //周期 Math.PI-π，设定一周期长度刚为控件宽
        double frequency = 2 * Math.PI / width;
        float amplitude = height * range;//幅度大小
        //Bitmap.Config.ARGB_8888，设定颜色位数为32位色
        Bitmap waveBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(waveBitmap);
        //第一个曲线图像
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//Paint.ANTI_ALIAS_FLAG-去锯齿
        paint.setColor(aboveColor);
        Path path = new Path();
        path.moveTo(0, height);//设置起点，默认为(0,0)
        //设置镂空（方便查看效果）- 不设置时将会与起点形成封闭图形并填充颜色
//        paint.setStyle(Paint.Style.STROKE);//只看见线条，不填充颜色
        //第二个曲线图像
        Paint belowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        belowPaint.setColor(belowColor);
        Path belowPath = new Path();
        belowPath.moveTo(0, height);
        for (int x = 0; x <= width; x++) {
            // k-振幅，A-周期，b-X偏移量，c-Y偏移量
            // y = k sin(Ax + b) + c
            float aboveY = amplitude * (float) Math.sin(frequency * x) + height * (1 - tempProgress);
            float belowY = amplitude * (float) Math.sin(frequency * x + Math.PI / 2) + height * (1 - tempProgress);//往X轴平移π/2
            path.lineTo(x, aboveY);
            belowPath.lineTo(x, belowY);
        }
        /**
         * 正弦函数的极值为±振幅加Y轴偏移量，即是amplitude + height * (1 - tempProgress)
         * 因为在android中原点在左上角，下方为x轴正轴，右方为Y轴正轴，所以Y轴偏移量为0
         * 因为Y轴偏移为0，极值为±amplitude
         */
        //只取最大值
        if (amplitude > 0) {
            max = amplitude;
//            min = -amplitude;
        } else {
            max = -amplitude;
//            min = amplitude;
        }
        Log.i("WaterView", "max=" + max + "");
        Log.i("WaterView", "min=" + min + "");

        path.lineTo(width + 1, height);
        belowPath.lineTo(width + 1, height);
        canvas.drawPath(path, paint);
        canvas.drawPath(belowPath, belowPaint);
        /**
         * BitmapShader
         * CLAMP：如果超出原始bounds(即原图的边界)，则重复边缘上的color
         * REPEAT：重复bitmap
         * MIRROR：重复bitmap，与REPEAT不同的时，它是镜像重复，即：反向重复
         */
        bitmapShader = new BitmapShader(waveBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);//把canvas处理过的bitmap存到BitmapShader
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置平移变换---注：Matris中，使用set是会重置所有变换，pre是在最前面插入一个变换，post是在最后面插入一个变换
        //减max，使正弦图像最低点高于进度，以使进度为100%(1f)时不会出现水波
        mShaderMatrix.setTranslate(mWaveTranslateRatio * getWidth(), (tempProgress - progress) * getHeight() - max);
        /**
         * postScale(float sx, float sy, float px, float py)
         * sx,sy-x、y轴上的伸缩比，大于1为放大，小于1大于0为缩小，负数为对称变化(镜子和倒影效果)
         * px，py用于指定缩放的中心，默认(0,0)
         * sx为x轴上的缩放比，所以通过缩放，可以控制正弦图像的周期
         * sy为y轴上的缩放比，所以通过缩放，可以控制正弦图像的振幅
         * 坐标(0, getHeight() * (1 - 0.5f)，为进度条的左上角位置，刚好为正弦图像的零点(中间点)，以此来缩放不会影响图像的对称性|
         */
        mShaderMatrix.postScale(1, 1, 0, getHeight() * (1 - progress));
        //设置矩阵变换
        bitmapShader.setLocalMatrix(mShaderMatrix);

//        borderWidth = mBorderPaint == null ? 0 : (int) mBorderPaint.getStrokeWidth();
        int borderWidth = (int) mBorderPaint.getStrokeWidth();
//        Log.i("WaterView", "borderWidth=" + borderWidth + "");
        switch (borderStyle) {
            case BORDER_SQUARE:
                //画外框
                //判断外框线宽是否大于0，因即使paint设定线宽为0，仍会绘制出线条，所以当线宽设置为0的时候，不绘制外框
                if (borderWidth > 0) {
                    /**
                     * canvas.rawRect(float left, float top, float right, float bottom, Paint paint)
                     * l,t,r,b-指定在线与边界的距离处画线，paint.setStrokeWidth(5);-设置线宽
                     * 注：因为l,t,r,b为指定在与边界距离的地方画线，并不是画的线与边界的距离，所以实际与边界的距离为l,t,r,b减去二分一的线宽
                     */
                    canvas.drawRect(borderWidth / 2f, borderWidth / 2f, getWidth() - borderWidth / 2f, getHeight() - borderWidth / 2f, mBorderPaint);
                }
                //把水波绘制至控件
                canvas.drawRect(borderWidth, borderWidth, getWidth() - borderWidth, getHeight() - borderWidth, mPaint);
                break;
            case BORDER_CIRCLE:
                if (borderWidth > 0) {
                    //画圆，原理同上
                    /**
                     * drawCircle (float cx, float cy, float radius, Paint paint)
                     * cx-圆心的x坐标，xy-圆心的y坐标，radius-圆的半径
                     */
                    canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - borderWidth / 2f, mBorderPaint);
                }
                //为了水波不漏出外框，也需要进行计算
                canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - borderWidth, mPaint);
                break;
        }

        //辅助线
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#ff0000"));
        linePaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(5);
        Path path = new Path();
        path.moveTo(0, getHeight() * (1 - progress));
        path.lineTo(getWidth(), getHeight() * (1 - progress));
        canvas.drawPath(path, linePaint);
    }

    public enum BorderStyle {
        square(0), circle(1);

        private final int value;

        BorderStyle(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
