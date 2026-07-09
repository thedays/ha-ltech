package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewDaliTextSeekbarBinding;
import com.ltech.smarthome.utils.LightUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DaliTextSeekBarView extends LinearLayout {
    private int minProgress;
    private OnProgressChangeListener onProgressChangeListener;
    List<String> values;
    private ViewDaliTextSeekbarBinding viewBinding;

    public interface OnProgressChangeListener {
        void onProgressChanged(int v, boolean finish);
    }

    public DaliTextSeekBarView(Context context) {
        this(context, null);
    }

    public DaliTextSeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaliTextSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.values = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextSeekBarView, defStyleAttr, 0);
        ViewDaliTextSeekbarBinding inflate = ViewDaliTextSeekbarBinding.inflate(LayoutInflater.from(context), this, true);
        this.viewBinding = inflate;
        inflate.tvTitle.setText(obtainStyledAttributes.getString(7));
        this.viewBinding.seekbar.setProgressDrawable(ContextCompat.getDrawable(getContext(), obtainStyledAttributes.getResourceId(2, R.drawable.style_seekbar_red)));
        this.viewBinding.seekbar.setMax(255);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setSeekBarColor(ContextCompat.getColor(context, resourceId), false);
        }
        this.viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.DaliTextSeekBarView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                DaliTextSeekBarView.this.setViewChange(progress);
                DaliTextSeekBarView daliTextSeekBarView = DaliTextSeekBarView.this;
                DaliTextSeekBarView.this.viewBinding.tvValue.setText(daliTextSeekBarView.getProgressValue(daliTextSeekBarView.minProgress + progress));
                if (DaliTextSeekBarView.this.onProgressChangeListener != null) {
                    DaliTextSeekBarView.this.onProgressChangeListener.onProgressChanged(progress + DaliTextSeekBarView.this.minProgress, false);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                DaliTextSeekBarView.this.setViewChange(seekBar.getProgress());
                DaliTextSeekBarView.this.viewBinding.tvValue.setText(DaliTextSeekBarView.this.getProgressValue(seekBar.getProgress() + DaliTextSeekBarView.this.minProgress));
                if (DaliTextSeekBarView.this.onProgressChangeListener != null) {
                    DaliTextSeekBarView.this.onProgressChangeListener.onProgressChanged(seekBar.getProgress() + DaliTextSeekBarView.this.minProgress, true);
                }
            }
        });
        this.viewBinding.ivReduce.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.DaliTextSeekBarView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DaliTextSeekBarView.this.lambda$new$0(view);
            }
        });
        this.viewBinding.ivPlus.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.DaliTextSeekBarView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DaliTextSeekBarView.this.lambda$new$1(view);
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
    public /* synthetic */ void lambda$new$0(View view) {
        int progress = this.viewBinding.seekbar.getProgress();
        if (progress > 0) {
            this.viewBinding.seekbar.setProgress(progress - 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        int progress = this.viewBinding.seekbar.getProgress();
        if (progress < this.viewBinding.seekbar.getMax()) {
            this.viewBinding.seekbar.setProgress(progress + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewChange(int progress) {
        if (progress == 0) {
            this.viewBinding.ivReduce.setImageResource(R.mipmap.ic_reduce_disable);
            this.viewBinding.ivPlus.setImageResource(R.mipmap.ic_plus);
        } else if (progress == this.viewBinding.seekbar.getMax()) {
            this.viewBinding.ivReduce.setImageResource(R.mipmap.ic_reduce);
            this.viewBinding.ivPlus.setImageResource(R.mipmap.ic_plus_disable);
        } else {
            this.viewBinding.ivReduce.setImageResource(R.mipmap.ic_reduce);
            this.viewBinding.ivPlus.setImageResource(R.mipmap.ic_plus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getProgressValue(int progress) {
        if (!this.values.isEmpty()) {
            return this.values.get(progress);
        }
        return LightUtils.brt2ProgressHasBelowZero(progress) + "%";
    }

    public void setRange(int min, int max) {
        this.minProgress = min;
        this.viewBinding.seekbar.setMax(max - min);
    }

    public void setProgress(int progress) {
        this.viewBinding.seekbar.setProgress(progress - this.minProgress);
    }

    public int getProgress() {
        return this.viewBinding.seekbar.getProgress() + this.minProgress;
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

    public void refreshValue() {
        this.viewBinding.tvValue.setText(getProgressValue(this.viewBinding.seekbar.getProgress() + this.minProgress));
    }

    public void setValueAlignRight() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.viewBinding.tvValue.getLayoutParams();
        layoutParams.removeRule(17);
        this.viewBinding.tvValue.setLayoutParams(layoutParams);
    }

    public void setList(List<String> valueList) {
        this.values = valueList;
        if (valueList.isEmpty()) {
            return;
        }
        this.viewBinding.seekbar.setMax(valueList.size() - 1);
    }

    public void setSeekBarColor(int color, boolean changeThumb) {
        ((LayerDrawable) this.viewBinding.seekbar.getProgressDrawable()).getDrawable(2).setColorFilter(color, PorterDuff.Mode.SRC);
        if (changeThumb) {
            ((LayerDrawable) this.viewBinding.seekbar.getThumb()).getDrawable(1).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.onProgressChangeListener = listener;
    }
}