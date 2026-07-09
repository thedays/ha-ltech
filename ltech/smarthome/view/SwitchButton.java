package com.ltech.smarthome.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class SwitchButton extends View implements Checkable {
    private final int ANIMATE_STATE_DRAGING;
    private final int ANIMATE_STATE_NONE;
    private final int ANIMATE_STATE_PENDING_DRAG;
    private final int ANIMATE_STATE_PENDING_RESET;
    private final int ANIMATE_STATE_PENDING_SETTLE;
    private final int ANIMATE_STATE_SWITCH;
    private ViewState afterState;
    private int animateState;
    private Animator.AnimatorListener animatorListener;
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private final ArgbEvaluator argbEvaluator;
    private int background;
    private ViewState beforeState;
    private int borderWidth;
    private float bottom;
    private float buttonMaxX;
    private float buttonMinX;
    private Paint buttonPaint;
    private float buttonRadius;
    private float centerX;
    private float centerY;
    private int checkLineColor;
    private float checkLineLength;
    private int checkLineWidth;
    private int checkedColor;
    private float checkedLineOffsetX;
    private float checkedLineOffsetY;
    private boolean enable;
    private boolean enableEffect;
    private float height;
    private boolean isChecked;
    private boolean isEventBroadcast;
    private boolean isTouchingDown;
    private boolean isUiInited;
    private float left;
    private OnCheckedChangeListener onCheckedChangeListener;
    private Paint paint;
    private Runnable postPendingDrag;
    private RectF rect;
    private float right;
    private int shadowColor;
    private boolean shadowEffect;
    private int shadowOffset;
    private int shadowRadius;
    private boolean showIndicator;
    private float top;
    private long touchDownTime;
    private int uncheckCircleColor;
    private float uncheckCircleOffsetX;
    private float uncheckCircleRadius;
    private int uncheckCircleWidth;
    private int uncheckColor;
    private ValueAnimator valueAnimator;
    private float viewRadius;
    private ViewState viewState;
    private float width;
    private static final int DEFAULT_WIDTH = dp2pxInt(58.0f);
    private static final int DEFAULT_HEIGHT = dp2pxInt(36.0f);

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SwitchButton view, boolean isChecked);
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener l) {
    }

    @Override // android.view.View
    public final void setOnLongClickListener(View.OnLongClickListener l) {
    }

    public SwitchButton(Context context) {
        super(context);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.ltech.smarthome.view.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float floatValue = ((Float) animation.getAnimatedValue()).floatValue();
                int i = SwitchButton.this.animateState;
                if (i == 1 || i == 3 || i == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * floatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.ltech.smarthome.view.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                int i = SwitchButton.this.animateState;
                if (i == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.ltech.smarthome.view.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float floatValue = ((Float) animation.getAnimatedValue()).floatValue();
                int i = SwitchButton.this.animateState;
                if (i == 1 || i == 3 || i == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * floatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.ltech.smarthome.view.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                int i = SwitchButton.this.animateState;
                if (i == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.ltech.smarthome.view.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float floatValue = ((Float) animation.getAnimatedValue()).floatValue();
                int i = SwitchButton.this.animateState;
                if (i == 1 || i == 3 || i == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * floatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.ltech.smarthome.view.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                int i = SwitchButton.this.animateState;
                if (i == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.ltech.smarthome.view.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float floatValue = ((Float) animation.getAnimatedValue()).floatValue();
                int i = SwitchButton.this.animateState;
                if (i == 1 || i == 3 || i == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * floatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(floatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * floatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.ltech.smarthome.view.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                int i = SwitchButton.this.animateState;
                if (i == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, attrs);
    }

    @Override // android.view.View
    public final void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(0, 0, 0, 0);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray obtainStyledAttributes = attrs != null ? context.obtainStyledAttributes(attrs, R.styleable.SwitchButton) : null;
        this.shadowEffect = optBoolean(obtainStyledAttributes, 11, true);
        this.uncheckCircleColor = optColor(obtainStyledAttributes, 16, -5592406);
        this.uncheckCircleWidth = optPixelSize(obtainStyledAttributes, 18, dp2pxInt(1.5f));
        this.uncheckCircleOffsetX = dp2px(10.0f);
        this.uncheckCircleRadius = optPixelSize(obtainStyledAttributes, 17, dp2px(4.0f));
        this.checkedLineOffsetX = dp2px(4.0f);
        this.checkedLineOffsetY = dp2px(4.0f);
        this.shadowRadius = optPixelSize(obtainStyledAttributes, 13, dp2pxInt(2.5f));
        this.shadowOffset = optPixelSize(obtainStyledAttributes, 12, dp2pxInt(1.5f));
        this.shadowColor = optColor(obtainStyledAttributes, 10, 855638016);
        this.uncheckColor = optColor(obtainStyledAttributes, 15, -2236963);
        this.checkedColor = optColor(obtainStyledAttributes, 4, -11414681);
        this.borderWidth = optPixelSize(obtainStyledAttributes, 1, dp2pxInt(1.0f));
        this.checkLineColor = optColor(obtainStyledAttributes, 5, -1);
        this.checkLineWidth = optPixelSize(obtainStyledAttributes, 6, dp2pxInt(1.0f));
        this.checkLineLength = dp2px(6.0f);
        int optColor = optColor(obtainStyledAttributes, 2, -1);
        int optInt = optInt(obtainStyledAttributes, 7, 300);
        this.isChecked = optBoolean(obtainStyledAttributes, 3, false);
        this.showIndicator = optBoolean(obtainStyledAttributes, 14, true);
        this.enable = optBoolean(obtainStyledAttributes, 8, true);
        this.background = optColor(obtainStyledAttributes, 0, -1);
        this.enableEffect = optBoolean(obtainStyledAttributes, 9, true);
        if (obtainStyledAttributes != null) {
            obtainStyledAttributes.recycle();
        }
        this.paint = new Paint(1);
        Paint paint = new Paint(1);
        this.buttonPaint = paint;
        paint.setColor(optColor);
        if (this.shadowEffect) {
            this.buttonPaint.setShadowLayer(this.shadowRadius, 0.0f, this.shadowOffset, this.shadowColor);
        }
        this.viewState = new ViewState();
        this.beforeState = new ViewState();
        this.afterState = new ViewState();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.valueAnimator = ofFloat;
        ofFloat.setDuration(optInt);
        this.valueAnimator.setRepeatCount(0);
        this.valueAnimator.addUpdateListener(this.animatorUpdateListener);
        this.valueAnimator.addListener(this.animatorListener);
        super.setClickable(true);
        setPadding(0, 0, 0, 0);
        setLayerType(1, null);
        setButtonEnable(this.enable);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        if (mode == 0 || mode == Integer.MIN_VALUE) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(DEFAULT_WIDTH, 1073741824);
        }
        if (mode2 == 0 || mode2 == Integer.MIN_VALUE) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(DEFAULT_HEIGHT, 1073741824);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float max = Math.max(this.shadowRadius + this.shadowOffset, this.borderWidth);
        float f = h - max;
        float f2 = f - max;
        this.height = f2;
        float f3 = w - max;
        this.width = f3 - max;
        float f4 = f2 * 0.5f;
        this.viewRadius = f4;
        this.buttonRadius = f4 - this.borderWidth;
        this.left = max;
        this.top = max;
        this.right = f3;
        this.bottom = f;
        this.centerX = (max + f3) * 0.5f;
        this.centerY = (f + max) * 0.5f;
        this.buttonMinX = max + f4;
        this.buttonMaxX = f3 - f4;
        if (isChecked()) {
            setCheckedViewState(this.viewState);
        } else {
            setUncheckViewState(this.viewState);
        }
        this.isUiInited = true;
        postInvalidate();
    }

    private void setUncheckViewState(ViewState viewState) {
        viewState.radius = 0.0f;
        viewState.checkStateColor = this.uncheckColor;
        viewState.checkedLineColor = 0;
        viewState.buttonX = this.buttonMinX;
    }

    private void setCheckedViewState(ViewState viewState) {
        viewState.radius = this.viewRadius;
        viewState.checkStateColor = this.checkedColor;
        viewState.checkedLineColor = this.checkLineColor;
        viewState.buttonX = this.buttonMaxX;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setStrokeWidth(this.borderWidth);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.background);
        drawRoundRect(canvas, this.left, this.top, this.right, this.bottom, this.viewRadius, this.paint);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.uncheckColor);
        drawRoundRect(canvas, this.left, this.top, this.right, this.bottom, this.viewRadius, this.paint);
        if (this.showIndicator) {
            drawUncheckIndicator(canvas);
        }
        float f = this.viewState.radius * 0.5f;
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.viewState.checkStateColor);
        this.paint.setStrokeWidth(this.borderWidth + (f * 2.0f));
        drawRoundRect(canvas, this.left + f, this.top + f, this.right - f, this.bottom - f, this.viewRadius, this.paint);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setStrokeWidth(1.0f);
        float f2 = this.left;
        float f3 = this.top;
        float f4 = this.viewRadius;
        drawArc(canvas, f2, f3, (f4 * 2.0f) + f2, f3 + (f4 * 2.0f), 90.0f, 180.0f, this.paint);
        canvas.drawRect(this.viewRadius + this.left, this.top, this.viewState.buttonX, (this.viewRadius * 2.0f) + this.top, this.paint);
        if (this.showIndicator) {
            drawCheckedIndicator(canvas);
        }
        drawButton(canvas, this.viewState.buttonX, this.centerY);
    }

    protected void drawCheckedIndicator(Canvas canvas) {
        int i = this.viewState.checkedLineColor;
        float f = this.checkLineWidth;
        float f2 = this.left;
        float f3 = this.viewRadius;
        float f4 = (f2 + f3) - this.checkedLineOffsetX;
        float f5 = this.centerY;
        float f6 = this.checkLineLength;
        drawCheckedIndicator(canvas, i, f, f4, f5 - f6, (f2 + f3) - this.checkedLineOffsetY, f5 + f6, this.paint);
    }

    protected void drawCheckedIndicator(Canvas canvas, int color, float lineWidth, float sx, float sy, float ex, float ey, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(lineWidth);
        canvas.drawLine(sx, sy, ex, ey, paint);
    }

    private void drawUncheckIndicator(Canvas canvas) {
        drawUncheckIndicator(canvas, this.uncheckCircleColor, this.uncheckCircleWidth, this.right - this.uncheckCircleOffsetX, this.centerY, this.uncheckCircleRadius, this.paint);
    }

    protected void drawUncheckIndicator(Canvas canvas, int color, float lineWidth, float centerX, float centerY, float radius, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(lineWidth);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    private void drawArc(Canvas canvas, float left, float top, float right, float bottom, float startAngle, float sweepAngle, Paint paint) {
        canvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, true, paint);
    }

    private void drawRoundRect(Canvas canvas, float left, float top, float right, float bottom, float backgroundRadius, Paint paint) {
        canvas.drawRoundRect(left, top, right, bottom, backgroundRadius, backgroundRadius, paint);
    }

    private void drawButton(Canvas canvas, float x, float y) {
        canvas.drawCircle(x, y, this.buttonRadius, this.buttonPaint);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(1.0f);
        this.paint.setColor(-2236963);
        canvas.drawCircle(x, y, this.buttonRadius, this.paint);
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean checked) {
        if (checked == isChecked()) {
            postInvalidate();
        } else {
            toggle(this.enableEffect, false);
        }
    }

    public void setCheckedNotByUser(boolean checked) {
        this.isChecked = checked;
        if (isChecked()) {
            setCheckedViewState(this.viewState);
        } else {
            setUncheckViewState(this.viewState);
        }
        postInvalidate();
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override // android.widget.Checkable
    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean animate) {
        toggle(animate, true);
    }

    private void toggle(boolean animate, boolean broadcast) {
        if (isEnabled()) {
            if (this.isEventBroadcast) {
                throw new RuntimeException("should NOT switch the state in method: [onCheckedChanged]!");
            }
            if (!this.isUiInited) {
                this.isChecked = !this.isChecked;
                if (broadcast) {
                    broadcastEvent();
                    return;
                }
                return;
            }
            if (this.valueAnimator.isRunning()) {
                this.valueAnimator.cancel();
            }
            if (!this.enableEffect || !animate) {
                this.isChecked = !this.isChecked;
                if (isChecked()) {
                    setCheckedViewState(this.viewState);
                } else {
                    setUncheckViewState(this.viewState);
                }
                postInvalidate();
                if (broadcast) {
                    broadcastEvent();
                    return;
                }
                return;
            }
            this.animateState = 5;
            this.beforeState.copy(this.viewState);
            if (isChecked()) {
                setUncheckViewState(this.afterState);
            } else {
                setCheckedViewState(this.afterState);
            }
            this.valueAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastEvent() {
        OnCheckedChangeListener onCheckedChangeListener = this.onCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            this.isEventBroadcast = true;
            onCheckedChangeListener.onCheckedChanged(this, isChecked());
        }
        this.isEventBroadcast = false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            this.isTouchingDown = true;
            this.touchDownTime = System.currentTimeMillis();
            removeCallbacks(this.postPendingDrag);
            postDelayed(this.postPendingDrag, 100L);
        } else if (actionMasked == 1) {
            this.isTouchingDown = false;
            removeCallbacks(this.postPendingDrag);
            if (System.currentTimeMillis() - this.touchDownTime <= 300) {
                toggle();
            } else if (isDragState()) {
                boolean z = Math.max(0.0f, Math.min(1.0f, event.getX() / ((float) getWidth()))) > 0.5f;
                if (z == isChecked()) {
                    pendingCancelDragState();
                } else {
                    this.isChecked = z;
                    pendingSettleState();
                }
            } else if (isPendingDragState()) {
                pendingCancelDragState();
            }
        } else if (actionMasked == 2) {
            float x = event.getX();
            if (isPendingDragState()) {
                float max = Math.max(0.0f, Math.min(1.0f, x / getWidth()));
                ViewState viewState = this.viewState;
                float f = this.buttonMinX;
                viewState.buttonX = f + ((this.buttonMaxX - f) * max);
            } else if (isDragState()) {
                float max2 = Math.max(0.0f, Math.min(1.0f, x / getWidth()));
                ViewState viewState2 = this.viewState;
                float f2 = this.buttonMinX;
                viewState2.buttonX = f2 + ((this.buttonMaxX - f2) * max2);
                this.viewState.checkStateColor = ((Integer) this.argbEvaluator.evaluate(max2, Integer.valueOf(this.uncheckColor), Integer.valueOf(this.checkedColor))).intValue();
                postInvalidate();
            }
        } else if (actionMasked == 3) {
            this.isTouchingDown = false;
            removeCallbacks(this.postPendingDrag);
            if (isPendingDragState() || isDragState()) {
                pendingCancelDragState();
            }
        }
        return true;
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInAnimating() {
        return this.animateState != 0;
    }

    private boolean isPendingDragState() {
        int i = this.animateState;
        return i == 1 || i == 3;
    }

    private boolean isDragState() {
        return this.animateState == 2;
    }

    public void setShadowEffect(boolean shadowEffect) {
        if (this.shadowEffect == shadowEffect) {
            return;
        }
        this.shadowEffect = shadowEffect;
        if (shadowEffect) {
            this.buttonPaint.setShadowLayer(this.shadowRadius, 0.0f, this.shadowOffset, this.shadowColor);
        } else {
            this.buttonPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        }
    }

    public void setEnableEffect(boolean enable) {
        this.enableEffect = enable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pendingDragState() {
        if (!isInAnimating() && this.isTouchingDown) {
            if (this.valueAnimator.isRunning()) {
                this.valueAnimator.cancel();
            }
            this.animateState = 1;
            this.beforeState.copy(this.viewState);
            this.afterState.copy(this.viewState);
            if (isChecked()) {
                this.afterState.checkStateColor = this.checkedColor;
                this.afterState.buttonX = this.buttonMaxX;
                this.afterState.checkedLineColor = this.checkedColor;
            } else {
                this.afterState.checkStateColor = this.uncheckColor;
                this.afterState.buttonX = this.buttonMinX;
                this.afterState.radius = this.viewRadius;
            }
            this.valueAnimator.start();
        }
    }

    private void pendingCancelDragState() {
        if (isDragState() || isPendingDragState()) {
            if (this.valueAnimator.isRunning()) {
                this.valueAnimator.cancel();
            }
            this.animateState = 3;
            this.beforeState.copy(this.viewState);
            if (isChecked()) {
                setCheckedViewState(this.afterState);
            } else {
                setUncheckViewState(this.afterState);
            }
            this.valueAnimator.start();
        }
    }

    private void pendingSettleState() {
        if (this.valueAnimator.isRunning()) {
            this.valueAnimator.cancel();
        }
        this.animateState = 4;
        this.beforeState.copy(this.viewState);
        if (isChecked()) {
            setCheckedViewState(this.afterState);
        } else {
            setUncheckViewState(this.afterState);
        }
        this.valueAnimator.start();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        this.onCheckedChangeListener = l;
    }

    private static float dp2px(float dp) {
        return TypedValue.applyDimension(1, dp, Resources.getSystem().getDisplayMetrics());
    }

    private static int dp2pxInt(float dp) {
        return (int) dp2px(dp);
    }

    private static int optInt(TypedArray typedArray, int index, int def) {
        return typedArray == null ? def : typedArray.getInt(index, def);
    }

    private static float optPixelSize(TypedArray typedArray, int index, float def) {
        return typedArray == null ? def : typedArray.getDimension(index, def);
    }

    private static int optPixelSize(TypedArray typedArray, int index, int def) {
        return typedArray == null ? def : typedArray.getDimensionPixelOffset(index, def);
    }

    private static int optColor(TypedArray typedArray, int index, int def) {
        return typedArray == null ? def : typedArray.getColor(index, def);
    }

    private static boolean optBoolean(TypedArray typedArray, int index, boolean def) {
        return typedArray == null ? def : typedArray.getBoolean(index, def);
    }

    public void setButtonEnable(boolean enabled) {
        this.enable = enabled;
        setEnabled(enabled);
        this.checkedColor = (this.checkedColor & 16777215) | (enabled ? -16777216 : 2130706432);
        this.uncheckColor = (this.uncheckColor & 16777215) | (enabled ? -16777216 : 2130706432);
    }

    private static class ViewState {
        float buttonX;
        int checkStateColor;
        int checkedLineColor;
        float radius;

        ViewState() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copy(ViewState source) {
            this.buttonX = source.buttonX;
            this.checkStateColor = source.checkStateColor;
            this.checkedLineColor = source.checkedLineColor;
            this.radius = source.radius;
        }
    }
}