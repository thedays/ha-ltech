package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewTextWithButtonBinding;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class TextWithButtonView extends LinearLayout {
    private OnValueChangeListener onValueChangeListener;
    private int position;
    List<String> values;
    private ViewTextWithButtonBinding viewBinding;

    public interface OnValueChangeListener {
        void onReduceOrPlusClick();
    }

    private int getReducePosition(int position) {
        if (position > 0) {
            return position - 1;
        }
        return 0;
    }

    public TextWithButtonView(Context context) {
        this(context, null);
    }

    public TextWithButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextWithButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.values = new ArrayList();
        this.position = 0;
        ViewTextWithButtonBinding inflate = ViewTextWithButtonBinding.inflate(LayoutInflater.from(context), this, true);
        this.viewBinding = inflate;
        inflate.ivReduce.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.TextWithButtonView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextWithButtonView.this.lambda$new$0(view);
            }
        });
        this.viewBinding.ivPlus.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.TextWithButtonView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextWithButtonView.this.lambda$new$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int reducePosition = getReducePosition(this.position);
        this.position = reducePosition;
        setViewChange(reducePosition);
        this.viewBinding.tvValue.setText(getValue(this.position));
        OnValueChangeListener onValueChangeListener = this.onValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onReduceOrPlusClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        int plusPosition = getPlusPosition(this.position);
        this.position = plusPosition;
        setViewChange(plusPosition);
        this.viewBinding.tvValue.setText(getValue(this.position));
        OnValueChangeListener onValueChangeListener = this.onValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onReduceOrPlusClick();
        }
    }

    private int getPlusPosition(int position) {
        return position < this.values.size() + (-1) ? position + 1 : this.values.size() - 1;
    }

    private void setViewChange(int position) {
        if (position == 0) {
            this.viewBinding.ivReduce.setImageResource(R.mipmap.ic_reduce_disable);
            this.viewBinding.ivPlus.setImageResource(R.mipmap.ic_plus);
        } else if (position == this.values.size() - 1) {
            this.viewBinding.ivReduce.setImageResource(R.mipmap.ic_reduce);
            this.viewBinding.ivPlus.setImageResource(R.mipmap.ic_plus_disable);
        } else {
            this.viewBinding.ivReduce.setImageResource(R.mipmap.ic_reduce);
            this.viewBinding.ivPlus.setImageResource(R.mipmap.ic_plus);
        }
    }

    private String getValue(int position) {
        List<String> list = this.values;
        if (list != null && list.size() > 0) {
            return this.values.get(position);
        }
        return "";
    }

    public int getPosition() {
        return this.position;
    }

    @Override // android.view.View
    public void setAlpha(float alpha) {
        this.viewBinding.tvValue.setAlpha(alpha);
        this.viewBinding.ivReduce.setAlpha(alpha);
        this.viewBinding.ivPlus.setAlpha(alpha);
    }

    public void setBtnEnable(boolean enable) {
        this.viewBinding.ivReduce.setEnabled(enable);
        this.viewBinding.ivPlus.setEnabled(enable);
    }

    public void setPosition(int position) {
        this.position = position;
        this.viewBinding.tvValue.setText(getValue(position));
        setViewChange(position);
    }

    public void setValue(String value) {
        this.viewBinding.tvValue.setText(value);
    }

    public void setList(List<String> valueList) {
        this.values = valueList;
        this.viewBinding.tvValue.setText(getValue(this.position));
        setViewChange(this.position);
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }
}