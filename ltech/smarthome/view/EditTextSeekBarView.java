package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.ColorEditText;

/* loaded from: classes4.dex */
public class EditTextSeekBarView extends RelativeLayout {
    private boolean canEdit;
    private ColorEditText etValue;
    private int minValue;
    private OnProgressChangeListener onProgressChangeListener;
    private AppCompatSeekBar seekBar;
    private int step;
    private TextView tvMax;
    private TextView tvMin;

    public interface OnProgressChangeListener {
        void onProgressChanged(int v, boolean finish);
    }

    public EditTextSeekBarView(Context context) {
        this(context, null);
    }

    public EditTextSeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.minValue = 0;
        this.step = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextSeekBarView, defStyleAttr, 0);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_edit_text_seekbar, this);
        ((TextView) inflate.findViewById(R.id.tv_title)).setText(obtainStyledAttributes.getString(7));
        ColorEditText colorEditText = (ColorEditText) inflate.findViewById(R.id.et_value);
        this.etValue = colorEditText;
        colorEditText.setFocusable(false);
        this.etValue.setFocusableInTouchMode(false);
        findViewById(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.EditTextSeekBarView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditTextSeekBarView.this.lambda$new$0(view);
            }
        });
        this.etValue.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.view.EditTextSeekBarView$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                EditTextSeekBarView.this.lambda$new$1(str);
            }
        });
        this.seekBar = (AppCompatSeekBar) inflate.findViewById(R.id.seekbar);
        this.seekBar.setProgressDrawable(ContextCompat.getDrawable(getContext(), obtainStyledAttributes.getResourceId(2, R.drawable.style_seekbar_red)));
        this.seekBar.setMax(255);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.EditTextSeekBarView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int progressValue = EditTextSeekBarView.this.getProgressValue(progress);
                    EditTextSeekBarView.this.etValue.setText(String.valueOf(progressValue));
                    if (EditTextSeekBarView.this.onProgressChangeListener != null) {
                        EditTextSeekBarView.this.onProgressChangeListener.onProgressChanged(progressValue, false);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (EditTextSeekBarView.this.onProgressChangeListener != null) {
                    EditTextSeekBarView.this.onProgressChangeListener.onProgressChanged(seekBar.getProgress(), true);
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
    public /* synthetic */ void lambda$new$0(View view) {
        if (this.canEdit) {
            setEditable(false);
        } else {
            setEditable(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(String str) {
        this.seekBar.setProgress(Integer.parseInt(str));
    }

    public void setEditable(boolean editable) {
        if (!editable) {
            this.canEdit = false;
            this.etValue.setFocusable(false);
            this.etValue.setFocusableInTouchMode(false);
            this.etValue.clearFocus();
            this.etValue.setTextColor(getResources().getColor(R.color.color_text_gray));
            ((InputMethodManager) this.etValue.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.etValue.getWindowToken(), 0);
            return;
        }
        this.canEdit = true;
        this.etValue.setFocusable(true);
        this.etValue.setFocusableInTouchMode(true);
        this.etValue.requestFocus();
        this.etValue.setTextColor(getResources().getColor(R.color.color_text_red));
        this.etValue.selectEnd();
        ((InputMethodManager) this.etValue.getContext().getSystemService("input_method")).showSoftInput(this.etValue, 0);
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
        this.etValue.setText(value);
    }

    public void setProgress(int progress) {
        this.seekBar.setProgress(progress - this.minValue);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.onProgressChangeListener = listener;
    }
}