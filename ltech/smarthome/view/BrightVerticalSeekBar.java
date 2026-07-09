package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.LightUtils;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class BrightVerticalSeekBar extends View {
    private double DEFAULT_DELTA;
    private final float INITIAL_PROGRESS_VALUE;
    private int bgColor;
    private Point centerPoint;
    private int curProgress;
    private float deltaY;
    private int height;
    private boolean isInitProgress;
    private int lastProgress;
    private float lastY;
    private RectF mBgRectF;
    private OnProgressChangeListener mOnProgressChangeListener;
    private Paint mPaint;
    private RectF mProgressRextF;
    private final int maxProgress;
    private int progressColor;
    private int radius;
    private int roundRadius;
    private int width;

    public interface OnProgressChangeListener {
        void onProgressChanged(BrightVerticalSeekBar bar);

        void onStartProgressChanged(BrightVerticalSeekBar bar);

        void onStopProgressChanged(BrightVerticalSeekBar bar);
    }

    public BrightVerticalSeekBar(Context context) {
        super(context, null);
        this.bgColor = -7829368;
        this.progressColor = SupportMenu.CATEGORY_MASK;
        this.INITIAL_PROGRESS_VALUE = 0.01f;
        this.DEFAULT_DELTA = 1.0E-6d;
        this.lastY = 0.0f;
        this.deltaY = 0.0f;
        this.lastProgress = 0;
        this.isInitProgress = false;
        this.curProgress = 0;
        this.roundRadius = 30;
        this.maxProgress = 100;
    }

    public BrightVerticalSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrightVerticalSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bgColor = -7829368;
        this.progressColor = SupportMenu.CATEGORY_MASK;
        this.INITIAL_PROGRESS_VALUE = 0.01f;
        this.DEFAULT_DELTA = 1.0E-6d;
        this.lastY = 0.0f;
        this.deltaY = 0.0f;
        this.lastProgress = 0;
        this.isInitProgress = false;
        this.curProgress = 0;
        this.roundRadius = 30;
        this.maxProgress = 100;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.BrightVerticalSeekBar);
        this.bgColor = obtainStyledAttributes.getColor(1, -7829368);
        this.progressColor = obtainStyledAttributes.getColor(2, SupportMenu.CATEGORY_MASK);
        this.roundRadius = (int) obtainStyledAttributes.getDimension(0, 30.0f);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setColor(-1);
        this.mPaint.setStrokeWidth(5.0f);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.centerPoint = new Point();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.mBgRectF = new RectF(0.0f, 0.0f, this.width, this.height);
        int i = this.height;
        this.mProgressRextF = new RectF(0.0f, i, this.width, i);
        this.centerPoint.x = this.width / 2;
        this.centerPoint.y = (this.height * 5) / 6;
        this.radius = this.width / 13;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(createImage(), 0.0f, 0.0f, this.mPaint);
    }

    public void setCurrentProgress(int progress) {
        this.curProgress = progress;
        if (!this.isInitProgress) {
            this.lastProgress = progress;
            this.isInitProgress = true;
        }
        postInvalidate();
    }

    private Bitmap createImage() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap createBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        paint.setColor(this.bgColor);
        RectF rectF = this.mBgRectF;
        int i = this.roundRadius;
        canvas.drawRoundRect(rectF, i, i, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        paint.setColor(this.progressColor);
        this.mProgressRextF.top = this.height * (1.0f - (this.curProgress / 100.0f));
        LHomeLog.i(getClass(), "onDraw=" + (this.curProgress / 100.0f) + "___mProgressRextF.top=" + this.mProgressRextF.top);
        canvas.drawRect(this.mProgressRextF, paint);
        return createBitmap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000d, code lost:
    
        if (r0 != 3) goto L18;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r4) {
        /*
            r3 = this;
            int r0 = r4.getAction()
            r1 = 1
            if (r0 == 0) goto L40
            if (r0 == r1) goto L2b
            r2 = 2
            if (r0 == r2) goto L10
            r2 = 3
            if (r0 == r2) goto L2b
            goto L46
        L10:
            float r0 = r4.getX()
            int r0 = (int) r0
            float r2 = r4.getY()
            int r2 = (int) r2
            r3.calculateProgress(r0, r2)
            com.ltech.smarthome.view.BrightVerticalSeekBar$OnProgressChangeListener r0 = r3.mOnProgressChangeListener
            if (r0 == 0) goto L24
            r0.onProgressChanged(r3)
        L24:
            float r4 = r4.getY()
            r3.lastY = r4
            goto L46
        L2b:
            float r0 = r4.getX()
            int r0 = (int) r0
            float r4 = r4.getY()
            int r4 = (int) r4
            r3.calculateProgress(r0, r4)
            com.ltech.smarthome.view.BrightVerticalSeekBar$OnProgressChangeListener r4 = r3.mOnProgressChangeListener
            if (r4 == 0) goto L46
            r4.onStopProgressChanged(r3)
            goto L46
        L40:
            float r4 = r4.getY()
            r3.lastY = r4
        L46:
            r3.postInvalidate()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.BrightVerticalSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public float getCurProgress() {
        return this.curProgress;
    }

    public int getProgress() {
        return this.curProgress;
    }

    public String getProgressPercent() {
        return LightUtils.getProgressHasBelowOne(this.curProgress);
    }

    private void calculateProgress(int x, int y) {
        this.deltaY = Math.abs(this.lastY - y);
        int i = this.height;
        if (y >= i) {
            this.curProgress = 1;
        } else if (y <= 0) {
            this.curProgress = 100;
        } else {
            int i2 = (int) ((((i - y) * 1.0f) / i) * 100.0f);
            this.curProgress = i2;
            this.curProgress = Math.max(i2, 1);
        }
        LHomeLog.i(getClass(), "y=" + y + "__height=" + this.height + "curProgress=" + this.curProgress);
        this.lastProgress = this.curProgress;
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.mOnProgressChangeListener = onProgressChangeListener;
    }
}