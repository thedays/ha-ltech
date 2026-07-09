package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/* loaded from: classes4.dex */
public class DeliverTouchLayout extends LinearLayout {
    private View mTargetView;

    public DeliverTouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTargetView(View targetView) {
        this.mTargetView = targetView;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        View view = this.mTargetView;
        if (view != null) {
            view.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
}