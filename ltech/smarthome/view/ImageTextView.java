package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewImageTextBinding;

/* loaded from: classes4.dex */
public class ImageTextView extends LinearLayout {
    private ViewImageTextBinding viewBinding;

    public ImageTextView(Context context) {
        this(context, null);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.viewBinding = ViewImageTextBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView, defStyleAttr, 0);
        this.viewBinding.ivIcon.setImageResource(obtainStyledAttributes.getResourceId(0, R.mipmap.ic_speaker_on));
        this.viewBinding.tvName.setText(obtainStyledAttributes.getString(1));
    }

    public void setText(String text) {
        this.viewBinding.tvName.setText(text);
    }

    public void setSrc(int src) {
        this.viewBinding.ivIcon.setImageResource(src);
    }
}