package com.ltech.smarthome.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class NumberSetView extends ConstraintLayout {
    public AppCompatEditText etNum;
    private AppCompatImageView ivNumMinus;
    private AppCompatImageView ivNumPlus;
    private OnNumberClickListener listener;
    private int maxNum;
    private int minNum;
    private int number;
    private int step;

    public interface OnNumberClickListener {
        void numberChanged(int number);

        void onNumberClick();
    }

    public NumberSetView(Context context) {
        this(context, null);
    }

    public NumberSetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberSetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.minNum = 0;
        this.maxNum = 100;
        this.step = 1;
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_number_setting, this);
        this.etNum = (AppCompatEditText) inflate.findViewById(R.id.et_add);
        this.ivNumMinus = (AppCompatImageView) inflate.findViewById(R.id.iv_add_minus);
        this.ivNumPlus = (AppCompatImageView) inflate.findViewById(R.id.iv_add_plus);
        setEditable(false);
        this.ivNumMinus.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.NumberSetView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NumberSetView.this.lambda$new$0(view);
            }
        });
        this.ivNumPlus.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.NumberSetView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NumberSetView.this.lambda$new$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        setNumber(Math.min(this.maxNum, Math.max(this.minNum, this.number - this.step)));
        setViewChange();
        OnNumberClickListener onNumberClickListener = this.listener;
        if (onNumberClickListener != null) {
            onNumberClickListener.numberChanged(this.number);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        setNumber(Math.min(this.maxNum, Math.max(this.minNum, this.number + this.step)));
        setViewChange();
        OnNumberClickListener onNumberClickListener = this.listener;
        if (onNumberClickListener != null) {
            onNumberClickListener.numberChanged(this.number);
        }
    }

    public void setEditable(boolean editable) {
        if (editable) {
            this.etNum.setFocusable(true);
            this.etNum.setFocusableInTouchMode(true);
            this.etNum.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.view.NumberSetView.1
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        return;
                    }
                    NumberSetView.this.number = Integer.parseInt(s.toString());
                }
            });
        } else {
            this.etNum.setFocusable(false);
            this.etNum.setFocusableInTouchMode(false);
            this.etNum.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.NumberSetView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NumberSetView.this.lambda$setEditable$2(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEditable$2(View view) {
        OnNumberClickListener onNumberClickListener = this.listener;
        if (onNumberClickListener != null) {
            onNumberClickListener.onNumberClick();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void setViewChange() {
        if (this.number > this.minNum) {
            this.ivNumMinus.setImageResource(R.mipmap.ic_reduce);
        } else {
            this.ivNumMinus.setImageResource(R.mipmap.ic_reduce_disable);
        }
        if (this.number < this.maxNum) {
            this.ivNumPlus.setImageResource(R.mipmap.ic_plus);
        } else {
            this.ivNumPlus.setImageResource(R.mipmap.ic_plus_disable);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean enable) {
        this.etNum.setEnabled(enable);
        this.ivNumMinus.setEnabled(enable);
        this.ivNumPlus.setEnabled(enable);
        if (enable) {
            this.ivNumMinus.setImageResource(R.mipmap.ic_reduce);
            this.ivNumPlus.setImageResource(R.mipmap.ic_plus);
            this.etNum.setTextColor(getResources().getColor(R.color.color_text_black));
            setViewChange();
            return;
        }
        this.ivNumMinus.setImageResource(R.mipmap.ic_reduce_disable);
        this.ivNumPlus.setImageResource(R.mipmap.ic_plus_disable);
        this.etNum.setTextColor(getResources().getColor(R.color.color_text_dark_gray));
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
        this.etNum.setText(String.valueOf(number));
        setViewChange();
    }

    public void setRange(int minNum, int maxNum) {
        this.minNum = minNum;
        this.maxNum = maxNum;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setListener(OnNumberClickListener listener) {
        this.listener = listener;
    }
}