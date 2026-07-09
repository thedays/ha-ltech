package com.ltech.smarthome.view;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import com.google.android.material.textfield.TextInputEditText;

/* loaded from: classes4.dex */
public class ClearEditText extends TextInputEditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Drawable drawable = getCompoundDrawables()[2];
        this.mClearDrawable = drawable;
        if (drawable == null) {
            this.mClearDrawable = getResources().getDrawable(com.ltech.smarthome.R.mipmap.ic_clear);
        }
        Drawable drawable2 = this.mClearDrawable;
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null && event.getAction() == 1 && event.getX() > (getWidth() - getPaddingRight()) - this.mClearDrawable.getIntrinsicWidth() && event.getX() < getWidth() - getPaddingRight()) {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    protected void setClearIconVisible(boolean visible) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], visible ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }

    @Override // android.widget.TextView, android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(s.length() > 0);
    }

    public void setShakeAnimation() {
        setAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000L);
        return translateAnimation;
    }
}