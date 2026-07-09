package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.SwitchCompat;

/* loaded from: classes4.dex */
public class SmartSwitch extends SwitchCompat {
    private boolean fromUser;
    private OnUserCheckedChangeListener onUserCheckedChangeListener;

    public interface OnUserCheckedChangeListener {
        void onUserCheckedChanged(SmartSwitch view, boolean isChecked);
    }

    public SmartSwitch(Context context) {
        super(context);
    }

    public SmartSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override // androidx.appcompat.widget.SwitchCompat, android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        this.fromUser = true;
        return super.onTouchEvent(ev);
    }

    @Override // androidx.appcompat.widget.SwitchCompat, android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean checked) {
        OnUserCheckedChangeListener onUserCheckedChangeListener;
        super.setChecked(checked);
        if (this.fromUser && (onUserCheckedChangeListener = this.onUserCheckedChangeListener) != null) {
            onUserCheckedChangeListener.onUserCheckedChanged(this, isChecked());
        }
        this.fromUser = false;
    }

    public boolean isFromUser() {
        return this.fromUser;
    }

    public void setFromUser(boolean fromUser) {
        this.fromUser = fromUser;
    }

    public void setOnUserCheckedChangeListener(OnUserCheckedChangeListener l) {
        this.onUserCheckedChangeListener = l;
    }
}