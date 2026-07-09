package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ColorEditText extends AppCompatEditText {
    private boolean isEdit;
    private OnColorTextChangedListener mListener;
    private TextWatcher mTextWatcher;
    private int max;

    public interface OnColorTextChangedListener {
        void onTextChangedListener(String s);
    }

    public ColorEditText(Context context) {
        super(context);
        this.max = 255;
        this.mTextWatcher = new TextWatcher() { // from class: com.ltech.smarthome.view.ColorEditText.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                ColorEditText.this.checkText(s);
            }
        };
        init(context, null);
    }

    public ColorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.max = 255;
        this.mTextWatcher = new TextWatcher() { // from class: com.ltech.smarthome.view.ColorEditText.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                ColorEditText.this.checkText(s);
            }
        };
        init(context, attrs);
    }

    public ColorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.max = 255;
        this.mTextWatcher = new TextWatcher() { // from class: com.ltech.smarthome.view.ColorEditText.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                ColorEditText.this.checkText(s);
            }
        };
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setInputType(2);
        setText(getText().toString().replaceAll("[^0-9]", ""));
        if (attrs != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ColorEditText);
            if (obtainStyledAttributes.hasValue(0)) {
                this.max = obtainStyledAttributes.getInt(0, 255);
            }
            obtainStyledAttributes.recycle();
        }
        if (TextUtils.isEmpty(getText())) {
            setText(String.valueOf(0));
            return;
        }
        int parseInt = Integer.parseInt(getText().toString());
        int i = this.max;
        if (parseInt > i) {
            setText(String.valueOf(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkText(Editable s) {
        if (!this.isEdit && hasFocus()) {
            this.isEdit = true;
            String replaceAll = s.toString().replaceAll("^(0+)", "");
            if (TextUtils.isEmpty(replaceAll)) {
                setText(String.valueOf(0));
            } else {
                int parseInt = Integer.parseInt(replaceAll);
                int i = this.max;
                if (parseInt > i) {
                    setText(String.valueOf(i));
                } else {
                    setText(replaceAll);
                }
            }
            selectEnd();
            OnColorTextChangedListener onColorTextChangedListener = this.mListener;
            if (onColorTextChangedListener != null) {
                onColorTextChangedListener.onTextChangedListener(getText().toString());
            }
            this.isEdit = false;
        }
    }

    public void selectEnd() {
        setSelection(getText().toString().trim().length());
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setListener(OnColorTextChangedListener listener) {
        this.mListener = listener;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addTextChangedListener(this.mTextWatcher);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeTextChangedListener(this.mTextWatcher);
    }
}