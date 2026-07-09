package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewSwitchTwoSeekbarBinding;

/* loaded from: classes4.dex */
public class SwitchTwoSeekBarView extends ConstraintLayout {
    private ViewSwitchTwoSeekbarBinding viewBinding;

    public SwitchTwoSeekBarView(Context context) {
        super(context);
    }

    public SwitchTwoSeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchTwoSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextSeekBarView, defStyleAttr, 0);
        ViewSwitchTwoSeekbarBinding inflate = ViewSwitchTwoSeekbarBinding.inflate(LayoutInflater.from(context), this, true);
        this.viewBinding = inflate;
        inflate.tvTitle.setText(obtainStyledAttributes.getString(7));
        this.viewBinding.seekbar.setProgressDrawable(ContextCompat.getDrawable(getContext(), obtainStyledAttributes.getResourceId(2, R.drawable.style_seekbar_red)));
        this.viewBinding.seekbar.setMax(100);
        this.viewBinding.seekbarSecond.setProgressDrawable(ContextCompat.getDrawable(getContext(), obtainStyledAttributes.getResourceId(3, R.drawable.style_seekbar_red)));
        this.viewBinding.seekbarSecond.setMax(255);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setSeekBarColor(ContextCompat.getColor(context, resourceId), false);
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 > 0) {
            setSecondSeekBarColor(ContextCompat.getColor(context, resourceId2), false);
        }
    }

    public void setSeekBarColor(int color, boolean changeThumb) {
        ((LayerDrawable) this.viewBinding.seekbar.getProgressDrawable()).getDrawable(2).setColorFilter(color, PorterDuff.Mode.SRC);
        if (changeThumb) {
            ((LayerDrawable) this.viewBinding.seekbar.getThumb()).getDrawable(1).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setSecondSeekBarColor(int color, boolean changeThumb) {
        ((LayerDrawable) this.viewBinding.seekbarSecond.getProgressDrawable()).getDrawable(2).setColorFilter(color, PorterDuff.Mode.SRC);
        if (changeThumb) {
            ((LayerDrawable) this.viewBinding.seekbarSecond.getThumb()).getDrawable(1).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }
}