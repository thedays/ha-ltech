package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import com.feasycom.feasymesh.library.ble.error.GattError;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class StepSetView extends RelativeLayout {
    private boolean changeProgressByBtn;
    private int drawableId;
    private AppCompatImageView ivMinus;
    private AppCompatImageView ivPlus;
    private int maxProgress;
    private int minProgress;
    private OnProgressChangeListener onProgressChangeListener;
    private AppCompatSeekBar seekbar;
    private int step;
    private TextView tvTitle;
    private TextView tvValue;

    public interface OnProgressChangeListener {
        void onProgressChanged(int v, boolean finish, boolean fromUser);
    }

    public StepSetView(Context context) {
        this(context, null);
    }

    public StepSetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepSetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.step = 1;
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_brt_setting, this);
        this.tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        this.tvValue = (TextView) inflate.findViewById(R.id.tv_value);
        this.seekbar = (AppCompatSeekBar) inflate.findViewById(R.id.seekbar);
        this.ivMinus = (AppCompatImageView) inflate.findViewById(R.id.iv_minus);
        this.ivPlus = (AppCompatImageView) inflate.findViewById(R.id.iv_plus);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.BrtSettingView, defStyleAttr, 0);
        inflate.findViewById(R.id.top_line).setVisibility(obtainStyledAttributes.getBoolean(5, false) ? 0 : 8);
        if (obtainStyledAttributes.getBoolean(4, true)) {
            this.tvTitle.setText(obtainStyledAttributes.getString(2));
            this.tvValue.setText(obtainStyledAttributes.getString(3));
        } else {
            this.tvTitle.setVisibility(8);
            this.tvValue.setVisibility(8);
        }
        this.drawableId = obtainStyledAttributes.getResourceId(0, R.drawable.style_seekbar_red);
        this.seekbar.setProgressDrawable(ContextCompat.getDrawable(getContext(), this.drawableId));
        this.seekbar.setMax(obtainStyledAttributes.getInt(1, GattError.GATT_CCCD_CFG_ERROR));
        this.maxProgress = this.seekbar.getMax();
        obtainStyledAttributes.recycle();
        this.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.StepSetView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = StepSetView.this.minProgress + i;
                if (i2 > StepSetView.this.maxProgress) {
                    seekBar.setProgress(StepSetView.this.maxProgress);
                    return;
                }
                boolean z2 = true;
                z2 = true;
                if (i2 % StepSetView.this.step != 0) {
                    StepSetView.this.setProgress(((i2 / StepSetView.this.step) + (i2 % StepSetView.this.step <= StepSetView.this.step / 2 ? 0 : 1)) * StepSetView.this.step);
                    return;
                }
                StepSetView.this.tvValue.setText(String.valueOf(i2));
                StepSetView.this.setViewChange(i);
                if (StepSetView.this.onProgressChangeListener != null) {
                    OnProgressChangeListener onProgressChangeListener = StepSetView.this.onProgressChangeListener;
                    boolean z3 = StepSetView.this.changeProgressByBtn;
                    if (!z && !StepSetView.this.changeProgressByBtn) {
                        z2 = false;
                    }
                    onProgressChangeListener.onProgressChanged(i2, z3, z2);
                }
                if (StepSetView.this.changeProgressByBtn) {
                    StepSetView.this.changeProgressByBtn = false;
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (StepSetView.this.onProgressChangeListener != null) {
                    StepSetView.this.onProgressChangeListener.onProgressChanged(StepSetView.this.getProgress(), true, true);
                }
            }
        });
        this.ivMinus.setEnabled(false);
        this.ivMinus.setImageResource(R.mipmap.ic_reduce_disable);
        this.ivMinus.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.StepSetView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StepSetView.this.lambda$new$0(view);
            }
        });
        this.ivPlus.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.StepSetView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StepSetView.this.lambda$new$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int progress = this.seekbar.getProgress();
        if (progress > 0) {
            this.changeProgressByBtn = true;
            this.seekbar.setProgress(progress - this.step);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        int progress = this.seekbar.getProgress();
        if (progress < this.maxProgress - this.minProgress) {
            this.changeProgressByBtn = true;
            this.seekbar.setProgress(progress + this.step);
        }
    }

    public void setRange(int min, int max) {
        this.minProgress = min;
        this.maxProgress = max;
        this.seekbar.setMax(max - min);
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        this.seekbar.setEnabled(enabled);
        this.ivMinus.setEnabled(enabled);
        this.ivPlus.setEnabled(enabled);
        Rect bounds = this.seekbar.getProgressDrawable().getBounds();
        if (enabled) {
            this.tvTitle.setTextColor(getResources().getColor(R.color.color_text_black));
            this.tvValue.setTextColor(getResources().getColor(R.color.color_text_black));
            this.seekbar.setProgressDrawable(ContextCompat.getDrawable(getContext(), this.drawableId));
            setViewChange(this.seekbar.getProgress());
        } else {
            this.tvTitle.setTextColor(getResources().getColor(R.color.color_text_dark_gray));
            this.tvValue.setTextColor(getResources().getColor(R.color.color_text_dark_gray));
            this.seekbar.setProgressDrawable(ContextCompat.getDrawable(getContext(), R.drawable.style_seekbar_gray));
        }
        this.seekbar.getProgressDrawable().setBounds(bounds);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewChange(int progress) {
        if (progress == 0) {
            this.ivMinus.setEnabled(false);
            this.ivPlus.setEnabled(true);
            this.ivMinus.setImageResource(R.mipmap.ic_reduce_disable);
            this.ivPlus.setImageResource(R.mipmap.ic_plus);
            return;
        }
        if (progress == this.maxProgress - this.minProgress) {
            this.ivMinus.setEnabled(true);
            this.ivPlus.setEnabled(false);
            this.ivMinus.setImageResource(R.mipmap.ic_reduce);
            this.ivPlus.setImageResource(R.mipmap.ic_plus_disable);
            return;
        }
        this.ivMinus.setEnabled(true);
        this.ivPlus.setEnabled(true);
        this.ivMinus.setImageResource(R.mipmap.ic_reduce);
        this.ivPlus.setImageResource(R.mipmap.ic_plus);
    }

    public void setProgress(int progress) {
        this.seekbar.setProgress(Math.min(this.maxProgress, Math.max(this.minProgress, progress)) - this.minProgress);
    }

    public int getProgress() {
        int progress = this.seekbar.getProgress() + this.minProgress;
        int i = this.step;
        if (progress % i != 0) {
            return ((progress / i) + (progress % i > i / 2 ? 1 : 0)) * i;
        }
        return progress;
    }

    public int getMinProgress() {
        return this.minProgress;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setValue(String value) {
        this.tvValue.setText(value);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.onProgressChangeListener = listener;
    }
}