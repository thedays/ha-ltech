package com.ltech.smarthome.view.dialog;

import android.widget.SeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogMusicVolumeBinding;

/* loaded from: classes4.dex */
public class MusicVolumeDialog extends SmartDialog<DialogMusicVolumeBinding> {
    private OnVolumeChangeCallback onVolumeChangeCallback;
    private int volume;

    public interface OnVolumeChangeCallback {
        void onVolumeChange(int volume);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_music_volume;
    }

    public static MusicVolumeDialog asDefault() {
        return (MusicVolumeDialog) new MusicVolumeDialog().setViewConverter(new SmartDialog.ViewConverter<DialogMusicVolumeBinding, MusicVolumeDialog>() { // from class: com.ltech.smarthome.view.dialog.MusicVolumeDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogMusicVolumeBinding viewBinding, final MusicVolumeDialog dialog) {
                viewBinding.sbMusicVolume.setProgress(dialog.volume);
                viewBinding.sbMusicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.MusicVolumeDialog.1.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b2) {
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (dialog.onVolumeChangeCallback != null) {
                            dialog.onVolumeChangeCallback.onVolumeChange(seekBar.getProgress());
                        }
                    }
                });
            }
        }).setMargin(16).setY(16).setGravity(80);
    }

    public MusicVolumeDialog setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public OnVolumeChangeCallback getOnVolumeChangeCallback() {
        return this.onVolumeChangeCallback;
    }

    public MusicVolumeDialog setOnVolumeChangeCallback(OnVolumeChangeCallback onVolumeChangeCallback) {
        this.onVolumeChangeCallback = onVolumeChangeCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "music_volume_dialog";
    }
}