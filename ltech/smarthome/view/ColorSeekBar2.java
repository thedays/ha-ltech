package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ColorSeekBar2 extends View {
    private int barHeight;
    private Paint barPaint;
    private int barWidth;
    private boolean enable;
    private boolean isFromUser;
    private boolean mIsDragging;
    private onColorChangedListener mOnColorChangedListener;
    private Bitmap mSeekBarBg;
    private float mTouchDownX;
    private float mTouchSlop;
    private int marginHeigth;
    private int marginWidth;
    private int seekbarHeight;
    private Paint thumbPaint;
    private Paint thumbStrokePaint;
    private int thumbWidth;
    private int xWidth;

    public interface onColorChangedListener {
        void onColorChanged(int xPercent, int color, boolean isFromUser);

        void onColorChangedFinish(int xPercent, int color, boolean isFromUser);

        void onColorChangedStart();
    }

    private boolean isInScrollingContainer() {
        return false;
    }

    public ColorSeekBar2(Context context) {
        this(context, null);
    }

    public ColorSeekBar2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorSeekBar2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.marginWidth = 1;
        this.marginHeigth = 1;
        this.enable = true;
        setLayerType(1, null);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ColorSeekBar2);
        if (obtainStyledAttributes.hasValue(0)) {
            this.mSeekBarBg = ((BitmapDrawable) obtainStyledAttributes.getDrawable(0)).getBitmap();
        }
        if (this.mSeekBarBg == null) {
            this.mSeekBarBg = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_ct_bar);
        }
        obtainStyledAttributes.recycle();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Paint paint = new Paint(1);
        this.barPaint = paint;
        paint.setDither(true);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(100, widthMeasureSpec), getDefaultSize(100, heightMeasureSpec));
    }

    public static int getDefaultSize(int size, int measureSpec) {
        return View.MeasureSpec.getMode(measureSpec) != 1073741824 ? size : View.MeasureSpec.getSize(measureSpec);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.barHeight = (getMeasuredHeight() - getPaddingBottom()) - getPaddingTop();
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        this.barWidth = measuredWidth;
        int i = this.barHeight;
        this.seekbarHeight = i;
        this.thumbWidth = measuredWidth / 12;
        this.mSeekBarBg = Bitmap.createScaledBitmap(this.mSeekBarBg, measuredWidth, i, false);
        this.xWidth = this.thumbWidth / 4;
        Paint paint = new Paint();
        this.thumbStrokePaint = paint;
        paint.setAntiAlias(true);
        this.thumbStrokePaint.setDither(true);
        this.thumbStrokePaint.setStyle(Paint.Style.STROKE);
        this.thumbStrokePaint.setColor(-7829368);
        this.thumbStrokePaint.setStrokeWidth(this.thumbWidth / 2);
        this.thumbStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        this.thumbStrokePaint.setMaskFilter(new BlurMaskFilter(25.0f, BlurMaskFilter.Blur.OUTER));
        Paint paint2 = new Paint();
        this.thumbPaint = paint2;
        paint2.setAntiAlias(true);
        this.thumbPaint.setDither(true);
        this.thumbPaint.setStyle(Paint.Style.FILL);
        this.thumbPaint.setColor(-1);
        this.thumbPaint.setStrokeWidth(this.thumbWidth / 2);
        this.thumbPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.mSeekBarBg, getPaddingLeft(), getPaddingTop(), this.barPaint);
        canvas.drawLine(getPaddingLeft() + this.xWidth, (this.thumbWidth / 4) - this.marginHeigth, getPaddingLeft() + this.xWidth, (this.barHeight - (this.thumbWidth / 4)) - this.marginHeigth, this.thumbStrokePaint);
        canvas.drawLine(getPaddingLeft() + this.xWidth, (this.thumbWidth / 4) - this.marginHeigth, getPaddingLeft() + this.xWidth, (this.barHeight - (this.thumbWidth / 4)) - this.marginHeigth, this.thumbPaint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        this.isFromUser = true;
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                if (!this.mIsDragging) {
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                }
                onStopTrackingTouch(event);
                postInvalidate();
                this.isFromUser = false;
            } else if (actionMasked != 2) {
                if (actionMasked == 3) {
                    onStopTrackingTouch(event);
                    this.isFromUser = false;
                }
            } else if (this.mIsDragging) {
                trackTouchEvent(event);
                postInvalidate();
            } else if (Math.abs(event.getY() - this.mTouchDownX) > this.mTouchSlop) {
                onStartTrackingTouch();
                trackTouchEvent(event);
                attemptClaimDrag();
                postInvalidate();
            }
        } else if (isInScrollingContainer()) {
            this.mTouchDownX = (int) event.getX();
        } else {
            onStartTrackingTouch();
            trackTouchEvent(event);
            attemptClaimDrag();
            postInvalidate();
        }
        return true;
    }

    public void setProgress(final int progress) {
        postDelayed(new Runnable() { // from class: com.ltech.smarthome.view.ColorSeekBar2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ColorSeekBar2.this.lambda$setProgress$0(progress);
            }
        }, 50L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgress$0(int i) {
        int i2 = this.barWidth;
        int i3 = this.thumbWidth;
        int i4 = (int) (((i * ((i2 - ((i3 * 2) / 4)) - (r2 * 2))) / 100.0f) + (i3 / 4) + this.marginWidth);
        this.xWidth = i4;
        checkPoint(i4);
        postInvalidate();
    }

    public int getProgress() {
        int i = this.xWidth - (this.thumbWidth / 4);
        int i2 = this.marginWidth;
        return (int) ((((i - i2) * 1.0f) / ((this.barWidth - ((r1 * 2) / 4)) - (i2 * 2))) * 100.0f);
    }

    private void onStartTrackingTouch() {
        this.mIsDragging = true;
        onColorChangedListener oncolorchangedlistener = this.mOnColorChangedListener;
        if (oncolorchangedlistener != null) {
            oncolorchangedlistener.onColorChangedStart();
        }
        setPressed(true);
    }

    private void trackTouchEvent(MotionEvent event) {
        checkPoint((int) event.getX());
        onColorChangedListener oncolorchangedlistener = this.mOnColorChangedListener;
        if (oncolorchangedlistener != null) {
            int i = this.xWidth;
            int i2 = this.thumbWidth;
            int i3 = this.marginWidth;
            oncolorchangedlistener.onColorChanged((int) (((((i - (i2 / 4)) - i3) * 1.0f) / ((r5 - ((i2 * 2) / 4)) - (i3 * 2))) * 100.0f), this.mSeekBarBg.getPixel((((int) (((((i - (i2 / 4)) - i3) * 1.0f) / ((r5 - ((i2 * 2) / 4)) - (i3 * 2))) * 100.0f)) / 100) * (this.barWidth - i3), this.seekbarHeight / 2), this.isFromUser);
        }
    }

    private void onStopTrackingTouch(MotionEvent event) {
        this.mIsDragging = false;
        checkPoint((int) event.getX());
        onColorChangedListener oncolorchangedlistener = this.mOnColorChangedListener;
        if (oncolorchangedlistener != null) {
            int i = this.xWidth;
            int i2 = this.thumbWidth;
            int i3 = this.marginWidth;
            oncolorchangedlistener.onColorChangedFinish((int) (((((i - (i2 / 4)) - i3) * 1.0f) / ((r6 - ((i2 * 2) / 4)) - (i3 * 2))) * 100.0f), this.mSeekBarBg.getPixel((((int) (((((i - (i2 / 4)) - i3) * 1.0f) / ((r6 - ((i2 * 2) / 4)) - (i3 * 2))) * 100.0f)) / 100) * (this.barWidth - i3), this.seekbarHeight / 2), this.isFromUser);
        }
        setPressed(false);
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean enable) {
        this.enable = enable;
        postInvalidate();
    }

    public void changeColorBar(final Bitmap bitmap) {
        post(new Runnable() { // from class: com.ltech.smarthome.view.ColorSeekBar2$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ColorSeekBar2.this.lambda$changeColorBar$1(bitmap);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColorBar$1(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.getWidth() == this.barWidth && bitmap.getHeight() == this.seekbarHeight) {
                this.mSeekBarBg = bitmap;
            } else {
                this.mSeekBarBg = Bitmap.createScaledBitmap(bitmap, this.barWidth, this.seekbarHeight, true);
            }
            postInvalidate();
        }
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.enable;
    }

    private void checkPoint(int x) {
        int i = this.thumbWidth;
        if (x <= i / 4) {
            this.xWidth = (i / 4) + this.marginWidth;
            return;
        }
        int i2 = this.barWidth;
        if (x >= i2 - (i / 4)) {
            this.xWidth = (i2 - (i / 4)) - this.marginWidth;
        } else {
            this.xWidth = x;
        }
    }

    public void setOnColorChangedListener(onColorChangedListener onColorChangedListener2) {
        this.mOnColorChangedListener = onColorChangedListener2;
    }
}