package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewDaliTextSeekbarNewBinding;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DaliTextSeekBarViewNew extends LinearLayout {
    private OnProgressChangeListener onProgressChangeListener;
    List<String> values;
    private ViewDaliTextSeekbarNewBinding viewBinding;

    public interface OnProgressChangeListener {
        void onProgressChanged(int v, boolean finish);
    }

    public DaliTextSeekBarViewNew(Context context) {
        this(context, null);
    }

    public DaliTextSeekBarViewNew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaliTextSeekBarViewNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.values = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextSeekBarView, defStyleAttr, 0);
        ViewDaliTextSeekbarNewBinding inflate = ViewDaliTextSeekbarNewBinding.inflate(LayoutInflater.from(context), this, true);
        this.viewBinding = inflate;
        inflate.tvTitle.setText(obtainStyledAttributes.getString(7));
        this.viewBinding.seekbar.setOnRangeChangedListener(new OnRangeChangedListener() { // from class: com.ltech.smarthome.view.DaliTextSeekBarViewNew.1
            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onRangeChanged(RangeSeekBar seekBar, float leftValue, float rightValue, boolean isFromUser) {
                int i = (int) leftValue;
                DaliTextSeekBarViewNew.this.viewBinding.tvValue.setText(DaliTextSeekBarViewNew.this.getProgressValue(i));
                if (DaliTextSeekBarViewNew.this.onProgressChangeListener != null) {
                    DaliTextSeekBarViewNew.this.onProgressChangeListener.onProgressChanged(i, false);
                }
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStopTrackingTouch(RangeSeekBar seekBar, boolean isLeft) {
                DaliTextSeekBarViewNew.this.viewBinding.tvValue.setText(DaliTextSeekBarViewNew.this.getProgressValue((int) seekBar.getLeftSeekBar().getProgress()));
                if (DaliTextSeekBarViewNew.this.onProgressChangeListener != null) {
                    DaliTextSeekBarViewNew.this.onProgressChangeListener.onProgressChanged((int) seekBar.getLeftSeekBar().getProgress(), true);
                }
            }
        });
        if (TextUtils.isEmpty(obtainStyledAttributes.getString(5)) || TextUtils.isEmpty(obtainStyledAttributes.getString(4))) {
            this.viewBinding.tvMin.setVisibility(8);
            this.viewBinding.tvMax.setVisibility(8);
        } else {
            this.viewBinding.tvMin.setText(obtainStyledAttributes.getString(5));
            this.viewBinding.tvMax.setText(obtainStyledAttributes.getString(4));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getProgressValue(int progress) {
        List<String> list = this.values;
        if (list != null && list.size() > 0) {
            return this.values.get(progress);
        }
        return "";
    }

    public void setProgress(int progress) {
        this.viewBinding.seekbar.setProgress(progress);
    }

    public int getProgress() {
        return (int) this.viewBinding.seekbar.getLeftSeekBar().getProgress();
    }

    public void setValue(String value) {
        this.viewBinding.tvValue.setText(value);
    }

    public void setValueAlpha(float alpha) {
        this.viewBinding.tvValue.setAlpha(alpha);
    }

    public void setTitle(String title) {
        this.viewBinding.tvTitle.setText(title);
    }

    public void setList(List<String> valueList) {
        this.values = valueList;
        this.viewBinding.seekbar.setRange(0.0f, valueList.size() - 1, 1.0f);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.onProgressChangeListener = listener;
    }
}