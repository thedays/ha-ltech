package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewSwitchSeekbarBinding;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes4.dex */
public class SwitchSeekBarView extends ConstraintLayout {
    private int drawableId;
    private boolean needPercent;
    private OnProgressChangeListener onProgressChangeListener;
    private ViewSwitchSeekbarBinding viewBinding;

    public interface OnProgressChangeListener {
        void onCheckedChanged(boolean isChecked);

        void onProgressChanged(int v, boolean finish);
    }

    public SwitchSeekBarView(Context context) {
        super(context);
        this.drawableId = 0;
    }

    public SwitchSeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.drawableId = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextSeekBarView, defStyleAttr, 0);
        ViewSwitchSeekbarBinding inflate = ViewSwitchSeekbarBinding.inflate(LayoutInflater.from(context), this, true);
        this.viewBinding = inflate;
        inflate.tvTitle.setText(obtainStyledAttributes.getString(7));
        this.drawableId = obtainStyledAttributes.getResourceId(2, R.drawable.style_seekbar_red);
        this.viewBinding.seekbar.setProgressDrawable(ContextCompat.getDrawable(getContext(), this.drawableId));
        this.viewBinding.seekbar.setMax(255);
        this.viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.SwitchSeekBarView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String valueOf;
                if (fromUser) {
                    if (SwitchSeekBarView.this.needPercent) {
                        valueOf = progress + "%";
                    } else {
                        valueOf = String.valueOf(progress);
                    }
                    SwitchSeekBarView.this.viewBinding.tvValue.setText(valueOf);
                    if (SwitchSeekBarView.this.onProgressChangeListener != null) {
                        SwitchSeekBarView.this.onProgressChangeListener.onProgressChanged(progress, false);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                String valueOf;
                if (SwitchSeekBarView.this.needPercent) {
                    valueOf = seekBar.getProgress() + "%";
                } else {
                    valueOf = String.valueOf(seekBar.getProgress());
                }
                SwitchSeekBarView.this.viewBinding.tvValue.setText(valueOf);
                if (SwitchSeekBarView.this.onProgressChangeListener != null) {
                    SwitchSeekBarView.this.onProgressChangeListener.onProgressChanged(seekBar.getProgress(), true);
                }
            }
        });
        if (obtainStyledAttributes.getBoolean(6, true)) {
            this.viewBinding.sbRgbOn.setVisibility(0);
            this.viewBinding.sbRgbOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.SwitchSeekBarView.2
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    SwitchSeekBarView.this.viewBinding.seekbar.setEnabled(isChecked);
                    SwitchSeekBarView.this.viewBinding.seekbar.setAlpha(isChecked ? 1.0f : 0.5f);
                    if (SwitchSeekBarView.this.onProgressChangeListener != null) {
                        SwitchSeekBarView.this.onProgressChangeListener.onCheckedChanged(isChecked);
                    }
                }
            });
        } else {
            this.viewBinding.sbRgbOn.setVisibility(8);
        }
    }

    public void setTitle(String title) {
        this.viewBinding.tvTitle.setText(title);
    }

    public void setCheckedNotByUser(boolean checked) {
        this.viewBinding.sbRgbOn.setCheckedNotByUser(checked);
    }

    public void setMax(int max) {
        this.viewBinding.seekbar.setMax(max);
    }

    public void setMin(int min) {
        this.viewBinding.seekbar.setMin(min);
    }

    public void needPercent(boolean b2) {
        this.needPercent = b2;
    }

    public void setSwitchVisible(boolean switchVisible) {
        this.viewBinding.sbRgbOn.setVisibility(switchVisible ? 0 : 8);
    }

    public void setProgressAndValue(int progress) {
        String valueOf;
        if (this.needPercent) {
            valueOf = progress + "%";
        } else {
            valueOf = String.valueOf(progress);
        }
        this.viewBinding.tvValue.setText(valueOf);
        this.viewBinding.seekbar.setProgress(progress);
    }

    public void setState(boolean isOpen) {
        this.viewBinding.sbRgbOn.setCheckedNotByUser(isOpen);
        this.viewBinding.seekbar.setEnabled(isOpen);
        this.viewBinding.seekbar.setAlpha(isOpen ? 1.0f : 0.5f);
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        this.viewBinding.seekbar.setEnabled(enabled);
        Rect bounds = this.viewBinding.seekbar.getProgressDrawable().getBounds();
        int i = this.drawableId;
        int i2 = R.drawable.style_seekbar_gray;
        if (i > 0) {
            AppCompatSeekBar appCompatSeekBar = this.viewBinding.seekbar;
            Context context = getContext();
            if (enabled) {
                i2 = this.drawableId;
            }
            appCompatSeekBar.setProgressDrawable(ContextCompat.getDrawable(context, i2));
        } else {
            AppCompatSeekBar appCompatSeekBar2 = this.viewBinding.seekbar;
            Context context2 = getContext();
            if (enabled) {
                i2 = R.drawable.style_seekbar_red;
            }
            appCompatSeekBar2.setProgressDrawable(ContextCompat.getDrawable(context2, i2));
        }
        this.viewBinding.seekbar.getProgressDrawable().setBounds(bounds);
    }

    public void setSwitchEnabled(boolean enabled) {
        this.viewBinding.sbRgbOn.setEnabled(enabled);
        this.viewBinding.sbRgbOn.setCheckedNotByUser(enabled);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.onProgressChangeListener = listener;
    }
}