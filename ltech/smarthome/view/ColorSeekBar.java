package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ColorSeekBar extends View {
    private Paint barBgPaint;
    private int barHeight;
    private Paint barPaint;
    private int barWidth;
    private boolean enable;
    private boolean isFromUser;
    private boolean isShowProgress;
    private IDisplayStrategy mIDisplayStrategy;
    private boolean mIsDragging;
    private OnColorChangedListener mOnColorChangedListener;
    private Bitmap mSeekBarBg;
    private Bitmap mSeekBarThumb;
    private float mTouchDownX;
    private float mTouchSlop;
    private int marginWidthLeft;
    private int marginWidthRight;
    private String progressText;
    private int seekbarHeight;
    private int textColor;
    private float textHeight;
    private Paint textPaint;
    private int textSize;
    private float textWidth;
    private int thumbHeight;
    private int thumbWidth;
    private int xWidth;

    public interface IDisplayStrategy {
        String getProgressText();
    }

    public interface OnColorChangedListener {
        void onColorChanged(float xProgress, int color, boolean isFromUser);

        void onColorChangedFinish(float xProgress, int color, boolean isFromUser);

        void onColorChangedStart();
    }

    private boolean isInScrollingContainer() {
        return false;
    }

    public ColorSeekBar(Context context) {
        this(context, null);
    }

    public ColorSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.enable = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ColorSeekBar);
        if (obtainStyledAttributes.hasValue(0)) {
            this.mSeekBarBg = ((BitmapDrawable) obtainStyledAttributes.getDrawable(0)).getBitmap();
        }
        if (this.mSeekBarBg == null) {
            this.mSeekBarBg = BitmapFactory.decodeResource(getResources(), R.mipmap.pic_ct);
            this.mSeekBarBg = BitmapFactory.decodeResource(getResources(), R.mipmap.pic_ct1);
        }
        if (obtainStyledAttributes.hasValue(7)) {
            this.mSeekBarThumb = ((BitmapDrawable) obtainStyledAttributes.getDrawable(7)).getBitmap();
        }
        if (this.mSeekBarThumb == null) {
            this.mSeekBarThumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_thumb_default);
        }
        this.seekbarHeight = (int) obtainStyledAttributes.getDimension(1, (int) TypedValue.applyDimension(1, 3.0f, context.getResources().getDisplayMetrics()));
        this.thumbWidth = (int) obtainStyledAttributes.getDimension(9, (int) TypedValue.applyDimension(1, 20.0f, context.getResources().getDisplayMetrics()));
        this.thumbHeight = (int) obtainStyledAttributes.getDimension(8, (int) TypedValue.applyDimension(1, 20.0f, context.getResources().getDisplayMetrics()));
        this.marginWidthRight = (int) obtainStyledAttributes.getDimension(3, (int) TypedValue.applyDimension(1, 1.0f, context.getResources().getDisplayMetrics()));
        this.marginWidthLeft = (int) obtainStyledAttributes.getDimension(2, (int) TypedValue.applyDimension(1, 0.0f, context.getResources().getDisplayMetrics()));
        this.isShowProgress = obtainStyledAttributes.getBoolean(4, false);
        this.textSize = (int) obtainStyledAttributes.getDimension(6, (int) TypedValue.applyDimension(1, 14.0f, context.getResources().getDisplayMetrics()));
        this.textColor = obtainStyledAttributes.getColor(5, -7829368);
        obtainStyledAttributes.recycle();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Paint paint = new Paint(1);
        this.barPaint = paint;
        paint.setDither(true);
        Paint paint2 = new Paint(1);
        this.barBgPaint = paint2;
        paint2.setColor(Color.parseColor("#ededed"));
        this.barBgPaint.setDither(true);
        Paint paint3 = new Paint(1);
        this.textPaint = paint3;
        paint3.setColor(this.textColor);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        this.textHeight = fontMetrics.descent + Math.abs(fontMetrics.ascent);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(100, widthMeasureSpec), getDefaultSize(this.thumbWidth, heightMeasureSpec));
    }

    public static int getDefaultSize(int size, int measureSpec) {
        return View.MeasureSpec.getMode(measureSpec) != 1073741824 ? size : View.MeasureSpec.getSize(measureSpec);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.barHeight = getMeasuredHeight();
        int measuredWidth = ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - this.thumbWidth;
        this.barWidth = measuredWidth;
        this.mSeekBarBg = Bitmap.createScaledBitmap(this.mSeekBarBg, measuredWidth, this.seekbarHeight, false);
        this.mSeekBarThumb = Bitmap.createScaledBitmap(this.mSeekBarThumb, this.thumbWidth, this.thumbHeight, false);
        this.xWidth = this.marginWidthLeft;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        IDisplayStrategy iDisplayStrategy;
        super.onDraw(canvas);
        if (isEnabled()) {
            canvas.drawBitmap(this.mSeekBarBg, getPaddingLeft() + (this.thumbWidth / 2), (this.barHeight - this.seekbarHeight) / 2, this.barPaint);
            canvas2 = canvas;
        } else {
            canvas2 = canvas;
            canvas2.drawRect(getPaddingLeft() + (this.thumbWidth / 2), (this.barHeight - this.seekbarHeight) / 2, getPaddingLeft() + (this.thumbWidth / 2) + this.barWidth, (this.barHeight + this.seekbarHeight) / 2, this.barBgPaint);
        }
        canvas2.drawBitmap(this.mSeekBarThumb, getPaddingLeft() + this.xWidth, (this.barHeight - this.thumbHeight) / 2, this.barPaint);
        if (!this.isShowProgress || (iDisplayStrategy = this.mIDisplayStrategy) == null) {
            return;
        }
        String progressText = iDisplayStrategy.getProgressText();
        this.progressText = progressText;
        this.textWidth = this.textPaint.measureText(progressText, 0, progressText.length());
        canvas2.drawText(this.progressText, ((getPaddingLeft() + this.xWidth) + (this.thumbWidth / 2)) - (this.textWidth / 2.0f), ((this.barHeight + this.thumbHeight) / 2) + this.textHeight, this.textPaint);
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

    public void setProgress(final float progress) {
        postDelayed(new Runnable() { // from class: com.ltech.smarthome.view.ColorSeekBar$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ColorSeekBar.this.lambda$setProgress$0(progress);
            }
        }, 50L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgress$0(float f) {
        int i = this.barWidth - this.marginWidthRight;
        int i2 = (int) ((f * (i - r1)) + this.marginWidthLeft);
        this.xWidth = i2;
        checkPoint(i2);
        postInvalidate();
    }

    public float getProgress() {
        int i = this.xWidth;
        int i2 = this.marginWidthLeft;
        return ((i - i2) * 1.0f) / ((this.barWidth - this.marginWidthRight) - i2);
    }

    public int getSelectColor() {
        return this.mSeekBarBg.getPixel(this.xWidth, this.seekbarHeight / 2);
    }

    private void onStartTrackingTouch() {
        this.mIsDragging = true;
        OnColorChangedListener onColorChangedListener = this.mOnColorChangedListener;
        if (onColorChangedListener != null) {
            onColorChangedListener.onColorChangedStart();
        }
        setPressed(true);
    }

    private void trackTouchEvent(MotionEvent event) {
        checkPoint((int) event.getX());
        OnColorChangedListener onColorChangedListener = this.mOnColorChangedListener;
        if (onColorChangedListener != null) {
            onColorChangedListener.onColorChanged(getProgress(), getSelectColor(), this.isFromUser);
        }
    }

    private void onStopTrackingTouch(MotionEvent event) {
        this.mIsDragging = false;
        checkPoint((int) event.getX());
        OnColorChangedListener onColorChangedListener = this.mOnColorChangedListener;
        if (onColorChangedListener != null) {
            onColorChangedListener.onColorChangedFinish(getProgress(), getSelectColor(), this.isFromUser);
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
        post(new Runnable() { // from class: com.ltech.smarthome.view.ColorSeekBar$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ColorSeekBar.this.lambda$changeColorBar$1(bitmap);
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

    public void changeThumb(final Bitmap bitmap) {
        post(new Runnable() { // from class: com.ltech.smarthome.view.ColorSeekBar$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ColorSeekBar.this.lambda$changeThumb$2(bitmap);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeThumb$2(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.getWidth() == this.thumbWidth && bitmap.getHeight() == this.thumbHeight) {
                this.mSeekBarThumb = bitmap;
            } else {
                this.mSeekBarThumb = Bitmap.createScaledBitmap(bitmap, this.thumbWidth, this.thumbHeight, true);
            }
            postInvalidate();
        }
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.enable;
    }

    public boolean isShowProgress() {
        return this.isShowProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.isShowProgress = showProgress;
    }

    private void checkPoint(int x) {
        int i = this.marginWidthLeft;
        if (x <= i) {
            this.xWidth = i;
            return;
        }
        int i2 = this.barWidth;
        if (x >= i2) {
            this.xWidth = i2 - this.marginWidthRight;
        } else {
            this.xWidth = x;
        }
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.mOnColorChangedListener = onColorChangedListener;
    }

    public void setIDisplayStrategy(IDisplayStrategy IDisplayStrategy2) {
        this.mIDisplayStrategy = IDisplayStrategy2;
    }
}