package com.ltech.smarthome.ui.device.light;

import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes4.dex */
public class BrtControlHelper {
    private LightBrtBar lightBrtBar;
    private OnBrtChangedListener onBrtChangedListener;
    private AppCompatTextView tvBrt;

    public interface OnBrtChangedListener {
        void onProgressChanged(int progress, boolean finish);
    }

    public BrtControlHelper(LightBrtBar lightBrtBar, AppCompatTextView tvBrt, OnBrtChangedListener onBrtChangedListener) {
        this(lightBrtBar, tvBrt, onBrtChangedListener, false);
    }

    public BrtControlHelper(LightBrtBar lightBrtBar, final AppCompatTextView tvBrt, OnBrtChangedListener listener, boolean includeZero) {
        this.onBrtChangedListener = listener;
        this.lightBrtBar = lightBrtBar;
        this.tvBrt = tvBrt;
        lightBrtBar.setIncludeZero(includeZero);
        lightBrtBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.light.BrtControlHelper.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                if (!fromUser || BrtControlHelper.this.onBrtChangedListener == null) {
                    return;
                }
                BrtControlHelper.this.onBrtChangedListener.onProgressChanged(progress, false);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                if (BrtControlHelper.this.onBrtChangedListener != null) {
                    BrtControlHelper.this.onBrtChangedListener.onProgressChanged(seekBar.getProgress(), true);
                }
            }
        });
        tvBrt.setText(lightBrtBar.getProgressHasBelowOne());
    }

    public void setProgress(int progress) {
        this.tvBrt.setText(this.lightBrtBar.getProgressHasBelowOne());
        this.lightBrtBar.setProgress(progress);
    }
}