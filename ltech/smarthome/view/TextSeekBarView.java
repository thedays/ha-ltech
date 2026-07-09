package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.LightUtils;

/* loaded from: classes4.dex */
public class TextSeekBarView extends RelativeLayout {
    private static final int VALUE_BRT = 1;
    private int drawableId;
    private int minValue;
    private OnProgressChangeListener onProgressChangeListener;
    private AppCompatSeekBar seekBar;
    private int step;
    private TextView tvMax;
    private TextView tvMin;
    private TextView tvTitle;
    private TextView tvValue;
    private int valueType;

    public interface OnProgressChangeListener {
        void onProgressChanged(int v, boolean finish);
    }

    public TextSeekBarView(Context context) {
        this(context, null);
    }

    public TextSeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.minValue = 0;
        this.step = 0;
        this.valueType = 0;
        this.drawableId = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextSeekBarView, defStyleAttr, 0);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_text_seekbar, this);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_title);
        this.tvTitle = textView;
        textView.setText(obtainStyledAttributes.getString(7));
        this.tvValue = (TextView) inflate.findViewById(R.id.tv_value);
        this.seekBar = (AppCompatSeekBar) inflate.findViewById(R.id.seekbar);
        this.valueType = obtainStyledAttributes.getInteger(8, 0);
        int resourceId = obtainStyledAttributes.getResourceId(2, 0);
        this.drawableId = resourceId;
        if (resourceId > 0) {
            this.seekBar.setProgressDrawable(ContextCompat.getDrawable(getContext(), this.drawableId));
        } else {
            int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId2 > 0) {
                setSeekBarColor(ContextCompat.getColor(context, resourceId2), false);
            }
        }
        this.seekBar.setMax(255);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.TextSeekBarView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int progressValue = TextSeekBarView.this.getProgressValue(progress);
                if (TextSeekBarView.this.valueType == 1) {
                    TextSeekBarView.this.tvValue.setText(LightUtils.brt2ProgressHasBelowZero(progressValue) + "%");
                } else {
                    TextSeekBarView.this.tvValue.setText(String.valueOf(progressValue));
                }
                if (TextSeekBarView.this.onProgressChangeListener == null || !fromUser) {
                    return;
                }
                TextSeekBarView.this.onProgressChangeListener.onProgressChanged(progressValue, false);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progressValue = TextSeekBarView.this.getProgressValue(seekBar.getProgress());
                if (TextSeekBarView.this.valueType == 1) {
                    TextSeekBarView.this.tvValue.setText(LightUtils.brt2ProgressHasBelowZero(progressValue) + "%");
                } else {
                    TextSeekBarView.this.tvValue.setText(String.valueOf(progressValue));
                }
                if (TextSeekBarView.this.onProgressChangeListener != null) {
                    TextSeekBarView.this.onProgressChangeListener.onProgressChanged(progressValue, true);
                }
            }
        });
        this.tvMin = (TextView) inflate.findViewById(R.id.tv_min);
        this.tvMax = (TextView) inflate.findViewById(R.id.tv_max);
        if (TextUtils.isEmpty(obtainStyledAttributes.getString(5)) || TextUtils.isEmpty(obtainStyledAttributes.getString(4))) {
            this.tvMin.setVisibility(8);
            this.tvMax.setVisibility(8);
        } else {
            this.tvMin.setText(obtainStyledAttributes.getString(5));
            this.tvMax.setText(obtainStyledAttributes.getString(4));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getProgressValue(int progress) {
        int i = this.step;
        if (i == 0) {
            return this.minValue + progress;
        }
        int i2 = this.minValue + progress;
        return ((i2 / i) + (i2 % i > i / 2 ? 1 : 0)) * i;
    }

    public void setValue(String value) {
        this.tvValue.setText(value);
    }

    public void setTextGone() {
        this.tvValue.setVisibility(8);
    }

    public void setRange(int min, int max, int step) {
        this.minValue = min;
        this.step = step;
        this.seekBar.setMax(max - min);
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        this.tvTitle.setAlpha(enabled ? 1.0f : 0.5f);
        this.tvValue.setAlpha(enabled ? 1.0f : 0.5f);
        this.seekBar.setEnabled(enabled);
        Rect bounds = this.seekBar.getProgressDrawable().getBounds();
        int i = this.drawableId;
        int i2 = R.drawable.style_seekbar_gray;
        if (i > 0) {
            AppCompatSeekBar appCompatSeekBar = this.seekBar;
            Context context = getContext();
            if (enabled) {
                i2 = this.drawableId;
            }
            appCompatSeekBar.setProgressDrawable(ContextCompat.getDrawable(context, i2));
        } else {
            AppCompatSeekBar appCompatSeekBar2 = this.seekBar;
            Context context2 = getContext();
            if (enabled) {
                i2 = R.drawable.style_seekbar_red;
            }
            appCompatSeekBar2.setProgressDrawable(ContextCompat.getDrawable(context2, i2));
        }
        this.seekBar.getProgressDrawable().setBounds(bounds);
    }

    public void setSeekBarColor(int color, boolean changeThumb) {
        ((LayerDrawable) this.seekBar.getProgressDrawable()).getDrawable(2).setColorFilter(color, PorterDuff.Mode.SRC);
        if (changeThumb) {
            ((LayerDrawable) this.seekBar.getThumb()).getDrawable(1).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setProgress(int progress) {
        this.seekBar.setProgress(progress - this.minValue);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.onProgressChangeListener = listener;
    }
}