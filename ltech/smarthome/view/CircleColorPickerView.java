package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class CircleColorPickerView extends View {
    private static final int PICKER_RADIUS = 100;
    private int bgHeight;
    private int bgWidth;
    private int bitmapBackResId;
    private boolean canChangeColor;
    private int flag;
    private Paint mBgPaint;
    private Paint mBgPaintOuter;
    private Bitmap mBitmapBack;
    private BitmapShader mBitmapShader;
    private Point mCenterPoint;
    private Context mContext;
    private final RectF mDrawableRect;
    private OnColorChangedListener mOnColorChangedListener;
    private Point mPickPoint;
    private Paint mPickerPaint;
    private Paint mPickerPaintOuter;
    private int mPickerRadius;
    private final Matrix mShaderMatrix;
    private float minXPercent;
    private float minYPercent;

    public interface OnColorChangedListener {
        void onChangedFinish(int color, float xPercent, float yPercent);

        void onColorChanged(int color, float xPercent, float yPercent);
    }

    public CircleColorPickerView(Context context) {
        this(context, null);
    }

    public CircleColorPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShaderMatrix = new Matrix();
        this.mDrawableRect = new RectF();
        this.canChangeColor = true;
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attrs, R.styleable.CircleColorPickerView);
        this.bitmapBackResId = obtainStyledAttributes.getResourceId(0, R.mipmap.bg_ct);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mBgPaint = paint;
        paint.setAntiAlias(true);
        this.mBgPaint.setDither(true);
        Paint paint2 = new Paint();
        this.mPickerPaint = paint2;
        paint2.setAntiAlias(true);
        this.mPickerPaint.setDither(true);
        this.mPickerPaint.setStyle(Paint.Style.FILL);
        Paint paint3 = new Paint();
        this.mPickerPaintOuter = paint3;
        paint3.setAntiAlias(true);
        this.mPickerPaintOuter.setDither(true);
        this.mPickerPaintOuter.setColor(-1);
        this.mPickerPaintOuter.setShadowLayer(15.0f, 0.0f, 0.0f, -7829368);
        this.mPickPoint = new Point(-100, -100);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getMeasuredHeight() > getMeasuredWidth()) {
            this.flag = 1;
        } else if (getMeasuredWidth() > getMeasuredHeight()) {
            this.flag = -1;
        }
        int min = Math.min(getMeasuredHeight(), getMeasuredWidth());
        this.bgHeight = min;
        this.bgWidth = min;
        int i = min / 30;
        this.mPickerRadius = i;
        if (i > 100) {
            this.mPickerRadius = 100;
        }
        this.mPickerPaint.setStrokeWidth(this.mPickerRadius >> 2);
        this.mCenterPoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        int measuredWidth = getMeasuredWidth();
        float f = ((measuredWidth - r2) / 2.0f) + this.bgWidth;
        int measuredHeight = getMeasuredHeight();
        this.mDrawableRect.set(new RectF((getMeasuredWidth() - this.bgWidth) / 2.0f, (getMeasuredHeight() - this.bgHeight) / 2.0f, f, ((measuredHeight - r3) / 2.0f) + this.bgHeight));
        if (this.bitmapBackResId != 0) {
            this.mBitmapBack = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), this.bitmapBackResId), this.bgWidth, this.bgHeight, false);
        }
        if (this.mBitmapBack != null) {
            BitmapShader bitmapShader = new BitmapShader(this.mBitmapBack, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapShader = bitmapShader;
            this.mBgPaint.setShader(bitmapShader);
            updateShaderMatrix();
        }
    }

    private void updateShaderMatrix() {
        float width;
        float height;
        this.mShaderMatrix.set(null);
        float f = 0.0f;
        if (this.bgWidth * this.mDrawableRect.height() > this.mDrawableRect.width() * this.bgHeight) {
            width = this.mDrawableRect.height() / this.bgHeight;
            f = (this.mDrawableRect.width() - (this.bgWidth * width)) * 0.5f;
            height = 0.0f;
        } else {
            width = this.mDrawableRect.width() / this.bgWidth;
            height = (this.mDrawableRect.height() - (this.bgHeight * width)) * 0.5f;
        }
        this.mShaderMatrix.setScale(width, width);
        this.mShaderMatrix.postTranslate(((int) (f + 0.5f)) + this.mDrawableRect.left, ((int) (height + 0.5f)) + this.mDrawableRect.top);
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mBitmapBack != null) {
            canvas.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, (this.bgWidth / 2) - this.mPickerRadius, this.mBgPaint);
        }
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius, this.mPickerPaintOuter);
        if (this.mPickPoint.x < 0 || this.mPickPoint.y < 0) {
            return;
        }
        this.mPickerPaint.setColor(getBitmapPixel());
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius - this.mPickerPaint.getStrokeWidth(), this.mPickerPaint);
    }

    private int getBitmapPixel() {
        int i = this.flag;
        if (i > 0) {
            return this.mBitmapBack.getPixel(this.mPickPoint.x, this.mPickPoint.y - (this.mCenterPoint.y - (this.bgHeight / 2)));
        }
        if (i < 0) {
            return this.mBitmapBack.getPixel(this.mPickPoint.x - (this.mCenterPoint.x - (this.bgWidth / 2)), this.mPickPoint.y);
        }
        return this.mBitmapBack.getPixel(this.mPickPoint.x, this.mPickPoint.y);
    }

    private float getXpercent() {
        float f;
        float f2;
        float f3;
        if (this.flag < 0) {
            f = this.minXPercent;
            int i = this.mPickPoint.x;
            int i2 = this.mCenterPoint.x;
            int i3 = this.bgWidth;
            int i4 = i - (i2 - (i3 >> 1));
            int i5 = this.mPickerRadius;
            f2 = (i4 - i5) * (100.0f - this.minXPercent);
            f3 = i3 - (i5 * 2);
        } else {
            f = this.minXPercent;
            int i6 = this.mPickPoint.x;
            int i7 = this.mPickerRadius;
            f2 = (i6 - i7) * (100.0f - this.minXPercent);
            f3 = this.bgWidth - (i7 * 2);
        }
        return f + (f2 / f3);
    }

    private float getYPercent() {
        float f;
        float f2;
        float f3;
        if (this.flag > 0) {
            f = this.minYPercent;
            int i = this.mPickPoint.y;
            int i2 = this.mCenterPoint.y;
            int i3 = this.bgHeight;
            int i4 = i - (i2 - (i3 >> 1));
            int i5 = this.mPickerRadius;
            f2 = (i4 - i5) * (100.0f - this.minYPercent);
            f3 = i3 - (i5 * 2);
        } else {
            f = this.minYPercent;
            int i6 = this.mPickPoint.y;
            int i7 = this.mPickerRadius;
            f2 = (i6 - i7) * (100.0f - this.minYPercent);
            f3 = this.bgHeight - (i7 * 2);
        }
        return f + (f2 / f3);
    }

    public void setBitmapBack(final Bitmap bitmap) {
        postDelayed(new Runnable() { // from class: com.ltech.smarthome.view.CircleColorPickerView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CircleColorPickerView.this.lambda$setBitmapBack$0(bitmap);
            }
        }, (this.bgWidth == 0 || this.bgHeight == 0) ? 100L : 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBitmapBack$0(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.getWidth() == this.bgWidth && bitmap.getHeight() == this.bgHeight) {
                this.mBitmapBack = bitmap;
            } else {
                this.mBitmapBack = Bitmap.createScaledBitmap(bitmap, this.bgWidth, this.bgHeight, true);
            }
            BitmapShader bitmapShader = new BitmapShader(this.mBitmapBack, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapShader = bitmapShader;
            this.mBgPaint.setShader(bitmapShader);
            updateShaderMatrix();
            postInvalidate();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0010, code lost:
    
        if (r0 != 3) goto L33;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.CircleColorPickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setCanChangeColor(boolean canChange) {
        this.canChangeColor = canChange;
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.mOnColorChangedListener = onColorChangedListener;
    }

    public float getMinXPercent() {
        return this.minXPercent;
    }

    public void setMinXPercent(float minXPercent) {
        this.minXPercent = minXPercent;
    }

    public float getMinYPercent() {
        return this.minYPercent;
    }

    public void setMinYPercent(float minYPercent) {
        this.minYPercent = minYPercent;
    }
}