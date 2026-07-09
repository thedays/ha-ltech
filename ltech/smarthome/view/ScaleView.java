package com.ltech.smarthome.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import androidx.core.view.InputDeviceCompat;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.ScaleView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class ScaleView extends View {
    public static final int END_ANIMATOR = 12;
    public static final int FINISH = 11;
    public static final int MOVE = 10;

    /* renamed from: a, reason: collision with root package name */
    private float f6279a;
    private int base;
    private int baseCircleRadius;
    private int baseHeight;
    private int baseLineColor;
    private int baseX;
    private int borderLeft;
    private int borderRight;
    private int circleColor;
    private boolean continueScroll;
    private int cur;
    private int defaultColor;
    private int enableColor;
    private int height;
    private boolean isEnable;
    private boolean isMeasured;
    private float lastX;
    private Paint mCirclePaint;
    private Context mContext;
    private Handler mHandler;
    private IValueChangeListener mIValueChangeListener;
    private Paint mPaint;
    private int max;
    private int min;
    private int minX;
    Rect rect;
    private int scaleHeight;
    private int scaleUnit;
    private int selectX;
    String str;
    private int textDisPlayMode;
    private int textSize;
    private String unit;
    private ValueAnimator valueAnimator;
    private float velocity;
    private VelocityTracker velocityTracker;
    private int width;

    public interface IValueChangeListener {
        void onValueChange(int cur, int base, boolean isFinish);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextDisplayMode {
        public static final int MODE_NORMAL = 1;
        public static final int MODE_WITH_BASE = 2;
    }

    public ScaleView(Context context) {
        this(context, null);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f6279a = 1000000.0f;
        this.isEnable = true;
        this.unit = "";
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.view.ScaleView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 10:
                        if (ScaleView.this.mIValueChangeListener != null) {
                            ScaleView.this.mIValueChangeListener.onValueChange(ScaleView.this.cur, ScaleView.this.base, false);
                            break;
                        }
                        break;
                    case 11:
                        if ((ScaleView.this.baseX - ScaleView.this.selectX) % ScaleView.this.scaleUnit != 0 || (ScaleView.this.selectX - ScaleView.this.baseX) / ScaleView.this.scaleUnit != ScaleView.this.cur) {
                            ScaleView.this.end();
                            break;
                        } else if (ScaleView.this.mIValueChangeListener != null) {
                            ScaleView.this.mIValueChangeListener.onValueChange(ScaleView.this.cur, ScaleView.this.base, true);
                            break;
                        }
                        break;
                    case 12:
                        if (ScaleView.this.valueAnimator != null && ScaleView.this.valueAnimator.isRunning()) {
                            ScaleView.this.valueAnimator.cancel();
                            break;
                        }
                        break;
                }
            }
        };
        this.rect = new Rect();
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ScaleView);
        this.baseLineColor = obtainStyledAttributes.getColor(1, InputDeviceCompat.SOURCE_ANY);
        this.defaultColor = obtainStyledAttributes.getColor(5, -7829368);
        this.enableColor = obtainStyledAttributes.getColor(6, -1);
        this.circleColor = obtainStyledAttributes.getColor(3, -1);
        this.scaleUnit = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        this.baseCircleRadius = obtainStyledAttributes.getDimensionPixelSize(0, 8);
        this.textSize = obtainStyledAttributes.getDimensionPixelSize(4, SizeUtils.sp2px(14.0f));
        this.min = obtainStyledAttributes.getInt(8, 0);
        this.max = obtainStyledAttributes.getInt(7, 100);
        this.base = obtainStyledAttributes.getInt(2, 0);
        this.textDisPlayMode = obtainStyledAttributes.getInt(10, 1);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mPaint.setColor(-16777216);
        this.mPaint.setStrokeWidth(this.baseCircleRadius >> 1);
        Paint paint2 = new Paint();
        this.mCirclePaint = paint2;
        paint2.setAntiAlias(true);
        this.mCirclePaint.setDither(true);
        this.mCirclePaint.setColor(this.circleColor);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.isMeasured) {
            return;
        }
        this.width = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        this.height = measuredHeight;
        if (this.scaleUnit == 0) {
            this.scaleUnit = this.width / 15;
        }
        int i = this.width;
        int i2 = this.base;
        int i3 = this.max;
        int i4 = this.min;
        int i5 = (int) (i * ((i2 * 1.0f) / (i3 - i4)));
        this.selectX = i5;
        if (i5 < (i >> 2)) {
            this.selectX = i >> 2;
        } else if (i5 > (i << 2)) {
            this.selectX = i << 2;
        }
        int i6 = this.selectX;
        int i7 = this.scaleUnit;
        int i8 = i6 - ((i2 - i4) * i7);
        this.borderLeft = i8;
        this.borderRight = ((i3 - i2) * i7) + i6;
        this.baseX = i6;
        this.minX = i8;
        int i9 = measuredHeight / 7;
        this.scaleHeight = i9;
        this.baseHeight = i9 * 3;
        this.isMeasured = true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isEnable) {
            this.mPaint.setColor(this.enableColor);
        } else {
            this.mPaint.setColor(this.defaultColor);
        }
        for (int i = this.min; i <= this.max; i++) {
            if (i == this.base) {
                float f = this.minX + ((i - this.min) * this.scaleUnit);
                int i2 = this.height - this.baseHeight;
                canvas.drawCircle(f, (i2 - (r3 * 4)) >> 1, this.baseCircleRadius, this.mCirclePaint);
            }
            int i3 = this.minX;
            int i4 = this.min;
            int i5 = this.scaleUnit;
            int i6 = this.height;
            int i7 = this.scaleHeight;
            canvas.drawLine(((i - i4) * i5) + i3, (i6 - i7) >> 1, i3 + ((i - i4) * i5), (i6 + i7) >> 1, this.mPaint);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.textDisPlayMode == 2 ? this.cur + this.base : this.cur);
        sb.append(this.unit);
        this.str = sb.toString();
        this.mPaint.setTextSize(this.textSize);
        Paint paint = this.mPaint;
        String str = this.str;
        paint.getTextBounds(str, 0, str.length(), this.rect);
        canvas.drawText(this.str, this.selectX - (this.rect.width() >> 1), (this.rect.height() * 1.5f) + ((this.height + this.baseHeight) >> 1), this.mPaint);
        if (this.isEnable) {
            this.mPaint.setColor(this.baseLineColor);
        }
        int i8 = this.selectX;
        int i9 = this.height;
        int i10 = this.baseHeight;
        canvas.drawLine(i8, (i9 - i10) >> 1, i8, (i9 + i10) >> 1, this.mPaint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        float x = event.getX();
        int action = event.getAction();
        if (action == 0) {
            this.mHandler.sendEmptyMessage(12);
            attemptClaimDrag();
            this.lastX = x;
            this.continueScroll = false;
            VelocityTracker velocityTracker = this.velocityTracker;
            if (velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            } else {
                velocityTracker.clear();
            }
        } else if (action == 1) {
            this.velocityTracker.computeCurrentVelocity(1000);
            this.velocity = this.velocityTracker.getXVelocity();
            if (Math.abs(this.velocity) > ViewConfiguration.get(this.mContext).getScaledMinimumFlingVelocity()) {
                this.continueScroll = true;
                continueScroll();
            } else {
                checkFinish(true);
                this.velocityTracker.recycle();
                this.velocityTracker = null;
            }
        } else if (action == 2) {
            attemptClaimDrag();
            this.velocityTracker.addMovement(event);
            int i = (int) (this.lastX - x);
            this.minX -= i;
            this.baseX -= i;
            calculateCurrentScale();
            this.mHandler.sendEmptyMessage(10);
            invalidate();
            this.lastX = x;
        } else if (action == 3) {
            checkFinish(true);
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }
        return true;
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.isEnable;
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        this.isEnable = enabled;
        postInvalidate();
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void continueScroll() {
        checkFinish(false);
        new Thread(new Runnable() { // from class: com.ltech.smarthome.view.ScaleView.2
            @Override // java.lang.Runnable
            public void run() {
                float f = (ScaleView.this.velocity * ScaleView.this.velocity) / ScaleView.this.f6279a;
                if (ScaleView.this.velocity > 0.0f && ScaleView.this.continueScroll) {
                    ScaleView.this.velocity -= ScaleView.this.scaleUnit;
                    ScaleView.this.minX = (int) (r1.minX + f);
                    ScaleView.this.baseX = (int) (r1.baseX + f);
                } else if (ScaleView.this.velocity < 0.0f && ScaleView.this.continueScroll) {
                    ScaleView.this.velocity += ScaleView.this.scaleUnit;
                    ScaleView.this.minX = (int) (r1.minX - f);
                    ScaleView.this.baseX = (int) (r1.baseX - f);
                }
                ScaleView.this.confirmBorder();
                ScaleView.this.calculateCurrentScale();
                ScaleView.this.postInvalidate();
                if (ScaleView.this.continueScroll && f > ScaleView.this.scaleUnit / 10.0f) {
                    ScaleView.this.post(this);
                    ScaleView.this.checkFinish(false);
                } else {
                    ScaleView.this.continueScroll = false;
                    ScaleView.this.checkFinish(true);
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkFinish(boolean send) {
        if (send) {
            this.mHandler.sendEmptyMessage(11);
        } else {
            this.mHandler.removeMessages(11);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void end() {
        WrapPoint wrapPoint = new WrapPoint(this.baseX, this.minX);
        int round = this.selectX + (Math.round((((this.baseX - r1) * 100.0f) / this.scaleUnit) / 100.0f) * this.scaleUnit);
        ValueAnimator ofObject = ValueAnimator.ofObject(new TypeEvaluator() { // from class: com.ltech.smarthome.view.ScaleView$$ExternalSyntheticLambda1
            @Override // android.animation.TypeEvaluator
            public final Object evaluate(float f, Object obj, Object obj2) {
                return ScaleView.lambda$end$0(f, (ScaleView.WrapPoint) obj, (ScaleView.WrapPoint) obj2);
            }
        }, wrapPoint, new WrapPoint(round, (round - this.selectX) + this.borderLeft));
        this.valueAnimator = ofObject;
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.ScaleView$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScaleView.this.lambda$end$1(valueAnimator);
            }
        });
        this.valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.ltech.smarthome.view.ScaleView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ScaleView.this.valueAnimator = null;
                ScaleView.this.checkFinish(true);
            }
        });
        this.valueAnimator.setInterpolator(new AccelerateInterpolator());
        this.valueAnimator.setDuration(80L);
        this.valueAnimator.start();
    }

    static /* synthetic */ WrapPoint lambda$end$0(float f, WrapPoint wrapPoint, WrapPoint wrapPoint2) {
        return new WrapPoint((int) (((wrapPoint2.getX() - wrapPoint.getX()) * f) + wrapPoint.getX()), (int) ((f * (wrapPoint2.getY() - wrapPoint.getY())) + wrapPoint.getY()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$end$1(ValueAnimator valueAnimator) {
        this.baseX = ((WrapPoint) valueAnimator.getAnimatedValue()).getX();
        this.minX = ((WrapPoint) valueAnimator.getAnimatedValue()).getY();
        confirmBorder();
        calculateCurrentScale();
        postInvalidate();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHandler.sendEmptyMessage(12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class WrapPoint {
        private int x;
        private int y;

        WrapPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calculateCurrentScale() {
        int round = Math.round(((this.selectX - this.baseX) * 1.0f) / this.scaleUnit);
        this.cur = round;
        int i = this.base;
        int i2 = i + round;
        int i3 = this.max;
        if (i2 >= i3) {
            this.cur = i3 - i;
            return;
        }
        int i4 = round + i;
        int i5 = this.min;
        if (i4 <= i5) {
            this.cur = i5 - i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmBorder() {
        int i = this.baseX;
        int i2 = this.selectX;
        int i3 = this.borderLeft;
        if (i > (i2 + i2) - i3) {
            this.minX = i2;
            this.baseX = (i2 + i2) - i3;
        } else {
            int i4 = this.borderRight;
            if (i < (i2 + i2) - i4) {
                this.minX = (i2 - i4) + i3;
                this.baseX = (i2 + i2) - i4;
            }
        }
        postInvalidate();
    }

    public void setCurPosition(final int position) {
        int i = this.min;
        int i2 = this.base;
        if ((position >= i - i2 || position <= this.max - i2) && this.cur != position - i2) {
            post(new Runnable() { // from class: com.ltech.smarthome.view.ScaleView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScaleView.this.lambda$setCurPosition$2(position);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCurPosition$2(int i) {
        int i2 = this.base;
        int i3 = i - i2;
        this.cur = i3;
        int i4 = i2 + i3;
        int i5 = this.max;
        if (i4 >= i5) {
            this.cur = i5 - i2;
        } else {
            int i6 = i3 + i2;
            int i7 = this.min;
            if (i6 <= i7) {
                this.cur = i7 - i2;
            }
        }
        int i8 = this.selectX;
        int i9 = i8 - (this.cur * this.scaleUnit);
        this.baseX = i9;
        this.minX = (i9 - i8) + this.borderLeft;
        postInvalidate();
        confirmBorder();
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void resetBase(boolean callBack) {
        setCurPosition(this.base);
        if (callBack) {
            checkFinish(true);
        }
    }

    public void setIValueChangeListener(IValueChangeListener ivaluechangelistener) {
        this.mIValueChangeListener = ivaluechangelistener;
    }
}