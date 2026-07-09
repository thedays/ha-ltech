package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class RadioImageTextView extends LinearLayout implements View.OnClickListener {
    private boolean check;
    private int checkImageRes;
    private boolean enable;
    private AppCompatImageView imageView;
    private OnCheckChangedListener mListener;
    private String textContent;
    private AppCompatTextView textView;
    private int unCheckImageRes;

    public interface OnCheckChangedListener {
        void onCheckChanged(RadioImageTextView view, boolean check);
    }

    public RadioImageTextView(Context context) {
        this(context, null);
    }

    public RadioImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.check = false;
        this.enable = true;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_radio_image_text_view, this);
        this.imageView = (AppCompatImageView) inflate.findViewById(R.id.iv_image);
        this.textView = (AppCompatTextView) inflate.findViewById(R.id.tv_text);
        inflate.setOnClickListener(this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RadioImageTextView);
        this.checkImageRes = obtainStyledAttributes.getResourceId(0, R.mipmap.icon_type_sel);
        this.unCheckImageRes = obtainStyledAttributes.getResourceId(3, R.mipmap.icon_type_default);
        this.textContent = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        refreshView();
    }

    private void refreshView() {
        this.imageView.setImageResource(this.check ? this.checkImageRes : this.unCheckImageRes);
        this.textView.setText(this.textContent);
    }

    public void setCheckImageRes(int res) {
        this.checkImageRes = res;
        refreshView();
    }

    public void setUnCheckImageRes(int res) {
        this.unCheckImageRes = res;
        refreshView();
    }

    public void setTextContent(String content) {
        this.textContent = content;
        this.textView.setText(content);
    }

    public void setTextContentColor(int color) {
        this.textView.setTextColor(color);
    }

    public void setCheck(boolean check) {
        this.check = check;
        refreshView();
    }

    public boolean isChecked() {
        return this.check;
    }

    public void setListener(OnCheckChangedListener mListener) {
        this.mListener = mListener;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (this.enable) {
            this.check = !this.check;
            refreshView();
            OnCheckChangedListener onCheckChangedListener = this.mListener;
            if (onCheckChangedListener != null) {
                onCheckChangedListener.onCheckChanged(this, this.check);
            }
        }
    }
}