package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class RectProgressBar extends View {
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private int bgColol;
    private float curProgress;
    private int height;
    private Paint mBgPaint;
    private RectF mBgRectF;
    private OnProgressChangeListener mOnProgressChangeListener;
    private Paint mProgressPaint;
    private RectF mProgressRectF;
    private Shader mProgressShader;
    private Paint mThumbPaint;
    private RectF mThumbRectF;
    private Matrix matrix;
    private int orientation;
    private int progressColor;
    private int thumbHeight;
    private int width;

    public interface OnProgressChangeListener {
        void onProgressChanged(RectProgressBar bar);

        void onStartProgressChanged(RectProgressBar bar);

        void onStopProgressChanged(RectProgressBar bar);
    }

    public RectProgressBar(Context context) {
        this(context, null);
    }

    public RectProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.orientation = 1;
        this.bgColol = -7829368;
        this.progressColor = SupportMenu.CATEGORY_MASK;
        this.curProgress = 0.0f;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RectProgressBar);
        if (obtainStyledAttributes != null) {
            this.orientation = obtainStyledAttributes.getInt(1, 1);
            this.bgColol = obtainStyledAttributes.getColor(0, -7829368);
            this.progressColor = obtainStyledAttributes.getColor(2, SupportMenu.CATEGORY_MASK);
            this.thumbHeight = (int) obtainStyledAttributes.getDimension(3, -1.0f);
            obtainStyledAttributes.recycle();
        }
        init();
        setLayerType(1, null);
    }

    private void init() {
        Paint paint = new Paint();
        this.mBgPaint = paint;
        paint.setAntiAlias(true);
        this.mBgPaint.setColor(this.bgColol);
        Paint paint2 = new Paint();
        this.mProgressPaint = paint2;
        paint2.setAntiAlias(true);
        Paint paint3 = new Paint();
        this.mThumbPaint = paint3;
        paint3.setAntiAlias(true);
        this.mThumbPaint.setColor(-1);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.mBgRectF = new RectF(0.0f, 0.0f, this.width, this.height);
        if (this.orientation == 0) {
            RectF rectF = new RectF();
            this.mProgressRectF = rectF;
            rectF.left = 0.0f;
            this.mProgressRectF.top = 0.0f;
            this.mProgressRectF.bottom = this.height;
            RectF rectF2 = new RectF();
            this.mThumbRectF = rectF2;
            rectF2.top = 0.0f;
            this.mThumbRectF.bottom = this.height;
            if (this.thumbHeight < 0) {
                this.thumbHeight = this.width / 12;
            }
            LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, this.width, 0.0f, this.progressColor, -1, Shader.TileMode.CLAMP);
            this.mProgressShader = linearGradient;
            this.mProgressPaint.setShader(linearGradient);
            Paint paint = this.mThumbPaint;
            int i = this.thumbHeight;
            paint.setShadowLayer(i / 3, -(i >> 3), 0.0f, -3355444);
        } else {
            RectF rectF3 = new RectF();
            this.mProgressRectF = rectF3;
            rectF3.left = 0.0f;
            this.mProgressRectF.right = this.width;
            this.mProgressRectF.bottom = this.height;
            RectF rectF4 = new RectF();
            this.mThumbRectF = rectF4;
            rectF4.left = 0.0f;
            this.mThumbRectF.right = this.width;
            if (this.thumbHeight < 0) {
                this.thumbHeight = this.height / 12;
            }
            LinearGradient linearGradient2 = new LinearGradient(0.0f, 0.0f, 0.0f, this.height, -1, this.progressColor, Shader.TileMode.CLAMP);
            this.mProgressShader = linearGradient2;
            this.mProgressPaint.setShader(linearGradient2);
            Paint paint2 = this.mThumbPaint;
            int i2 = this.thumbHeight;
            paint2.setShadowLayer(i2 / 3, 0.0f, i2 >> 3, -3355444);
        }
        this.matrix = new Matrix();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.mBgRectF;
        int i = this.thumbHeight;
        canvas.drawRoundRect(rectF, i >> 1, i >> 1, this.mBgPaint);
        if (this.orientation == 0) {
            RectF rectF2 = this.mProgressRectF;
            int i2 = this.width;
            rectF2.right = ((i2 - r4) * this.curProgress) + this.thumbHeight;
            this.matrix.setScale(this.curProgress, 1.0f, 0.0f, 0.0f);
            this.mProgressShader.setLocalMatrix(this.matrix);
            RectF rectF3 = this.mProgressRectF;
            int i3 = this.thumbHeight;
            canvas.drawRoundRect(rectF3, i3 >> 1, i3 >> 1, this.mProgressPaint);
            this.mThumbRectF.left = (this.width - this.thumbHeight) * this.curProgress;
            RectF rectF4 = this.mThumbRectF;
            rectF4.right = rectF4.left + this.thumbHeight;
            RectF rectF5 = this.mThumbRectF;
            int i4 = this.thumbHeight;
            canvas.drawRoundRect(rectF5, i4 >> 1, i4 >> 1, this.mThumbPaint);
            return;
        }
        this.mProgressRectF.top = (this.height - this.thumbHeight) * (1.0f - this.curProgress);
        this.matrix.setScale(1.0f, this.curProgress, 0.0f, this.height);
        this.mProgressShader.setLocalMatrix(this.matrix);
        RectF rectF6 = this.mProgressRectF;
        int i5 = this.thumbHeight;
        canvas.drawRoundRect(rectF6, i5 >> 1, i5 >> 1, this.mProgressPaint);
        this.mThumbRectF.top = (this.height - this.thumbHeight) * (1.0f - this.curProgress);
        RectF rectF7 = this.mThumbRectF;
        rectF7.bottom = rectF7.top + this.thumbHeight;
        RectF rectF8 = this.mThumbRectF;
        int i6 = this.thumbHeight;
        canvas.drawRoundRect(rectF8, i6 >> 1, i6 >> 1, this.mThumbPaint);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000d, code lost:
    
        if (r0 != 3) goto L19;
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
            if (r0 == 0) goto L3a
            if (r0 == r1) goto L25
            r2 = 2
            if (r0 == r2) goto L10
            r2 = 3
            if (r0 == r2) goto L25
            goto L4e
        L10:
            float r0 = r4.getX()
            int r0 = (int) r0
            float r4 = r4.getY()
            int r4 = (int) r4
            r3.calculateProgress(r0, r4)
            com.ltech.smarthome.view.RectProgressBar$OnProgressChangeListener r4 = r3.mOnProgressChangeListener
            if (r4 == 0) goto L4e
            r4.onProgressChanged(r3)
            goto L4e
        L25:
            float r0 = r4.getX()
            int r0 = (int) r0
            float r4 = r4.getY()
            int r4 = (int) r4
            r3.calculateProgress(r0, r4)
            com.ltech.smarthome.view.RectProgressBar$OnProgressChangeListener r4 = r3.mOnProgressChangeListener
            if (r4 == 0) goto L4e
            r4.onStopProgressChanged(r3)
            goto L4e
        L3a:
            float r0 = r4.getX()
            int r0 = (int) r0
            float r4 = r4.getY()
            int r4 = (int) r4
            r3.calculateProgress(r0, r4)
            com.ltech.smarthome.view.RectProgressBar$OnProgressChangeListener r4 = r3.mOnProgressChangeListener
            if (r4 == 0) goto L4e
            r4.onStartProgressChanged(r3)
        L4e:
            r3.postInvalidate()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.RectProgressBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void calculateProgress(int x, int y) {
        if (this.orientation == 0) {
            int i = this.width;
            if (x > i) {
                x = i;
            } else if (x < 0) {
                x = 0;
            }
            this.curProgress = (x * 1.0f) / i;
            return;
        }
        int i2 = this.height;
        if (y > i2) {
            y = i2;
        } else if (y < 0) {
            y = 0;
        }
        this.curProgress = ((i2 - y) * 1.0f) / i2;
    }

    public void setCurrentProgress(float progress) {
        this.curProgress = progress;
        postInvalidate();
    }

    public float getCurProgress() {
        return this.curProgress;
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.mOnProgressChangeListener = onProgressChangeListener;
    }
}