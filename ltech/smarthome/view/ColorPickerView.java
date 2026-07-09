package com.ltech.smarthome.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ColorPickerView extends View {
    private static final int PICKER_RADIUS = 100;
    private int bgHeight;
    private int bgWidth;
    private int bitmapBackResId;
    private Paint mBgPaint;
    private Bitmap mBitmapBack;
    private Context mContext;
    private OnColorChangedListener mOnColorChangedListener;
    private Point mPickPoint;
    private Paint mPickerPaint;
    private Paint mPickerPaintOuter;
    private int mPickerRadius;
    private int minX;
    private float minXPercent;
    private int minY;
    private float minYPercent;

    public interface OnColorChangedListener {
        void onChangedFinish(int color, float xPercent, float yPercent);

        void onColorChanged(int color, float xPercent, float yPercent);
    }

    public ColorPickerView(Context context) {
        this(context, null);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
        setLayerType(1, null);
    }

    private void init(AttributeSet attrs) {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attrs, R.styleable.ColorPickerView);
        if (obtainStyledAttributes.hasValue(0)) {
            this.bitmapBackResId = obtainStyledAttributes.getResourceId(0, 0);
        }
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
        this.bgHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        this.bgWidth = measuredWidth;
        int i = measuredWidth / 30;
        this.mPickerRadius = i;
        if (i > 100) {
            this.mPickerRadius = 100;
        }
        this.mPickerPaint.setStrokeWidth(this.mPickerRadius / 4.0f);
        if (this.bitmapBackResId != 0) {
            this.mBitmapBack = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), this.bitmapBackResId), this.bgWidth, this.bgHeight, false);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.mBitmapBack;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mBgPaint);
        }
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius, this.mPickerPaintOuter);
        if (this.mPickPoint.x < 0 || this.mPickPoint.y < 0) {
            return;
        }
        this.mPickerPaint.setColor(this.mBitmapBack.getPixel(this.mPickPoint.x, this.mPickPoint.y));
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius - this.mPickerPaint.getStrokeWidth(), this.mPickerPaint);
    }

    public void setPickPosition(float xPercent, float yPercent) {
        int i = this.bgWidth;
        int i2 = this.mPickerRadius;
        float f = this.minXPercent;
        final int i3 = (int) ((((i - (i2 * 2)) * (xPercent - f)) / (100.0f - f)) + i2);
        float f2 = this.bgHeight - (i2 * 2);
        float f3 = this.minYPercent;
        final int i4 = (int) (((f2 * (yPercent - f3)) / (100.0f - f3)) + i2);
        final int i5 = this.mPickPoint.x;
        final int i6 = this.mPickPoint.y;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.ColorPickerView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ColorPickerView.this.lambda$setPickPosition$0(i5, i3, i6, i4, valueAnimator);
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.ltech.smarthome.view.ColorPickerView.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                ColorPickerView.this.mPickPoint.x = i3;
                ColorPickerView.this.mPickPoint.y = i4;
                ColorPickerView.this.invalidate();
            }
        });
        ofFloat.setDuration(400L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPickPosition$0(int i, int i2, int i3, int i4, ValueAnimator valueAnimator) {
        this.mPickPoint.x = (int) (i + ((i2 - i) * ((Float) valueAnimator.getAnimatedValue()).floatValue()));
        this.mPickPoint.y = (int) (i3 + ((i4 - i3) * ((Float) valueAnimator.getAnimatedValue()).floatValue()));
        invalidate();
    }

    public void clearPicker() {
        setPickPosition(-100.0f, -100.0f);
    }

    public void setBitmapBack(final Bitmap bitmap) {
        postDelayed(new Runnable() { // from class: com.ltech.smarthome.view.ColorPickerView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ColorPickerView.this.lambda$setBitmapBack$1(bitmap);
            }
        }, (this.bgWidth == 0 || this.bgHeight == 0) ? 100L : 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBitmapBack$1(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.getWidth() == this.bgWidth && bitmap.getHeight() == this.bgHeight) {
                this.mBitmapBack = bitmap;
            } else {
                this.mBitmapBack = Bitmap.createScaledBitmap(bitmap, this.bgWidth, this.bgHeight, true);
            }
            postInvalidate();
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0010, code lost:
    
        if (r0 != 3) goto L20;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            r4.attemptClaimDrag()
            int r0 = r5.getAction()
            r1 = 1
            if (r0 == 0) goto L40
            if (r0 == r1) goto L13
            r2 = 2
            if (r0 == r2) goto L40
            r2 = 3
            if (r0 == r2) goto L13
            goto L6c
        L13:
            float r0 = r5.getX()
            int r0 = (int) r0
            float r5 = r5.getY()
            int r5 = (int) r5
            r4.checkPickPoint(r0, r5)
            com.ltech.smarthome.view.ColorPickerView$OnColorChangedListener r5 = r4.mOnColorChangedListener
            if (r5 == 0) goto L6c
            android.graphics.Bitmap r0 = r4.mBitmapBack
            if (r0 == 0) goto L6c
            android.graphics.Point r2 = r4.mPickPoint
            int r2 = r2.x
            android.graphics.Point r3 = r4.mPickPoint
            int r3 = r3.y
            int r0 = r0.getPixel(r2, r3)
            float r2 = r4.getCurXPercent()
            float r3 = r4.getCurYPercent()
            r5.onChangedFinish(r0, r2, r3)
            goto L6c
        L40:
            float r0 = r5.getX()
            int r0 = (int) r0
            float r5 = r5.getY()
            int r5 = (int) r5
            r4.checkPickPoint(r0, r5)
            com.ltech.smarthome.view.ColorPickerView$OnColorChangedListener r5 = r4.mOnColorChangedListener
            if (r5 == 0) goto L6c
            android.graphics.Bitmap r0 = r4.mBitmapBack
            if (r0 == 0) goto L6c
            android.graphics.Point r2 = r4.mPickPoint
            int r2 = r2.x
            android.graphics.Point r3 = r4.mPickPoint
            int r3 = r3.y
            int r0 = r0.getPixel(r2, r3)
            float r2 = r4.getCurXPercent()
            float r3 = r4.getCurYPercent()
            r5.onColorChanged(r0, r2, r3)
        L6c:
            r4.postInvalidate()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.ColorPickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public float getCurXPercent() {
        float f = this.minXPercent;
        int i = this.mPickPoint.x;
        int i2 = this.mPickerRadius;
        return f + (((i - i2) * (100.0f - this.minXPercent)) / (this.bgWidth - (i2 * 2)));
    }

    public float getCurYPercent() {
        float f = this.minYPercent;
        int i = this.mPickPoint.y;
        int i2 = this.mPickerRadius;
        return f + (((i - i2) * (100.0f - this.minYPercent)) / (this.bgHeight - (i2 * 2)));
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void checkPickPoint(int x, int y) {
        int i = this.mPickerRadius;
        if (x <= i) {
            x = i;
        } else {
            int i2 = this.bgWidth;
            if (x >= i2 - i) {
                x = i2 - i;
            }
        }
        if (y <= i) {
            y = i;
        } else {
            int i3 = this.bgHeight;
            if (y >= i3 - i) {
                y = i3 - i;
            }
        }
        this.mPickPoint.set(x, y);
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