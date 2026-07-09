package com.ltech.smarthome.view.dialog;

import android.content.DialogInterface;
import android.view.View;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogSensingDistanceSettingBinding;

/* loaded from: classes4.dex */
public class SensingDistanceDialog extends SmartDialog<DialogSensingDistanceSettingBinding> {
    private OnCancelCallback cancelCallback;
    private String confirmString;
    private int imageRes;
    private OnConfirmCallback mCallback;
    private int progress = 25;
    private String title;

    public interface OnCancelCallback {
        void onCancel();
    }

    public interface OnConfirmCallback {
        void onConfirmClick(SensingDistanceDialog dialog);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_sensing_distance_setting;
    }

    public static SensingDistanceDialog asDefault() {
        return (SensingDistanceDialog) new SensingDistanceDialog().setViewConverter(new SmartDialog.ViewConverter<DialogSensingDistanceSettingBinding, SensingDistanceDialog>() { // from class: com.ltech.smarthome.view.dialog.SensingDistanceDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(final DialogSensingDistanceSettingBinding viewBinding, final SensingDistanceDialog dialog) {
                viewBinding.seekBar.setProgress(dialog.progress);
                viewBinding.tvValue.setText(viewBinding.seekBar.getLeftSeekBar().getProgress() + "%");
                viewBinding.tvFinish.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.SensingDistanceDialog.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (dialog.mCallback != null) {
                            dialog.mCallback.onConfirmClick(dialog);
                        }
                        dialog.dismissDialog();
                    }
                });
                viewBinding.tvCancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.SensingDistanceDialog.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        dialog.dismissDialog();
                    }
                });
                viewBinding.seekBar.setOnRangeChangedListener(new OnRangeChangedListener(this) { // from class: com.ltech.smarthome.view.dialog.SensingDistanceDialog.1.3
                    @Override // com.jaygoo.widget.OnRangeChangedListener
                    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                    }

                    @Override // com.jaygoo.widget.OnRangeChangedListener
                    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                        if (leftValue == 0.0f) {
                            view.setProgress(25.0f);
                        }
                    }

                    @Override // com.jaygoo.widget.OnRangeChangedListener
                    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                        viewBinding.tvValue.setText(((int) view.getLeftSeekBar().getProgress()) + "%");
                        dialog.setProgress((int) view.getLeftSeekBar().getProgress());
                    }
                });
            }
        }).setMargin(16).setY(16).setGravity(80);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        OnCancelCallback onCancelCallback = this.cancelCallback;
        if (onCancelCallback != null) {
            onCancelCallback.onCancel();
        }
    }

    public SensingDistanceDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SensingDistanceDialog setProgress(int p) {
        this.progress = p;
        return this;
    }

    public int getProgress() {
        return this.progress;
    }

    public SensingDistanceDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SensingDistanceDialog setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public SensingDistanceDialog setCallback(OnConfirmCallback callback) {
        this.mCallback = callback;
        return this;
    }

    public SensingDistanceDialog setCancelCallback(OnCancelCallback callback) {
        this.cancelCallback = callback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "SensingDistanceDialog";
    }
}